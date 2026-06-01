<%--
プロジェクト名：書籍管理システムWeb版Ver1.0
 プログラム名：menu.jsp
 プログラムの説明：各機能(主に一覧と登録)への導線となるメニュー画面。
 作成日：2026年5月12日
 更新日：2026年5月19日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>メニュー</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h2 align="center">MENU</h2>
	<hr align="center" size="2" color="black" width="100%">
	<table align="center">
		<tr>
			<td><a href="<%=request.getContextPath()%>/list">【書籍一覧】</a></td>
		</tr>
		<tr>
			<td><a href="<%=request.getContextPath()%>/view/insert.jsp">【書籍登録】</a></td>
		</tr>
	</table>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>