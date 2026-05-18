<%--
プロジェクト名：書籍管理システムWeb版Ver1.0
 プログラム名：insert.jsp
 プログラムの説明：書籍を新規登録するための入力画面。
 作成日：2026年5月12日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>書籍登録</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div
		style="position: relative; text-align: center; width: 100%; padding: 0px;">
		<div
			style="position: absolute; left: 40px; top: 50%; transform: translateY(-50%);">
			<a href="<%=request.getContextPath()%>/view/menu.jsp"style="margin-right: 20px;">[メニュー]</a> 
			<a href="<%=request.getContextPath()%>/list">[書籍一覧]</a>
		</div>
		<div>
			<h2>書籍登録</h2>

		</div>
	</div>

	<hr align="center" size="2" color="black" width="100%">
	<form action="<%=request.getContextPath()%>/insert" method="get">
		<table align="center" style="width: 50%; table-layout: fixed;">
			<tr>
				<td
					style="background-color: #5679E7; color: #000000; width: 30%; text-align: center;">ISBN</td>
				<td><input type="text" name="isbn" style="width: 90%;"></td>
			</tr>
			<tr>
				<td
					style="background-color: #5679E7; color: #000000; text-align: center;">TITLE</td>
				<td><input type="text" name="title" style="width: 90%;"></td>
			</tr>
			<tr>
				<td
					style="background-color: #5679E7; color: #000000; text-align: center;">価格</td>
				<td><input type="text" name="price" style="width: 90%;"></td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top: 15px;"><input
					type="submit" value="登録" style="background-color:#FFFFFF ; border-radius: 5px"></td>
					
			</tr>
		</table>
	</form>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>