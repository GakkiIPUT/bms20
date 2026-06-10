/*
 * プロジェクト名：書籍管理システムWeb版Ver4.0
 * プログラム名：CreateExcelServlet.java
 * プログラムの説明：書籍一覧データをExcelファイル（.xlsx）に変換してダウンロード出力するサーブレット。
 * 作成日：2026年6月10日
 * 作成者：髙垣湧侑翔
*/
package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bean.Book;
import bean.User;
import dao.BookDAO;

/**
 * 書籍一覧データをExcelに出力するサーブレットです。
 * 一度サーバー上にファイルを生成してからダウンロードさせます。
 */
@WebServlet("/createExcel")
public class CreateExcelServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// セッション切れチェック
		if (user == null) {
			request.setAttribute("error", "セッション切れの為、Excel出力はできません。");
			request.setAttribute("cmd", "logout");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}

		String filePath = null;

		try {
			// 1. 書籍一覧をBookDAOを利用して取得
			BookDAO bookDao = new BookDAO();
			ArrayList<Book> list = bookDao.selectAll();

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("書籍一覧");

			// ==========================================
			// 1. スタイルの作成
			// ==========================================
			// ① ヘッダー用のスタイル（太字、薄水色背景、枠線）
			CellStyle headerStyle = workbook.createCellStyle();

			// 背景色を薄水色に設定
			headerStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// 上下左右に細い枠線を設定
			headerStyle.setBorderTop(BorderStyle.THIN);
			headerStyle.setBorderBottom(BorderStyle.THIN);
			headerStyle.setBorderLeft(BorderStyle.THIN);
			headerStyle.setBorderRight(BorderStyle.THIN);

			// フォントを太字に設定
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerStyle.setFont(headerFont);

			// ② データ行用のスタイル（枠線のみ）
			CellStyle dataStyle = workbook.createCellStyle();
			dataStyle.setBorderTop(BorderStyle.THIN);
			dataStyle.setBorderBottom(BorderStyle.THIN);
			dataStyle.setBorderLeft(BorderStyle.THIN);
			dataStyle.setBorderRight(BorderStyle.THIN);

			// ==========================================
			// 2. ヘッダー行の書き込み（スタイル適用）
			// ==========================================
			Row headerRow = sheet.createRow(0);
			String[] headers = { "ISBN", "TITLE", "価格" };
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(headerStyle); // ヘッダースタイルをセット
			}

			// ==========================================
			// 3. データ行の書き込み（スタイル適用）
			// ==========================================
			int rowIndex = 1;
			for (Book book : list) {
				Row row = sheet.createRow(rowIndex++);

				Cell cellIsbn = row.createCell(0);
				cellIsbn.setCellValue(book.getIsbn());
				cellIsbn.setCellStyle(dataStyle);

				Cell cellTitle = row.createCell(1);
				cellTitle.setCellValue(book.getTitle());
				cellTitle.setCellStyle(dataStyle);

				Cell cellPrice = row.createCell(2);
				cellPrice.setCellValue(book.getPrice());
				cellPrice.setCellStyle(dataStyle);
			}

			// 列幅の見やすい調整
			sheet.setColumnWidth(0, 4000);
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 3000);

			// 4. 出力ファイルのフルパスを指定 (サーバーの一時領域を利用)
			File tempFile = File.createTempFile("booklist_", ".xlsx");
			filePath = tempFile.getAbsolutePath();

			// 5. ファイルパスをアウトプットストリームに変換してワークブックを保存
			try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
				workbook.write(fileOut);
			}
			workbook.close();

			// 6. レスポンスのコンテンツタイプ、ヘッダーにMIMEなどを設定
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"booklist.xlsx\"");

			// 7. ファイルパスをインプットストリームに変換し、レスポンスに書き込み
			try (FileInputStream inputStream = new FileInputStream(filePath);
					OutputStream outStream = response.getOutputStream()) {

				// 8. ファイルの内容をバッファーに読み込みストリームに書き込み
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
				outStream.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Excel出力処理中にエラーが発生しました。");
			request.setAttribute("cmd", "menu");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);

		} finally {
			// 9. リソースの解放 (読み終わったサーバー上の一時ファイルを削除)
			if (filePath != null) {
				File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}
}