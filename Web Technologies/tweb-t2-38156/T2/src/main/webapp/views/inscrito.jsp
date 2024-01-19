<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
<title>T2_TW_Inscrito</title>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
 <script>
     
     function getRef(){
         var ref = ${referencia};
         const obj = JSON.parse(ref);
         console.log(obj.mb_entity);
         var p_nome =  ${p_nome}.replaceAll(' ', '+');
         var p_evento =  ${p_evento}.replaceAll(' ', '+');
         $("#referencia").append("<div id='ref'><p> Entidade: " + obj.mb_entity + "</p>" +
                                "<p> Referencia: " + obj.mb_reference + "</p>" +
                                "<p>Valor: " + obj.mb_amount+ "</p>" +
                                "<p><a href=${pageContext.request.contextPath}pagarInscrever?nome=" + p_nome + "&nomevento=" + p_evento + ">PAGAR</a></p></div>");
     }
 </script>
</head>
<body onload="getRef()">
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    <div>
        <h2> ${feedback}</h2>
        <p id="referencia"><p>
    </div>
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>
        
    </div>
</body>
</html>