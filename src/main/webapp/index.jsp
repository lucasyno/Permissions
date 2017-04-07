<%--
  Created by IntelliJ IDEA.
  User: lukaszgodlewski
  Date: 02.04.2017
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
    <meta http-equiv="content-type" content="text/html" charset="ISO-8859-1">
</head>
<body>
<form action="login" method="post">
    <label>Username: <input type="text" id="username" name="username"></label><br/>
    <label>password: <input type="password" id="password" name="password"></label><br/>
    <input type="submit" value="LOG IN"/>
</form>
<a href="registration.jsp" class="button">SIGN UP</a>

</body>
</html>
