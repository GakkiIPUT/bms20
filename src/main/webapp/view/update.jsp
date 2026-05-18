<%--
プロジェクト名：書籍管理システムWeb版Ver1.0
 プログラム名：update.jsp
 プログラムの説明：特定の書籍情報を編集するための入力画面。
 作成日：2026年5月12日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="bean.Book, util.MyFormat"%>
<%
Book book = (Book) request.getAttribute("book");
MyFormat format = new MyFormat();

%>

<html>
<head>
<title>書籍変更</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div
		style="position: relative; text-align: center; width: 100%; padding: 0px;">
		<div
			style="position: absolute; left: 40px; top: 50%; transform: translateY(-50%);">
			<a href="<%=request.getContextPath()%>/view/menu.jsp"style="margin-right: 20px;">[メニュー]</a>
			<a href="<%=request.getContextPath()%>/view/insert.jsp"style="margin-right: 20px;">[書籍登録]</a>
			<a href="<%=request.getContextPath()%>/list">[書籍一覧]</a>
		</div>
		<div>
			<h2>書籍変更</h2>
		</div>
	</div>

	<hr align="center" size="2" color="green" width="100%">

	<form action="<%=request.getContextPath()%>/update" method="get">
		<input type="hidden" name="isbn" value="<%=book.getIsbn()%>">
		<br>
		<table align="center">
			<tr>
				<th style="width: 10%"></th>
				<th style="width: 30%">&lt;&lt;変更前情報&gt;&gt;</th>
				<th style="width: 5%"></th>
				<th style="width: 30%">&lt;&lt;変更後情報&gt;&gt;</th>
			</tr>
			<tr>
				<td style="background-color: #5679E7; color: #000000">ISBN</td>
				<td align="center" style="background-color: #66E4DD; color: #000000"><%=book.getIsbn()%></td>
				<td></td>
				<td align="center"><%=book.getIsbn()%></td>
			</tr>
			<tr>
				<td style="background-color: #5679E7; color: #000000">TITLE</td>
				<td align="center" style="background-color: #66E4DD; color: #000000"><%=book.getTitle()%></td>
				<td></td>
				<td align="center"><input type="text" name="title" value=""></td>
			</tr>
			<tr>
				<td style="background-color: #5679E7; color: #000000">価格</td>
				<td align="center" style="background-color: #66E4DD; color: #000000"><%=format.moneyFormat(book.getPrice())%></td>
				<td></td>
				<td align="center"><input type="text" name="price" value=""></td>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><input type="submit"
					value="変更完了" style="background-color:#FFFFFF ; border-radius: 5px"></td>
			</tr>
		</table>
	</form>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>