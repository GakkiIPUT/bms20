<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：detailUser.jsp
 プログラムの説明：ユーザー詳細の表示画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>
<%
User u = (User) request.getAttribute("user");
%>
<html>
<head>
<title>ユーザー詳細情報</title>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<main>
		<div class="nav-header">
			<div class="nav-header-links">
				<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> <a
					href="<%=request.getContextPath()%>/view/insertUser.jsp">[ユーザー登録]</a>
				<a href="<%=request.getContextPath()%>/listUser">[ユーザー一覧]</a>
			</div>
			<div class="nav-header-title">
				<h2 class="title">ユーザー詳細情報</h2>
			</div>
		</div>
		<hr class="head_foot_hr">
		<table align="center" class="form-table-30">
			<tr>
				<td colspan="2" align="center">
					<form action="<%=request.getContextPath()%>/detailUser"
						method="get" class="form-inline">
						<input type="hidden" name="user" value="<%=user.getUserid()%>">
						<input type="hidden" name="cmd" value="updateUser"> <input
							type="submit" value="変更">
					</form> <a>&emsp;&emsp;</a>
					<form action="<%=request.getContextPath()%>/deleteUser"
						method="get" class="form-inline">
						<input type="hidden" name="user" value="<%=user.getUserid()%>">
						<input type="submit" value="削除">
					</form>
				</td>
			</tr>
			<tr>
				<td><br></td>
				<td><br></td>
			</tr>
			<tr>
				<td class="header-color">ユーザー</td>
				<td class="column-color"><%=user.getUserid()%></td>
			</tr>
			<tr>
				<td class="header-color">パスワード</td>
				<td class="column-color"><%=user.getPassword()%></td>
			</tr>
			<tr>
				<td class="header-color">Eメール</td>
				<td class="column-color"><%=user.getEmail()%></td>
			</tr>
			<tr>
				<td class="header-color">権限</td>
				<td class="column-color"><%="2".equals(user.getAuthority()) ? "管理者" : "一般ユーザー"%></td>
			</tr>
		</table>
	</main>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>