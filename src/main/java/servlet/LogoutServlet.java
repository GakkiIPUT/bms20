package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // ① セッション情報をクリアする
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        
        // ② login.jspにフォワードする
        request.getRequestDispatcher("/view/login.jsp").forward(request, response);
    }
}