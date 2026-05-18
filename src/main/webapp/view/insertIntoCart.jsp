<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.Book, util.MyFormat" %>
<%
    Book book = (Book) request.getAttribute("book");
    MyFormat mf = new MyFormat();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カート追加</title>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    <div style="text-align: center;">
        <div style="float: left; margin-left: 40px;">
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニュー]</a>
            <a href="<%= request.getContextPath() %>/list">[書籍一覧]</a>
        </div>
        <h2>カート追加</h2>
    </div>
    <hr align="center" size="2" color="black" width="100%">
    
    <p align="center">下記の書籍をカートに追加しました。</p>
    
    <table align="center" border="1" style="width: 40%; border-collapse: collapse;">
        <tr>
            <td style="background-color: #5679E7;">ISBN</td>
            <td><%= book.getIsbn() %></td>
        </tr>
        <tr>
            <td style="background-color: #5679E7;">TITLE</td>
            <td><%= book.getTitle() %></td>
        </tr>
        <tr>
            <td style="background-color: #5679E7;">価格</td>
            <td><%= mf.moneyFormat(book.getPrice()) %></td>
        </tr>
    </table>
    
    <div align="center" style="margin-top: 20px;">
        <form action="<%= request.getContextPath() %>/showCart">
            <input type="submit" value="カート確認" style="border-radius: 5px;">
        </form>
    </div>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>