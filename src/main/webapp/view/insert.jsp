<%--
プロジェクト名：書籍管理システムWeb版Ver2.0
 プログラム名：insert.jsp
 プログラムの説明：書籍を新規登録するための入力画面。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>書籍登録</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> 
			<a href="<%=request.getContextPath()%>/list">[書籍一覧]</a>
		</div>
		<div class="nav-header-title">
			<h2>書籍登録</h2>
		</div>
	</div>

	<hr align="center" size="2" color="black" width="100%">
	<form action="<%=request.getContextPath()%>/insert" method="get">
		<table align="center" class="form-table">
			<tr>
				<td class="form-row-header">ISBN</td>
				<td><input type="text" name="isbn" class="form-input-full"></td>
			</tr>
			<tr>
				<td class="form-row-header-full">TITLE</td>
				<td><input type="text" name="title" class="form-input-full"></td>
			</tr>
			<tr>
				<td class="form-row-header-full">価格</td>
				<td><input type="text" name="price" class="form-input-full"></td>
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