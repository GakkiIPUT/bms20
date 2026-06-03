/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：Book.java
 * プログラムの説明：書籍情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年5月8日
 * 作成者：髙垣湧侑翔
*/

package bean;


/**
 * DTOクラス
 * データの受け渡し専用のクラス
 * */
public class Book {
	
	//書籍番号を格納する変数
    private String isbn;
    
	//タイトルを格納する変数
    private String title;
 
	//価格を格納する変数
    private int price;

    //引数なしコンストラクタ
    public Book(){
        this.isbn = null;
        this.title = null;
        this.price = 0;
    }

    /**
     * isbnのゲッターメソッド
     * @returnフィールド変数isbnで管理された値を返す
     */
    public String getIsbn(){ return isbn; }
    
    /**
     * isbnのセッターメソッド
     * @param引数に受け取った値をフィールド変数isbnに格納する
     */
    public void setIsbn(String isbn){ this.isbn = isbn; }

    /**
     * titleのゲッターメソッド
     * @returnフィールド変数titleで管理された値を返す
     */
    public String getTitle(){ return title; }
    
    /**
     * titleのセッターメソッド
     * @param 引数に受け取った値をフィールド変数titleに格納する
     */
    public void setTitle(String title){ this.title = title; }

    /**
     * priceのゲッターメソッド
     * @returnフィールド変数priceで管理された値を返す
     */
    public int getPrice(){ return price; }
    
    /**
     * priceのセッターメソッド
     * @param 引数に受け取った値をフィールド変数priceに格納する
     */
    public void setPrice(int price){ this.price = price; }
}
