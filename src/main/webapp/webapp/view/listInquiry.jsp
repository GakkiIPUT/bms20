<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Inquiry"%>
<%@page import="bean.User"%>
<%
	User user = (User) session.getAttribute("user");
	if (user == null || !"2".equals(user.getAuthority())) {
		request.setAttribute("error", "管理者権限がない為、アクセスできません。");
		request.setAttribute("cmd", "menu");
		request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		return;
	}
	// Servletからリストを受け取る
	ArrayList<Inquiry> list = (ArrayList<Inquiry>) request.getAttribute("inquiry_list");
%>
<html>
<head>
<title>お問い合わせ一覧</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a>
		</div>
		<h2 class="title">お問い合わせ一覧</h2>
	</div>
	<hr align="center" size="2" color="black" width="100%">

	<table align="center">
		<tr>
			<td><%@ include file="../common/userInfo.jsp"%></td>
		</tr>
	</table>

	<% if (list == null || list.isEmpty()) { %>
		<p style="text-align: center; font-weight: bold; margin-top: 20px;">現在、お問い合わせはありません。</p>
	<% } else { %>
		<table align="center" border="1" style="margin-top: 20px; text-align: center; border-collapse: collapse;">
			<tr style="background-color: #e0e0e0;">
				<th width="50">No.</th>
				<th width="150">日付</th>
				<th width="200">タイトル</th>
				<th width="150">名前</th>
			</tr>
			<% for (Inquiry inquiry : list) { %>
				<tr>
					<td><%= inquiry.getId() %></td>
					<td><%= inquiry.getDate().toString().substring(0, 19) %></td>
					<td>
						<a href="<%=request.getContextPath()%>/detailInquiry?id=<%= inquiry.getId() %>">
							<%= inquiry.getTitle() %>
						</a>
					</td>
					<td><%= inquiry.getName() %></td>
				</tr>
			<% } %>
		</table>
	<% } %>

	<%@ include file="../common/footer.jsp"%>
</body>
</html>