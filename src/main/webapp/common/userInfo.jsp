<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：userInfo.jsp
 プログラムの説明：ログインユーザー情報の表示を行う共通コンポーネント。
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.User" %>
<%
    User loginUser = (User) session.getAttribute("user");

    String authorityStr = "";
    if (loginUser != null) {
        if ("1".equals(loginUser.getAuthority())) {
            authorityStr = "一般ユーザー";
        } else if ("2".equals(loginUser.getAuthority())) {
            authorityStr = "管理者";
        } else {
            authorityStr = "不明";
        }
    }
%>

<div class="user-info-box">
    <table align="right" class="user-info-table">
        <tr>
            <td>名前：</td>
            <td><strong><%= loginUser != null ? loginUser.getUserid() : "" %></strong></td>
        </tr>
        <tr>
            <td>権限：</td>
            <td><%= authorityStr %></td>
        </tr>
    </table>
</div>