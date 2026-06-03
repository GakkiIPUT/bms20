/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：Sale.java
 * プログラムの説明：売上集計情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */

package bean;

/**
 * 売上集計情報を保持するDTOクラスです。
 */
public class Sale {
	private String isbn;
	private String title;
	private int price;
	private int quantity;
    private int amount; // 売上げ小計（単価×数量）


	/**
	 * 引数なしコンストラクタです。
	 */
	public Sale() {
		this.isbn = null;
		this.title = null;
		this.price = 0;
		this.quantity = 0;
		this.amount = 0;
		
	}

	/**
	 * 書籍情報と数量から売上情報を生成します。
	 *
	 * @param book 書籍情報
	 * @param quantity 数量
	 */
	public Sale(Book book, int quantity) {
		this.isbn = book.getIsbn();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.quantity = quantity;
	}

	/**
	 * ISBNを取得します。
	 * @return ISBN
	 */
	public String getIsbn() { return isbn; }
	/**
	 * ISBNを設定します。
	 * @param isbn ISBN
	 */
	public void setIsbn(String isbn) { this.isbn = isbn; }

	/**
	 * タイトルを取得します。
	 * @return タイトル
	 */
	public String getTitle() { return title; }
	/**
	 * タイトルを設定します。
	 * @param title タイトル
	 */
	public void setTitle(String title) { this.title = title; }

	/**
	 * 価格を取得します。
	 * @return 価格
	 */
	public int getPrice() { return price; }
	/**
	 * 価格を設定します。
	 * @param price 価格
	 */
	public void setPrice(int price) { this.price = price; }

	/**
	 * 数量を取得します。
	 * @return 数量
	 */
	public int getQuantity() { return quantity; }
	/**
	 * 数量を設定します。
	 * @param quantity 数量
	 */
	public void setQuantity(int quantity) { this.quantity = quantity; }

	/**
	 * 売上小計を取得します。
	 * @return 売上小計
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * 売上小計を設定します。
	 * @param amount 売上小計
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
}