package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Inquiry;
import bean.User;
import dao.InquiryDAO;

@WebServlet("/listInquiry")
public class ListInquiryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = null;
		String path = "/view/listInquiry.jsp";

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			// セッション切れ、または管理者(2)以外なら弾くガード節
			if (user == null || !"2".equals(user.getAuthority())) {
				error = "管理者権限がない為、お問い合わせ一覧は表示できません。";
				request.setAttribute("error", error);
				request.setAttribute("cmd", "menu");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// DAOを使って全件取得
			InquiryDAO dao = new InquiryDAO();
			ArrayList<Inquiry> list = dao.selectAll();

			// リクエストスコープに格納
			request.setAttribute("inquiry_list", list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、お問い合わせ一覧は表示できませんでした。";
			request.setAttribute("error", error);
			request.setAttribute("cmd", "menu");
			path = "/view/error.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		}

		request.getRequestDispatcher(path).forward(request, response);
	}
}