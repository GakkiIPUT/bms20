/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：DeleteServlet.java
 * プログラムの説明：書籍情報の削除処理を制御するサーブレットクラス。
 * 作成日：2026年5月20日
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
 * 指定されたISBNの書籍を削除します。
 * 対象の存在確認を行い、存在すれば削除して一覧へ、
 * 存在しなければエラーページへフォワードします。
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String isbn = request.getParameter("isbn");

		// 制御用の変数を初期化
		String path = "/list";
		String error = null;
		String cmd = "list";

		try {
			BookDAO dao = new BookDAO();

			if (dao.selectByIsbn(isbn).getIsbn() == null) {
				path = "/view/error.jsp";
				error = "削除対象の書籍が存在しない為、書籍削除処理は行えませんでした。";
				cmd = "list";
				return;
			}

			dao.delete(isbn);

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、書籍削除処理は行えませんでした。";
			cmd = "menu";
			return;
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}