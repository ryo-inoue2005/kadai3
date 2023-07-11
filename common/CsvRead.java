package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import omikuji.Chukichi;
import omikuji.Daikichi;
import omikuji.Kichi;
import omikuji.Kyo;
import omikuji.Omikuji;
import omikuji.Shokichi;
import omikuji.Suekichi;

/**
 * CsvReadクラス. <br>
 * CsvReadクラスは、csvまわりの作業を管理します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class CsvRead {

	/**
	 * 読み込んだcsvを一行ごとデータを分解し、おみくじオブジェクト格納したリストを返します。
	 * 
	 * @throws IOException
	 *			ファイル未発見例外
	 * @return omikujiList
	 * 			読み込んだcsvのListを返す
	 */
	public static List<Omikuji> getUnsei() throws IOException {

		// ファイルパスを指定
		final String FILE_PATH = "/Users/r_inoue/Documents/workspaces/uranai/src/unsei.csv";

		//CSVデータ読み込み
		File file = new File(FILE_PATH);
		BufferedReader br = new BufferedReader(new FileReader(file));

		try {
			List<Omikuji> omikujiList = new ArrayList<>();

			// 一行目を読み込む
			String line = br.readLine();

			// 行数がなくなるまで実行し、一行ずつ要素ごとに分解しmapに格納
			while (line != null) {
				
				String[] csvData = line.split(",");
				
				// おみくじ初期化
				Omikuji omikuji = null;

				switch (csvData[0]) {

				case "大吉":
					omikuji = new Daikichi();
					break;

				case "中吉":
					omikuji = new Chukichi();
					break;

				case "吉":
					omikuji = new Kichi();
					break;

				case "小吉":
					omikuji = new Shokichi();
					break;

				case "末吉":
					omikuji = new Suekichi();
					break;

				case "凶":
					omikuji = new Kyo();
					break;

				//一行でもCSVに不正があった場合、強制終了させる
				default:
					System.out.println("不正なおみくじがあります。");
					return null;
				}
				
				// 運勢をセットする
				omikuji.setUnsei(csvData[1], csvData[2], csvData[3]);

				// リストに追加
				omikujiList.add(omikuji);

				// 初期化
				csvData = null;

				// 次の行に移動
				line = br.readLine();
			}
			return omikujiList;
			
		} finally {
			br.close();
		}
	}
}