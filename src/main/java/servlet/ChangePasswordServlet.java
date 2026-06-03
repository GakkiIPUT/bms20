/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：ChangePasswordServlet.java
 * プログラムの説明：パスワード変更処理を制御するサーブレットクラス。
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

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String path = "/view/menu.jsp";
		String error = null;

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			// ①セッション切れチェック
			if (user == null) {
				request.setAttribute("error", "セッション切れの為、パスワード変更は行えませんでした。");
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// ②画面からの入力データを取得
			String oldPassword = request.getParameter("oldpassword");
			String newPassword = request.getParameter("password");
			String newPasswordCk = request.getParameter("password_ck");

			// ③入力値チェック
			if (oldPassword == null || oldPassword.equals("") || 
				newPassword == null || newPassword.equals("") || 
				newPasswordCk == null || newPasswordCk.equals("")) {
				error = "未入力の項目があります。";
				path = "/view/error.jsp";
			} else if (!oldPassword.equals(user.getPassword())) {
				error = "旧パスワードが間違っています。";
				path = "/view/error.jsp";
			} else if (!newPassword.equals(newPasswordCk)) {
				error = "新パスワードと確認用パスワードが一致しません。";
				path = "/view/error.jsp";
			} else {
				// ④UserDAOでパスワード変更処理を呼び出し
				UserDAO userDao = new UserDAO();
				userDao.updatePassword(user.getUserid(), newPassword);
				
				// ⑤新パスワードをセッションのuserに設定し、再格納
				user.setPassword(newPassword);
				session.setAttribute("user", user);
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、パスワード変更処理は行えませんでした。";
			path = "/view/error.jsp";
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", "menu");
			}
			// ⑥menu.jsp（またはerror.jsp）にフォワード
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}