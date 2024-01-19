<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
<title>T2_TW_HomePage</title>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script>

// Info sobre os eventos de hoje/passados/futuros e a respetiva página 
        var l_eh = ${eventos_hoje};             // Retira do model a informação relativa aos eventos por data
        var p_eh = 0;
        
        var l_ep = ${eventos_passados};
        var p_ep = 0;
        
        var l_ef = ${eventos_futuros};
        var p_ef = 0;
        
        // Informações sobre o evento em pesquisa
        var e_pesquisado;
        var l_inscritos = [];
        var l_rank = [];
        
        // Info sobre filtros
        var showfiltro = 0;
       
        // Coloca os devidos eventos na página
        function att(){
        
            var p;      // n. paginas necessarias
            
            
            // ---------- Eventos Hoje ----------------------   
            if(l_eh.length == 0) $('#h1_h').css('display','none');
            
            // Percorre os 4 primeiros eventos para poder colocar na pagina
            // No caso de existir mais de 4 elementos é feita uma paginação
            for(var i = 0; i < l_eh.length && i < 4; i++){                
                $('#e_hoje').append("<div class=e_info>" +
                                               "<p> <a href='${pageContext.request.contextPath}pesquisaNome?nomevento=" + l_eh[i] + "'>"  + l_eh[i] + "</a> </p>" +
                                               "<img src=/static/Img/Evento.jpg alt= EVENTO PNG width=160 height=140>" +
                                               "</div>" );
            }
            
            // Adiciona o navegador de páginas 
            if ( 1 < (l_eh.length/4) ){
                $('#pag_h').css( 'display' , 'center');
            }else{
                $('#pag_h').css( 'display' , 'none');
            }
            
            for(var i = 1; i < (l_eh.length/4); i++){
                console.log("Adicionado pag. a e_Hoje");
                $('#pag_h').append("<li> <a onclick = setpagH(" + i + ")> " + (i+1) +
                                   "</a></li>");                                    
            }
            
            
            // ---------- Eventos Futuros ----------------------
            if(l_ef.length == 0) $('#h1_f').css('display','none');
            
            for(var i = 0; i < l_ef.length && i < 4; i++){                
                $('#e_futuros').append("<div class=e_info>" +
                                               "<p> <a href='${pageContext.request.contextPath}pesquisaNome?nomevento=" + l_ef[i] + "'>"  + l_ef[i] + "</a> </p>" +
                                               "<img src=/static/Img/Evento.jpg  alt= EVENTO PNG width=160 height=140>" +
                                               "</div>" );
            }
            
            // Adiciona o navegador de páginas 
            if( 1 < (l_ef.length/4) ){
                $('#pag_f').css( 'display' , 'center');
            }else{
                $('#pag_f').css( 'display' , 'none');
            }
            
            for(var i = 1; i < (l_ef.length/4); i++){
                console.log("Adicionado pag. a e_Futuros");
                $('#pag_f').append("<li> <a onclick = setpagF(" + i + ")> " + (i+1) +
                                   "</a></li>");                                    
            }
            
            
            // ---------- Eventos Passados ----------------------
            if(l_ep.length == 0) $('#h1_p').css('display','none');
            
            for(var i = 0; i < l_ep.length && i < 4; i++){ 
                $('#e_passados').append("<div class=e_info>" +
                                               "<p> <a href='${pageContext.request.contextPath}pesquisaNome?nomevento=" + l_ep[i] + "'>"  + l_ep[i] + "</a> </p>"  +
                                               "<img src=/static/Img/Evento.jpg alt= EVENTO PNG width=160 height=140>" +
                                               "</div>" );
            }
            
            // Adiciona o navegador de páginas 
            if ( 1 < (l_ep.length/4) ){
                $('#pag_p').css( 'display' , 'center');
            }else{
                $('#pag_p').css( 'display' , 'none');
            }
            
            for(var i = 1; i < (l_ep.length/4); i++){
                console.log("Adicionado pag. a e_Passados");
                $('#pag_p').append("<li> <a onclick = setpagP(" + i + ")> " + (i+1) +
                                   "</a></li>");                                    
            }
            
        }
                       
        
        // Altera a página
        function setpagH(p){
            p_eh = p;
            console.log("Eventos Hoje " + p*4 + " até " + ( (p*4) + 3 ) );
            $('#e_hoje').empty();                                           // Limpa a lista para atualizar os eventos
            var y = 0;
            for(var i = (p*4); i < (p*4) + 4 && i < l_eh.length; i++){
                console.log("ADICIONA: " + l_eh[i] + " pag. " + p_eh);
                $('#e_hoje').append("<div class=e_info>" +
                                    "<p> <a href='${pageContext.request.contextPath}pesquisaNome?nomevento=" + l_eh[i] + "'>"  + l_eh[i] + "</a> </p>" +
                                    "<img src=/static/Img/Evento.jpg alt= EVENTO PNG width=160 height=140>" +
                                    "</div>" );     
                y++;
            }
        }
        
        // Altera a página
        function setpagF(p){
            p_ef = p;
            console.log("Eventos Futuros " + p*4 + " até " + ( (p*4) + 3 ) );
            $('#e_futuros').empty();                                           // Limpa a lista para atualizar os eventos
            var y = 0;
            for(var i = (p*4); i < (p*4) + 4 && i < l_ef.length; i++){
                console.log("ADICIONA: " + l_ef[i] + " pag. " + p_ef);
                $('#e_futuros').append("<div class=e_info>" +
                                       "<p> <a href='${pageContext.request.contextPath}pesquisaNome?nomevento=" + l_ef[i] + "'>"  + l_ef[i] + "</a> </p>" +
                                       "<img src=/static/Img/Evento.jpg  alt= EVENTO PNG width=160 height=140>" +
                                       "</div>" ); 
                y++;
            }
        }
        
        // Altera a página
        function setpagP(p){
            
            p_ep = p;
            console.log("Eventos Passados " + p*4 + " até " + ( (p*4) + 3 ) );
            $('#e_passados').empty();                                           // Limpa a lista para atualizar os eventos
            var y = 0;
            for(var i = (p*4); i < (p*4) + 4 && i < l_ep.length; i++){
                console.log("ADICIONA: " + l_ep[i] + " pag. " + p_ep);
                $('#e_passados').append("<div class=e_info>" +
                                               "<p> <a href='${pageContext.request.contextPath}pesquisaNome?nomevento=" + l_ep[i] + "'>"  + l_ep[i] + "</a> </p>" +
                                               "<img src=/static/Img/Evento.jpg  alt= EVENTO PNG width=160 height=140>" +
                                               "</div>" ); 
                y++;
            }
        }
            
</script>
</head>
<body onload = "att()">
    <div id="menu">
        <nav><a href="${pageContext.request.contextPath}/login">Login</a></nav>        
        <nav><a href="${pageContext.request.contextPath}/newuser">Registar</a></nav>
        <nav><a href="${pageContext.request.contextPath}/user/inscricoes">Inscrições</a></nav>
        <nav><a href="${pageContext.request.contextPath}/pesquisa">Pesquisar</a></nav>
        <nav><a href="${pageContext.request.contextPath}/info">Classificações</a></nav>
        <nav><a href="${pageContext.request.contextPath}/admin">ADMIN</a></nav>
    </div>
    <div id="logout">
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            
            <nav>${pageContext.request.userPrincipal.name}<a href="<c:url value='logout'/>" > Sair </a> </nav>                     
        </c:if>
    </div>
    
     <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
        
        
    <div id="Lista_eventos">
        <h1 id="h1_h" class="h1_hp">Eventos de Hoje</h1>
        <p class="eventos" id= "e_hoje" ></p>   
        <div id='pag'>
            <ul class="paginas" id ="pag_h">
                <li><a onclick = setpagH(0)> 1 </a></li>
            </ul>
        </div>
        
        <h1 id = "h1_f" class="h1_hp">Futuros Eventos</h1>
        <p  class="eventos" id= "e_futuros" ></p>
        <div id='pag'>
            <ul class="paginas" id ="pag_f">
                <li><a onclick = setpagF(0)> 1 </a></li>
            </ul>       
        </div>
        
        <h1 id="h1_p" class="h1_hp">Eventos Concluidos</h1>
        <p  class="eventos" id= "e_passados" ></p>
        <div id='pag'>
            <ul class="paginas" id ="pag_p">
                <li><a onclick = setpagP(0)> 1 </a></li>
            </ul>
        </div>
    </div>   
    
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>        
    </div>
</body>
</html>