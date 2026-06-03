/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：ListServlet.java
 * プログラムの説明：書籍一覧の取得および画面表示を制御するサーブレットクラス。
 * 作成日：2026年5月20日
 * 作成者：髙垣湧侑翔
*/

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Book;
import dao.BookDAO;

/**
 * 書籍一覧画面（list.jsp）の表示処理を制御するサーブレットクラスです。
 * データベースから全書籍情報を取得し、JSPへフォワードします。
 */
@WebServlet("/list")
public class ListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 制御用の変数を初期化
		String path = "/view/list.jsp";
		String error = null;
		String cmd = "menu";

		try {
			BookDAO dao = new BookDAO();
			ArrayList<Book> list = dao.selectAll();
			request.setAttribute("book_list", list);

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、一覧表示は行えませんでした。";
			cmd = "menu";
			return;
		}
		request.getRequestDispatcher(path).forward(request, response);
	}
}
