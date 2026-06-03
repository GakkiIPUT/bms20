<%--
プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：detail.jsp
 プログラムの説明：書籍の1件詳細情報を表示する画面。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="bean.Book, util.MyFormat"%>
<%
Book book = (Book) request.getAttribute("book");
MyFormat format = new MyFormat();
%>
<!DOCTYPE html>
<html>
<head>
<title>書籍詳細情報</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp" >[メニュー]</a>
			<a href="<%=request.getContextPath()%>/view/insert.jsp" >[書籍登録]</a>
			<a href="<%=request.getContextPath()%>/list">[書籍一覧]</a>
		</div>
		<div class="nav-header-title">
			<h2 class="title">書籍詳細情報</h2>
		</div>
	</div>
	<hr align="center" size="2" color="black" width="100%">
	<br>

	<table align="center" class="form-table-30">
		<tr>
			<td colspan="2" align="center">
				<form action="<%=request.getContextPath()%>/detail" method="get"
					class="form-inline">
					<input type="hidden" name="isbn" value="<%=book.getIsbn()%>">
					<input type="hidden" name="cmd" value="update"> <input
						type="submit" value="変更">
				</form>
				<a>&emsp;&emsp;</a>
				<form action="<%=request.getContextPath()%>/delete" method="get"
					class="form-inline">
					<input type="hidden" name="isbn" value="<%=book.getIsbn()%>">
					<input type="submit" value="削除">
				</form>
			</td>
		</tr>
		<tr>
			<td><br></td>
			<td><br></td>

		</tr>
		<tr>
			<td class="header-color">ISBN</td>
			<td class="column-color"><%=book.getIsbn()%></td>
		</tr>
		<tr>
			<td class="header-color">TITLE</td>
			<td class="column-color"><%=book.getTitle()%></td>
		</tr>
		<tr>
			<td class="header-color">価格</td>
			<td class="column-color"><%=format.moneyFormat(book.getPrice())%></td>
		</tr>

	</table>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>