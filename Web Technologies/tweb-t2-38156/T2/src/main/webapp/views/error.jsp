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
    
     <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="erro" align="center">
        <h1>ERRO ao carregar a página</h1>
        <a href="${pageContext.request.contextPath}/">HomePage</a>
    </div>
    
        
</body>
</html>