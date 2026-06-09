package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Inquiry;

public class InquiryDAO {

	// DB接続情報（ご自身の環境に合わせて適宜修正してください）
	private final String URL = "jdbc:mysql://localhost/mybookdb";
	private final String USER = "root";
	private final String PASS = "root123";

	private Connection con = null;

	public void connect() {
		try {
			// JDBCドライバのロード (MySQL 8.0以降)
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			throw new IllegalStateException("DB接続エラー", e);
		}
	}

	public void disconnect() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			throw new IllegalStateException("DB切断エラー", e);
		}
	}

	// ① お問い合わせの新規登録（一般ユーザー用）
	public void insert(Inquiry inquiry) {
		String sql = "INSERT INTO inquiryinfo (name, phone, email, address, title, content, date) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, inquiry.getName());
			stmt.setString(2, inquiry.getPhone());
			stmt.setString(3, inquiry.getEmail());
			stmt.setString(4, inquiry.getAddress());
			stmt.setString(5, inquiry.getTitle());
			stmt.setString(6, inquiry.getContent());
			stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis())); // 現在時刻

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB更新エラー", e);
		} finally {
			disconnect();
		}
	}

	// ② お問い合わせの全件取得（管理者用）
	public ArrayList<Inquiry> selectAll() {
		ArrayList<Inquiry> list = new ArrayList<Inquiry>();
		String sql = "SELECT * FROM inquiryinfo ORDER BY date DESC"; // 新しい順
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Inquiry inquiry = new Inquiry();
				inquiry.setId(rs.getInt("id"));
				inquiry.setName(rs.getString("name"));
				inquiry.setPhone(rs.getString("phone"));
				inquiry.setEmail(rs.getString("email"));
				inquiry.setAddress(rs.getString("address"));
				inquiry.setTitle(rs.getString("title"));
				inquiry.setContent(rs.getString("content"));
				inquiry.setDate(rs.getTimestamp("date"));
				list.add(inquiry);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB検索エラー", e);
		} finally {
			disconnect();
		}
		return list;
	}

	// ③ お問い合わせ詳細の取得（管理者用）
	public Inquiry selectById(int id) {
		Inquiry inquiry = new Inquiry();
		String sql = "SELECT * FROM inquiryinfo WHERE id = ?";
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				inquiry.setId(rs.getInt("id"));
				inquiry.setName(rs.getString("name"));
				inquiry.setPhone(rs.getString("phone"));
				inquiry.setEmail(rs.getString("email"));
				inquiry.setAddress(rs.getString("address"));
				inquiry.setTitle(rs.getString("title"));
				inquiry.setContent(rs.getString("content"));
				inquiry.setDate(rs.getTimestamp("date"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB検索エラー", e);
		} finally {
			disconnect();
		}
		return inquiry;
	}

	// ④ お問い合わせの削除（管理者用）
	public void delete(int id) {
		String sql = "DELETE FROM inquiryinfo WHERE id = ?";
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB更新エラー", e);
		} finally {
			disconnect();
		}
	}
}