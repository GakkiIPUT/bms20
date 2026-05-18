package servlet;


import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Book;
import dao.BookDAO;
import util.FileIn;


@WebServlet("/insertIniData")
public class InsertIniDataServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            // ① BookDAOをインスタンス化し、関連メソッドを呼び出す
            BookDAO bookDao = new BookDAO();
            
            // ② 戻り値として、Bookオブジェクトのリスト(List)を取得する
            ArrayList<Book> list = bookDao.selectAll();
            
            // ③ Listに1件でも書籍データがあればerror.jspにフォワードする
            if (list.size() > 0) {
                request.setAttribute("error", "DBにはデータが存在するので、初期データは登録できません。");
                request.setAttribute("cmd", "menu");
                request.getRequestDispatcher("/view/error.jsp").forward(request, response);
                return;
            }
            
            // ④ 初期データ用CSVファイルよりデータを取得する
            FileIn fileIn = new FileIn();
            String path = getServletContext().getRealPath("initial_data.csv");
            
            ArrayList<Book> bookList = new ArrayList<>();
            if (fileIn.open(path)) {
                String line;
                while ((line = fileIn.readLine()) != null) {
                    // CSV形式(isbn,title,price)を分割
                    String[] data = line.split(",");
                    
                    // ⑤ Bookのオブジェクトを生成し、初期データの値を設定する
                    Book book = new Book();
                    book.setIsbn(data[0]);
                    book.setTitle(data[1]);
                    book.setPrice(Integer.parseInt(data[2]));
                    
                    // ⑥ 取得した各BookをListに追加し、DAOで登録を行う
                    bookList.add(book);
                    // ⑦ 関連メソッド(insert)を呼び出す
                    bookDao.insert(book);
                }
                fileIn.close();
            } else {
                throw new IOException("初期データファイルが無い為、登録は行えません。");
            }
            
            // リクエストスコープに登録したリストを格納
            request.setAttribute("book_list", bookList);
            
            // ⑧ insertIniData.jspにフォワードする
            request.getRequestDispatcher("/view/insertIniData.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "初期データ登録中にエラーが発生しました：" + e.getMessage());
            request.setAttribute("cmd", "menu");
            request.getRequestDispatcher("/view/error.jsp").forward(request, response);
        }
    }
}