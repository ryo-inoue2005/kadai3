package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DataBaseAcess;

/**
 * GetTablesInfoクラス. <br>
 * GetTablesInfoクラスは、データベースからテーブル情報を取得します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class GetTablesInfo {

	/** データベースアクセスオブジェクト */
	DataBaseAcess dba = new DataBaseAcess();

	/**
	 * データベースに保存されているおみくじの行数を取得します。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 */
	public int getLines() throws ClassNotFoundException, SQLException {

		ResultSet resultSet = null;

		try {
			// データベース接続
			dba.open();
			// SQL文をセット
			dba.setSql("SELECT COUNT(omikuji_code) AS LINES FROM omikuji");
			// SQLを実行
			resultSet = dba.selectSql();

			// 結果を取り出す
			if (resultSet.next()) {
				return resultSet.getInt("LINES");
			}

			return 0;

		} finally {
			// クローズ処理
			if (resultSet != null) {
				resultSet.close();
			}
			dba.close();
		}

	}

	/**
	 * データベースからおみくじコードを取得します。
	 * 
	 * @throws ClassNotFoundException
	 *			ファイル未発見例外
	 * @throws SQLException
	 * 			SQL例外
	 */
	public List<Integer> getOmikujiList() throws ClassNotFoundException, SQLException {

		ResultSet resultSet = null;

		try {
			// データベース接続
			dba.open();
			// SQL文をセット
			dba.setSql("SELECT omikuji_code FROM omikuji");
			// SQLを実行
			resultSet = dba.selectSql();

			List<Integer> omikujiCodeList = new ArrayList<>();

			// おみくじコードをリストに追加
			while (resultSet.next()) {
				omikujiCodeList.add(resultSet.getInt("omikuji_code"));
			}

			return omikujiCodeList;

		} finally {
			// クローズ処理
			if (resultSet != null) {
				resultSet.close();
			}
			dba.close();
		}
	}
}
