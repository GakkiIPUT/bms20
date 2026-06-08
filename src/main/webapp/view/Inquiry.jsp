<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
	// セッションチェック
	User user = (User) session.getAttribute("user");
	if (user == null) {
		request.setAttribute("error", "セッション切れの為、お問い合わせ画面が表示できませんでした。");
		request.setAttribute("cmd", "logout");
		request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		return;
	}
%>
<html>
<head>
<title>お問い合わせ入力</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a>
		</div>
		<h2 class="title">お問い合わせ</h2>
	</div>
	<hr align="center" size="2" color="black" width="100%">

	<table align="center">
		<tr>
			<td><%@ include file="../common/userInfo.jsp"%></td>
		</tr>
	</table>

	<form action="<%=request.getContextPath()%>/view/inquiryCheck.jsp" method="post">
		<table align="center" style="margin-top: 20px;">
			<tr>
				<th style="text-align: right">お名前（必須）：</th>
				<td><input type="text" name="name" size="30"></td>
			</tr>
			<tr>
				<th style="text-align: right">電話番号：</th>
				<td><input type="text" name="phone" size="30"></td>
			</tr>
			<tr>
				<th style="text-align: right">メールアドレス（必須）：</th>
				<td><input type="text" name="email" size="40"></td>
			</tr>
			<tr>
				<th style="text-align: right">住所：</th>
				<td><input type="text" name="address" size="50"></td>
			</tr>
			<tr>
				<th style="text-align: right">お問い合わせタイトル（必須）：</th>
				<td>
					<label><input type="radio" name="title" value="書籍について" checked> 書籍について</label>
					<label><input type="radio" name="title" value="購入について"> 購入について</label>
					<label><input type="radio" name="title" value="その他"> その他</label>
				</td>
			</tr>
			<tr>
				<th style="text-align: right; vertical-align: top;">お問い合わせ内容（必須）：</th>
				<td><textarea name="content" rows="6" cols="50"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center; padding-top: 15px;">
					<input type="submit" value="確認">
				</td>
			</tr>
		</table>
	</form>

	<%@ include file="../common/footer.jsp"%>
</body>
</html>