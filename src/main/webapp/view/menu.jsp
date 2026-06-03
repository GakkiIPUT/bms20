<%--
プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：menu.jsp
 プログラムの説明：各機能(主に一覧と登録)への導線となるメニュー画面。
 作成日：2026年5月18日
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

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<%-- ユーザー情報表示 兼 セッション切れチェック --%>
	<%@ include file="/common/userInfo.jsp"%>
	<hr align="center" size="2" color="black" width="100%">
	<h2 align="center"class="title">MENU</h2>
	<hr align="center" size="2" color="black" width="100%">

	<table align="center">
		<tr>
			<td align="center"><a href="<%=request.getContextPath()%>/list">【書籍一覧】</a></td>
		</tr>
		<%
		if ("1".equals(user.getAuthority())) {
		%>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/showCart">【カート状況確認】</a></td>
		</tr>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/showHistoryOrderedItem">【購入履歴】</a></td>
		</tr>
		<%
		}
		if ("2".equals(user.getAuthority())) {
		%>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/view/insert.jsp">【書籍登録】</a></td>
		</tr>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/insertIniData">【初期データ登録(データがない場合のみ)】</a></td>
		</tr>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/showOrderedItem">【購入状況確認】</a></td>
		</tr>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/showSalesByMonth">【売上状況】</a></td>
		</tr>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/view/insertUser.jsp">【ユーザー登録】</a></td>
		</tr>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/listUser">【ユーザー一覧】</a></td>
		</tr>
		<%
		}
		%>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/view/changePassword.jsp">【パスワード変更】</a></td>
		</tr>
		<tr>
			<td align="center"><a
				href="<%=request.getContextPath()%>/logout">【ログアウト】</a></td>
		</tr>
	</table>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>