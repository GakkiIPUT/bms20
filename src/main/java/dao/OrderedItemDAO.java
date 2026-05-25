/*
 * プロジェクト名：書籍管理システムWeb版Ver2.0
 * プログラム名：OrderedItemDAO.java
 * プログラムの説明：bookinfo テーブルと orderinfo テーブルを結合して、注文済み商品の情報を取得します。
 * 					userid、title、date を含む OrderedItem DTO のリストを返します。
 * 作成日：2026年5月20日
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

public class OrderedItemDAO {
	// DB情報をフィールド変数に定義
	private static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost/mybookdb";
	private static String USER = "root";
	private static String PASSWD = "root123";

	private static Connection getConnection() {
		try {
			Class.forName(RDB_DRIVE);
			return DriverManager.getConnection(URL, USER, PASSWD);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	// 購入情報を取得 (テーブル結合)
	public ArrayList<OrderedItem> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<OrderedItem> list = new ArrayList<>();

		// SQL文例: isbnを条件に結合
		String sql = "SELECT o.user, b.title, o.date FROM bookinfo b INNER JOIN orderinfo o ON b.isbn = o.isbn";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderedItem item = new OrderedItem();
				item.setUserid(rs.getString("user"));
				item.setTitle(rs.getString("title"));
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