/*
 * プロジェクト名：書籍管理システムWeb版Ver1.0
 * プログラム名：DeleteServlet.java
 * プログラムの説明：書籍情報の削除処理を制御するサーブレットクラス。
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

import dao.BookDAO;

/**
 * 書籍情報の削除処理を制御するサーブレットクラスです。
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String isbn = request.getParameter("isbn");

		try {
			BookDAO dao = new BookDAO();

			if (dao.selectByIsbn(isbn).getIsbn() == null) {
				request.setAttribute("error", "削除対象の書籍が存在しない為、書籍削除処理は行えませんでした。");
				request.setAttribute("cmd", "list");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			dao.delete(isbn);
			request.getRequestDispatcher("/list").forward(request, response);

		} catch (IllegalStateException e) {
			request.setAttribute("error", "DB接続エラーの為、書籍削除処理は行えませんでした。");
			request.setAttribute("cmd", "menu");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}
}
