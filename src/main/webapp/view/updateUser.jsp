<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：updateUser.jsp
 プログラムの説明：ユーザー情報の更新画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bean.User" %>
<% 
    User u = (User)request.getAttribute("user"); 
%>
<html>
<head>
    <title>ユーザー変更</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div style="text-align:center">
    <h2 class="title">ユーザー変更</h2>
    <form action="<%=request.getContextPath()%>/updateUser" method="post">
        <input type="hidden" name="user" value="<%=u.getUserid()%>">
        
        <table align="center">
            <tr>
                <th style="text-align:right">ユーザー：</th>
                <td style="text-align:left"><%=u.getUserid()%></td>
            </tr>
            <tr>
                <th style="text-align:right">パスワード：</th>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <th style="text-align:right">パスワード(確認用)：</th>
                <td><input type="password" name="passwordConfirm"></td>
            </tr>
            <tr>
                <th style="text-align:right">Eメール：</th>
                <td><input type="text" name="email" value="<%=u.getEmail()%>"></td>
            </tr>
            <tr>
                <th style="text-align:right">権限：</th>
                <td style="text-align:left">
                    <select name="authority">
                        <option value="1" <%= "1".equals(u.getAuthority()) ? "selected" : "" %>>一般ユーザー</option>
                        <option value="2" <%= "2".equals(u.getAuthority()) ? "selected" : "" %>>管理者</option>
                    </select>
                </td>
            </tr>
        </table>
        <br>
        <input type="submit" value="変更完了">
    </form>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>