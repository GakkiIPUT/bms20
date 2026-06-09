package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Inquiry;
import bean.User;
import dao.InquiryDAO;

@WebServlet("/detailInquiry")
public class DetailInquiryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = null;

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			if (user == null || !"2".equals(user.getAuthority())) {
				error = "管理者権限がない為、アクセスできません。";
				request.setAttribute("error", error);
				request.setAttribute("cmd", "menu");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// URLにくっついてきた id (例: ?id=1) を取得
			String idStr = request.getParameter("id");
			if (idStr == null) {
				response.sendRedirect(request.getContextPath() + "/listInquiry");
				return;
			}

			int id = Integer.parseInt(idStr);
			InquiryDAO dao = new InquiryDAO();
			Inquiry inquiry = dao.selectById(id);

			request.setAttribute("inquiry", inquiry);
			request.getRequestDispatcher("/view/detailInquiry.jsp").forward(request, response);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、詳細は表示できませんでした。";
			request.setAttribute("error", error);
			request.setAttribute("cmd", "menu");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		}
	}
}