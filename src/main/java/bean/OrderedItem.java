/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：OrderedItem.java
 * プログラムの説明：注文済み商品情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */

package bean;

/**
 * 注文済み商品の情報を保持するDTOクラスです。
 */
public class OrderedItem {

	//ユーザーIDを格納する変数
	private String userid;

	//titleを格納する変数
	private String title;

	private int quantity;

	//購入日付を格納する変数
	private String date;

	/**
	 * 引数なしコンストラクタです。
	 */
	public OrderedItem() {
		this.userid = null;
		this.title = null;
		this.quantity = 0;
		this.date = null;
	}

	/**
	 * ユーザーIDを取得します。
	 * @return ユーザーID
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * ユーザーIDを設定します。
	 * @param userid ユーザーID
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * タイトルを取得します。
	 * @return タイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * タイトルを設定します。
	 * @param title タイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 数量を取得します。
	 * @return 数量
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * 数量を設定します。
	 * @param quantity 数量
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 購入日付を取得します。
	 * @return 購入日付
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 購入日付を設定します。
	 * @param date 購入日付
	 */
	public void setDate(String date) {
		this.date = date;
	}

}
