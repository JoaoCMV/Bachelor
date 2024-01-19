<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>T2_TW_Admin</title>
</head>
<body>
    
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    
    <div class="form-box" id="Novo_Evento">
        <h1>Novo Evento...</h1>
         <form id="form1" method="GET" action="/admin/registerEvento">
                Nome Evento: <input type="text" name="nomeEvento"><br>
                Valor:<input type="number" name="valor" step="0.01"><br>
                Data:<input type="date" name="d"><br>
                Descrição:<textarea name="desc" rows="6" cols="64">Descrição de evento...</textarea>
                <input id="sub" type="submit" value="Registar"><br>
            </form><!-- comment -->
    </div>
    
    <div class="form-box" id="Tempo">
        <h1>Registar Tempo</h1>
        <form id="form1" method="GET" action="/admin/RegisTime">
                Nome Evento: <input type="text" name="nomevento"><br>
                Etapa:<input type="text" name="etapa"><br>
                Dorsal:<input type="text" name="dorsal"><br>
                Tempo:<input type="text" name="t"><br>
                <input id="sub" type="submit" value="Registar"><br>
            </form>
    </div>
        
</body>
</html>