package common;

import java.sql.Date;

/**
 * ConvertDataクラス. <br>
 * ConvertDataクラスは、与えられた値を変換します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class ConvertData {

	/**
	 * 与えられた文字列をSQL.DATE型に変換します。
	 * 
	 * @param dateStr
	 * 			yyyyMMddで入力された文字列
	 * 
	 * @return SQL.DATE型に変換された値
	 */
	public static Date toSqlDate(String dateStr) {

		// -を挿入する場所を設定
		final int yearIdx = "yyyy".length();
		final int yearMonthIdx = "yyyyMM".length();

		// -を挿入
		StringBuilder sb = new StringBuilder(dateStr);
		sb.insert(yearMonthIdx, '-');
		sb.insert(yearIdx, '-');

		// SQL.DATE型に変換
		return Date.valueOf(sb.toString());
	}

	/**
	 * 与えられた数値を小数点以下に変換します。
	 * 
	 * @param seed
	 * 			SQLのROUND()に渡すSEED値
	 * 
	 * @return 
	 * 			小数点以下に変換された値(0.1234)
	 */
	public static double toZeroPoint(long seed) {

		// 倍数を設定
		double multiple = 1;

		// 長さ分、倍数を設定する
		for (int i = 1; i <= String.valueOf(seed).length(); i++) {
			multiple *= 10;
		}
		return seed / multiple;
	}

}
