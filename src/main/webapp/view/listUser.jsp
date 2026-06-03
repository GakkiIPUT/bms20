<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList, bean.User"%>
<%
ArrayList<User> userList = (ArrayList<User>) request.getAttribute("user_list");
%>
<html>
<head>
<title>ユーザー一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">【メニュー】</a> <a
				href="<%=request.getContextPath()%>/view/insertUser.jsp">【ユーザー登録】</a>
		</div>
		<h2 class="title">ユーザー一覧</h2>
	</div>
	<hr align="center" size="2" color="black" width="100%">

	<div align="center">
		<form action="<%=request.getContextPath()%>/listUser" method="post"
			class="form-inline">
			ユーザー名：<input type="text" name="searchUserid"> <input
				type="submit" value="検索">
		</form>
		<form action="<%=request.getContextPath()%>/listUser" method="get"
			class="form-inline">
			<input type="submit" value="全件表示">
		</form>
	</div>
	<table align="center" border="1">
		<tr>
			<th>ユーザー</th>
			<th>Eメール</th>
			<th>権限</th>
			<th>変更</th>
			<th>削除</th>
		</tr>
		<%
		if (userList != null) {
			for (User u : userList) {
		%>
		<tr>
			<td><a
				href="<%=request.getContextPath()%>/detailUser?cmd=detailUser&user=<%=u.getUserid()%>"><%=u.getUserid()%></a></td>
			<td><%=u.getEmail()%></td>
			<td><%="2".equals(u.getAuthority()) ? "管理者" : "一般ユーザー"%></td>
			<td><a
				href="<%=request.getContextPath()%>/detailUser?cmd=updateUser&user=<%=u.getUserid()%>">変更</a></td>
			<td><a
				href="<%=request.getContextPath()%>/deleteUser?user=<%=u.getUserid()%>">削除</a></td>
		</tr>
		<%
		}
		}
		%>
	</table>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>