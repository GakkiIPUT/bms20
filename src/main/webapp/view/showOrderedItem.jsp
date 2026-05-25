<%--
プロジェクト名：書籍管理システムWeb版Ver2.0
 プログラム名：showOrderedItem.jsp
 プログラムの説明：購入情報を表示する画面。全ユーザーの購入履歴を表示する。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
  --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, bean.OrderedItem" %>
<%
    ArrayList<OrderedItem> list = (ArrayList<OrderedItem>) request.getAttribute("ordered_list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購入状況</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/style.css">
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    
	<div class="nav-header">
		<div class="nav-header-links">
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニュー]</a>
        </div>
        <h2>購入状況</h2>
    </div>
    <hr align="center" size="2" color="black" width="100%">
    
    <table align="center" class="form-table-60">
        <tr>
            <th class="header-blue">ユーザー</th>
            <th class="header-blue">TITLE</th>
            <th class="header-blue">注文日</th>
        </tr>
        <% if(list != null && list.size() > 0) { 
            for(OrderedItem item : list) { %>
        <tr>
            <td align="center"><%= item.getUserid() %></td>
            <td><%= item.getTitle() %></td>
            <td align="center"><%= item.getDate() %></td>
        </tr>
        <% } } else { %>
        <tr>
            <td colspan="3" align="center">購入履歴はありません。</td>
        </tr>
        <% } %>
    </table>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>