/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：User.java
 * プログラムの説明：ユーザー情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年5月15日
 * 作成者：髙垣湧侑翔
*/

package bean;


/**
 * DTOクラス
 * データの受け渡し専用のクラス
 * */
public class User {
	
	//ユーザーIDを格納する変数
    private String userid;
    
	//パスワードを格納する変数
    private String password;
 
	//メールアドレスを格納する変数
    private String email;

	//権限を格納する変数（1：一般ユーザー、2：管理者）
    private String authority;
    
    //引数なしコンストラクタ
    public User(){
        this.userid = null;
        this.password = null;
        this.email = null;
        this.authority = null;
    }

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
     * passwordのゲッターメソッド
     * @returnフィールド変数passwordで管理された値を返す
     */
    public String getPassword(){ return password; }
    
    /**
     * passwordのセッターメソッド
     * @param 引数に受け取った値をフィールド変数passwordに格納する
     */
    public void setPassword(String password){ this.password = password; }
   
    /**
     * emailのゲッターメソッド
     * @returnフィールド変数emailで管理された値を返す
     */
    public String getEmail(){ return email; }
    
    /**
     * emailのセッターメソッド
     * @param 引数に受け取った値をフィールド変数emailに格納する
     */
    public void setEmail(String email){ this.email = email; }
    
    /**
     * authorityのゲッターメソッド
     * @returnフィールド変数authorityで管理された値を返す
     */
    public String getAuthority(){ return authority; }
    
    /**
     * authorityのセッターメソッド(権限（1：一般ユーザー、2：管理者）)
     * @param 引数に受け取った値をフィールド変数emailに格納する
     */
    public void setAuthority(String authority){ this.authority = authority; }
}


