package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Inquiry;
import bean.User;
import dao.InquiryDAO;

@WebServlet("/inquiry")
public class InquiryServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String error = null;
		String cmd = "menu";
		String path = "/view/menu.jsp";

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			if (user == null) {
				error = "セッション切れの為、お問い合わせ処理は行えませんでした。";
				cmd = "logout";
				path = "/view/error.jsp";
				return;
			}

			// 確認画面の hidden から送られてきたデータを取得
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// DTOに詰める
			Inquiry inquiry = new Inquiry();
			inquiry.setName(name);
			inquiry.setPhone(phone);
			inquiry.setEmail(email);
			inquiry.setAddress(address);
			inquiry.setTitle(title);
			inquiry.setContent(content);

			// DBへ登録
			InquiryDAO dao = new InquiryDAO();
			dao.insert(inquiry);

			// 成功時の処理（今回はそのままメニュー画面へ戻る、あるいは完了メッセージを出す仕様に合わせる）
			// ※必要に応じて「お問い合わせを受け付けました」という画面を挟むことも可能です。
			request.setAttribute("message", "お問い合わせの送信が完了しました。");
			path = "/view/menu.jsp";

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、お問い合わせの送信は行えませんでした。";
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