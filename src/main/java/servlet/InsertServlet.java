/*
 * プロジェクト名：書籍管理システムWeb版Ver1.0
 * プログラム名：InsertServlet.java
 * プログラムの説明：書籍情報の登録処理を制御するサーブレットクラス。
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
 * 書籍登録処理を制御するサーブレットクラスです。
 * 画面からの入力値を受け取り、バリデーションを行った後、データベースに登録します。
 */
@WebServlet("/insert")
public class InsertServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String priceStr = request.getParameter("price");

		String error = "";

		if (isbn.equals("")) {
			error = "ISBNが未入力の為、書籍登録処理は行えませんでした。";
		} else if (title.equals("")) {
			error = "タイトルが未入力の為、書籍登録処理は行えませんでした。";
		} else if (priceStr.equals("")) {
			error = "価格が未入力の為、書籍登録処理は行えませんでした。";
		}

		if (!error.equals("")) {
			request.setAttribute("error", error);
			request.setAttribute("cmd", "list");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}

		try {
			int price = Integer.parseInt(priceStr);
			BookDAO dao = new BookDAO();

			if (dao.selectByIsbn(isbn).getIsbn() != null) {
				request.setAttribute("error", "入力ISBNは既に登録済みの為、書籍登録処理は行えませんでした。");
				request.setAttribute("cmd", "list");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			Book book = new Book();
			book.setIsbn(isbn);
			book.setTitle(title);
			book.setPrice(price);

			dao.insert(book);
			request.getRequestDispatcher("/list").forward(request, response);

		} catch (NumberFormatException e) {
			request.setAttribute("error", "価格の値が不正の為、書籍登録処理は行えませんでした。");
			request.setAttribute("cmd", "list");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			request.setAttribute("error", "DB接続エラーの為、書籍登録処理は行えませんでした。");
			request.setAttribute("cmd", "logout");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}

}
