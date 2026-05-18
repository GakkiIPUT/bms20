<%--
プロジェクト名：書籍管理システムWeb版Ver1.0
 プログラム名：error.jsp
 プログラムの説明：システム内で発生したエラーメッセージを表示し、適切な画面へ誘導する。
 作成日：2026年5月12日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    String error = (String)request.getAttribute("error");
    String cmd = (String)request.getAttribute("cmd");
%>
<html>
<head><title>エラー</title></head>
<body>
    <%@ include file="/common/header.jsp" %>
    
    <h2 align="center" style="color: red;">●●エラー●●</h2>
    <br>
    <p align="center" style="font-weight: bold;"><%= error %></p>
    <br>
    
    <p align="center">
        <% if("menu".equals(cmd)) { %>
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニューに戻る]</a>
        <% } else if ("logout".equals(cmd)) { %>
            <a href="<%= request.getContextPath() %>/logout">[ログイン画面へ]</a>
        <% } else { %>
            <a href="<%= request.getContextPath() %>/list">[一覧表示に戻る]</a>
        <% } %>
    </p>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>