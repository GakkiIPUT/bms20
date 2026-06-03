<%--
プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：showOrderedItem.jsp
 プログラムの説明：購入情報を表示する画面。全ユーザーの購入履歴を表示する。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
  --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, bean.OrderedItem, bean.User"%>
<%@page import="bean.User"%>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
ArrayList<OrderedItem> list = (ArrayList<OrderedItem>) request.getAttribute("ordered_list");
boolean isAdmin = (user != null && "2".equals(user.getAuthority()));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購入状況</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a>
		</div>
		<h2 class="title">購入状況</h2>
	</div>
	<hr align="center" size="2" color="black" width="100%">

	<table align="center" class="form-table-60">
		<tr>
			<th class="header-color">ユーザー</th>
			<th class="header-color">TITLE</th>
			<th class="header-color">数量</th>
			<th class="header-color">注文日</th>
		</tr>
		<%
		if (list != null && list.size() > 0) {
			for (OrderedItem item : list) {
		%>
		<tr>
			<td align="center"><%=item.getUserid()%></td>
			<td align="center"><%=item.getTitle()%></td>
			<td align="center"><%=item.getQuantity()%></td>
			<td align="center"><%=item.getDate()%></td>
		</tr>
		<%
		}
		} else {
		%>
		<tr>
			<td colspan="3" align="center">購入履歴はありません。</td>
		</tr>
		<%
		}
		%>
	</table>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>