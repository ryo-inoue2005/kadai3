package omikuji;

/**
 * OmikujiFactoryクラス. <br>
 * OmikujiFactoryクラスは、おみくじオブジェクトを生成します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public class OmikujiFactory {

	/**
	 * 運勢を元におみくじオブジェクトを生成して返します。
	 * 
	 * @return おみくじオブジェクト
	 */
	public static Omikuji create(String unsei) {

		// おみくじ初期化
		Omikuji omikuji = null;

		switch (unsei) {

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

		default:
			System.out.println("不正な運勢です");
			return null;
		}
		return omikuji;
	}
}
