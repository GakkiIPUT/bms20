<%--
 プロジェクト名：書籍管理システムWeb版Ver2.0
 プログラム名：userInfo.jsp
 プログラムの説明：ログインユーザー情報の表示およびセッション切れチェックを行う共通コンポーネント。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.User" %>
<%
    // ① セッションからユーザー情報を取得
    User user = (User) session.getAttribute("user");

    // ② セッション切れか確認
    if (user == null) {
        // セッション切れならエラーメッセージを設定してerror.jspへフォワード
        request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
        request.setAttribute("cmd", "logout");
        request.getRequestDispatcher("/view/error.jsp").forward(request, response);
        
        // 以降のJSP処理（呼び出し元のmenu.jspなど）が実行されないように遮断する
        return;
    }
    
    // 権限の数値を分かりやすい文字列に変換
    String authorityStr = "";
    if ("1".equals(user.getAuthority())) {
        authorityStr = "一般ユーザー";
    } else if ("2".equals(user.getAuthority())) {
        authorityStr = "管理者";
    } else {
        authorityStr = "不明";
    }
%>

<div class="user-info-box">
    <table align="right" class="user-info-table">
        <tr>
            <td>名前：</td>
            <td><strong><%= user.getUserid() %></strong></td>
        </tr>
        <tr>
            <td>権限：</td>
            <td><%= authorityStr %></td>
        </tr>
    </table>
</div>