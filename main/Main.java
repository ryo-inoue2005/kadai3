package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import common.CsvRead;
import common.InputCheck;
import omikuji.Fortune;
import omikuji.Omikuji;
import service.GetTablesInfo;
import service.RegisterTables;

/**
 * Mainクラス. <br>
 * Mainクラスは、おみくじを実行します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class Main {

	/**
	 * mainメソッド
	 * <br>
	 * おみくじを実行します
	 */
	public static void main(String[] args) {

		ResultSet resultSet = null;
		Scanner scanner = new Scanner(System.in);

		// 行数を指定
		final int LINES = 50;

		try {

			// データベースから登録、取得するオブジェクトを生成
			RegisterTables registerTables = new RegisterTables();
			GetTablesInfo gettable = new GetTablesInfo();

			// おみくじリストを取得
			List<Omikuji> omikujiList = CsvRead.getUnsei();

			// 50行ではなかったら登録処理を実行
			if (omikujiList != null && gettable.getLines() != LINES) {
				registerTables.registerOmikuji(omikujiList);
			}

			// チェック用フラグ変数
			boolean check = true;

			// 誕生日入力用変数
			String birthDay = null;

			// 正しい入力がされるまで繰り返す
			while (check) {
				System.out.println("誕生日を入力してください(yyyyMMddで入力)");
				birthDay = scanner.next();
				check = InputCheck.isInputNg(birthDay);
			}

			// データベースからおみくじコードを全取得する
			List<Integer> omikujiCodeList = gettable.getOmikujiList();

			// 現在の日付のミリ秒を取得する
			Long longToday = System.currentTimeMillis() / (1000 * 60 * 60 * 24);

			// 入力された誕生日と今日の日付を元にランダムオブジェクトを生成
			Random random = new Random(longToday + Integer.parseInt(birthDay));

			// おみくじから一枚引く
			int draw = omikujiCodeList.get(random.nextInt(omikujiCodeList.size()));

			// 取り出したおみくじをデータベースに登録する
			registerTables.registerResult(birthDay, draw);

			// Fortune型のオブジェクトに格納 -1で調整
			Fortune fortune = omikujiList.get(draw - 1);

			// おみくじ結果を表示
			System.out.println(fortune.disp());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// クローズ処理
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				scanner.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
