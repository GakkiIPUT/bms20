/*
 * プロジェクト名：書籍管理システムWeb版Ver2.0
 * プログラム名：InsertIniDataServlet.java
 * プログラムの説明：初期データをデータベースに登録するサーブレットクラス。
 * 作成日：2026年5月20日
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

import bean.Book;
import dao.BookDAO;
import util.FileIn;

/**
* 初期データ（CSV）をデータベースに登録します。
* データベースが空の場合のみ /file/initial_data.csv を読み込み、各行を Book として登録します。
* 成功時は insertIniData.jsp にフォワードし、エラー時は error.jsp にフォワードします。
*/
@WebServlet("/insertIniData")
public class InsertIniDataServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 制御用の変数を初期化
		String path = "/view/insertIniData.jsp";
		String error = null;
		String cmd = "menu";

		try {
			// BookDAOをインスタンス化し、関連メソッドを呼び出す
			BookDAO bookDao = new BookDAO();

			// 戻り値として、Bookオブジェクトのリスト(List)を取得する
			ArrayList<Book> list = bookDao.selectAll();

			// Listに1件でも書籍データがあればerror.jspにフォワードする
			if (list.size() > 0) {
				path = "/view/error.jsp";
				error = "DBにはデータが存在するので、初期データは登録できません。";
				cmd = "menu";
				return;
			}

			// 初期データ用CSVファイルよりデータを取得する
			FileIn fileIn = new FileIn();
			String pathCsv = getServletContext().getRealPath("/file/initial_data.csv");

			ArrayList<Book> bookList = new ArrayList<>();

			if (fileIn.open(pathCsv)) {
				String line;
				while ((line = fileIn.readLine()) != null) {
					// CSV形式(isbn,title,price)を分割
					String[] data = line.split(",");

					// Bookのオブジェクトを生成し、初期データの値を設定する
					Book book = new Book();
					book.setIsbn(data[0]);
					book.setTitle(data[1]);
					book.setPrice(Integer.parseInt(data[2]));

					// 取得した各BookをListに追加し、DAOで登録を行う
					bookList.add(book);
					// 関連メソッド(insert)を呼び出す
					bookDao.insert(book);
				}
				fileIn.close();
				// リクエストスコープに登録したリストを格納
				request.setAttribute("book_list", bookList);

			} else {
				error = "初期データファイルが無い為、初期データ登録は行えません。";
				path = "/view/error.jsp";
				cmd = "menu";
			}

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、初期データ登録は行えません。";
			cmd = "menu";
			return;
		} catch (NumberFormatException e) {
			// 価格に文字列が含まれていた場合
			error = "初期データファイルが不備がある為、登録は行えません。";
			path = "/view/error.jsp";
			cmd = "menu";
			return;
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}