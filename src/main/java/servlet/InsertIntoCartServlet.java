/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：InsertIntoCartServlet.java
 * プログラムの説明：セッション内のカートに書籍を追加するサーブレットクラス。
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
 * セッション内のカートに書籍を追加します。
 * セッションの有効性を確認し、ISBN で書籍を取得して 
 * session の order_list を更新します。
 */
@WebServlet("/insertIntoCart")
public class InsertIntoCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		// 制御用の変数を初期化
		String path = "/view/insertIntoCart.jsp";
		String error = null;
		String cmd = "logout";
		
		try {
			// セッションから"user"のUserオブジェクトを取得する(セッション切れチェック)
			User user = (User) session.getAttribute("user");
			if (user == null) {
				path = "/view/error.jsp";
				error = "セッション切れの為、カートに追加出来ません。";
				cmd = "logout";
				return;
			}

			// isbnのパラメータを取得する
			String isbn = request.getParameter("isbn");

			// BookDAOをインスタンス化し、該当書籍を検索する
			BookDAO bookDao = new BookDAO();
			Book book = bookDao.selectByIsbn(isbn);

			// 取得したBookオブジェクトをリクエストスコープに格納
			request.setAttribute("book", book);

			// Orderのインスタンスを生成し、値を設定する(数量は1固定)
			Order order = new Order();
			order.setIsbn(isbn);
			order.setUserid(user.getUserid());
			order.setQuantity(1);

			// セッションから"order_list"を取得する。なければ新規作成
			ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");
			if (orderList == null) {
				orderList = new ArrayList<Order>();
			}

			// OrderオブジェクトをListに追加し、セッションに再登録
			orderList.add(order);
			session.setAttribute("order_list", orderList);
			
		} catch (Exception e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、カートに追加は出来ません。";
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