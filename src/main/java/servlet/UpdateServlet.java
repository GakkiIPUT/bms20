/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：UpdateServlet.java
 * プログラムの説明：書籍情報の更新処理を制御するサーブレットクラス。
 * 作成日：2026年6月3日
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
 * 書籍情報の更新処理を制御するサーブレットクラスです。
 * 画面からの変更内容を受け取り、データベースを更新します。
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

	/**
	 * 書籍情報の更新処理を実行し、結果画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 制御用の変数を初期化
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String priceStr = request.getParameter("price");

		// 制御用の変数を初期化
		String path = "/list";
		String error = null;
		String cmd = "list";

		try {
			if (title == null || title.equals("")) {
				error = "タイトルが未入力の為、書籍更新処理は行えませんでした。";
				path = "/view/error.jsp";
				return;
			}
			if (priceStr == null || priceStr.equals("")) {
				error = "価格が未入力の為、書籍更新処理は行えませんでした。";
				path = "/view/error.jsp";
				return;
			}
			int price = Integer.parseInt(priceStr);
			BookDAO bookDao = new BookDAO();

			if (bookDao.selectByIsbn(isbn).getIsbn() == null) {
				error = "更新対象の書籍が存在しない為、書籍更新処理は行えませんでした。";
				path = "/view/error.jsp";
				return;
			}
			Book book = new Book();
			book.setIsbn(isbn);
			book.setTitle(title);
			book.setPrice(price);
			bookDao.update(book);

		} catch (NumberFormatException e) {
			error = "価格の値が不正の為、書籍更新処理は行えませんでした。";
			path = "/view/error.jsp";
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、書籍更新処理は行えませんでした。";
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