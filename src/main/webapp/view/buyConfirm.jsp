<%--
プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：buyConfirm.jsp
 プログラムの説明：購入確認画面。購入した書籍の確認情報を表示する。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.ArrayList, bean.Sale, bean.User, util.MyFormat"%>
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
ArrayList<Sale> list = (ArrayList<Sale>) request.getAttribute("book_list");
MyFormat mf = new MyFormat();
int total = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購入品確認</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="nav-header">
		<div class="nav-header-links">
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> <a
				href="<%=request.getContextPath()%>/list">[書籍一覧]</a>
		</div>
		<h2 class="title">購入品確認</h2>
	</div>
	<hr align="center" size="2" color="black" width="100%">

	<p align="center">
		下記の商品を購入しました。<br>ご利用ありがとうございました。
	</p>

	<table align="center" border="1" class="form-table">
		<tr class="header-color">
			<th>ISBN</th>
			<th>TITLE</th>
			<th>価格</th>
			<th>購入数</th>
		</tr>
		<%
		if (list != null) {
			for (Sale s : list) {
				total += s.getPrice() * s.getQuantity();
		%>
		<tr>
			<td align="center"><%=s.getIsbn()%></td>
			<td><%=s.getTitle()%></td>
			<td align="right"><%=mf.moneyFormat(s.getPrice())%></td>
			<td align="center"><%=s.getQuantity()%></td>
		</tr>
		<%
			}
		}
		%>
	</table>

	<hr
		style="border: 0; border-top: 5px double #333333; width: 90%; margin: 15px auto;">
	<table align="center" style="width: 50%;">
		<tr>
			<td></td>
			<td align="right" class="header-color">合計</td>
			<td align="left"><%=mf.moneyFormat(total)%></td>
		</tr>
	</table>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>