<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, bean.Book, util.MyFormat" %>
<%
    ArrayList<Book> list = (ArrayList<Book>) request.getAttribute("book_list");
    MyFormat mf = new MyFormat();
    int total = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購入品確認</title>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    
    <div style="text-align: center;">
        <div style="float: left; margin-left: 40px;">
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニュー]</a>
            <a href="<%= request.getContextPath() %>/list">[書籍一覧]</a>
        </div>
        <h2>購入品確認</h2>
    </div>
    <hr align="center" size="2" color="black" width="100%">
    
    <p align="center">下記の商品を購入しました。<br>ご利用ありがとうございました。</p>
    
    <table align="center" border="1" style="width: 50%; border-collapse: collapse;">
        <tr style="background-color: #5679E7;">
            <th>ISBN</th>
            <th>TITLE</th>
            <th>価格</th>
        </tr>
        <% 
        if(list != null) { 
            for(Book b : list) {
                total += b.getPrice();
        %>
        <tr>
            <td align="center"><%= b.getIsbn() %></td>
            <td><%= b.getTitle() %></td>
            <td align="right"><%= mf.moneyFormat(b.getPrice()) %></td>
        </tr>
        <% 
            }
        } 
        %>
        <tr>
            <td colspan="2" align="center" style="background-color: #5679E7;">合計</td>
            <td align="right"><%= mf.moneyFormat(total) %></td>
        </tr>
    </table>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>