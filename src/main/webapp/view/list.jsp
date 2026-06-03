<%--
プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：list.jsp
 プログラムの説明：データベースから取得した書籍一覧を表示する画面。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.ArrayList,bean.Book, util.MyFormat"%>
<%@page import="bean.User"%>
<%
// 仕様書通りのセッションチェック記述例
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>
<%
ArrayList<Book> list = (ArrayList<Book>) request.getAttribute("book_list");
MyFormat format = new MyFormat();
%>
<!DOCTYPE html>
<html>
<head>
<title>書籍一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> <a
				href="<%=request.getContextPath()%>/view/insert.jsp">[書籍登録]</a>
		</div>
		<div class="nav-header-title">
			<h2 class="title">書籍一覧</h2>
		</div>
	</div>

	<hr align="center" size="2" color="black" width="100%">

	<div class="form-layout">

		<form action="<%=request.getContextPath()%>/search" method="get"
			class="form-inline">
			ISBN:<input type="text" name="isbn" class="input-text-border-gray">
			TITLE:<input type="text" name="title" class="input-text-border-gray">
			価格:<input type="text" name="price" class="input-text-border-gray">
			<input type="submit" value="検索">
		</form>

		<form action="<%=request.getContextPath()%>/list" method="get"
			class="form-inline">
			<input type="submit" value="全件表示">
		</form>

	</div>
	<table align="center" class="form-table-80">
		<tr>
			<th class="header-color">ISBN</th>
			<th class="header-color">TITLE</th>
			<th class="header-color">価格</th>
			<th class="header-color">変更/削除/カートに入れる</th>
		</tr>
		<%
		if (list != null) {
			for (Book b : list) {
		%>
		<tr>
			<td><a
				href="<%=request.getContextPath()%>/detail?isbn=<%=b.getIsbn()%>&cmd=detail"><%=b.getIsbn()%></a></td>
			<td><%=b.getTitle()%></td>
			<td><%=format.moneyFormat(b.getPrice())%></td>
			<td>
				<a href="<%=request.getContextPath()%>/detail?isbn=<%=b.getIsbn()%>&cmd=update">変更</a>
				<b>&emsp;&emsp;</b>
				<a href="<%=request.getContextPath()%>/delete?isbn=<%=b.getIsbn()%>">削除</a>
				<b>&emsp;&emsp;</b>
				<form action="<%=request.getContextPath()%>/insertIntoCart" method="get" style="display:inline; margin:0; padding:0;">
					<input type="hidden" name="isbn" value="<%=b.getIsbn()%>">
					<input type="text" name="quantity" size="3" value="1">
					<input type="submit" value="カートに入れる">
				</form>
			</td>
		</tr>
		<%
		}
		}
		%>
	</table>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>