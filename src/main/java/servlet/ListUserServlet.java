/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：ListUserServlet.java
 * プログラムの説明：ユーザー一覧の表示および検索を制御するサーブレットクラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */
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

/**
 * ユーザー一覧の表示と検索を制御するサーブレットクラスです。
 */
@WebServlet("/listUser")
public class ListUserServlet extends HttpServlet {
	/**
	 * ユーザー一覧の表示または検索結果を取得してフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = "/view/listUser.jsp";
		String error = null;

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			// セッション切れチェック
			if (user == null) {
				request.setAttribute("error", "セッション切れの為、ユーザー一覧表示は行えませんでした。");
				request.setAttribute("cmd", "logout");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			// 管理者権限チェック (2: 管理者)
			if (!"2".equals(user.getAuthority())) {
				request.setAttribute("error", "管理者権限がない為、ユーザー一覧表示は行えませんでした。");
				request.setAttribute("cmd", "menu");
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			String searchUserid = request.getParameter("searchUserid");
			UserDAO dao = new UserDAO();
			ArrayList<User> userList;

			// 検索ワードの有無で全件検索か曖昧検索かを分岐
			if (searchUserid == null || searchUserid.equals("")) {
				userList = dao.selectAll();
			} else {
				userList = dao.search(searchUserid);
			}

			request.setAttribute("user_list", userList);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザー一覧表示は行えませんでした。";
			path = "/view/error.jsp";
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", "menu");
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	/**
	 * POSTリクエストをdoGetに委譲します。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POSTで送られてきても、上で書いたdoGetと同じ処理を実行させる
		doGet(request, response);
	}
}