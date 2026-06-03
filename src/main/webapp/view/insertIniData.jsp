<%--
プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：insertIniData.jsp
 プログラムの説明：初期データを登録し、確認画面を表示する。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
  --%>

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
	<div class="nav-header">
		<div class="nav-header-links">
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニュー]</a>
        </div>
        <h2 class="title">初期データ登録</h2>
    </div>
    <hr align="center" size="2" color="black" width="100%">
    
    <p align="center">初期データとして以下のデータを登録しました。</p>
    
    <table align="center" border="1" class="form-table-80">
        <tr class="header-color">
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