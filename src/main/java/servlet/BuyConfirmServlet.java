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
import dao.OrderDAO;

@WebServlet("/buyConfirm")
public class BuyConfirmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		try {
			// ① セッションから"user"を取得する。(セッション切れの場合はerror.jspに遷移する)
			User user = (User) session.getAttribute("user");
			if (user == null) {
				request.setAttribute("error", "セッション切れの為、購入は出来ません。");
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// ② セッションから"order_list"を取得する。(カートの中身がない場合はerror.jspに遷移する)
			@SuppressWarnings("unchecked")
			ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");
			if (orderList == null || orderList.size() == 0) {
				request.setAttribute("error", "カートの中に何も無かったので購入は出来ません。");
				request.setAttribute("cmd", "menu");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// DAOのインスタンス化
			BookDAO bookDao = new BookDAO();
			OrderDAO orderDao = new OrderDAO();
			ArrayList<Book> bookList = new ArrayList<Book>();

			// ③ 関連メソッドをorder_listの(カート追加データ分)だけ呼び出す。
			for (Order order : orderList) {
				// 書籍情報の取得
				Book book = bookDao.selectByIsbn(order.getIsbn());
				// 購入情報の登録
				orderDao.insert(order);

				// ④ 取得した各BookをListに追加する
				bookList.add(book);
			}

			// リクエストスコープに"book_list"という名前で格納する。
			request.setAttribute("book_list", bookList);

			// ⑤ "order_list"の注文情報内容をメール送信する。（オプション機能のためコンソール出力等の代替処理）
			// SendMail sendMail = new SendMail();
			// sendMail.sendOrderMail(user, bookList);
			System.out.println("※ここにメール送信処理が入ります");

			// ⑥ セッションの"order_list"情報をクリアする。
			session.setAttribute("order_list", null);

			// ⑦ buyConfirm.jspにフォワードする。
			request.getRequestDispatcher("/view/buyConfirm.jsp").forward(request, response);

		} catch (Exception e) {
			request.setAttribute("error", "DB接続エラーの為、購入は出来ません。");
			request.setAttribute("cmd", "logout");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}
}