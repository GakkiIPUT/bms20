/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：Book.java
 * プログラムの説明：書籍情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */

package bean;


/**
 * 書籍情報を保持するDTOクラスです。
 */
public class Book {
	
	//書籍番号を格納する変数
    private String isbn;
    
	//タイトルを格納する変数
    private String title;
 
	//価格を格納する変数
    private int price;

      /**
       * 引数なしコンストラクタです。
       */
      public Book(){
        this.isbn = null;
        this.title = null;
        this.price = 0;
    }

      /**
       * ISBNを取得します。
       * @return ISBN
       */
    public String getIsbn(){ return isbn; }
    
      /**
       * ISBNを設定します。
       * @param isbn ISBN
       */
    public void setIsbn(String isbn){ this.isbn = isbn; }

      /**
       * タイトルを取得します。
       * @return タイトル
       */
    public String getTitle(){ return title; }
    
      /**
       * タイトルを設定します。
       * @param title タイトル
       */
    public void setTitle(String title){ this.title = title; }

      /**
       * 価格を取得します。
       * @return 価格
       */
    public int getPrice(){ return price; }
    
      /**
       * 価格を設定します。
       * @param price 価格
       */
    public void setPrice(int price){ this.price = price; }
}
