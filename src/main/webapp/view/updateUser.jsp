<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：updateUser.jsp
 プログラムの説明：ユーザー情報の更新画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
User user = (User) request.getAttribute("user");
%>
<html>
<head>
<title>ユーザー変更</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<main>
		<div style="text-align: center">
			<h2 class="title">ユーザー変更</h2>
			<form action="<%=request.getContextPath()%>/updateUser" method="post">
				<input type="hidden" name="user" value="<%=user.getUserid()%>">

				<table align="center" class="form-table-40">
					<tr>
						<th class="table-header-width-40"></th>
						<th class="table-header-width-40"><p>&lt;&lt;変更前情報&gt;&gt;</p></th>
						<th class="table-header-width-5"></th>
						<th class="table-header-width-40"><p>&lt;&lt;変更後情報&gt;&gt;</p></th>
					</tr>
					<tr>
						<td class="header-color">ユーザー</td>
						<td align="center" class="form-row-info"><%=user.getUserid()%></td>
						<td></td>
						<td align="center"><%=user.getUserid()%></td>
					</tr>
					<tr>
						<td class="header-color">パスワード</td>
						<td align="center" class="form-row-info">**</td>
						<td></td>
						<td align="center"><input type="password" name="password"></td>
					</tr>
					<tr>
						<td class="header-color">パスワード(確認用)</td>
						<td align="center" class="form-row-info"></td>
						<td></td>
						<td align="center"><input type="password"
							name="passwordConfirm"></td>
					</tr>
					<tr>
						<td class="header-color">Eメール</td>
						<td align="center" class="form-row-info"><%=user.getEmail()%></td>
						<td></td>
						<td align="center"><input type="text" name="email"
							value="<%=user.getEmail()%>"></td>
					</tr>
					<tr>
						<td class="header-color">権限</td>
						<td align="center" class="form-row-info"><%="2".equals(user.getAuthority()) ? "管理者" : "一般ユーザー"%></td>
						<td></td>
						<td align="center"><select name="authority">
								<option value="1"
									<%="1".equals(user.getAuthority()) ? "selected" : ""%>>一般ユーザー</option>
								<option value="2"
									<%="2".equals(user.getAuthority()) ? "selected" : ""%>>管理者</option>
						</select></td>
					</tr>
				</table>
				<div style="text-align: center; margin-top: 20px;">
					<input type="submit" value="変更完了">
				</div>
			</form>
		</div>
	</main>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>