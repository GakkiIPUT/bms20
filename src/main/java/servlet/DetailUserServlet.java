package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;

@WebServlet("/detailUser")
public class DetailUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String error = "";
		String path = "";
		String cmd = "cmd";

		try {
			String userid = request.getParameter("user");
			UserDAO userDao = new UserDAO();
			User objUser = userDao.selectByUser(userid);

			if (objUser == null || objUser.getUserid() == null) {
				error = "更新対象のユーザーが存在しない為、変更画面は表示出来ませんでした。";
			} else {
				request.setAttribute("user", objUser);
				// cmdパラメータによって遷移先を分岐
				if ("detailUser".equals(cmd)) {
					path = "/view/detailUser.jsp";
				} else if ("updateUser".equals(cmd)) {
					path = "/view/updateUser.jsp";
				}
			}
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザーの詳細情報は表示出来ません。";
		} finally {
			if (!error.isEmpty()) {
				request.setAttribute("error", error);
				if (error.contains("DB接続")) {
					request.setAttribute("cmd", "logout");
				} else {
					request.setAttribute("cmd", "listUser");
				}
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher(path).forward(request, response);
			}
		}
	}
}