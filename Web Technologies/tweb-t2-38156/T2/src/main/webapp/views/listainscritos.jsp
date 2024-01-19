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
        
        var i_nome = ${e_lista_nome};
        var i_genero = ${e_lista_genero};
        var i_escalao = ${e_lista_escalao};
        var i_estado = ${e_lista_estado};
        var i_dorsal = ${e_lista_dorsal};
        var e = new URLSearchParams(window.location.search).get("nomevento");
        const evento = e.replaceAll(' ', '+');
        
            
        function normal(){
            $('#lista_insc').empty();
            $('#lista_insc').css('display','block');
            $('#button_lista_escalao').css('display','block');
            
            $('#lista_escaloes').css('display','none');          
            $('#button_lista_normal').css('display','none');
            
            for(var i = 0; i < i_nome.length; i++){ 
                var nome = i_nome[i].replaceAll(' ', '+');
                $('#lista_insc').append("<p>" + i_dorsal[i] + " - Nome: " + "<a href=${pageContext.request.contextPath}p_info?inscrito=" + nome + "&nomevento=" + evento + ">"  + i_nome[i] + "</a>" +
                        ", " + i_genero[i] + ", " + i_escalao[i] + ", " + i_estado[i] + "</p>");
            }
        }
        
        function listaEscalao(){
            
            //Limpa os ranks todos
            $('#l_juvenis').empty();
            $('#l_junior').empty();
            $('#l_senior').empty();
            $('#l_senior65').empty();
            $('#lista_insc').css('display','none');
            $('#button_lista_escalao').css('display','none');
            
            $('#button_lista_normal').css('display','block');
            $('#lista_escaloes').css('display','block');
            
            for(var i = 0; i<i_nome.length; i++){
                console.log(i_escalao[i]);
                if(i_escalao[i] == "Juvenis"){
                    $('#l_juvenis').append("<p>" + i_dorsal[i] + " -Nome: " + "<a href=${pageContext.request.contextPath}p_info?participante= '" + i_nome[i] + "'>"  + i_nome[i] + "</a>" +
                            ", " + i_genero[i] + ", " + i_estado[i] + "</p>");
                }
                
                if(i_escalao[i] == "Junior"){
                    $('#l_junior').append("<p>" + i_dorsal[i] + " -Nome: " + i_nome[i] + ", " + i_genero[i] + ", " + i_estado[i] + "</p>");
                }
                
                if(i_escalao[i] == "Senior"){
                    $('#l_senior').append("<p>" + i_dorsal[i] + " -Nome: " + i_nome[i] + ", " + i_genero[i] + ", " + i_estado[i] + "</p>");
                }
                
                if(i_escalao[i] == "Senior65+"){
                    $('#l_senior65').append("<p>" + i_dorsal[i] + " -Nome: " + i_nome[i] + ", " + i_genero[i] + ", " + i_estado[i] + "</p>");
                }
            }
        }
        
        function listaNome(){        
            $('#lista_escaloes').css('display','none');
            $('#lista_insc').css('display','block');
            $('#button_lista_escalao').css('display','block');           
            $('#button_lista_normal').css('display','block');
            $('#lista_insc').empty();
            
            var nome = $('#p_nome').val();
            
            for(var i = 0; i<i_nome.length; i++){
                
                if(i_nome[i] == nome ){
                    $('#lista_insc').append("<p>" + i_dorsal[i] + " -Nome: " + i_nome[i] + ", " + i_genero[i] + ", " + i_escalao[i] + ", " + i_estado[i] + "</p>");
                }
            }
        }
    </script>
</head>

<body onload="normal()">
    
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="lista_pesquisa_buttons">
        <button class = "sub" id="button_lista_normal" type ="button" onclick="normal()"> Lista Normal</button>
        <button class="sub" id="button_lista_escalao" type ="button" onclick="listaEscalao()"> Lista por Escalao</button>
        
        <p>
        <label>Nome: </label>
                        <input id="p_nome" type="text" name ="nome" value="">
                        <input id="sub" type='submit' value='Procurar' onclick="listaNome()"> 
        </p>  
    </div>
    
    <div id="lista_insc">
    </div>
    <div id="lista_escaloes">
        
        <div class="ranks" id="lista_juvenis">
            <h5>Juvenis</h5>
            <p id="l_juvenis"></p>
        </div>
        
        <div class="ranks" id="lista_junior">
            <h5>Juniores</h5>
            <p id="l_junior"></p>
        </div>
        
        <div class="ranks" id="lista_senior">
            <h5>Seniores</h5>
            <p id="l_senior"></p>
        </div>
        
        <div class="ranks" id="lista_senior655">
            <h5>Seniores 65+</h5>
            <p id="l_senior65"></p>
        </div>   
    </div>
             
    <div id="rodape">      
        <p id="patron">UE- Universidade de Évora e TW- Tecnologias Web</p>
        <p id = "sobre"> Empresa fundada em 2021 na cidade de Évora reponsavél por vários eventos</p>
        <p id="autor"> João Verdilheiro</p>
        
    </div>
        
</body>
</html>
