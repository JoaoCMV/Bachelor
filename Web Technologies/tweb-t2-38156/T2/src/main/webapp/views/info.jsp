<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
    <title>T2_TW_Pesquisa</title>
    <link rel="stylesheet" href= "style.css">
</head>

<body>
    
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="Pesquisa">
        
        <form id="form1" method="GET" action="/getInfoABS">
                Nome Evento (Geral): <input type="text" name="nomevento"><br>
                <input id="sub" type="submit" value="Pesquisar" ><br>
        </form><!-- comment --> 
        
        <form id="form1" method="GET" action="/getInfoM">
                Nome Evento(classificação M): <input type="text" name="nomevento"><br>
                <input id="sub" type="submit" value="Pesquisar" ><br>
        </form><!-- comment -->
        
        <form id="form1" method="GET" action="/getInfoF">
                Nome Evento(classificação F): <input type="text" name="nomevento"><br>
                <input id="sub" type="submit" value="Pesquisar" ><br>
        </form><!-- comment -->
        
    </div>
            
    <div id="response">
        <p>${e_info_lpart}</p>
        <p>${e_info_time}</p>
    </div>
             
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>
        
    </div>
        
</body>
</html>
