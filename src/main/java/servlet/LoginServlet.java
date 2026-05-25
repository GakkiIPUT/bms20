/*
 * プロジェクト名：書籍管理システムWeb版Ver2.0
 * プログラム名：LoginServlet.java
 * プログラムの説明：ログイン処理を制御するサーブレットクラス。
 * 作成日：2026年5月20日
 * 作成者：髙垣湧侑翔
*/

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

/**
 * パスワード保護のため doPost で処理を行う。
 * ログインフォームの POST を処理します。
 * UserDAO による認証を行い、成功時はセッションとクッキーを設定してメニュー画面へ遷移、
 * 失敗時はログイン画面へ戻します。DB エラー時はエラーページへフォワードします。
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// userid, password入力パラメータを取得する
		String userid = request.getParameter("user");
		String password = request.getParameter("password");

		// 制御用の変数を初期化
		String path = "/view/login.jsp";
		String error = null;
		String cmd = "logout";

		try {
			// UserDAOをインスタンス化し、関連メソッドを呼び出す
			UserDAO userDaoObj = new UserDAO();
			User user = userDaoObj.selectByUser(userid, password);

			// User情報取得の有無でフォワード先を呼び別ける
			if (user != null && user.getUserid() != null) {
				// User情報が取得出来た場合

				// セッションスコープに"user"という名前で登録する
				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				// クッキーにuseridとpasswordを登録する（期間は5日間 = 60秒 * 60分 * 24時間 * 5日）
				Cookie cookieUser = new Cookie("user", userid);
				cookieUser.setMaxAge(60 * 60 * 24 * 5);
				response.addCookie(cookieUser);

				Cookie cookiePass = new Cookie("password", password);
				cookiePass.setMaxAge(60 * 60 * 24 * 5);
				response.addCookie(cookiePass);

				path = "/view/menu.jsp";
				return;
			}
			// User情報が取得出来なかった場合
			request.setAttribute("message", "入力データが間違っています！");
			path = "/view/login.jsp";

		} catch (IllegalStateException e) {
			// DB接続エラーなどの場合
			path = "/view/error.jsp";
			error = "DB接続エラーの為、ログインは出来ません。 ";
			cmd = "logout";
			return;
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	// doGetでアクセスされた場合もlogin.jspへ遷移させるか、doPostを呼び出す
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/view/login.jsp").forward(request, response);
	}
}