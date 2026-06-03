/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：User.java
 * プログラムの説明：ユーザー情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */

package bean;


/**
 * ユーザー情報を保持するDTOクラスです。
 */
public class User {
	
	//ユーザーIDを格納する変数
    private String userid;
    
	//パスワードを格納する変数
    private String password;
 
	//メールアドレスを格納する変数
    private String email;

	//権限を格納する変数（1：一般ユーザー、2：管理者）
    private String authority;

    /**
     * 引数なしコンストラクタです。
     */
    public User(){
        this.userid = null;
        this.password = null;
        this.email = null;
        this.authority = null;
    }

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
     * パスワードを取得します。
     * @return パスワード
     */
    public String getPassword(){ return password; }
    
    /**
     * パスワードを設定します。
     * @param password パスワード
     */
    public void setPassword(String password){ this.password = password; }
   
    /**
     * メールアドレスを取得します。
     * @return メールアドレス
     */
    public String getEmail(){ return email; }
    
    /**
     * メールアドレスを設定します。
     * @param email メールアドレス
     */
    public void setEmail(String email){ this.email = email; }
    
    /**
     * 権限を取得します。
     * @return 権限
     */
    public String getAuthority(){ return authority; }
    
    /**
     * 権限を設定します。
     * @param authority 権限（1：一般ユーザー、2：管理者）
     */
    public void setAuthority(String authority){ this.authority = authority; }
}


