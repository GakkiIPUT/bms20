package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.User;
import dao.InquiryDAO;

@WebServlet("/deleteInquiry")
public class DeleteInquiryServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

			String idStr = request.getParameter("id");
			if (idStr != null) {
				int id = Integer.parseInt(idStr);
				InquiryDAO dao = new InquiryDAO();
				dao.delete(id); // DBから削除
			}

			// 削除後は、再び一覧サーブレットを呼び出して最新のリストを表示させる
			response.sendRedirect(request.getContextPath() + "/listInquiry");

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、削除は行えませんでした。";
			request.setAttribute("error", error);
			request.setAttribute("cmd", "menu");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		}finally {
			
		}
	}
}