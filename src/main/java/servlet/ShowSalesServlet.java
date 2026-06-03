package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Sale;
import dao.SaleDAO;

@WebServlet("/showSales")
public class ShowSalesServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String error = "";

		try {
			String year = request.getParameter("year");
			String month = request.getParameter("month");

			// 初回アクセス時など、年月が未指定の場合は「現在の年月」を設定
			if (year == null || month == null) {
				LocalDate currentDate = LocalDate.now();
				year = String.valueOf(currentDate.getYear());
				month = String.valueOf(currentDate.getMonthValue());
			}

			SaleDAO saleDao = new SaleDAO();
			ArrayList<Sale> saleList = saleDao.selectBySales(year, month);

			request.setAttribute("sale_list", saleList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、売上げ状況は表示出来ません。";
		} finally {
			if (!error.isEmpty()) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/view/showSales.jsp").forward(request, response);
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}