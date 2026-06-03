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

@WebServlet("/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String error = "";

		try {
			// セッションからログインユーザー情報を取得
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			if (user == null) {
				error = "セッション切れの為、パスワード変更は行えません。";
				request.setAttribute("error", error);
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			String password = request.getParameter("password");
			String passwordConfirm = request.getParameter("passwordConfirm");

			// 入力チェック
			if (password == null || password.isEmpty()) {
				error = "パスワード入力値不正の為、変更できません。";
			} else if (passwordConfirm == null || passwordConfirm.isEmpty()) {
				error = "パスワード(確認用)入力値不正の為、変更できません。";
			} else if (!password.equals(passwordConfirm)) {
				error = "入力パスワードがパスワード(確認用)と一致しない為、変更できません。";
			}

			// エラーが無ければパスワード更新
			if (error.isEmpty()) {
				UserDAO userDao = new UserDAO();
				userDao.updateForPassword(user.getUserid(), password);
				// セッション上のユーザー情報も更新しておく
				user.setPassword(password);
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、パスワード変更は行えません。";
		} finally {
			if (!error.isEmpty()) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", "menu"); // エラー時はメニューに戻る
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			} else {
				// 成功時はメッセージをセットしてメニュー画面等へ
				request.setAttribute("message", "パスワードの変更が完了しました。");
				request.getRequestDispatcher("/view/menu.jsp").forward(request, response);
			}
		}
	}
}