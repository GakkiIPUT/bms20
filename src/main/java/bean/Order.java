/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：Order.java
 * プログラムの説明：オーダー情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */

package bean;

/**
 * 注文情報を保持するDTOクラスです。
 */
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

    /**
     * 引数なしコンストラクタです。
     */
    public Order(){
    	this.orderno = 0;
        this.userid = null;
        this.isbn = null;
        this.quantity = 0;;
        this.date = null;
    }

    /**
     * 注文番号を取得します。
     * @return 注文番号
     */
    public int getOrderno() {return orderno;}
    
    /**
     * 注文番号を設定します。
     * @param orderno 注文番号
     */
   public void setOrderno(int orderno) {this.orderno = orderno;}
    
    /**
     * ユーザーIDを取得します。
     * @return ユーザーID
     */
    public String getUserid(){ return userid; }
    
    /**
     * ユーザーIDを設定します。
     * @param userid ユーザーID
     */
    public void setUserid(String userid){ this.userid = userid; }

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
     * 数量を取得します。
     * @return 数量
     */
    public int getQuantity(){ return quantity; }
    
    /**
     * 数量を設定します。
     * @param quantity 数量
     */
    public void setQuantity(int quantity){ this.quantity = quantity; }
    
    /**
     * 購入日付を取得します。
     * @return 購入日付
     */
    public String getDate(){ return date; }
    
    /**
     * 購入日付を設定します。
     * @param date 購入日付
     */
    public void setDate(String date){ this.date = date; }
}


