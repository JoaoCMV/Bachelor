<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>User Registration: new user</title>
    </head>
    <body>
        <div align="center">
            <h1>${title}</h1>
            <h2>${message}, new user</h2>

            <form id="form1" method="GET" action="/registerEvento">
                Nome Evento: <input type="text" name="nomeEvento"><br>
                data:<input type="date" name="d"><br>
                valor:<input type="float" name="valor"><br>
                descrição:<textarea name="desc" rows="6" cols="64">Descrição de evento...</textarea>
                <input type="submit" value="send"><br>
            </form><!-- comment -->
        </div>
    </body>
</html>