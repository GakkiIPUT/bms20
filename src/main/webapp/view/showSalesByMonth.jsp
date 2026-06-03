<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Sale"%>
<%@page import="bean.User"%>
<%@page import="util.MyFormat"%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
        request.setAttribute("cmd", "logout");
        request.getRequestDispatcher("/view/error.jsp").forward(request, response);
        return;
    }

    // 検索結果と入力パラメータを受け取る
    ArrayList<Sale> list = (ArrayList<Sale>) request.getAttribute("sale_list");
    String year = request.getParameter("year");
    String month = request.getParameter("month");
    MyFormat mf = new MyFormat();
    int total = 0; // 総合計計算用
%>
<html>
<head>
<title>売上げ状況</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<table align="center" width="800">
        <tr>
            <td width="80" align="center"><a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a></td>
            <td width="640" align="center"><h2>売上げ状況</h2></td>
            <td width="80" align="center"></td>
        </tr>
    </table>
    <hr align="center" size="2" color="black" width="800">

    <table align="center">
        <tr><td><%--@ include file="/common/userInfo.jsp" --%></td></tr>
    </table>

    <form action="<%=request.getContextPath()%>/showSalesByMonth" method="get">
        <table align="center" style="margin-top: 15px;">
            <tr>
                <td><input type="text" name="year" value="<%= year != null ? year : "" %>" size="5">年 </td>
                <td><input type="text" name="month" value="<%= month != null ? month : "" %>" size="5">月 </td>
                <td><input type="submit" value="検索"></td>
            </tr>
        </table>
    </form>

    <% if (list != null) { %>
    <table align="center" width="800" style="margin-top: 20px;">
        <tr><td align="center"><h3><%= year %>年<%= month %>月売上げ状況</h3></td></tr>
    </table>

    <table align="center" width="700">
        <tr>
            <td class="header-blue" align="center" width="100">ISBN</td>
            <td class="header-blue" align="center" width="250">Title</td>
            <td class="header-blue" align="center" width="100">価格</td>
            <td class="header-blue" align="center" width="100">
数量</td>
            <td class="header-blue" align="center" width="150">売上げ小計</td>
        </tr>
        <% 
            // 拡張for文でリストを回し、同時に合計金額を加算する
            for(Sale sale : list) { 
                total += sale.getAmount();
        %>
        <tr>
            <td align="center"><%= sale.getIsbn() %></td>
            <td align="center"><%= sale.getTitle() %></td>
            <td align="right"><%= mf.moneyFormat(sale.getPrice()) %></td>
            <td align="center"><%= sale.getQuantity() %></td>
            <td align="right"><%= mf.moneyFormat(sale.getAmount()) %></td>
        </tr>
        <% } %>
    </table>

    <table align="center" width="700" style="margin-top: 10px;">
        <tr>
            <td align="right" width="500"><strong>合計</strong></td>
            <td align="right" width="200"><strong><%= mf.moneyFormat(total) %></strong></td>
        </tr>
    </table>
    <% } %>

    <%@ include file="/common/footer.jsp" %>
</body>
</html>