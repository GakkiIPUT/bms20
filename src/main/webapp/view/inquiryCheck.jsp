<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
// 文字コード指定（フォームデータ受け取り用）
request.setCharacterEncoding("UTF-8");

User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}

// inquiry.jsp からの入力データを取得
String name = request.getParameter("name");
String phone = request.getParameter("phone");
String email = request.getParameter("email");
String address = request.getParameter("address");
String title = request.getParameter("title");
String content = request.getParameter("content");

// 必須入力チェック
if (name == null || name.isEmpty() || email == null || email.isEmpty() || content == null || content.isEmpty()) {
	request.setAttribute("error", "お名前、メールアドレス、お問い合わせ内容は必須項目です。");
	request.setAttribute("cmd", "menu"); // メニューに戻すか、inquiryに戻す制御
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>
<html>
<head>
<title>お問い合わせ確認</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/CSS/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>

	<main>
		<div class="nav-header">
			<div class="nav-header-links">
				<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a>
			</div>
			<h2 class="title">お問い合わせ確認</h2>
		</div>
		<hr class="head_foot_hr">

		<div style="text-align: center; margin-top: 10px;">
			<b>上記の内容でよろしければ、送信ボタンを押してください。</b>
		</div>

		<form action="<%=request.getContextPath()%>/inquiry" method="post">
			<table align="center" style="margin-top: 20px;">
				<tr>
					<th style="text-align: right">お名前：</th>
					<td><%=name%> <input type="hidden" name="name"
						value="<%=name%>"></td>
				</tr>
				<tr>
					<th style="text-align: right">電話番号：</th>
					<td><%=phone != null ? phone : ""%> <input type="hidden"
						name="phone" value="<%=phone%>"></td>
				</tr>
				<tr>
					<th style="text-align: right">メールアドレス：</th>
					<td><%=email%> <input type="hidden" name="email"
						value="<%=email%>"></td>
				</tr>
				<tr>
					<th style="text-align: right">住所：</th>
					<td><%=address != null ? address : ""%> <input type="hidden"
						name="address" value="<%=address%>"></td>
				</tr>
				<tr>
					<th style="text-align: right">お問い合わせタイトル：</th>
					<td><%=title%> <input type="hidden" name="title"
						value="<%=title%>"></td>
				</tr>
				<tr>
					<th style="text-align: right; vertical-align: top;">お問い合わせ内容：</th>
					<td><%=content.replace("\n", "<br>")%> <input type="hidden"
						name="content" value="<%=content%>"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center; padding-top: 15px;">
						<input type="submit" value="送信する">
					</td>
				</tr>
			</table>
		</form>
	</main>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>