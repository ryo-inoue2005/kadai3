package common;

import java.text.SimpleDateFormat;

/**
 * InputCheckクラス. <br>
 * InputCheckクラスは、入力された値をチェックします。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class InputCheck {

	/**
	 * 入力された日付が正しい日付どうかチェックします。
	 * 
	 * @return チェック結果
	 */
	public static boolean isInputNg(String input) {

		// 数値がどうかチェック
		if (input == null || !input.matches("^[0-9]+$|-[0-9]+$")) {
			System.out.println("数値で入力してください");
			return true;
		}

		String fs = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		// 正しい形式かチェック
		try {
			fs = df.format(df.parse(input));
		} catch (Exception e) {
			System.out.println("指定された入力形式ではありません");
			return true;
		}

		// 存在する日付かチェック
		if (!input.equals(fs)) {
			System.out.println("日付が存在しません");
			return true;
		}

		return false;
	}
}
