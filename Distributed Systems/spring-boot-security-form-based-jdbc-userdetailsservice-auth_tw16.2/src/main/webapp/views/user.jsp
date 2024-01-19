<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security Basic - Form Based JDBC Authentication</title>
</head>
<body>
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
	<div align="center">
		<h1>${title}</h1>
		<h2>${message}</h2>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				Welcome
				: ${pageContext.request.userPrincipal.name} | <a
					href="<c:url value='logout'/>">Logout</a>
			</h2>                       
		</c:if>
                    <div id="menu">
                        <nav><a href="Fonte.html">HomePage</a></nav>
                        <nav><a href="${pageContext.request.contextPath}/user/newinscrever">Registar em Evento</a></nav>
                    </div>
	</div>
                    
                    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>        
    </div>
</body>
</html>