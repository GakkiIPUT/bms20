
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

@WebServlet("/insertIntoCart")
public class InsertIntoCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		try {
			// ① セッションから"user"のUserオブジェクトを取得する(セッション切れチェック)
			User user = (User) session.getAttribute("user");
			if (user == null) {
				request.setAttribute("error", "セッション切れの為、カートに追加出来ません。");
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// ② isbnのパラメータを取得する
			String isbn = request.getParameter("isbn");

			// ③ BookDAOをインスタンス化し、該当書籍を検索する
			BookDAO bookDao = new BookDAO();
			Book book = bookDao.selectByIsbn(isbn);

			// ④ 取得したBookオブジェクトをリクエストスコープに格納
			request.setAttribute("book", book);

			// ⑤ Orderのインスタンスを生成し、値を設定する(数量は1固定)
			Order order = new Order();
			order.setIsbn(isbn);
			order.setUserid(user.getUserid());
			order.setQuantity(1);

			// ⑥ セッションから"order_list"を取得する。なければ新規作成
			ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");
			if (orderList == null) {
				orderList = new ArrayList<Order>();
			}

			// ⑦ OrderオブジェクトをListに追加し、セッションに再登録
			orderList.add(order);
			session.setAttribute("order_list", orderList);

			// ⑧ insertIntoCart.jspにフォワードする
			request.getRequestDispatcher("/view/insertIntoCart.jsp").forward(request, response);

		} catch (Exception e) {
			request.setAttribute("error", "DB接続エラーの為、カートに追加は出来ません。");
			request.setAttribute("cmd", "logout");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}
}