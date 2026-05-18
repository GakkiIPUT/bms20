<%--
プロジェクト名：書籍管理システムWeb版Ver1.0
 プログラム名：list.jsp
 プログラムの説明：データベースから取得した書籍一覧を表示する画面。
 作成日：2026年5月12日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.ArrayList,bean.Book, util.MyFormat"%>
<%
ArrayList<Book> list = (ArrayList<Book>) request.getAttribute("book_list");
MyFormat format = new MyFormat();
%>
<html>
<head>
<title>書籍一覧</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<div
		style="position: relative; text-align: center; width: 100%; padding: 0px;">
		<div
			style="position: absolute; left: 40px; top: 50%; transform: translateY(-50%);">
			<a href="<%=request.getContextPath()%>/view/menu.jsp" style="margin-right: 20px;">[メニュー]</a>
			<a href="<%=request.getContextPath()%>/view/insert.jsp">[書籍登録]</a>
		</div>
		<div>
			<h2>書籍一覧</h2>

		</div>
	</div>

	<hr align="center" size="2" color="black" width="100%">

	<div
		style="display: flex; justify-content: center; align-items: center; gap: 10px;">

		<form action="<%=request.getContextPath()%>/search" method="get"
			style="margin: 0;">
			ISBN:<input type="text" name="isbn" style="border-color:#8E8E8E"> 
			TITLE:<input type="text"name="title" style="border-color:#8E8E8E"> 
			価格:<input type="text" name="price" style="border-color:#8E8E8E"> 
			<input type="submit" value="検索" style="background-color:#FFFFFF ; border-radius: 5px">
		</form>

		<form action="<%=request.getContextPath()%>/list" method="get"
			style="margin: 0;">
			<input type="submit" value="全件表示" style="background-color:#FFFFFF ; border-radius: 5px">
		</form>

	</div>
	<table align="center" style="width: 80%; table-layout: fixed;">
		<tr>
			<th style="background-color: #5679E7; color: #000000">ISBN</th>
			<th style="background-color: #5679E7; color: #000000">TITLE</th>
			<th style="background-color: #5679E7; color: #000000">価格</th>
			<th style="background-color: #5679E7; color: #000000">変更/削除/カートに入れる</th>
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
			<td><a
				href="<%=request.getContextPath()%>/detail?isbn=<%=b.getIsbn()%>&cmd=update">変更</a>
				<a >&emsp;&emsp;</a>
				<a href="<%=request.getContextPath()%>/delete?isbn=<%=b.getIsbn()%>">削除</a>
				<a >&emsp;&emsp;</a>
				<a href="<%=request.getContextPath()%>/insertIntoCart?isbn=<%=b.getIsbn()%>">カートに入れる</a>
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