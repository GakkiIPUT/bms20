package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

@WebServlet("/listUser")
public class ListUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String error = "";
		String cmd = "";

		try {
			// セッション切れ確認
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null) {
				error = "セッション切れの為、ユーザー一覧表示は行えません。";
				cmd = "logout";
				return;
			}

			ArrayList<User> userList;
			UserDAO userDao = new UserDAO();
			String searchUserid = request.getParameter("searchUserid");

			// 検索ワードの有無で全件検索か曖昧検索かを分岐
			if (searchUserid == null || searchUserid.isEmpty()) {
				userList = userDao.selectAll();
			} else {
				userList = userDao.search(searchUserid);
			}

			request.setAttribute("user_list", userList);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザー一覧は表示出来ません。";
			cmd = "logout";
		} finally {
			if (!error.isEmpty()) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/view/listUser.jsp").forward(request, response);
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}
}