<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.member.model.Member" %>
<% //見com.emp.controller.EmpServlet.java第163行存入req的empVO物件 (此為從資料庫取出的empVO, 也可以是輸入格式有錯誤時的empVO物件)
    Member member = (Member) request.getAttribute("member");
%>
<html>
<head>
    <title>會員資料 - listOneMem.jsp</title>

    <style>
        table#table-1 {
            background-color: #CCCCFF;
            border: 2px solid black;
            text-align: center;
        }

        table#table-1 h4 {
            color: red;
            display: block;
            margin-bottom: 1px;
        }

        h4 {
            color: blue;
            display: inline;
        }
    </style>

    <style>
        table {
            width: 600px;
            background-color: white;
            margin-top: 5px;
            margin-bottom: 5px;
        }

        table, th, td {
            border: 1px solid #CCCCFF;
        }

        th, td {
            padding: 5px;
            text-align: center;
        }
    </style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
    <tr>
        <td>
            <h3>會員資料 - listOneMem.jsp</h3>
            <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
        </td>
    </tr>
</table>

<table>
    <tr>
        <th>會員編號</th>
        <th>註冊日期</th>
        <th>會員性別</th>
        <th>會員姓名</th>
        <th>會員帳號</th>
        <th>會員密碼</th>
        <th>會員電話</th>
        <th>會員信箱</th>
        <th>會員照片</th>
        <th>修改</th>
        <th>刪除</th>
    </tr>
    <tr>
        <td>${member.memberId}</td>
        <td>${member.regisDate}</td>
        <td>
            <c:choose>
                <c:when test="${member.gender == 0}">
                    女
                </c:when>
                <c:when test="${member.gender == 1}">
                    男
                </c:when>
            </c:choose>
        </td>
        <td>${member.name}</td>
        <td>${member.account}</td>
        <td>${member.password}</td>
        <td>${member.mobileNo}</td>
        <td>${member.email}</td>
        <td>
            <c:if test="${not empty member.sticker}">
                <img src="data:image/png;base64,${member.stickerBase64}" alt="photo" width="100" height="100"/>
            </c:if>
        </td>
        <td>
            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
                <input type="submit" value="修改">
                <input type="hidden" name="memberId" value="${member.memberId}">
                <input type="hidden" name="action" value="getOne_For_Update">
            </FORM>
        </td>
        <td>
            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;"
                  id="deleteForm${member.memberId}">
                <input type="submit" value="刪除">
                <input type="hidden" name="memberId" value="${member.memberId}">
                <input type="hidden" name="action" value="delete">
            </FORM>
        </td>
    </tr>
</table>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        $(document).on("submit", "form[id^='deleteForm']", function (event) {
            if (!confirm("確定要刪除此筆會員嗎？")) {
                event.preventDefault();
            }
        });
    });
</script>
</body>
</html>
