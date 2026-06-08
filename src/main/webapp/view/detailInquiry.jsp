<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.Inquiry"%>
<%@page import="bean.User"%>
<%
	User user = (User) session.getAttribute("user");
	if (user == null || !"2".equals(user.getAuthority())) {
		request.setAttribute("error", "セッション切れの為、アクセスできません。");
		request.setAttribute("cmd", "logout");
		request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		return;
	}
	Inquiry inquiry = (Inquiry) request.getAttribute("inquiry");
%>
<html>
<head>
<title>お問い合わせ詳細</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> <a href="<%=request.getContextPath()%>/listInquiry">[お問い合わせ一覧]</a>
		</div>
		<h2 class="title">お問い合わせ詳細</h2>
	</div>
	<hr align="center" size="2" color="black" width="100%">

	<table align="center" border="1" style="margin-top: 20px; border-collapse: collapse;">
		<tr>
			<th width="150" style="background-color: #e0e0e0; text-align: right; padding: 5px;">名前：</th>
			<td width="350" style="padding: 5px;"><%= inquiry.getName() %></td>
		</tr>
		<tr>
			<th style="background-color: #e0e0e0; text-align: right; padding: 5px;">電話番号：</th>
			<td style="padding: 5px;"><%= inquiry.getPhone() != null ? inquiry.getPhone() : "未入力" %></td>
		</tr>
		<tr>
			<th style="background-color: #e0e0e0; text-align: right; padding: 5px;">E-mail：</th>
			<td style="padding: 5px;"><%= inquiry.getEmail() %></td>
		</tr>
		<tr>
			<th style="background-color: #e0e0e0; text-align: right; padding: 5px;">住所：</th>
			<td style="padding: 5px;"><%= inquiry.getAddress() != null ? inquiry.getAddress() : "未入力" %></td>
		</tr>
		<tr>
			<th style="background-color: #e0e0e0; text-align: right; padding: 5px;">タイトル：</th>
			<td style="padding: 5px;"><%= inquiry.getTitle() %></td>
		</tr>
		<tr>
			<th style="background-color: #e0e0e0; text-align: right; vertical-align: top; padding: 5px;">内容：</th>
			<td style="padding: 5px; height: 100px; vertical-align: top;">
				<%= inquiry.getContent().replace("\n", "<br>") %>
			</td>
		</tr>
	</table>

	<form action="<%=request.getContextPath()%>/deleteInquiry" method="post" style="text-align: center; margin-top: 20px;">
		<input type="hidden" name="id" value="<%= inquiry.getId() %>">
		<input type="submit" value="削除" onclick="return confirm('本当に削除しますか？');">
	</form>

	<%@ include file="../common/footer.jsp"%>
</body>
</html>
