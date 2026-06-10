<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// Servletから渡されたデータを受け取る
String year = (String) request.getAttribute("year");
String month = (String) request.getAttribute("month");
String fileName = (String) request.getAttribute("fileName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>売上げ状況グラフ</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<main>
		<div class="nav-header">
			<div class="nav-header-links">
				<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a>
			</div>
			<div class="nav-header-title">
				<h2 class="title"><%=year%>年<%=month%>月 売り上げ状況グラフ
				</h2>
			</div>
		</div>
		<hr class="head_foot_hr">

		<div align="center" style="margin-top: 30px;">
			<%
			if (fileName != null) {
			%>
			<img src="<%=request.getContextPath()%>/graph/<%=fileName%>"
				alt="売上げグラフ" style="border: 1px solid #ccc;">
			<%
			}
			%>

			<br> <br> <a
				href="<%=request.getContextPath()%>/showSalesByMonth?year=
			<%=year%>&month=<%=month%>">売り上げ状況に戻る</a>
		</div>
	</main>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>