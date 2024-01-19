<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
    <title>T1_HomePage</title>
    <link rel="stylesheet" href= "style.css">
</head>

<body>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="Pesquisa">
        
        <form id="form1" method="GET" action="/pesquisaNome">
                Pesquisa por Nome: <input type="text" name="nomevento"><br>
                <input type="submit" value="send"><br>
        </form><!-- comment -->
        
        <form id="form1" method="GET" action="/pesquisaData">
                Pesquisa por Data: <input type="date" name="d"><br>
                <input type="submit" value="send"><br>
        </form><!-- comment -->
    </div>
            
    <div id="response">
        <p>${e_nome}</p>
        <p>${e_data}</p>
        <p>${e_desc}</p>
    </div>
             
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>
        
    </div>
        
</body>
</html>
