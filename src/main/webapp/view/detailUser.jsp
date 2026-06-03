<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bean.User" %>
<% 
    User u = (User)request.getAttribute("user"); 
%>
<html>
<head>
    <title>ユーザー詳細情報</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div style="text-align:center">
    <h2 class="title">ユーザー詳細情報</h2>
    <table align="center" border="1" cellpadding="5">
        <tr>
            <th>ユーザー</th>
            <td><%=u.getUserid()%></td>
        </tr>
        <tr>
            <th>パスワード</th>
            <td>********</td>
        </tr>
        <tr>
            <th>Eメール</th>
            <td><%=u.getEmail()%></td>
        </tr>
        <tr>
            <th>権限</th>
            <td><%= "2".equals(u.getAuthority()) ? "管理者" : "一般ユーザー" %></td>
        </tr>
    </table>
    <br>
    <a href="<%=request.getContextPath()%>/listUser">一覧へ戻る</a>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>