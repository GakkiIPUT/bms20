<%--
 プロジェクト名：書籍管理システムWeb版Ver2.0
 プログラム名：login.jsp
 プログラムの説明：ログイン画面。
 作成日：2026年5月
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // エラーメッセージ等の受け取り
    String message = (String) request.getAttribute("message");
    if (message == null) {
        message = "";
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>
    <%@ include file="/common/header.jsp"%>

    <h2 align="center">ログイン</h2>
    <hr align="center" size="2" color="black" width="100%">
    
    <p align="center" style="color: red;"><%= message %></p>

    <form action="<%= request.getContextPath() %>/login" method="post">
        <table align="center">
            <tr>
                <td style="background-color: #5679E7; color: #000000; text-align: center;">ユーザー</td>
                <td><input type="text" name="user"></td>
            </tr>
            <tr>
                <td style="background-color: #5679E7; color: #000000; text-align: center;">パスワード</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="ログイン" style="background-color:#FFFFFF; border-radius: 5px;">
                </td>
            </tr>
        </table>
    </form>

    <%@ include file="/common/footer.jsp"%>
</body>
</html>