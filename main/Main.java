package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import common.InputCheck;
import omikuji.Omikuji;
import service.GetTable;
import service.RegisterTable;

/**
 * Mainクラス. <br>
 * Mainクラスは、おみくじを実行します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class Main {

	/**
	 * おみくじを実行します
	 */
	public static void main(String[] args) {

		ResultSet resultSet = null;
		Scanner scanner = new Scanner(System.in);

		try {
			// データベースから登録、取得するオブジェクトを生成
			RegisterTable registerTables = new RegisterTable();

			//おみくじを登録
			registerTables.registerOmikuji();

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

			// データベースから取得用のオブジェクト
			GetTable getOmikuji = new GetTable();

			// resultテーブルから同一日と同一誕生日の結果が無いか確認する
			Omikuji omikuji = getOmikuji.getResult(birthDay);

			// resultテーブルに存在しなかったら、おみくじをDBから引き、resultテーブルに結果を登録する
			if (omikuji == null) {
				
				Random random = new Random();

				// データベースからおみくじの数を取得し、ランダムにデータベースからおみくじを取得する
				omikuji = getOmikuji.drawOmikuji(random.nextInt(getOmikuji.getMaxOmikuji()));

				// resultテーブルに登録する
				int result = registerTables.registerResult(birthDay, omikuji.getOmikujiCode());

				// 登録件数が1件以外だったら強制終了させる
				if (result != 1) {
					System.out.println("登録エラー");
					return;
				}
			}

			// 結果をコンソールに表示
			System.out.println(omikuji.disp());

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
