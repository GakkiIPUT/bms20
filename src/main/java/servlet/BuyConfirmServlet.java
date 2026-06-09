/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：BuyConfirmServlet.java
 * プログラムの説明：購入確定とメール送信を行うサーブレットクラス。
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
import bean.Sale;
import bean.User;
import dao.BookDAO;
import dao.OrderDAO;
import util.SendMail;

/**
 * 購入確定処理を行います。
 * セッションとカートの確認、注文情報の登録、確認メールの送信、
 * カートのクリアを実施し、確認ページへフォワードします。
 */
@WebServlet("/buyConfirm")
public class BuyConfirmServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		// 制御用の変数を初期化
		String path = "/view/buyConfirm.jsp";
		String error = null;
		String cmd = "logout";

		try {
			// セッションから"user"を取得する。(セッション切れの場合はerror.jspに遷移する)
			User user = (User) session.getAttribute("user");
			if (user == null) {
				path = "/view/error.jsp";
				error = "セッション切れの為、購入は出来ません。";
				cmd = "logout";
				return;
			}

			// セッションから"order_list"を取得する。(カートの中身がない場合はerror.jspに遷移する)
			@SuppressWarnings("unchecked")
			ArrayList<Order> orderList = (ArrayList<Order>) session.getAttribute("order_list");
			if (orderList == null || orderList.size() == 0) {
				path = "/view/error.jsp";
				error = "カートの中に何も無かったので購入は出来ません。";
				cmd = "menu";
				return;
			}

			// DAOのインスタンス化
			BookDAO bookDao = new BookDAO();
			OrderDAO orderDao = new OrderDAO();

			// 購入確定処理の実装
			ArrayList<Sale> saleList = new ArrayList<Sale>();

			// メール本文の土台を構築
			StringBuilder mailBody = new StringBuilder();
			mailBody.append(user.getUserid()).append("様\n");
			mailBody.append("本のご購入ありがとうございます。\n");
			mailBody.append("以下内容でご注文を受け付けましたので、ご連絡致します。\n\n");

			int total = 0; // 合計金額用変数

			// 関連メソッドをorder_listの(カート追加データ分)だけ呼び出す。
			for (Order order : orderList) {

				// 書籍情報の取得
				Book book = bookDao.selectByIsbn(order.getIsbn());

				// 購入情報の登録
				orderDao.insert(order);

				// Saleオブジェクトに書籍情報と「数量」を詰め替える
				Sale sale = new Sale();
				sale.setIsbn(book.getIsbn());
				sale.setTitle(book.getTitle());
				sale.setPrice(book.getPrice());
				sale.setQuantity(order.getQuantity()); // 数量をセット
				saleList.add(sale);

				// ループ中にメール明細本文を追加する（単価と数量を記載）
				mailBody.append(book.getIsbn()).append(" ")
						.append(book.getTitle()).append(" ")
						.append(book.getPrice()).append("円 × ")
						.append(order.getQuantity()).append("\n");

				// 単価 × 数量 で合計金額を計算
				total += book.getPrice() * order.getQuantity();

			}

			// メールのフッター部分（合計金額など）を結合
			mailBody.append("\n合計：").append(total).append("円\n");
			mailBody.append("またのご利用よろしくお願いします。");

			// リクエストスコープに"book_list"という名前で格納する。
			request.setAttribute("book_list", saleList);

			// "order_list"の注文情報内容をメール送信する。
			SendMail sendMail = new SendMail();
			sendMail.sendMail(user.getEmail(), "書籍ご購入ありがとうございました", mailBody.toString());

			// セッションの"order_list"情報をクリアする。
			session.setAttribute("order_list", null);

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、購入は出来ません。";
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