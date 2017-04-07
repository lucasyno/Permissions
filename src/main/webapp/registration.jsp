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
    <title>Sign up!</title>
    <meta http-equiv="content-type" content="text/html" charset="ISO-8859-1">
</head>
<body>
<form action="register" method="post">
    <label>Username: <input type="text" id="username" name="username"></label><br/>
    <label>email: <input type="email" id="email" name="email"></label><br/>
    <label>password: <input type="password" id="password" name="password"></label><br/>
    <label>confirm password: <input type="password" id="password2" name="password2"></label><br/>
    <input type="submit" value="REGISTER"/>
</form>
</body>
</html>
