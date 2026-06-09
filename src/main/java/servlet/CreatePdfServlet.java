package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import bean.Book;
import dao.BookDAO;

@WebServlet("/createPdf")
public class CreatePdfServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String error = null;
        String path = null;

        try {
            // 1. 書籍一覧をBooksDAOを利用して取得
            BookDAO dao = new BookDAO();
            ArrayList<Book> list = dao.selectAll();

            // PDFの保存先ディレクトリを設定（webapp/pdf フォルダ）
            String pdfDir = getServletContext().getRealPath("/pdf");
            File dir = new File(pdfDir);
            if (!dir.exists()) {
                dir.mkdirs(); // フォルダが無ければ作成
            }
            String pdfPath = pdfDir + File.separator + "book_list.pdf";

            // 2. Documentオブジェクトを利用してPDFのサイズを指定
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                // 日本語を使えるようにttfファイルを読み込む
                String fontPath = getServletContext().getRealPath("/font/arial-unicode-ms.ttf");
                File fontFile = new File(fontPath);
                if (!fontFile.exists()) {
                    throw new Exception("フォントファイルが見つかりません。配置場所を確認してください: " + fontPath);
                }
                PDType0Font font = PDType0Font.load(document, fontFile);

                // 3 & 4. PDFへの書き込み設定（コンテンツストリームの作成）
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(font, 12); // フォントとサイズを指定
                    contentStream.beginText();
                    
                    // 開始座標を指定（左から100、下から750の位置）
                    contentStream.newLineAtOffset(100, 750);
                    contentStream.setLeading(20f); // 改行時の行間を指定

                    // タイトルとヘッダーの書き込み
                    contentStream.showText("■ 書籍一覧リスト");
                    contentStream.newLine();
                    contentStream.newLine();
                    contentStream.showText("ISBN            TITLE                           価格");
                    contentStream.newLine();
                    contentStream.showText("--------------------------------------------------");
                    contentStream.newLine();

                    // 取得した書籍リストをループで1件ずつ書き込み
                    for (Book book : list) {
                        // 文字列の長さをある程度揃えて出力
                        String line = String.format("%-15s %-30s %d円", book.getIsbn(), book.getTitle(), book.getPrice());
                        contentStream.showText(line);
                        contentStream.newLine();
                    }

                    contentStream.endText();
                }
                
                // 5. 保存（ドキュメントをクローズして出力）
                document.save(pdfPath);
            }

            // エラーが無い場合、作成したPDFファイルへフォワードする
            path = "/pdf/book_list.pdf";

        } catch (Exception e) {
            e.printStackTrace();
            error = "PDF作成処理でエラーが発生しました：" + e.getMessage();
            path = "/view/error.jsp";
            request.setAttribute("error", error);
        }

        // フォワード処理
        request.getRequestDispatcher(path).forward(request, response);
    }
}