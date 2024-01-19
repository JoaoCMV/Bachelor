<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>T2_TW_Registo</title>
    </head>
    <body>
        
            <div id="menu">
        <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav> 
            </div><!-- comment -->
            
        <div align="center">
            
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>

        <form id="form-box" method="POST" action="/registerUser">
            Nome de Utilizador: <input type="text" name="username"><br>
            Password:<input type="password" name="password"><br>            
            <input id="sub" type="submit" value="Registar" onclick="sub()"><br>
        </form><!-- comment -->
    </div>
        
      <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>        
    </div>
    </body>
</html>