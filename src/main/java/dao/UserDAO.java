/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：UserDAO.java
 * プログラムの説明：DB接続とuserinfoテーブルに対するユーザー情報の取得や
 * 					パスワードに合致する情報を取得するSQL実行を担当する。
 * 作成日：2026年5月15日
 * 作成者：髙垣湧侑翔
*/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;

/**
 */
public class UserDAO {

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
	 * userid に基づいてユーザーを選択します。
	 *
	 * @param userid 検索対象のユーザー ID
	 * @return ユーザーが見つかった場合は User DTO、見つからない場合は null
	 * @throws データベースエラーが発生した場合は IllegalStateException をスローします
	 */
	public User selectByUser(String userid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		User user = null;

		String sql = "SELECT * FROM userinfo WHERE user =?";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return null;
	}

	/**
	 * ユーザーIDとパスワードに基づいてユーザーを選択します（認証に使用されます）。
	 *
	 * @param userid   検索するユーザーID
	 * @param password 照合するパスワード
	 * @return 認証情報が一致した場合、User DTOを返します。一致しない場合はnullを返します。
	 * @throws データベースエラーが発生した場合、IllegalStateExceptionをスローします
	 */
	public User selectByUser(String userid, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		User user = null;

		String sql = "SELECT * FROM userinfo WHERE user = ? AND password = ?";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return user;
	}

	public java.util.ArrayList<User> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		java.util.ArrayList<User> userList = new java.util.ArrayList<>();
		String sql = "SELECT * FROM userinfo";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
				userList.add(user);
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return userList;
	}

	public int insert(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "INSERT INTO userinfo VALUES(?, ?, ?, ?)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserid());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getAuthority());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return count;
	}

	public int delete(String userid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "DELETE FROM userinfo WHERE user = ?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return count;
	}

	public int update(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "UPDATE userinfo SET password = ?, email = ?, authority = ? WHERE user = ?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getAuthority());
			pstmt.setString(4, user.getUserid());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return count;
	}

	public java.util.ArrayList<User> search(String userid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		java.util.ArrayList<User> userList = new java.util.ArrayList<>();
		String sql = "SELECT * FROM userinfo WHERE user LIKE ?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + userid + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
				userList.add(user);
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return userList;
	}

	/**
	 * ユーザーのパスワードを更新します。
	 */
	public int updateForPassword(String userid, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "UPDATE userinfo SET password = ? WHERE user = ?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, userid);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		} finally {
			closeResources(con, pstmt);
		}
		return count;
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
