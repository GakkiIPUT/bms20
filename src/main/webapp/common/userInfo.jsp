<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：userInfo.jsp
 プログラムの説明：ログインユーザー情報の表示を行う共通コンポーネント。
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // ※ここでは「User user = ...」の取得は行いません。
    // インクルード元の親画面（menu.jsp等）で取得した user 変数をそのまま使います！

    String authorityStr = "";
    if (user != null) {
        if ("1".equals(user.getAuthority())) {
            authorityStr = "一般ユーザー";
        } else if ("2".equals(user.getAuthority())) {
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
            <td><strong><%= user != null ? user.getUserid() : "" %></strong></td>
        </tr>
        <tr>
            <td>権限：</td>
            <td><%= authorityStr %></td>
        </tr>
    </table>
</div>