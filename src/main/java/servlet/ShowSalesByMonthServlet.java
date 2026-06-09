/*
 
プロジェクト名：書籍管理システムWeb版Ver3.0
プログラム名：ShowSalesByMonthServlet.java
プログラムの説明：月別売上げ状況の検索および表示を制御するクラス。
作成者：髙垣湧侑翔
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

import bean.Sale;
import bean.User;
import dao.SaleDAO;

@WebServlet("/showSalesByMonth")
public class ShowSalesByMonthServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String path = "/view/showSalesByMonth.jsp";
		String error = null;

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			if (user == null) {
				request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// 管理者権限(2)があるかチェック
			if (!"2".equals(user.getAuthority())) {
				error = "管理者権限がない為、売上げ状況は表示できません。";
				request.setAttribute("error", error);
				request.setAttribute("cmd", "menu");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}
			// 画面から「年」と「月」を取得
			String year = request.getParameter("year");
			String month = request.getParameter("month");

			// 年月が両方とも入力されている場合のみ検索を実行
			if (year != null && month != null && !year.equals("") && !month.equals("")) {
				SaleDAO dao = new SaleDAO();
				ArrayList<Sale> list = dao.selectByYearMonth(year, month);
				request.setAttribute("sale_list", list);
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、売上げ状況は表示できませんでした。";
			path = "/view/error.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		}  finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", "menu");
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}