<%--
プロジェクト名：書籍管理システムWeb版Ver1.0
 プログラム名：detail.jsp
 プログラムの説明：書籍の1件詳細情報を表示する画面。
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
<title>書籍詳細情報</title>
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
			<h2>書籍詳細情報</h2>

		</div>
	</div>
	<hr align="center" size="2" color="black" width="100%">
	<br>

	<table align="center" style="width: 30%; table-layout: fixed;">
		<tr>
			<td colspan="2" align="center">
				<form action="<%=request.getContextPath()%>/detail" method="get"
					style="display: inline;">
					<input type="hidden" name="isbn" value="<%=book.getIsbn()%>">
					<input type="hidden" name="cmd" value="update"> <input
						type="submit" value="変更" style="background-color:#FFFFFF ; border-radius: 5px">
				</form>
				<a>&emsp;&emsp;</a>
				<form action="<%=request.getContextPath()%>/delete" method="get"
					style="display: inline;">
					<input type="hidden" name="isbn" value="<%=book.getIsbn()%>">
					<input type="submit" value="削除" style="background-color:#FFFFFF ; border-radius: 5px">
				</form>
			</td>
		</tr>
		<tr>
			<td><br></td>
			<td><br></td>

		</tr>
		<tr>
			<td style="background-color: #5679E7; color: #000000">ISBN</td>
			<td style="background-color: #66E4DD; color: #000000"><%=book.getIsbn()%></td>
		</tr>
		<tr>
			<td style="background-color: #5679E7; color: #000000">TITLE</td>
			<td style="background-color: #66E4DD; color: #000000"><%=book.getTitle()%></td>
		</tr>
		<tr>
			<td style="background-color: #5679E7; color: #000000">価格</td>
			<td style="background-color: #66E4DD; color: #000000"><%=format.moneyFormat(book.getPrice())%></td>
		</tr>

	</table>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>