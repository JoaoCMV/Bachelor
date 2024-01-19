<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security Basic - Form Based JDBC Authentication</title>
</head>
<body>
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
                        <nav><a href="${pageContext.request.contextPath}/admin/newevento">Registar Evento</a></nav>        
                        <nav><a href="${pageContext.request.contextPath}/admin/RegisTime">Registar Tempo</a></nav>
                    </div>
	</div>
</body>
</html>