/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：UpdateUserServlet.java
 * プログラムの説明：ユーザー情報の更新処理を制御するサーブレットクラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;

/**
 * ユーザー情報の更新処理を制御するサーブレットクラスです。
 */
@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
	/**
	 * ユーザー情報の更新処理を実行し、結果画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String error = "";
		
		try {
			String userid = request.getParameter("user");
			String password = request.getParameter("password");
			String passwordConfirm = request.getParameter("passwordConfirm");
			String email = request.getParameter("email");
			String authority = request.getParameter("authority");

			if (password == null || password.isEmpty()) {
				error = "パスワード入力値不正の為、変更できません。<br>\n";
			} else if (passwordConfirm == null || passwordConfirm.isEmpty()) {
				error = "パスワード(確認用)入力値不正の為、変更できません。<br>\n";
			} else if (email == null || email.isEmpty()) {
				error = "Eメール入力値不正の為、変更できません。<br>\n";
			} else if (authority == null || authority.isEmpty()) {
				error = "権限が未選択の為、変更できません。<br>\n";
			} else if (!password.equals(passwordConfirm)) {
				error = "入力パスワードがパスワード(確認用)と一致しない為、変更できません。<br>\n";
			}

			if (error.isEmpty()) {
				User objUser = new User();
				objUser.setUserid(userid); // 変更不可パラメータだがWhere句の条件に必要
				objUser.setPassword(password);
				objUser.setEmail(email);
				objUser.setAuthority(authority);

				UserDAO userDao = new UserDAO();
				userDao.update(objUser);
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザー変更は行えません。";
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
				request.getRequestDispatcher("/listUser").forward(request, response);
			}
		}
	}
}