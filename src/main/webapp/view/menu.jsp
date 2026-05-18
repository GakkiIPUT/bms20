<%--
プロジェクト名：書籍管理システムWeb版Ver2.0
 プログラム名：menu.jsp
 プログラムの説明：各機能(主に一覧と登録)への導線となるメニュー画面。
 作成日：2026年5月18日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.User"%>
<%
// セッションからユーザー情報を取得
User user = (User) session.getAttribute("user");
// セッション切れか確認
if (user == null) {
	// セッション切れならerror.jspへフォワード
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>

<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/style.css">
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    
    <%-- ユーザー情報表示 兼 セッション切れチェック --%>
    <%@ include file="/common/userInfo.jsp" %>
    
    <h2 align="center">MENU</h2>
    <hr align="center" size="2" color="black" width="100%">
    
    <table align="center">
        <tr><td align="center"><a href="<%= request.getContextPath() %>/view/insert.jsp">【書籍登録】</a></td></tr>
        <tr><td align="center"><a href="<%= request.getContextPath() %>/list">【書籍一覧】</a></td></tr>
        <tr><td align="center"><a href="<%= request.getContextPath() %>/showCart">【カート状況確認】</a></td></tr>
        <tr><td align="center"><a href="<%= request.getContextPath() %>/insertIniData">【初期データ登録(データがない場合のみ)】</a></td></tr>
        <tr><td align="center"><a href="<%= request.getContextPath() %>/showOrderedItem">【購入状況確認】</a></td></tr>
        <tr><td align="center"><a href="<%= request.getContextPath() %>/logout">【ログアウト】</a></td></tr>
    </table>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>