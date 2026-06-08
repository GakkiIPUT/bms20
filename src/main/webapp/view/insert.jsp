<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：insert.jsp
 プログラムの説明：書籍登録画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
<%@page contentType="text/html; charset=UTF-8"%>
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
<!DOCTYPE html>
<html>
<head>
<title>書籍登録</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> <a
				href="<%=request.getContextPath()%>/list">[書籍一覧]</a>
		</div>
		<div class="nav-header-title">
			<h2 class="title">書籍登録</h2>
		</div>
	</div>

	<hr align="center" size="2" color="black" width="100%">
	<form action="<%=request.getContextPath()%>/insert" method="post"
		enctype="multipart/form-data">
		<table align="center" class="form-table">
			<tr>
				<td class="header-color">ISBN</td>
				<td><input type="text" name="isbn" class="form-input-full"></td>
			</tr>
			<tr>
				<td class="header-color">TITLE</td>
				<td><input type="text" name="title" class="form-input-full"></td>
			</tr>
			<tr>
				<td class="header-color">価格</td>
				<td><input type="text" name="price" class="form-input-full"></td>
			</tr>
			<tr>
				<td class="header-color">画像</td>
				<td><input type="file" name="image" class="form-input-full"></td>
			</tr>
			<tr>
				<td colspan="2" align="center" class="form-padding-top"><input
					type="submit" value="登録"></td>

			</tr>
		</table>
	</form>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>