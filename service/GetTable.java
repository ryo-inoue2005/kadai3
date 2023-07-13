package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.ConvertData;
import common.DataBaseAcess;
import omikuji.Omikuji;
import omikuji.OmikujiFactory;

/**
 * GetOmikujiクラス. <br>
 * GetOmikujiクラスは、データベースからテーブル情報を取得します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class GetTable {

	/** データベースアクセスオブジェクト */
	DataBaseAcess dba = new DataBaseAcess();

	/**
	 * resultテーブルに保存されているおみくじ結果を取得します。
	 * 同一誕生日、同一日ならば、おみくじ結果を返す。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * 
	 * @return Omikuji
	 * 			おみくじオブジェクト
	 * @return null
	 * 			取得できない場合
	 */
	public Omikuji getResult(String birthDay) throws ClassNotFoundException, SQLException {

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();

			sql.append("SELECT unsei_name, negaigoto, akinai, gakumon ");
			sql.append("FROM unseimaster AS u ");
			sql.append("INNER JOIN omikuji AS o ON u.unsei_code = o.unsei_code ");
			sql.append("INNER JOIN result AS r ON o.omikuji_code = r.omikuji_code ");
			sql.append("WHERE r.create_date = CURRENT_DATE ");
			sql.append("AND r.birthday = ? ");

			// SQL文をセット
			dba.setSql(sql.toString());

			// 文字列をDate型に変換してセット
			dba.setData(1, ConvertData.toSqlDate(birthDay));

			// SQLを実行
			ResultSet resultSet = dba.select();

			Omikuji omikuji = null;

			if (resultSet.next()) {
				// 運勢名を元におみくじオブジェクトを生成
				omikuji = OmikujiFactory.create(resultSet.getString("unsei_name"));
				// おみくじの内容をセット
				omikuji.setUnsei(resultSet.getString("negaigoto"), resultSet.getString("akinai"),
						resultSet.getString("gakumon"));

				return omikuji;
			}
			return null;

		} finally {
			// データベース切断
			dba.close();
		}

	}

	/**
	 * データベースからおみくじを引きます。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * 
	 * @return Omikuji
	 * 			おみくじオブジェクト
	 * @return null
	 * 			取得できない場合
	 */
	public Omikuji drawOmikuji(int random) throws ClassNotFoundException, SQLException {

		try {
			// データベース接続
			dba.open();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT u.unsei_name, o.omikuji_code, o.negaigoto, o.akinai, o.gakumon ");
			sql.append("FROM unseimaster AS u ");
			sql.append("INNER JOIN omikuji AS o ON u.unsei_code = o.unsei_code ");
			sql.append("WHERE o.omikuji_code = ?");

			dba.setSql(sql.toString());

			dba.setData(1, random);

			ResultSet resultSet = dba.select();

			Omikuji omikuji = null;

			if (resultSet.next()) {
				// 運勢を元におみくじオブジェクトを生成
				omikuji = OmikujiFactory.create(resultSet.getString("unsei_name"));
				// 運勢をセット
				omikuji.setUnsei(resultSet.getString("negaigoto"), resultSet.getString("akinai"),
						resultSet.getString("gakumon"));
				// おみくじコードをセット
				omikuji.setOmikujiCode(resultSet.getInt("omikuji_code"));
				return omikuji;
			}
			return null;

		} finally {
			// データベース切断
			dba.close();
		}
	}

	/**
	 * データベースからおみくじの数を取得します。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 * 
	 * @return おみくじの数
	 * 
	 * @return 0
	 * 			おみくじがない場合
	 */
	public int getMaxOmikuji() throws ClassNotFoundException, SQLException {
		try {
			dba.open();
			dba.setSql("SELECT MAX(omikuji_code) AS MAX FROM omikuji");
			ResultSet resultSet = dba.select();

			if (resultSet.next()) {
				return resultSet.getInt("MAX");
			}
			return 0;
		} finally {
			dba.close();
		}
	}
}
