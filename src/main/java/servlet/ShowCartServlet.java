/*
 * プロジェクト名：書籍管理システムWeb版Ver2.0
 * プログラム名：ShowCartServlet.java
 * プログラムの説明：現在のカートの表示やアイテムの削除などを制御するサーブレットクラス。
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
import jakarta.servlet.http.HttpSession;

import bean.Book;
import bean.Order;
import bean.User;
import dao.BookDAO;

/**
 * 現在のユーザーのカートを表示します。
 * セッションの確認、アイテムの削除処理、
 * 各アイテムの書籍情報取得を行い、showCart.jsp にフォワードします。
 * エラー発生時は error.jsp にフォワードします。
 */
@WebServlet("/showCart")
public class ShowCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		// 制御用の変数を初期化
		String path = "/view/showCart.jsp";
		String error = null;
		String cmd = "logout";

		try {
			// delnoの入力パラメータを取得する
			String delno = request.getParameter("delno");

			// セッションから "user" を取得する。(セッション切れの場合は error.jsp に遷移する)
			User user = (User) session.getAttribute("user");
			if (user == null) {
				path = "/view/error.jsp";
				error = "セッション切れの為、カート状況は確認出来ません。";
				cmd = "logout";
				return;
			}

			// セッションから "order_list" を取得する。
			@SuppressWarnings("unchecked")
			ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");

			// delno が「null」でない場合 order_list から該当の書籍情報を削除する。
			if (delno != null && orderList != null) {
				orderList.remove(Integer.parseInt(delno));
			}

			// BookDAO をインスタンス化し、関連メソッドを order_list (カートデータ分) だけ呼び出す。
			BookDAO bookDao = new BookDAO();
			ArrayList<Book> bookList = new ArrayList<Book>();

			if (orderList != null) {
				for (Order order : orderList) {
					Book book = bookDao.selectByIsbn(order.getIsbn());
					// 取得した各BookをListに追加する。
					bookList.add(book);
				}
			}

			// リクエストスコープに "book_list" という名前で格納する。
			request.setAttribute("book_list", bookList);

		} catch (Exception e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、カート状況は確認出来ません。";
			cmd = "logout";
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