<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
    <title>T2_TW_Pesquisa</title>
    <link rel="stylesheet" href= "style.css">
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script>
    
    function att(){
        
        var l_nome = ${e_nome};
        var l_data = ${e_data};
        var l_desc = ${e_desc};    

        for(var i = 0; i < l_nome.length; i++){                
            $('#response').append("<div class=e_response>" +               
                "<img src=/static/Img/Evento.jpg alt= EVENTO PNG width=160 height=140>" +
                "<h3>" + l_nome[i] + "</h3>" +
                "<h4>" + l_data[i] + "</h4>" +
                "<p id='desc'>" + l_desc[i] + "</p>" +
                "<p id='a_inscrever'> <a href='${pageContext.request.contextPath}/user/newinscrever?nomevento=" + l_nome[i] + "'>Inscrever</a> </p>" +
                "<p id='a_inscrito'> <a href='${pageContext.request.contextPath}/listainscritos?nomevento=" + l_nome[i] + "'>Inscritos</a> </p>" +
                "</div>");
        }
    }
    
    
</script>

<body onload = "att()">
    
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="Pesquisa">
        
        <form id="form1" method="GET" action="/pesquisaNome" >
                Pesquisa por Nome: <input type="text" name="nomevento"><br>
                <input id="sub" type="submit" value="Procurar"><br>
        </form><!-- comment -->
        
        <form id="form1" method="GET" action="/pesquisaData">
                Pesquisa por Data: <input type="date" name="d"><br>
                <input id="sub" type="submit" value="Procurar"><br>
        </form><!-- comment -->
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
