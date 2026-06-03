<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.OrderedItem"%>
<%@page import="bean.User"%>
<%
	User user = (User) session.getAttribute("user");
	if (user == null) {
		request.setAttribute("error", "セッション切れの為、購入履歴画面が表示できませんでした。");
		request.setAttribute("cmd", "logout");
		request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		return;
	}
	ArrayList<OrderedItem> list = (ArrayList<OrderedItem>) request.getAttribute("ordered_list");
%>
<html>
<head>
<title>購入履歴</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    
	<table align="center" width="800">
		<tr>
			<td width="80" align="center"><a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a></td>
			<td width="640" align="center"><h2 class="title">購入履歴</h2></td>
			<td width="80" align="center"></td>
		</tr>
	</table>
	<hr align="center" size="2" color="black" width="100%">

	<table align="center">
		<tr>
			<td>
				<%--@ include file="/common/userInfo.jsp" --%>
			</td>
		</tr>
	</table>

	<table align="center" width="600" style="margin-top: 20px;">
		<tr>
			<td class="header-color" width="250" align="center">Title</td>
			<td class="header-color" width="100" align="center">数量</td>
			<td class="header-color" width="150" align="center">購入日</td>
		</tr>
		<%
			if (list != null && list.size() > 0) {
				for (OrderedItem item : list) {
		%>
		<tr>
			<td align="center"><%=item.getTitle()%></td>
			<td align="center"><%=item.getQuantity()%></td>
			<td align="center"><%=item.getDate()%></td>
		</tr>
		<%
				}
			} else {
		%>
		<tr>
			<td colspan="3" align="center">購入履歴がありません。</td>
		</tr>
		<%
			}
		%>
	</table>

	<%@ include file="/common/footer.jsp" %>
</body>
</html>