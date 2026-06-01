<%--
プロジェクト名：書籍管理システムWeb版Ver1.0
 プログラム名：error.jsp
 プログラムの説明：システム内で発生したエラーメッセージを表示し、適切な画面へ誘導する。
 作成日：2026年5月12日
 更新日：2026年5月19日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
String error = (String) request.getAttribute("error");
String cmd = (String) request.getAttribute("cmd");
%>
<html>
<head>
<title>エラー</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h2 align="center">●●エラー●●</h2>
	<p align="center"><%=error%></p>
	<p align="center">
		<%
		if (cmd.equals("menu")) {
		%>
		<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニューに戻る]</a>
		<%
		} else {
		%>
		<a href="<%=request.getContextPath()%>/list">[一覧表示に戻る]</a>
		<%
		}
		%>
	</p>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>