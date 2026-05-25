/*
 * プロジェクト名：書籍管理システムWeb版Ver2.0
 * プログラム名：DetailServlet.java
 * プログラムの説明：書籍の詳細情報表示、および更新画面への遷移を制御する。
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

import bean.Book;
import dao.BookDAO;

/**
 * 書籍の詳細情報表示、および更新画面への遷移を制御するサーブレットクラスです。
 * 一覧画面から渡されたISBNをもとにデータベースを検索し、結果をJSPへ渡します。
 *  存在しなければエラーページへフォワードします。
 */
@WebServlet("/detail")
public class DetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 制御用の変数を初期化
		String path = "/view/detail.jsp";
		String error = null;
		String cmd = "list";

		String isbn = request.getParameter("isbn");
		String cmdParam = request.getParameter("cmd");

		try {
			BookDAO dao = new BookDAO();
			Book book = dao.selectByIsbn(isbn);

			if (book.getIsbn() == null) {
				if ("update".equals(cmdParam)) {
					error = "更新対象の書籍が存在しない為、変更画面は表示できませんでした。";
				} else {
					error = "表示対象の書籍が存在しない為、詳細情報は表示できませんでした。";
				}
				path = "/view/error.jsp";
				return;
			}
			request.setAttribute("book", book);
			if ("update".equals(cmdParam)) {
				path = "/view/update.jsp";
			}

		} catch (IllegalStateException e) {
			if ("update".equals(cmdParam)) {
				error = "DB接続エラーの為、変更画面は表示できませんでした。";
			} else {
				error = "DB接続エラーの為、書籍詳細は表示できませんでした。";
			}
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
