/*
 * プロジェクト名：書籍管理システムWeb版Ver1.0
 * プログラム名：BookDAO.java
 * プログラムの説明：bookinfoテーブルに対する検索・登録・更新・削除のSQL実行を担当する。
 * 作成日：2026年5月12日
 * 更新日：2026年5月15日
 * 作成者：髙垣湧侑翔
*/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Book;

/**
 * データベース（mybookdb）のbookinfoテーブル
 * に対するアクセス処理（CRUD）を担当するDAOクラスです。
 * SQLの実行と、取得した結果セットのDTO（Bookクラス）への
 * マッピングを行います。
 */
public class BookDAO {

	// 接続用の情報をフィールドに定数として定義
	private static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost/mybookdb";
	private static String USER = "root";
	private static String PASSWD = "root123";

	/**
	 * データベースへのコネクションを取得します。
	 * @return 確立されたデータベース接続（Connectionオブジェクト）
	 * @throws IllegalStateException JDBCドライバーのロードや接続に失敗した場合にスローされます
	 */
	private static Connection getConnection() {
		try {
			// JDBCドライバーをメモリにロードする
			Class.forName(RDB_DRIVE);
			// URL、ユーザー名、パスワードを使用してデータベースへの接続を確立する
			Connection con = DriverManager.getConnection(URL, USER, PASSWD);
			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	* bookinfoテーブルから全書籍情報を取得し、ISBNの昇順で返します。
	* @return 検索された全書籍情報を含むArrayList
	* @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	*/
	public ArrayList<Book> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		String sql = "SELECT isbn, title, price FROM bookinfo ORDER BY isbn";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return bookList;
	}

	/**
	 * 引数で受け取った書籍情報をデータベースに新規登録します。
	 * @param book 登録する書籍情報が格納されたBookオブジェクト
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public void insert(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO bookinfo VALUES(?,?,?)";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			//パラメータに値をセット
			pstmt.setString(1, book.getIsbn());
			pstmt.setString(2, book.getTitle());
			pstmt.setInt(3, book.getPrice());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new IllegalStateException(e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

	/**
	 * 引数で指定されたISBNに合致する書籍情報をデータベースから検索します。
	 * @param isbn 検索対象のISBN番号
	 * @return 検索結果が格納されたBookオブジェクト（見つからない場合は初期状態のBookオブジェクト）
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public Book selectByIsbn(String isbn) {
		Connection con = null;
		PreparedStatement pstmt = null;
		// 戻り値用のBookインスタンスを生成（初期状態）
		Book book = new Book();
		String sql = "SELECT isbn, title, price FROM bookinfo WHERE isbn = ?";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			// SELECT文を組み立てて実行
			ResultSet rs = pstmt.executeQuery();

			// 結果セットからデータが存在するか確認
			if (rs.next()) {
				// データが存在する場合、DTOに値をセット
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
			}
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new IllegalStateException(e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		// 検索結果が格納されたBookオブジェクトを返す
		return book;
	}

	/**
	 * 引数で指定されたISBNの書籍情報をデータベースから削除します。
	 * @param isbn 削除対象のISBN番号
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public void delete(String isbn) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM bookinfo WHERE isbn = ?";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new IllegalStateException(e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

	/**
	 * 引数で受け取った書籍情報で、データベースの該当レコードを更新します。
	 * @param book 更新する書籍情報が格納されたBookオブジェクト
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public void update(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE bookinfo SET title= ?, price=?  WHERE isbn=?";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book.getTitle());
			pstmt.setInt(2, book.getPrice());
			pstmt.setString(3, book.getIsbn());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new IllegalStateException(e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

	/**
	 * 引数で受け取った条件（ISBN、タイトル、価格）で書籍情報を曖昧検索します。
	 * @param isbn 検索条件：ISBN番号（部分一致）
	 * @param title 検索条件：タイトル（部分一致）
	 * @param price 検索条件：価格（部分一致）
	 * @return 検索条件に合致した書籍情報を含むArrayList
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public ArrayList<Book> search(String isbn, String title, String price) {
		Connection con = null;
		PreparedStatement pstmt = null;// 戻り値として返すためのArrayListを初期化
		ArrayList<Book> bookList = new ArrayList<Book>();
		String sql = "SELECT isbn, title, price FROM bookinfo"
				+ " WHERE isbn LIKE % ? % AND title LIKE % ? %"
				+ " AND price LIKE % ? %";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, price);
			ResultSet rs = pstmt.executeQuery(sql);

			// 結果セットのカーソルを順次進めながら全行を処理する
			while (rs.next()) {
				// 1行分のデータを格納するためのBookインスタンスを生成
				Book b = new Book();

				// データベースから取得した値をDTOにセット
				b.setIsbn(rs.getString("isbn"));
				b.setTitle(rs.getString("title"));
				b.setPrice(rs.getInt("price"));// リストにDTOを追加
				bookList.add(b);
			}
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new IllegalStateException(e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		} // 作成した書籍リストを返す
		return bookList;
	}
}
