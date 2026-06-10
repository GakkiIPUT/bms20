<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：listUser.jsp
 プログラムの説明：ユーザー一覧の表示画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%><main>

	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> <a
				href="<%=request.getContextPath()%>/view/insertUser.jsp">[ユーザー登録]</a>
		</div>
		<div class="nav-header-title">
			<h2 class="title">ユーザー一覧</h2>
		</div>
	</div>

	<hr class="head_foot_hr">

	<form action="<%=request.getContextPath()%>/listUser" method="get" class="form-inline">
		<table align="center" style="margin-top: 15px;">
			<tr>
				<td>ユーザー <input type="text" name="searchUserid" size="20"></td>
				<td><input type="submit" value="検索"></td>
				<td>
					<form action="<%=request.getContextPath()%>/listUser" method="get" style="display:inline; margin:0; padding:0;">
						<input type="submit" value="全件表示">
					</form>
				</td>
			</tr>
		</table>
	</form>

	<table align="center" class="form-table-80" style="margin-top: 20px;">
		<tr>
			<th class="header-color" align="center" width="150">ユーザー</th>
			<th class="header-color" align="center" width="200">Eメール</th>
			<th class="header-color" align="center" width="150">権限</th>
			<th class="header-color" align="center" width="100">変更/削除</th>
		</tr>
		<%
		if (userList != null && userList.size() > 0) {
			for (User u : userList) {
				String authStr = "1".equals(u.getAuthority()) ? "一般ユーザー" : "管理者";
		%>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/detailUser?user=<%=u.getUserid()%>&cmd=detailUser"><%=u.getUserid()%></a></td>
			<td align="center"><%=u.getEmail()%></td>
			<td align="center"><%=authStr%></td>
			<td align="center">
				<a href="<%=request.getContextPath()%>/detailUser?user=<%=u.getUserid()%>&cmd=updateUser">変更</a>
				<b>&emsp;&emsp;</b>
				<a href="<%=request.getContextPath()%>/deleteUser?user=<%=u.getUserid()%>">削除</a>
			</td>
		</tr>
		<%
		}
		} else {
		%>
		<tr>
			<td colspan="4" align="center">該当するユーザーが存在しません。</td>
		</tr>
		<%
		}
		%>
	</table>
	</main><%@ include file="/common/footer.jsp"%>
</body>
</html>