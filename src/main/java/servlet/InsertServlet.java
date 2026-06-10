/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：InsertServlet.java
 * プログラムの説明：書籍情報の登録処理を制御するサーブレットクラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */

package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import bean.Book;
import dao.BookDAO;

/**
 * 書籍登録処理を制御するサーブレットクラスです。
 * 画面からの入力値を受け取り、バリデーションを行った後、データベースに登録します。
 */

@WebServlet("/insert")
@MultipartConfig
public class InsertServlet extends HttpServlet {

	/**
	 * 書籍情報の登録処理を実行し、結果画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String priceStr = request.getParameter("price");

		// 制御用の変数を初期化
		String path = "/list";
		String error = null;
		String cmd = "list";

		try {
			if (isbn == null || isbn.equals("")) {
				error = "ISBNが未入力の為、書籍登録処理は行えませんでした。";
				path = "/view/error.jsp";
				return;
			}
			if (title == null || title.equals("")) {
				error = "タイトルが未入力の為、書籍登録処理は行えませんでした。";
				path = "/view/error.jsp";
				return;
			}
			if (priceStr == null || priceStr.equals("")) {
				error = "価格が未入力の為、書籍登録処理は行えませんでした。";
				path = "/view/error.jsp";
				return;
			}

			BookDAO dao = new BookDAO();

			if (dao.selectByIsbn(isbn).getIsbn() != null) {
				cmd = "list";
				error = "入力ISBNは既に登録済みの為、書籍登録処理は行えませんでした。";
				path = "/view/error.jsp";
				return;
			}
			String imageName = "no_image.jpg"; // デフォルトの画像名
			Part filePart = request.getPart("image"); // 画像のパラメータを受け取る

			// 画像ファイルが選択されている場合のみ保存処理を行う
			if (filePart != null && filePart.getSize() > 0) {
				// 正規表現を使ってパラメータからファイル名を抽出
				String contentDisposition = filePart.getHeader("content-disposition");
				Pattern pattern = Pattern.compile("filename=\"(.*)\"");
				Matcher matcher = pattern.matcher(contentDisposition);

				if (matcher.find()) {
					imageName = new File(matcher.group(1)).getName();
				}

				// 保存先のディレクトリを設定する
				String saveDirectory = getServletContext().getRealPath("/image");
				
				File dir = new File(saveDirectory);
				if (!dir.exists()) {
					dir.mkdirs(); // フォルダが無ければ作成
				}

				String filePath = saveDirectory + File.separator + imageName;

				// InputStreamとFiles.copyを利用してサーバーに保存する
				try (InputStream inputStream = filePart.getInputStream()) {
					Files.copy(inputStream, new File(filePath).toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				}
			}

			// 入力チェックを通過した場合の正常処理
			int price = Integer.parseInt(priceStr);

			Book book = new Book();
			book.setIsbn(isbn);
			book.setTitle(title);
			book.setPrice(price);
			book.setImage(imageName);
			dao.insert(book);

		} catch (NumberFormatException e) {
			error = "価格の値が不正の為、書籍登録処理は行えませんでした。";
			path = "/view/error.jsp";
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、書籍登録処理は行えませんでした。";
			path = "/view/error.jsp";
			cmd = "menu";
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		} finally {
			// 例外が発生してもしなくても、最後に1回だけまとめてフォワード処理を行う
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher(path).forward(request, response);
			}
			// 一覧画面へリダイレクトする
			response.sendRedirect(request.getContextPath() + path);
		}
	}
}
