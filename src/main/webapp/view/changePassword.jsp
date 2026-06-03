<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%

    User user = (User)session.getAttribute("user");
    if(user == null){
        request.setAttribute("error","セッション切れの為、メニュー画面が表示できませんでした。");
        request.setAttribute("cmd","logout");
        request.getRequestDispatcher("/view/error.jsp").forward(request, response);
        return;
    }
%>

<html>
<head>
<title>パスワード変更</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<table align="center" width="800">
		<tr>
			<td width="80" align="center"><a
				href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a></td>
			<td width="640" align="center"><h2 class="title">パスワード変更</h2></td>
			<td width="80" align="center"></td>
		</tr>
	</table>
	<hr align="center" size="2" color="black" width="100%">

	<table align="center">
		<tr>
			<td><%@ include file="/common/userInfo.jsp"%>
			</td>
		</tr>
	</table>

	<form action="<%=request.getContextPath()%>/changePassword"
		method="post">
		<table align="center" style="margin-top: 20px;">
			<tr>
				<td class="header-color" width="180">ユーザー</td>
				<td width="200"><%=user.getUserid()%></td>
			</tr>
			<tr>
				<td class="header-color">旧パスワード</td>
				<td><input type="password" name="oldpassword" size="25"></td>
			</tr>
			<tr>
				<td class="header-color">新パスワード</td>
				<td><input type="password" name="password" size="25"></td>
			</tr>
			<tr>
				<td class="header-color">新パスワード(確認用)</td>
				<td><input type="password" name="password_ck" size="25"></td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top: 15px;"><input
					type="submit" value="変更"></td>
			</tr>
		</table>
	</form>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>