<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：insertIntoCart.jsp
 プログラムの説明：カート追加内容の表示画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.Book, util.MyFormat"%>
<%@page import="bean.User"%>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>
<%
Book book = (Book) request.getAttribute("book");
// Servletから送られてきた数量(quantity)を受け取る
Integer quantity = (Integer) request.getAttribute("quantity");
if(quantity == null) {
	quantity = 1; // 万が一のための保険
}
MyFormat mf = new MyFormat();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カート追加</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%><main>
	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> <a
				href="<%=request.getContextPath()%>/list">[書籍一覧]</a>
		</div>
		<h2 class="title">カート追加</h2>
	</div>
	<hr align="center" size="2" color="black" width="100%">

	<p align="center">下記の書籍をカートに追加しました。</p>

	<table align="center" border="1" class="form-table-40">
		<tr>
			<td class="header-color">ISBN</td>
			<td><%=book.getIsbn()%></td>
		</tr>
		<tr>
			<td class="header-color">TITLE</td>
			<td><%=book.getTitle()%></td>
		</tr>
		<tr>
			<td class="header-color">価格</td>
			<td><%=mf.moneyFormat(book.getPrice())%></td>
		</tr>
		<tr>
			<td class="header-color">購入数</td>
			<td><%=quantity%></td>
		</tr>
	</table>

	<form action="<%=request.getContextPath()%>/showCart" method="get">
		<div align="center" class="form-padding-top">
			<input type="submit" value="カート確認">
		</div>
	</form>

	</main><%@ include file="/common/footer.jsp"%>
</body>
</html>