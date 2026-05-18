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
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    
    <div style="text-align: center;">
        <div style="float: left; margin-left: 40px;">
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニュー]</a>
        </div>
        <h2>購入状況</h2>
    </div>
    <hr align="center" size="2" color="black" width="100%">
    
    <table align="center" border="1" style="width: 60%; border-collapse: collapse;">
        <tr style="background-color: #5679E7;">
            <th>ユーザー</th>
            <th>TITLE</th>
            <th>注文日</th>
        </tr>
        <% 
        if(list != null && list.size() > 0) { 
            for(OrderedItem item : list) {
        %>
        <tr>
            <td align="center"><%= item.getUserid() %></td>
            <td><%= item.getIsbn() %><%-- titleはDTOのgetIsbn()にマッピングされているため --%></td>
            <td align="center"><%= item.getDate() %></td>
        </tr>
        <% 
            }
        } else {
        %>
        <tr>
            <td colspan="3" align="center">購入履歴はありません。</td>
        </tr>
        <% } %>
    </table>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>