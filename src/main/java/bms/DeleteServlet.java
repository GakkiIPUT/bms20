/*
 * プロジェクト名：書籍管理システムWeb版Ver1.0
 * プログラム名：DeleteServlet.java
 * プログラムの説明：書籍情報の削除処理を制御するサーブレットクラス。
 * 作成日：2026年5月12日
 * 更新日：2026年5月19日
 * 作成者：髙垣湧侑翔
*/

package bms;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 書籍情報の削除処理を制御するサーブレットクラスです。
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 制御用の変数を初期化
		String path = "/list";
		String error = null;
		String cmd = "list";

		String isbn = request.getParameter("isbn");
		try {
			BookDAO bookDao = new BookDAO();

			if (bookDao.selectByIsbn(isbn).getIsbn() == null) {
				error = "削除対象の書籍が存在しない為、書籍削除処理は行えませんでした。";
				path = "/view/error.jsp";
				return;
			}
				bookDao.delete(isbn);
			
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、書籍削除処理は行えませんでした。";
			path = "/view/error.jsp";
			cmd = "menu";
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}