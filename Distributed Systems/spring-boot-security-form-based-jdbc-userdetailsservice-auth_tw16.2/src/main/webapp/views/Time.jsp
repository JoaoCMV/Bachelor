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

            <form id="form1" method="GET" action="/RegisTime">
                Nome Evento: <input type="text" name="nomeEvento"><br>
                participante:<input type="text" name="username"><br>
                etapa:<input type="text" name="etapa"><br>
                dorsal:<input type="text" name="dorsal"><br>
                tempo:<input type="text" name="t"><br>
                <input type="submit" value="send"><br>
            </form><!-- comment -->
        </div>
    </body>
</html>