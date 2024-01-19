<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
    <title>T2_TW_Lista Inscrições</title>
    <link rel="stylesheet" href= "style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
    <script>
        
        function att(){
            
            var insc_evento = ${insc_evento};
            var insc_estado = ${insc_estado};
            var insc_participante = ${insc_participante};
            var insc_ref = ${insc_ref};
            
            
            for(var i = 0; i < insc_evento.length; i++){ 
                
                if(insc_estado[i] == 'nao paga'){
                    
                    var evento = insc_evento[i].replaceAll(' ', '+');
                    var nome = insc_participante[i].replaceAll(' ', '+');
                    var obj = JSON.parse(insc_ref[i]);
                    
                    $('#lista_insc').append("<h3>Evento: " + insc_evento[i] + "</h3>" +
                            "<p id='p_insc' >Participante: " + insc_participante[i] + " estado de pagamento: " + insc_estado[i] + ".</p>" +
                            "<div id=ref><p> Entidade: " + obj.mb_entity + "</p>" +
                            "<p> Referencia: " + obj.mb_reference + "</p>" +
                            "<p>Valor: " + obj.mb_amount+ "</p>" +
                            "<p><a href=${pageContext.request.contextPath}pagarLista?nome=" + nome + "&nomevento=" + evento + ">PAGAR</a></p></div>");
                }else{
                    $('#lista_insc').append("<h3>Evento: " + insc_evento[i] + "</h3>" +
                        "<p id='p_insc'>Participante: " + insc_participante[i] + " estado de pagamento: " + insc_estado[i] + ".</p>");
                }
            }
        }
    </script>
</head>

<body onload="att()">
    
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="lista_insc">
    </div>
             
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>
        
    </div>
        
</body>
</html>
