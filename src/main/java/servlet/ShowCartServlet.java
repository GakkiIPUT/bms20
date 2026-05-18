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

@WebServlet("/showCart")
public class ShowCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		try {
			// ① delnoの入力パラメータを取得する
			String delno = request.getParameter("delno");

			// ② セッションから "user" を取得する。(セッション切れの場合は error.jsp に遷移する)
			User user = (User) session.getAttribute("user");
			if (user == null) {
				request.setAttribute("error", "セッション切れの為、カート状況は確認出来ません。");
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// ③ セッションから "order_list" を取得する。
			@SuppressWarnings("unchecked")
			ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");
			
			// ④ delno が「null」でない場合 order_list から該当の書籍情報を削除する。
			if (delno != null && orderList != null) {
				orderList.remove(Integer.parseInt(delno));
			}

			// ⑤ BookDAO をインスタンス化し、関連メソッドを order_list (カートデータ分) だけ呼び出す。
			BookDAO bookDao = new BookDAO();
			ArrayList<Book> bookList = new ArrayList<Book>();

			if (orderList != null) {
				for (Order order : orderList) {
					Book book = bookDao.selectByIsbn(order.getIsbn());
					// ⑥ 取得した各BookをListに追加する。
					bookList.add(book);
				}
			}

			// リクエストスコープに "book_list" という名前で格納する。
			request.setAttribute("book_list", bookList);

			// ⑦ showCart.jsp にフォワードする。
			request.getRequestDispatcher("/view/showCart.jsp").forward(request, response);

		} catch (Exception e) {
			request.setAttribute("error", "DB接続エラーの為、カート状況は確認出来ません。");
			request.setAttribute("cmd", "logout");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}
}