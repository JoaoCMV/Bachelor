<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
    <title>T2_TW_ranks</title>
    <link rel="stylesheet" href= "style.css">
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
    <script>
        
      
        var l_participantes = ${e_info_lpart};   // Lista de participantes
            var l_tempos = ${e_info_time};                // Lista de tempos
            var l_escaloes = ${e_info_escalao};
            
            
        function rank(){
            $('#rank').empty();
            $('#rank').css('display','block');
            $('#button_rank_escalao').css('display','block');
            
            $('#rank_escaloes').css('display','none');          
            $('#button_rank_normal').css('display','none');
                     
            for(var i = 0; i<l_participantes.length; i++){
                $('#rank').append("<p>" + l_participantes[i] + " time: " + l_tempos[i] + "</p>");
            }
        }
        
        function rankEscalao(){
            
            //Limpa os ranks todos
            $('#r_juvenis').empty();
            $('#r_junior').empty();
            $('#r_senior').empty();
            $('#r_senior65').empty();
            $('#rank').css('display','none');
            $('#button_rank_escalao').css('display','none');
            
            $('#button_rank_normal').css('display','block');
            $('#rank_escaloes').css('display','block');
            
            for(var i = 0; i<l_participantes.length; i++){
                console.log(l_escaloes[i]);
                if(l_escaloes[i] == "Juvenis"){
                    $('#r_juvenis').append("<p>" + l_participantes[i] + " time: " + l_tempos[i] + "</p>");
                }
                
                if(l_escaloes[i] == "Junior"){
                    $('#r_junior').append("<p>" + l_participantes[i] + " time: " + l_tempos[i] + "</p>");
                }
                
                if(l_escaloes[i] == "Senior"){
                    $('#r_senior').append("<p>" + l_participantes[i] + " time: " + l_tempos[i] + "</p>");
                }
                
                if(l_escaloes[i] == "Senior65+"){
                    $('#r_senior65').append("<p>" + l_participantes[i] + " time: " + l_tempos[i] + "</p>");
                }
            }
        }
        
        function rankNome(){        
            $('#rank_escaloes').css('display','none');
            $('#rank').css('display','block');
            $('#button_rank_escalao').css('display','block');           
            $('#button_rank_normal').css('display','block');
            $('#rank').empty();
            
            var nome = $('#p_nome').val();
            console.log(nome);
            
            for(var i = 0; i<l_participantes.length; i++){
                
                if(l_participantes[i] == nome ){
                    $('#rank').append("<p>" + l_participantes[i] + " time: " + l_tempos[i] + " escalao: " + l_escaloes[i] + "</p>");
                }
            }
        }
    </script>
</head>

<body onload="rank()">
    
    <div id="menu">
            <nav><a href="${pageContext.request.contextPath}/">HomePage</a></nav>
    </div>
    
    <div id="Logo">
        <img src="/static/Img/Logo.jpeg" alt="LOGO" width="240" height="120">
        <h1>Penguin Eventos</h1>
    </div>
    
    <div id="lista_pesquisa_buttons">
        <button class="sub" id="button_rank_normal" type ="button" onclick="rank()"> Rank Normal</button>
        <button class="sub" id="button_rank_escalao" type ="button" onclick="rankEscalao()"> Rank por Escalao</button>
        
        <p>
        <label>Nome: </label>
                        <input id="p_nome" type="text" name ="nome" value="">
                        <input id="sub" type='submit' value='Procurar' onclick="rankNome()"> 
        </p>  
    </div>
    
    <div id="rank">      
        
    </div>
    
    <div id="rank_escaloes">
        
        <div class="ranks" id="rank_juvenis">
            <h5>Juvenis</h5>
            <p id="r_juvenis"></p>
        </div>
        
        <div class="ranks" id="rank_junior">
            <h5>Juniores</h5>
            <p id="r_junior"></p>
        </div>
        
        <div class="ranks" id="rank_senior">
            <h5>Seniores</h5>
            <p id="r_senior"></p>
        </div>
        
        <div class="ranks" id="rank_senior655">
            <h5>Seniores 65+</h5>
            <p id="r_senior65"></p>
        </div>   
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
