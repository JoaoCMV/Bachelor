<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
    <title>T1_HomePage</title>
    <link rel="stylesheet" href= "style.css">
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
    <script>
        function rank(){
            var lp = ${e_info_lpart};
            var lt = ${e_info_time};
            
            for(var i = 0; i<lp.length; i++){
                $('#rank').append("<p>" + lp[i] + " time: " + lt[i] + "</p>");
            }
        }
    </script>
</head>

<body onload="rank()">
    
    <div id="menu">
        <nav><a href="Fonte.html">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="rank">      
        
    </div>
            
    <div id="response">
    </div>
             
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>
        
    </div>
        
</body>
</html>
