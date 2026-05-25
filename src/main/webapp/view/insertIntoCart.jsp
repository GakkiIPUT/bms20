<%--
プロジェクト名：書籍管理システムWeb版Ver2.0
 プログラム名：insertIntoCart.jsp
 プログラムの説明：書籍をカートに追加し、確認画面を表示する。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
  --%>

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
	<div class="nav-header">
		<div class="nav-header-links">
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニュー]</a>
            <a href="<%= request.getContextPath() %>/list">[書籍一覧]</a>
        </div>
        <h2>カート追加</h2>
    </div>
    <hr align="center" size="2" color="black" width="100%">
    
    <p align="center">下記の書籍をカートに追加しました。</p>
    
    <table align="center" border="1" class="form-table-40">
        <tr>
            <td class="form-row-header">ISBN</td>
            <td><%= book.getIsbn() %></td>
        </tr>
        <tr>
            <td class="form-row-header">TITLE</td>
            <td><%= book.getTitle() %></td>
        </tr>
        <tr>
            <td class="form-row-header">価格</td>
            <td><%= mf.moneyFormat(book.getPrice()) %></td>
        </tr>
    </table>
    
    <div align="center" class="form-padding-top">
        <form action="<%= request.getContextPath() %>/showCart">
            <input type="submit" value="カート確認">
        </form>
    </div>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>