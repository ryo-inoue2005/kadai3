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
public class RegisterOmikuji {

	/** データベースアクセスオブジェクト */
	private final DataBaseAcess dba = new DataBaseAcess();

	/**
	 * おみくじリストに入っているおみくじをデータベースに登録します。
	 * 既に登録されているおみくじは登録されません
	 * @return 
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * 
	 */
	public void registerOmikuji(List<Omikuji> omikujiBox) throws ClassNotFoundException, SQLException {

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO omikuji (unsei_code, negaigoto, akinai, gakumon, create_by, create_date) ");
			sql.append("SELECT (SELECT unsei_code ");
			sql.append("FROM unseimaster ");
			sql.append("WHERE unsei_name = ?), ?, ?, ?, 'Ryo.inoue', CURRENT_DATE ");

			sql.append("WHERE NOT EXISTS ( ");
			sql.append("SELECT omikuji_code ");
			sql.append("FROM omikuji ");
			sql.append("WHERE negaigoto = ? ");
			sql.append("AND akinai = ? ");
			sql.append("AND gakumon = ?);");

			//SQL文をセット
			dba.setSql(sql.toString());

			for (Omikuji omikuji : omikujiBox) {
				dba.setData(1, omikuji.getUnsei());
				dba.setData(2, omikuji.getNegaigoto());
				dba.setData(3, omikuji.getAkinai());
				dba.setData(4, omikuji.getGakumon());
				dba.setData(5, omikuji.getNegaigoto());
				dba.setData(6, omikuji.getAkinai());
				dba.setData(7, omikuji.getGakumon());
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
	 * おみくじの結果をデータベースに登録します。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * @throws ParseException
	 * 			変換時の例外
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
			return dba.updateSql();

		} finally {
			// クローズ処理
			dba.close();
		}
	}
}
