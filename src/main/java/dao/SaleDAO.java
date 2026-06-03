package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Sale;

public class SaleDAO {
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

	public ArrayList<Sale> selectBySales(String year, String month) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<Sale> saleList = new ArrayList<>();

		// 月が一桁の場合は頭に0をつける
		if (month != null && month.length() == 1) {
			month = "0" + month;
		}

		String sql = "SELECT b.isbn, title, price, sum(quantity) as quantity "
				+ "FROM orderinfo o INNER JOIN bookinfo b ON o.isbn = b.isbn "
				+ "WHERE date LIKE ? GROUP BY b.isbn ORDER BY b.isbn";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, year + "-" + month + "-%");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Sale sale = new Sale();
				sale.setIsbn(rs.getString("isbn"));
				sale.setTitle(rs.getString("title"));
				sale.setPrice(rs.getInt("price"));
				sale.setQuantity(rs.getInt("quantity"));
				saleList.add(sale);
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
		return saleList;
	}
	
	// 指定された年月の売上げを集計して取得するメソッド
    public ArrayList<Sale> selectByYearMonth(String year, String month) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Sale> list = new ArrayList<>();

        // MySQLの YEAR(), MONTH() 関数を使って条件を絞り込み、ISBNごとに数量をSUM()で集計します
        String sql = "SELECT b.isbn, b.title, b.price, SUM(o.quantity) AS quantity "
                + "FROM bookinfo b INNER JOIN orderinfo o ON b.isbn = o.isbn "
                + "WHERE YEAR(o.date) = ? AND MONTH(o.date) = ? "
                + "GROUP BY b.isbn, b.title, b.price";

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, year);
            pstmt.setString(2, month);
ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sale sale = new Sale();
                sale.setIsbn(rs.getString("isbn"));
                sale.setTitle(rs.getString("title"));
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                sale.setPrice(price);
                sale.setQuantity(quantity);
                sale.setAmount(price * quantity); // 小計を計算して格納
                list.add(sale);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
