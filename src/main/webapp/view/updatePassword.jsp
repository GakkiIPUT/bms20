<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>パスワード変更</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div style="text-align:center">
    <h2 class="title">パスワード変更</h2>
    <form action="<%=request.getContextPath()%>/updatePassword" method="post">
        <table align="center">
            <tr>
                <th style="text-align:right">新パスワード：</th>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <th style="text-align:right">新パスワード(確認用)：</th>
                <td><input type="password" name="passwordConfirm"></td>
            </tr>
        </table>
        <br>
        <input type="submit" value="変更">
    </form>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>