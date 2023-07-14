package main;

import java.util.Random;
import java.util.Scanner;

import common.InputCheck;
import omikuji.Fortune;
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

		Scanner scanner = new Scanner(System.in);

		try {
			// データベースから登録、取得するオブジェクトを生成
			RegisterTable registerTable = new RegisterTable();

			//おみくじを登録
			registerTable.registerOmikuji();

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
			GetTable getTable = new GetTable();

			// resultテーブルから同一日と同一誕生日のおみくじコードがないか確認する
			int omikujiCode = getTable.getResult(birthDay);

			// resultテーブルに存在しなかったら、おみくじをDBから引き、resultテーブルに結果を登録する
			if (omikujiCode == 0) {

				Random random = new Random();
				int min = 1;
				
				// データベースからおみくじの数を取得し、ランダムにおみくじコードを発行する
				omikujiCode = random.nextInt(min, getTable.getMaxOmikujiCode() + min);

				// resultテーブルに登録する
				int result = registerTable.registerResult(omikujiCode, birthDay);

				// 登録件数が1件以外だったら強制終了させる
				if (result != 1) {
					System.out.println("登録エラー");
					return;
				}
			}

			// おみくじコードでおみくじを引く
			Fortune fortune = getTable.drawOmikuji(omikujiCode);

			// 結果をコンソールに表示
			System.out.println(fortune.disp());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				scanner.close();
		}
	}
}
