/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：OrderedItemDAO.java
 * プログラムの説明：bookinfoテーブルとorderinfoテーブルを結合し、注文済み商品情報を取得する。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.OrderedItem;

/**
 * 注文済み商品の情報を取得するDAOクラスです。
 */
public class OrderedItemDAO {
	// DB情報をフィールド変数に定義
	private static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost/mybookdb";
	private static String USER = "root";
	private static String PASSWD = "root123";

	/**
	 * データベースへのコネクションを取得します。
	 * @return 確立されたデータベース接続
	 * @throws IllegalStateException JDBCドライバーのロードや接続に失敗した場合
	 */
	private static Connection getConnection() {
		try {
			Class.forName(RDB_DRIVE);
			return DriverManager.getConnection(URL, USER, PASSWD);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * bookinfoとorderinfoを結合して購入情報を取得します。
	 *
	 * @return 注文済み商品の一覧
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public ArrayList<OrderedItem> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<OrderedItem> list = new ArrayList<>();

		// SQL文例: isbnを条件に結合
		String sql = "SELECT o.user, b.title, o.quantity, o.date FROM bookinfo b INNER JOIN orderinfo o ON b.isbn = o.isbn";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderedItem item = new OrderedItem();
				item.setUserid(rs.getString("user"));
				item.setTitle(rs.getString("title"));
				item.setQuantity(rs.getInt("quantity"));
				item.setDate(rs.getString("date"));
				list.add(item);
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return list;
	}

	/**
	 * useridを指定して注文済み商品を取得します。
	 *
	 * @param userid 検索対象のユーザーID
	 * @return 該当ユーザーの注文済み商品一覧
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public ArrayList<OrderedItem> selectByUser(String userid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<OrderedItem> list = new ArrayList<>();
		String sql = "SELECT o.user, b.title, o.quantity, o.date FROM bookinfo b, orderinfo o "
				+ "WHERE b.isbn = o.isbn AND o.user = ?";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderedItem item = new OrderedItem();
				item.setUserid(rs.getString("user"));
				item.setTitle(rs.getString("title"));
				item.setQuantity(rs.getInt("quantity"));
				item.setDate(rs.getString("date"));
				list.add(item);
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException ignore) {
			}
		}
		return list;
	}

	/**
	 * DBリソースをクローズします。
	 *
	 * @param con DB接続
	 * @param pstmt ステートメント
	 */
	private void closeResources(Connection con, PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}