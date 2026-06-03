<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：update.jsp
 プログラムの説明：書籍情報の更新画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="bean.Book, util.MyFormat"%>
<%@page import="bean.User"%>
<%

    User user = (User)session.getAttribute("user");
    if(user == null){
        request.setAttribute("error","セッション切れの為、メニュー画面が表示できませんでした。");
        request.setAttribute("cmd","logout");
        request.getRequestDispatcher("/view/error.jsp").forward(request, response);
        return;
    }
%>
<%
Book book = (Book) request.getAttribute("book");
MyFormat format = new MyFormat();
%>

<!DOCTYPE html>
<html>
<head>
<title>書籍変更</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a>
			<a href="<%=request.getContextPath()%>/view/insert.jsp">[書籍登録]</a>
			<a href="<%=request.getContextPath()%>/list">[書籍一覧]</a>
		</div>
		<div class="nav-header-title">
			<h2 class="title">書籍変更</h2>
		</div>
	</div>

	<hr align="center" size="2" color="black" width="100%">

	<form action="<%=request.getContextPath()%>/update" method="get">
		<input type="hidden" name="isbn" value="<%=book.getIsbn()%>">
		<br>
		<table align="center">
			<tr>
				<th class="table-header-width-10"></th>
				<th class="table-header-width-30"><p>&lt;&lt;変更前情報&gt;&gt;</p></th>
				<th class="table-header-width-5"></th>
				<th class="table-header-width-30"><p>&lt;&lt;変更後情報&gt;&gt;<p></th>
			</tr>
			<tr>
				<td class="header-color">ISBN</td>
				<td align="center" class="form-row-info"><%=book.getIsbn()%></td>
				<td></td>
				<td align="center"><%=book.getIsbn()%></td>
			</tr>
			<tr>
				<td class="header-color">TITLE</td>
				<td align="center" class="form-row-info"><%=book.getTitle()%></td>
				<td></td>
				<td align="center"><input type="text" name="title" value=""></td>
			</tr>
			<tr>
				<td class="header-color">価格</td>
				<td align="center" class="form-row-info"><%=format.moneyFormat(book.getPrice())%></td>
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
					value="変更完了"></td>
			</tr>
		</table>
	</form>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>