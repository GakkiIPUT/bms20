/*
 * プロジェクト名：書籍管理システムWeb版Ver4.0
 * プログラム名：CreateGraphServlet.java
 * プログラムの説明：月別売上データを集計し、JFreeChartを用いてグラフ画像（PNG）を生成するサーブレット。
 * 作成日：2026年6月10日
 * 作成者：髙垣湧侑翔
*/
package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import bean.Sale;
import dao.SaleDAO;

@WebServlet("/graph")
public class GraphServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String dispDate = request.getParameter("dispDate");
        
        String error = null;
        String path = "/view/graph.jsp"; // 成功時の遷移先

        try {
            // 1. 受け取ったパラメータを正規表現で年と月に抽出
            Pattern pattern = Pattern.compile("(\\d{4})年(\\d{1,2})月");
            Matcher matcher = pattern.matcher(dispDate);
            
            String year = "";
            String month = "";
            if (matcher.find()) {
                year = matcher.group(1);
                month = matcher.group(2);
            }

            // 2. パラメータを元にDBからデータを取得する
            SaleDAO dao = new SaleDAO();
            ArrayList<Sale> list = dao.selectByYearMonth(year, month);
            
            // 保存先ディレクトリの設定（webapp/graph）
            String graphDir = getServletContext().getRealPath("/graph");
            File dir = new File(graphDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // ファイル名とパスの設定
            String fileName = "sales_list_" + year + "_" + month + ".png";
            String graphPath = graphDir + File.separator + fileName;

            // 3. ファイルの有無を判定してある場合は削除する
            File file = new File(graphPath);
            if (file.exists()) {
                file.delete();
            }

            // 4. 日本語を利用できるようにレガシーテーマを設定
            ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());

            // 5. グラフのデータを設定して生成する
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Sale sale : list) {
                // 売上げ小計(または合計)とISBNをセット
                dataset.addValue(sale.getAmount(), "売り上げ小計", sale.getIsbn());
            }

            // 棒グラフを作成
            JFreeChart chart = ChartFactory.createBarChart(
                year + "年" + month + "月 売上げ状況", // タイトル
                "ISBN",                           // X軸ラベル
                "売り上げ小計",                     // Y軸ラベル
                dataset,                          // データセット
                PlotOrientation.VERTICAL,         // グラフの向き
                true,                             // 凡例を表示
                false,                            // ツールチップを表示
                false                             // URLを表示
            );

            // 6. 画像ファイルとして保存する
            ChartUtilities.saveChartAsPNG(file, chart, 600, 400);

            // 7. JSPへ渡すパラメータをリクエストスコープにセット
            request.setAttribute("year", year);
            request.setAttribute("month", month);
            request.setAttribute("fileName", fileName);

        } catch (Exception e) {
            e.printStackTrace();
            error = "グラフ生成中にエラーが発生しました：" + e.getMessage();
            path = "/view/error.jsp";
            request.setAttribute("error", error);
        }

        request.getRequestDispatcher(path).forward(request, response);
    }
}