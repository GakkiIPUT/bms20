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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	// 詳細設計書の指示通り、パスワード保護のため doPost で処理を行う
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// ① userid, password入力パラメータを取得する
		String userid = request.getParameter("user");
		String password = request.getParameter("password");

		try {
			// ② UserDAOをインスタンス化し、関連メソッドを呼び出す
			UserDAO userDaoObj = new UserDAO();
			User user = userDaoObj.selectByUser(userid, password);

			// ③ User情報取得の有無でフォワード先を呼び別ける
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

				// menu.jspにフォワードする
				request.getRequestDispatcher("/view/menu.jsp").forward(request, response);

			} else {
				// User情報が取得出来なかった場合
				request.setAttribute("message", "入力データが間違っています！");
				request.getRequestDispatcher("/view/login.jsp").forward(request, response);
			}

		} catch (IllegalStateException e) {
			// DB接続エラーなどの場合
			request.setAttribute("error", "DB接続エラーの為、ログインは出来ません。");
			request.setAttribute("cmd", "logout");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}

	// doGetでアクセスされた場合もlogin.jspへ遷移させるか、doPostを呼び出す
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/view/login.jsp").forward(request, response);
	}
}