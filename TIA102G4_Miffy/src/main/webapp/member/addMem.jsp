<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.member.model.Member"%>
<%
    Member member = (Member) request.getAttribute("member");
%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>會員資料新增 - addMem.jsp</title>

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
            width: 450px;
            background-color: white;
            margin-top: 1px;
            margin-bottom: 1px;
        }

        table, th, td {
            border: 0px solid #CCCCFF;
        }

        th, td {
            padding: 1px;
        }
    </style>

</head>
<body bgcolor='white'>

<table id="table-1">
    <tr>
        <td>
            <h3>會員資料新增 - addMem.jsp</h3></td>
        <td>
            <h4><a href="select_page.jsp"><img src="images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
        </td>
    </tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
    <font style="color:red">請修正以下錯誤:</font>
    <ul>
        <c:forEach var="message" items="${errorMsgs}">
            <li style="color:red">${message}</li>
        </c:forEach>
    </ul>
</c:if>

<FORM METHOD="post" ACTION="member.do" name="form1" enctype="multipart/form-data">
    <!-- 月曆是用js寫的，寫在下面 -->
    <input name="regisDate" id="f_date1" type="hidden">
    <table>
        <tr>
            <td>name:</td>
            <td><input type="TEXT" name="name" value="<%= (member==null)? "" : member.getName()%>" size="45"/></td>
        </tr>
        <tr>
            <td>gender:</td>
            <td>
                <input type="radio" name="gender" value="男" <%= (member != null && member.getGender() == 1) ? "checked" : "" %> /> 男
                <input type="radio" name="gender" value="女" <%= (member != null && member.getGender() == 0) ? "checked" : "" %> /> 女
            </td>
        </tr>

        <tr>
            <td>account:</td>
            <td><input type="TEXT" name="account" value="<%= (member==null)? "" : member.getAccount()%>" size="45"/>
            </td>
        </tr>
        <tr>
            <td>password:</td>
            <td><input type="TEXT" name="password" value="<%= (member==null)? "" : member.getPassword()%>" size="45"/>
            </td>
        </tr>
        <tr>
            <td>mobileNo:</td>
            <td><input type="TEXT" name="mobileNo" value="<%= (member==null)? "" : member.getMobileNo()%>" size="45"/></td>
        </tr>
        <tr>
            <td>email:</td>
            <td><input type="TEXT" name="email" value="<%= (member==null)? "" : member.getEmail()%>" size="45"/></td>
        </tr>
        <tr>
            <td>sticker:</td>
            <td><input type="file" name="sticker" />
            </td>
        </tr>
    </table>
    <br>
    <input type="hidden" name="action" value="insert">
    <input type="submit" value="送出新增">
</FORM>

</body>

<!-- 日期時間選擇器的相關設定 -->
<%
    java.sql.Timestamp regisDate = null;
    try {
        regisDate = member.getRegisDate();
    } catch (Exception e) {
        regisDate = new java.sql.Timestamp(System.currentTimeMillis());
    }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css"/>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
    .xdsoft_datetimepicker .xdsoft_datepicker {
        width: 300px; 
    }

    .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
        height: 151px;
    }
</style>

<script>
    $.datetimepicker.setLocale('zh');
    $("#f_date1").datetimepicker({
        theme: '',
        timepicker: false,
        step: 1,
        format: 'Y-m-d',
        value: '<%=regisDate%>',
    });
</script>
</html>
