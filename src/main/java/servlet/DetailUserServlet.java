/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：DetailUserServlet.java
 * プログラムの説明：ユーザー詳細の表示、および変更画面への遷移を制御するクラス。
 * 作成者：髙垣湧侑翔
*/
package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

@WebServlet("/detailUser")
public class DetailUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = "/view/detailUser.jsp";
		String error = null;
		String cmd = "user";

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null) {
				request.setAttribute("error", "セッション切れの為、ユーザー詳細画面が表示できませんでした。");
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			if (!"2".equals(user.getAuthority())) {
				request.setAttribute("error", "管理者権限がない為、ユーザー詳細画面が表示できませんでした。");
				request.setAttribute("cmd", "menu");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			String targetUser = request.getParameter("user");
			String cmdParam = request.getParameter("cmd");

			UserDAO dao = new UserDAO();
			User resultUser = dao.selectByUser(targetUser);

			if (resultUser == null || resultUser.getUserid() == null) {
				if ("updateUser".equals(cmdParam)) {
					error = "更新対象のユーザーが存在しない為、変更画面は表示できませんでした。";
				} else {
					error = "表示対象のユーザーが存在しない為、詳細情報は表示できませんでした。";
				}
				path = "/view/error.jsp";
			} else {
				request.setAttribute("user", resultUser);
				if ("updateUser".equals(cmdParam)) {
					path = "/view/updateUser.jsp";
				}
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、画面表示は行えませんでした。";
			path = "/view/error.jsp";
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