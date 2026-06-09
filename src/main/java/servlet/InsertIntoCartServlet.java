/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：InsertIntoCartServlet.java
 * プログラムの説明：セッション内のカートに書籍を追加するサーブレットクラス。
 * 作成日：2026年6月3日
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
 * session の order_list を更新します。（同一商品の場合は数量を加算）
 */
@WebServlet("/insertIntoCart")
public class InsertIntoCartServlet extends HttpServlet {

	/**
	 * カートへ書籍を追加し、結果画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
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

			// isbnとquantityのパラメータを取得する
			String isbn = request.getParameter("isbn");
			String quantityStr = request.getParameter("quantity");

			// 数量が未入力などの場合はとりあえず1とする
			int quantity = 1;
			if (quantityStr != null && !quantityStr.isEmpty()) {
				quantity = Integer.parseInt(quantityStr);
			}

			// BookDAOをインスタンス化し、該当書籍を検索する
			BookDAO bookDao = new BookDAO();
			Book book = bookDao.selectByIsbn(isbn);

			// 取得したBookオブジェクトをリクエストスコープに格納
			request.setAttribute("book", book);
			// 画面に表示するために、取得した「数量」もリクエストスコープに格納
			request.setAttribute("quantity", quantity);

			// セッションから"order_list"を取得する。なければ新規作成
			ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");
			if (orderList == null) {
				orderList = new ArrayList<Order>();
			}

			// カート内に既に同じISBNが存在するかチェック
			boolean isExist = false;
			for (Order o : orderList) {
				if (o.getIsbn().equals(isbn)) {
					// 存在する場合は数量を加算
					o.setQuantity(o.getQuantity() + quantity);
					isExist = true;
					break;
				}
			}

			// 存在しなかった場合のみ、新規でOrderオブジェクトを作成してリストに追加
			if (!isExist) {
				Order order = new Order();
				order.setIsbn(isbn);
				order.setUserid(user.getUserid());
				order.setQuantity(quantity);
				orderList.add(order);
			}

			// セッションに再登録
			session.setAttribute("order_list", orderList);

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、カートに追加は出来ません。";
			cmd = "logout";
			return;
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}