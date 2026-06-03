/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：SearchServlet.java
 * プログラムの説明：書籍情報の検索処理を制御するサーブレットクラス。
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
 * 書籍の絞り込み検索処理を制御するサーブレットクラスです。
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 制御用の変数を初期化
		String path = "/view/list.jsp";
		String error = null;
		String cmd = "menu";

		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String price = request.getParameter("price");

		try {
			BookDAO dao = new BookDAO();
			ArrayList<Book> list = dao.search(isbn, title, price);
			request.setAttribute("book_list", list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、一覧表示は行えませんでした。";
			path = "/view/error.jsp";
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}