<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
<title>T2_TW_Inscrever</title>
</head>
<body>
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div><!-- comment -->
    
	<div id = "form-box"  align="center">
                <form class="insc" id="form1" method="GET" action="/inscrever" + >
                Evento: <input type="text" value="${evento}" name="nomevento"><br>
                Nome: <input type="text" name="nome"><br>
                <ol id="form-box">
                <p>Genero:</p>    
                <li><input type="radio" name="genero" value="m"> Masculino</li>
                <li><input type="radio" name="genero" value="f"> Feminino</li>
                </ol>          
                <ol id="form-box">
                <p>Idade:</p>    
                <li><input type="radio" name="escalao" value="Juvenis" checked>
                Menos de 18 </li>
                <li><input type="radio" name="escalao" value="Junior"> 18-25</li>
                <li><input type="radio" name="escalao" value="Senior"> 25-65</li>
                <li><input type="radio" name="escalao" value="Senior65+"> 65+</li>
                </ol>
                <input id="sub" type="submit" value="Inscrever"><br>
            </form><!-- comment -->
	</div>
    
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>
        
    </div>
</body>
</html>