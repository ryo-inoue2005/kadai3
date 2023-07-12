package omikuji;

/**
 * Omikujiクラス. <br>
 * Omikujiクラスは、おみくじ周りを管理します。
 *
 * @author Ryo.inoue
 * @version 1.00
 */
public abstract class Omikuji implements Fortune {

	/** 運勢を表します */
	protected String unsei;
	/** 願い事を表します */
	protected String negaigoto;
	/** 商いを表します */
	protected String akinai;
	/** 学問を表します */
	protected String gakumon;
	/** おみくじコードを表します */
	protected int omikujiCode;

	/**
	 * 運勢の取得
	 */
	public String getUnsei() {
		return unsei;
	}

	/**
	 * 願い事の取得
	 */
	public String getNegaigoto() {
		return negaigoto;
	}

	/**
	 * 商いの取得
	 */
	public String getAkinai() {
		return akinai;
	}

	/**
	 * 学問の取得
	 */
	public String getGakumon() {
		return gakumon;
	}

	/**
	 * おみくじコードの取得
	 */
	public int getOmikujiCode() {
		return omikujiCode;
	}

	/**
	 * 運勢をセットします。	
	 * 
	 * @param negaigoto
	 * 			願い事
	 * @param akinai
	 * 			商い
	 * @param gakumon
	 * 			学問
	 */
	public abstract void setUnsei(String negaigoto, String akinai, String gakumon);

	/**
	 * おみくじコードをセットします。	
	 * 
	 * @param code
	 * 			おみくじコード
	 */
	public void setOmikujiCode(int code) {
		this.omikujiCode = code;
	}

	/**
	 * @see Fortune#disp()
	 */
	@Override
	public String disp() {

		return String.format(DISP_STR + "\n" + "願い事：%s \n" + "商い：%s \n" + "学問：%s \n",
				this.unsei, this.negaigoto, this.akinai, this.gakumon);
	}
}
