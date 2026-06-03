/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：Order.java
 * プログラムの説明：オーダー情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年5月15日
 * 作成者：髙垣湧侑翔
*/

package bean;

/**
 * OrderのDTOクラス
 * データの受け渡し専用のクラス
 * */
public class Order {
	
	//注文Noを格納する変数
	private int orderno;
	
	//ユーザーIDを格納する変数
    private String userid;
    
	//ISBNを格納する変数
    private String isbn;
 
	//数量を格納する変数
    private int quantity;

	//購入日付を格納する変数
    private String date;
    
    //引数なしコンストラクタ
    public Order(){
    	this.orderno = 0;
        this.userid = null;
        this.isbn = null;
        this.quantity = 0;;
        this.date = null;
    }

    /**
     * ordernoのゲッターメソッド
     * @returnフィールド変数ordernoで管理された値を返す
     */
    public int getOrderno() {return orderno;}
    
    /**
     * ordernoのセッターメソッド
     * @param フィールド変数ordernoで管理された値を返す
     */
   public void setOrderno(int orderno) {this.orderno = orderno;}
    
    /**
     * useridのゲッターメソッド
     * @returnフィールド変数useridで管理された値を返す
     */
    public String getUserid(){ return userid; }
    
    /**
     * useridのセッターメソッド
     * @param引数に受け取った値をフィールド変数useridに格納する
     */
    public void setUserid(String userid){ this.userid = userid; }

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
     * emailのゲッターメソッド
     * @returnフィールド変数emailで管理された値を返す
     */
    public int getQuantity(){ return quantity; }
    
    /**
     * quantityのセッターメソッド
     * @param 引数に受け取った値をフィールド変数quantityに格納する
     */
    public void setQuantity(int quantity){ this.quantity = quantity; }
    
    /**
     * dateのゲッターメソッド
     * @returnフィールド変数dateで管理された値を返す
     */
    public String getDate(){ return date; }
    
    /**
     * dateのセッターメソッド
     * @param 引数に受け取った値をフィールド変数dateに格納する
     */
    public void setDate(String date){ this.date = date; }
}


