/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：ShowOrderedItemServlet.java
 * プログラムの説明：購入履歴（注文情報）を表示するサーブレットクラス。
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

import bean.OrderedItem;
import dao.OrderedItemDAO;

/**
 * 購入履歴（注文情報）を表示します。
 * OrderedItemDAO により購入情報を取得し、
 * showOrderedItem.jsp にフォワードします。
 */
@WebServlet("/showOrderedItem")
public class ShowOrderedItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 制御用の変数を初期化
		String path = "/view/showOrderedItem.jsp";
		String error = null;
		String cmd = "logout";
		
		try {
			// OrderedItemDAOをインスタンス化し、関連メソッドを呼び出す。
			OrderedItemDAO orderedItemDao = new OrderedItemDAO();
			
			// 戻り値として、OrderedItemオブジェクトのリスト(List)を取得する。
			ArrayList<OrderedItem> list = orderedItemDao.selectAll();

			// 取得したListをリクエストスコープに"ordered_list"という名前で格納する。
			request.setAttribute("ordered_list", list);

		} catch (Exception e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、購入状況確認は出来ません。";
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