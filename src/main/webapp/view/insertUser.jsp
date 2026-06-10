<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：insertUser.jsp
 プログラムの説明：ユーザー登録画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
// 仕様書通りのセッションチェック記述例
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>
<html>
<head>
<title>ユーザー登録</title>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<main>
		<div class="nav-header">
			<div class="nav-header-links">
				<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> <a
					href="<%=request.getContextPath()%>/listUser">[ユーザー一覧]</a>
			</div>
			<h2 class="title">ユーザー登録</h2>
		</div>
		<hr class="head_foot_hr">

		<form action="<%=request.getContextPath()%>/insertUser" method="post">
			<table align="center">
				<tr>
					<th style="text-align: right">ユーザー：</th>
					<td><input type="text" name="userid"></td>
				</tr>
				<tr>
					<th style="text-align: right">パスワード：</th>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<th style="text-align: right">パスワード(確認用)：</th>
					<td><input type="password" name="passwordConfirm"></td>
				</tr>
				<tr>
					<th style="text-align: right">Eメール：</th>
					<td><input type="text" name="email"></td>
				</tr>
				<tr>
					<th style="text-align: right">権限：</th>
					<td style="text-align: left"><select name="authority">
							<option value="1">一般ユーザー</option>
							<option value="2">管理者</option>
					</select></td>
				</tr>
			</table>
			<br>
			<div align="center">
				<input type="submit" value="登録">
			</div>
		</form>
	</main>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>