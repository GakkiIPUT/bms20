/*
 * プロジェクト名：書籍管理システムWeb版Ver2.0
 * プログラム名：OrderedItem.java
 * プログラムの説明：オーダー情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年5月15日
 * 作成者：髙垣湧侑翔
*/

package bean;

public class OrderedItem {

	//ユーザーIDを格納する変数
	private String userid;

	//titleを格納する変数
	private String title;

	//購入日付を格納する変数
	private String date;

	//引数なしコンストラクタ
	public OrderedItem() {
		this.userid = null;
		this.title = null;
		this.date = null;
	}

	/**
	 * useridのゲッターメソッド
	 * @returnフィールド変数useridで管理された値を返す
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * useridのセッターメソッド
	 * @param引数に受け取った値をフィールド変数useridに格納する
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * titleのゲッターメソッド
	 * @returnフィールド変数titleで管理された値を返す
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * titleのセッターメソッド
	 * @param引数に受け取った値をフィールド変数titleに格納する
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * dateのゲッターメソッド
	 * @returnフィールド変数dateで管理された値を返す
	 */
	public String getDate() {
		return date;
	}

	/**
	 * dateのセッターメソッド
	 * @param 引数に受け取った値をフィールド変数dateに格納する
	 */
	public void setDate(String date) {
		this.date = date;
	}

}
