/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：InsertUserServlet.java
 * プログラムの説明：ユーザー登録処理を制御するサーブレットクラス。
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
 * ユーザー登録処理を制御するサーブレットクラスです。
 */
@WebServlet("/insertUser")
public class InsertUserServlet extends HttpServlet {
	/**
	 * ユーザー登録処理を実行し、結果画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 制御用の変数を初期化
				String path = "/listUser";
				String error = null;
				String cmd = "listUser";
		try {
			String userid = request.getParameter("userid");
			String password = request.getParameter("password");
			String passwordConfirm = request.getParameter("passwordConfirm");
			String email = request.getParameter("email");
			String authority = request.getParameter("authority");

			// 入力値のエラーチェック
			if (userid == null || userid.isEmpty()) {
				error = "ユーザー入力値不正の為、登録できません。";
				return;
			}
			if (password == null || password.isEmpty()) {
				error = "パスワード入力値不正の為、登録できません。";
				return;
			}
			if (passwordConfirm == null || passwordConfirm.isEmpty()) {
				error = "パスワード(確認用)入力値不正の為、登録できません。";
				return;
			}
			if (email == null || email.isEmpty()) {
				error = "Eメール入力値不正の為、登録できません。";
				return;
			}
			if (authority == null || authority.isEmpty()) {
				error = "権限が未選択の為、登録できません。";
				return;
			}
			if (!password.equals(passwordConfirm)) {
				error = "入力パスワードがパスワード(確認用)と一致しない為、登録できません。";
				return;
			}
			// ユーザーIDの重複チェック
			UserDAO userDao = new UserDAO();
			User existingUser = userDao.selectByUser(userid);
			if (existingUser != null && existingUser.getUserid() != null) {
				error = "入力ユーザー名は既に使用済みの為、登録できません。";
				return;
			}

			// エラーがなければ登録
			User newUser = new User();
			newUser.setUserid(userid);
			newUser.setPassword(password);
			newUser.setEmail(email);
			newUser.setAuthority(authority);
			userDao.insert(newUser);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザー登録処理は行えませんでした。";
		} finally {
			// 例外が発生してもしなくても、最後に1回だけまとめてフォワード処理を行う
			if (error != null) {
				path = "/view/error.jsp";
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}