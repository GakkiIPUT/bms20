/*
 * プロジェクト名：書籍管理システムWeb版Ver1.0
 * プログラム名：DetailServlet.java
 * プログラムの説明：書籍の詳細情報表示、および更新画面への遷移を制御する。
 * 作成日：2026年5月12日
 * 作成者：髙垣湧侑翔
*/

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Book;
import dao.BookDAO;

/**
 * 書籍の詳細情報表示、および更新画面への遷移を制御するサーブレットクラスです。
 * 一覧画面から渡されたISBNをもとにデータベースを検索し、結果をJSPへ渡します。
 */
@WebServlet("/detail")
public class DetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String isbn = request.getParameter("isbn");
		String cmd = request.getParameter("cmd");
		String error = "";

		try {
			BookDAO dao = new BookDAO();
			Book book = dao.selectByIsbn(isbn);

			if (book.getIsbn() == null) {
				if ("update".equals(cmd)) {
					error = "更新対象の書籍が存在しない為、変更画面は表示できませんでした。";
				} else {
					error = "表示対象の書籍が存在しない為、詳細情報は表示できませんでした。";
				}
				request.setAttribute("error", error);
				request.setAttribute("cmd", "list");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			request.setAttribute("book", book);

			if ("update".equals(cmd)) {
				request.getRequestDispatcher("/view/update.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/view/detail.jsp").forward(request, response);
			}

		} catch (IllegalStateException e) {
			if ("update".equals(cmd)) {
				error = "DB接続エラーの為、変更画面は表示できませんでした。";
			} else {
				error = "DB接続エラーの為、書籍詳細は表示できませんでした。";
			}
			request.setAttribute("error", error);
			request.setAttribute("cmd", "logout"); 
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}
}
