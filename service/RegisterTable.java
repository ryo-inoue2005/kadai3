package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import common.ConvertData;
import common.DataBaseAcess;

/**
 * RegisterTablesクラス. <br>
 * RegisterTablesクラスは、データベース登録処理を行います。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class RegisterTable {

	/** データベースアクセスオブジェクト */
	private final DataBaseAcess dba = new DataBaseAcess();

	/**
	 * CSVに入っているおみくじをデータベースに登録します。
	 * 既に登録されているおみくじは登録されません
	 * 
	 * @throws ClassNotFoundException
	 *			クラス未発見の例外
	 * @throws SQLException
	 * 			SQL例外
	 * @throws IOException 
	 * 
	 */
	public void registerOmikuji() throws ClassNotFoundException, SQLException, IOException {

		// ファイルパスを指定
		final String FILE_PATH = "/Users/r_inoue/Documents/workspaces/uranai/src/unsei.csv";

		//CSVデータ読み込み
		File file = new File(FILE_PATH);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO omikuji (omikuji_code, unsei_code, negaigoto, akinai, gakumon, create_by, create_date) ");
			sql.append("SELECT ?, ");
			sql.append("(SELECT unsei_code "
					+ "FROM unseimaster "
					+ "WHERE unsei_name = ?), ");
			sql.append("?, ?, ?, 'Ryo.inoue', CURRENT_DATE ");
			sql.append("ON CONFLICT DO NOTHING; ");

			// SQL文をセット
			dba.setSql(sql.toString());

			// 一行目を読み込む
			String line = br.readLine();
			
			int omikujiCode = 1;

			// CSVの行数がなくなるまで実行し、一行ずつデータベースに登録する
			while (line != null) {

				String[] csvData = line.split(",");

				// CSVから受け取ったデータをセットし、データベースに登録
				dba.setData(1, omikujiCode);
				dba.setData(2, csvData[0]);
				dba.setData(3, csvData[1]);
				dba.setData(4, csvData[2]);
				dba.setData(5, csvData[3]);
				
				// 登録できたらおみくじコードをインクリメントする
				if(dba.update() == 1) {
					omikujiCode++;
				}

				// 初期化
				csvData = null;

				// 次の行に移動
				line = br.readLine();
			}

		} finally {
			// クローズ処理
			dba.close();
			br.close();
		}
	}

	/**
	 * おみくじの結果をデータベースに登録します。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * @throws ParseException
	 * 			変換時の例外
	 * 
	 * @param birthday
	 * 			誕生日
	 * @param omikujiCode
	 * 			おみくじコード
	 * 
	 * @return 登録件数
	 */
	public int registerResult(String birthDay, int omikujiCode)
			throws ClassNotFoundException, SQLException, ParseException {

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO result (uranai_date, birthday, omikuji_code, create_by, create_date) ");
			sql.append("VALUES (CURRENT_DATE, ?, ");
			sql.append("(SELECT omikuji_code FROM omikuji WHERE omikuji_code = ? ), ");
			sql.append("'Ryo.inoue', CURRENT_DATE) ");

			// SQL文セット
			dba.setSql(sql.toString());
			dba.setData(1, ConvertData.toSqlDate(birthDay));
			dba.setData(2, omikujiCode);

			// UPDATE処理実行
			return dba.update();

		} finally {
			// クローズ処理
			dba.close();
		}
	}
}
