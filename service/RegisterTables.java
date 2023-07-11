package service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import common.ConvertData;
import common.DataBaseAcess;
import omikuji.Omikuji;

/**
 * RegisterTablesクラス. <br>
 * RegisterTablesクラスは、データベース登録処理を行います。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class RegisterTables {

	/** データベースアクセスオブジェクト */
	private final DataBaseAcess dba = new DataBaseAcess();

	/**
	 * おみくじをデータベースに登録するメソッド
	 * <br>
	 * おみくじリストに入っているおみくじをデータベースに登録します。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 */
	public void registerOmikuji(List<Omikuji> omikujiBox) throws ClassNotFoundException, SQLException {

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO omikuji (unsei_code, negaigoto, akinai, gakumon, create_by, create_date) ");
			sql.append("SELECT ("
					+ "	SELECT unsei_code "
					+ "	FROM unseimaster "
					+ "	WHERE unsei_name = ?), ?, ?, ?, 'Ryo.inoue', CURRENT_DATE ");

			// SQL文セット
			dba.setSql(sql.toString());

			// おみくじを一枚ずつ取り出してSQL文を作成し、バッチに追加
			for (Omikuji omikuji : omikujiBox) {
				dba.setData(1, omikuji.getUnsei());
				dba.setData(2, omikuji.getNegaigoto());
				dba.setData(3, omikuji.getAkinai());
				dba.setData(4, omikuji.getGakumon());
				dba.addBatch();
			}
			// バッチ処理実行
			dba.executeBatch();
		} finally {
			// クローズ処理
			dba.close();
		}
	}

	/**
	 * おみくじの結果をデータベースに登録するメソッド
	 * <br>
	 * おみくじの結果をデータベースに登録します。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * @throws ParseException
	 * 			変換時の例外
	 */
	public void registerResult(String birthDay, int omikujiCode)
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
			dba.updateSql();

		} finally {
			// クローズ処理
			dba.close();
		}
	}
}
