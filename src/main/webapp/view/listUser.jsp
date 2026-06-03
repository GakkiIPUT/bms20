<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.User"%>
<%
	User user = (User) session.getAttribute("user");
	if (user == null) {
		request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
		request.setAttribute("cmd", "logout");
		request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		return;
	}
	ArrayList<User> userList = (ArrayList<User>) request.getAttribute("user_list");
%>
<html>
<head>
<title>ユーザー一覧</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<table align="center" width="800">
		<tr>
			<td width="80" align="center"><a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a></td>
			<td width="640" align="center"><h2>ユーザー一覧</h2></td>
			<td width="80" align="center"><a href="<%=request.getContextPath()%>/view/insertUser.jsp">[ユーザー登録]</a></td>
		</tr>
	</table>
	<hr align="center" size="2" color="black" width="800">

	<table align="center">
		<tr>
			<td><%@ include file="/common/userInfo.jsp" %></td>
		</tr>
	</table>

	<form action="<%=request.getContextPath()%>/listUser" method="get">
		<table align="center" style="margin-top: 15px;">
			<tr>
				<td>ユーザー <input type="text" name="searchUserid" size="20"></td>
				<td><input type="submit" value="検索"></td>
				<td><input type="button" value="全件表示" onclick="location.href='<%=request.getContextPath()%>/listUser'"></td>
			</tr>
		</table>
	</form>

	<table align="center" width="700" style="margin-top: 20px;">
		<tr>
			<td class="header-blue" align="center" width="150">ユーザー</td>
			<td class="header-blue" align="center" width="200">Eメール</td>
			<td class="header-blue" align="center" width="150">権限</td>
			<td class="header-blue" align="center" width="50">変更</td>
			<td class="header-blue" align="center" width="50">削除</td>
		</tr>
		<%
			if (userList != null && userList.size() > 0) {
				for (User u : userList) {
					String authStr = "1".equals(u.getAuthority()) ? "一般ユーザー" : "管理者";
		%>
		<tr>
			<td align="center"><a href="<%=request.getContextPath()%>/detailUser?user=<%=u.getUserid()%>&cmd=detailUser"><%=u.getUserid()%></a></td>
			<td align="center"><%=u.getEmail()%></td>
			<td align="center"><%=authStr%></td>
			<td align="center"><a href="<%=request.getContextPath()%>/detailUser?user=<%=u.getUserid()%>&cmd=updateUser">変更</a></td>
			<td align="center"><a href="<%=request.getContextPath()%>/deleteUser?user=<%=u.getUserid()%>">削除</a></td>
		</tr>
		<%
				}
			} else {
		%>
		<tr>
			<td colspan="5" align="center">該当するユーザーが存在しません。</td>
		</tr>
		<%
			}
		%>
	</table>
	<%@ include file="/common/footer.jsp" %>
</body>
</html>