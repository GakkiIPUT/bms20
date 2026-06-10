/*
 * プロジェクト名：書籍管理システムWeb版Ver4.0
 * プログラム名：CreatePdfServlet.java
 * プログラムの説明：書籍一覧のPDFを中心揃えで出力するサーブレットクラス。
 * 作成日：2026年6月10日
 * 作成者：髙垣湧侑翔
*/
package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import bean.Book;
import bean.User;
import dao.BookDAO;

/**
 * 書籍一覧データをPDFファイルとして中央レイアウトで生成し、出力します。
 */
@WebServlet("/createPdf")
public class CreatePdfServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// セッション切れチェック
		if (user == null) {
			request.setAttribute("error", "セッション切れの為、PDF出力はできません。");
			request.setAttribute("cmd", "logout");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}

		BookDAO bookDao = new BookDAO();
		ArrayList<Book> list = bookDao.selectAll();

		// レスポンスのコンテンツタイプとファイル名を設定
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"booklist.pdf\"");

		try (PDDocument document = new PDDocument()) {
			PDPage page = new PDPage();
			document.addPage(page);

			// 日本語を使えるようにttfファイルを読み込む
			String fontPath = getServletContext().getRealPath("/font/GenShinGothic-Medium.ttf");
			File fontFile = new File(fontPath);
			if (!fontFile.exists()) {
				throw new Exception("フォントファイルが見つかりません。配置場所を確認してください: " + fontPath);
			}
			PDFont font = PDType0Font.load(document, new File(fontPath));

			try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

				// ページ幅の取得（A4サイズの横幅）
				float pageWidth = page.getMediaBox().getWidth();

				// ==========================================
				// タイトル「書籍管理システム  書籍リスト」を中央に描画
				// ==========================================
				String title = "書籍管理システム  書籍リスト";
				float titleFontSize = 24;
				// 文字列の幅を計算
				float titleWidth = font.getStringWidth(title) / 1000 * titleFontSize;
				// ページ幅から文字列の幅を引いて2で割ると、中央のX座標が求まる
				float titleStartX = (pageWidth - titleWidth) / 2;
				float currentY = 700;

				contentStream.setFont(font, titleFontSize);
				contentStream.beginText();
				contentStream.newLineAtOffset(titleStartX, currentY);
				contentStream.showText(title);
				contentStream.endText();

				// ==========================================
				// テーブルの列幅と中央位置の計算
				// ==========================================
				float[] columnWidths = { 100, 250, 100 }; // 各列の幅（ISBN, TITLE, 価格）
				float tableWidth = 0;
				for (float w : columnWidths) {
					tableWidth += w;
				}

				// テーブル全体の中央のX座標を計算
				float tableStartX = (pageWidth - tableWidth) / 2;
				
				// ==========================================
				// タイトルとテーブルの間に線を引く
				// ==========================================
				currentY -= 20; // タイトルから少し下げる
				contentStream.setLineWidth(1.0f); // 線の太さ
				contentStream.moveTo(tableStartX, currentY); // 左端から
				contentStream.lineTo(tableStartX + tableWidth, currentY); // 右端まで
				contentStream.stroke(); // 線を描画する

				// ==========================================
				// テーブル（表）のテキストを中央に描画
				// ==========================================
				currentY -= 40; // タイトルから少し下にずらす
				float rowHeight = 30; // 行の高さ
				float fontSize = 12;

				contentStream.setFont(font, fontSize);

				// ヘッダー行の描画
				drawRow(contentStream, tableStartX, currentY, columnWidths, rowHeight, font, fontSize,
						new String[] { "ISBN", "書籍名", "価格" }, true);
				currentY -= rowHeight;

				// データ行の描画
				for (Book book : list) {
					String priceStr = String.format("%,d円",book.getPrice());

					// ページの下端に到達したら新しいページを追加する処理
					if (currentY < 50) {
						contentStream.close();
						page = new PDPage();
						document.addPage(page);
					}

					drawRow(contentStream, tableStartX, currentY, columnWidths, rowHeight, font, fontSize,
							new String[] { book.getIsbn(), book.getTitle(), priceStr }, false);

					currentY -= rowHeight;
				}
			}

			// PDFをレスポンスに直接書き込む
			document.save(response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "PDF出力処理中にエラーが発生しました。");
			request.setAttribute("cmd", "menu");
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}

	/**
	 * テーブルの1行分（枠線とテキスト）を描画するヘルパーメソッドです。
	 */
	private void drawRow(PDPageContentStream contentStream, float startX, float startY, float[] columnWidths,
			float rowHeight, PDFont font, float fontSize, String[] text, boolean isHeader) throws IOException {

		float currentX = startX;

		// セルの枠線を描画する
		for (int i = 0; i < columnWidths.length; i++) {
			contentStream.addRect(currentX, startY - rowHeight, columnWidths[i], rowHeight);
			currentX += columnWidths[i];
		}
		contentStream.stroke(); // 枠線を確定

		// セルの中にテキストを描画する
		currentX = startX;
		for (int i = 0; i < text.length; i++) {
			String cellText = text[i];
			if (cellText == null)
				cellText = "";

			// テキストの幅を計算
			float textWidth = font.getStringWidth(cellText) / 1000 * fontSize;

			float textStartX = 0;

			// 中央揃え、左揃え、右揃えの配置ロジック
			if (isHeader) {
				// ヘッダーはすべて中央揃え
				textStartX = currentX + (columnWidths[i] - textWidth) / 2;
			} else {
				if (i == 0) {
					// ISBN: 中央揃え
					textStartX = currentX + (columnWidths[i] - textWidth) / 2;
				} else if (i == 1) {
					// TITLE: 左揃え (左から10pxの余白)
					textStartX = currentX + 10;
				} else {
					// 価格: 右揃え (右から10pxの余白)
					textStartX = currentX + columnWidths[i] - textWidth - 10;
				}
			}

			// テキストのY座標（セルの縦方向の中央付近）
			float textStartY = startY - rowHeight + (rowHeight - fontSize) / 2 + 2;

			// テキストの書き込み
			contentStream.beginText();
			contentStream.newLineAtOffset(textStartX, textStartY);
			contentStream.showText(cellText);
			contentStream.endText();

			currentX += columnWidths[i];
		}
	}
}