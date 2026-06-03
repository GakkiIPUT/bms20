/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：OrderDAO.java
 * プログラムの説明：orderinfo購入データを新規登録するSQLを担当する。
 * 作成日：2026年5月20日
 * 作成者：髙垣湧侑翔
*/
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.Order;

public class OrderDAO {
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

   // 購入データをorderinfoテーブルに新規登録
    public void insert(Order order) {
        Connection con = null;
        PreparedStatement pstmt = null;

     // ordernoはNULL、dateはCURDATE()を使用
        String sql = "INSERT INTO orderinfo VALUES (NULL, ?, ?, ?, CURDATE())";

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, order.getUserid());
            pstmt.setString(2, order.getIsbn());
            pstmt.setInt(3, order.getQuantity()); 

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            closeResources(con, pstmt);
        }
    }

    private void closeResources(Connection con, PreparedStatement pstmt) {
        try {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}