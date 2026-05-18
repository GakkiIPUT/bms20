<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, bean.Book, util.MyFormat" %>
<%
    ArrayList<Book> list = (ArrayList<Book>) request.getAttribute("book_list");
    MyFormat mf = new MyFormat();
    int total = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カート内容</title>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    
    <div style="text-align: center;">
        <div style="float: left; margin-left: 40px;">
            <a href="<%= request.getContextPath() %>/view/menu.jsp">[メニュー]</a>
            <a href="<%= request.getContextPath() %>/list">[書籍一覧]</a>
        </div>
        <h2>カート内容</h2>
    </div>
    <hr align="center" size="2" color="black" width="100%">
    
    <table align="center" border="1" style="width: 60%; border-collapse: collapse;">
        <tr style="background-color: #5679E7;">
            <th>ISBN</th>
            <th>TITLE</th>
            <th>価格</th>
            <th></th>
        </tr>
        <% 
        if(list != null && list.size() > 0) { 
            for(int i = 0; i < list.size(); i++) {
                Book b = list.get(i);
                total += b.getPrice();
        %>
        <tr>
            <td align="center"><a href="<%= request.getContextPath() %>/detail?isbn=<%= b.getIsbn() %>&cmd=detail"><%= b.getIsbn() %></a></td>
            <td><%= b.getTitle() %></td>
            <td align="right"><%= mf.moneyFormat(b.getPrice()) %></td>
            <td align="center">
                <%-- 削除リンクの delno パラメータには、リストのインデックス(i)を指定 --%>
                <a href="<%= request.getContextPath() %>/showCart?delno=<%= i %>">削除</a>
            </td>
        </tr>
        <% 
            }
        } else {
        %>
        <tr>
            <td colspan="4" align="center">カートに商品は入っていません。</td>
        </tr>
        <% } %>
        <tr>
            <td colspan="2" align="center" style="background-color: #5679E7;">合計</td>
            <td align="right"><%= mf.moneyFormat(total) %></td>
            <td></td>
        </tr>
    </table>
    
    <div align="center" style="margin-top: 20px;">
        <form action="<%= request.getContextPath() %>/buyConfirm" method="get">
            <input type="submit" value="購入" style="border-radius: 5px;" <%= list == null || list.size() == 0 ? "disabled" : "" %>>
        </form>
    </div>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>