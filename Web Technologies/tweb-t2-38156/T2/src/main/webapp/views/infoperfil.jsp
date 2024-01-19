<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
    <title>T2_TW_Participante info</title>
</head>

<body>
    
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="Info_Participante">
        <img src="/static/Img/foto_perfil.png" alt="Foto Perfil" width="120" height="150">
        <h3>Nome: ${nome_p}</h3>
        <h3>Evento: ${evento_p}</h3>
        <p>Start: ${start_time}</p>
        <p>P1: ${p1_time}</p>
        <p>P2: ${p2_time}</p>
        <p>P3: ${p3_time}</p>
        <p>Finish: ${finish_time}</p>
    </div>
             
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>
        
    </div>
        
</body>
</html>
