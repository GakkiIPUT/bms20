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

@WebServlet("/showOrderedItem")
public class ShowOrderedItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// ① OrderedItemDAOをインスタンス化し、関連メソッドを呼び出す。
			OrderedItemDAO orderedItemDao = new OrderedItemDAO();
			
			// ② 戻り値として、OrderedItemオブジェクトのリスト(List)を取得する。
			ArrayList<OrderedItem> list = orderedItemDao.selectAll();

			// ③ 取得したListをリクエストスコープに"ordered_list"という名前で格納する。
			request.setAttribute("ordered_list", list);

			// ④ showOrderedItem.jspにフォワードする。
			request.getRequestDispatcher("/view/showOrderedItem.jsp").forward(request, response);

		} catch (Exception e) {
			request.setAttribute("error", "DB接続エラーの為、購入状況確認は出来ません。");
			request.setAttribute("cmd", "logout");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}
}