<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, bean.Book, util.MyFormat" %>
<%
    ArrayList<Book> list = (ArrayList<Book>) request.getAttribute("book_list");
    MyFormat mf = new MyFormat();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>初期データ登録</title>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    <div style="text-align: center;">
        <div style="float: left; margin-left: 40px;">
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニュー]</a>
        </div>
        <h2>初期データ登録</h2>
    </div>
    <hr align="center" size="2" color="black" width="100%">
    
    <p align="center">初期データとして以下のデータを登録しました。</p>
    
    <table align="center" border="1" style="width: 80%; border-collapse: collapse;">
        <tr style="background-color: #5679E7;">
            <th>ISBN</th>
            <th>TITLE</th>
            <th>価格</th>
        </tr>
        <% if(list != null) { 
            for(Book b : list) { %>
        <tr>
            <td><%= b.getIsbn() %></td>
            <td><%= b.getTitle() %></td>
            <td><%= mf.moneyFormat(b.getPrice()) %></td>
        </tr>
        <% } } %>
    </table>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>