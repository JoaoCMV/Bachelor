
package com.t2.sd;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/sd") // This means URL's start with /sd (after Application path)
public class MainController {
  @Autowired // This means to get the bean called userRepository
             // Which is auto-generated by Spring, we will use it to handle the data
  private ParticipanteRepository ParticipanteRepository;
  
  @Autowired
  private EventoRepository EventoRepository;
  
  @Autowired
  private ChipRepository ChipRepository;

  
  // ADICIONA UM EVENTO (NOME E DATA)
  @PostMapping(path="/addE")
  public @ResponseBody String addNewEvento (@RequestParam String nome
      , @RequestParam Date data) {
    
    Evento e = new Evento(nome, data);
    EventoRepository.save(e);
    return "\n -> Evento Criado!";
  }
  
  
  // DEVOLVE TODOS OS EVENTOS NA DATA 
  @PostMapping(path="/allE")
  public @ResponseBody Iterable<Evento> getAllEventosByDate(@RequestParam Date data) {
     
    System.out.println("\n -> Todos os Eventos do Sistema na Data pretendida");
    
    return EventoRepository.findByData(data);
  }
  
  // DEVOLVE A LISTA DE INSCRITOS NO EVENTO 
  @PostMapping(path="/allIns")
  public @ResponseBody Iterable<Participante> getAllInscritos(@RequestParam String nome) {
     
    System.out.println("\n -> Inscitos no Evento" + nome);
    // Procura o evento pretendido
    Evento e = EventoRepository.findByNome(nome);
    // Devolve todos os participantes com idE correspondente ao evento
    return ParticipanteRepository.findByidE(e.getId());
  }
  
  //  ADICIONA UM PARTICIPANTE
  @PostMapping(path="/addP") // Map ONLY POST Requests
  public @ResponseBody String addNewParticipante (@RequestParam String nome
      , @RequestParam char genero, @RequestParam String escalao, @RequestParam String nome_Evento) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    
    //Identifica o evento que pretende ser inscrito
    Evento e = EventoRepository.findByNome(nome_Evento);
    // Verifica quantos inscritos tem para saber o dorsal do atleta a inscrever
    int dorsal = ParticipanteRepository.countByIdE(e.getId());
    // Cria o novo participante e adiciona com os devidos dados
    Participante p = new Participante(nome, genero, escalao, e.getId(), dorsal);
    ParticipanteRepository.save(p);
    
    System.out.println("\ncid: " + p.getchipId());
    // Atualiza a tabela de chips com o novo chipId
    Chip c = new Chip( p.getchipId() );
    ChipRepository.save(c);
    
    // Retorna com sucesso e o devido dorsal do atleta
    return "\n -> Participante Adicionado! \nEvento: " + e.getNome() + ", dorsal: " + dorsal;
  }
  
  
  // ATUALIZA O TIMESTAMP DE CADA PARTICIPANTE NO LOCAL LIDO
  @PostMapping("/Chip")
  public @ResponseBody String attTimestamp(@RequestParam Timestamp time,@RequestParam String local, @RequestParam int chipId2) {
    
    // Encontra o chip pretendido e atualiza a informação consoante o local onde foi feita a leitura
    ChipRepository.findById(chipId2)
        .map(chip -> {
            switch(local){
        
                case "start":
                    chip.setStart( time );
                    break;
                case "p1":
                    chip.setP1( time );
                    break;
                case "p2":
                    chip.setP2( time );
                    break;
                case "p3":
                    chip.setP3( time );
                    break;
                case "finish":
                    chip.setFinish( time );
                    break;
            }      
            return ChipRepository.save(chip);
        });
    
    return "Atualizado!";
  }
  
  
  // Verifica se o tempo foi alterado
  private boolean passou(Timestamp t){
      
      Timestamp tzero = new Timestamp(0);
      // verifica se o tempo foi alterado, no caso se for igual a 0 então nao sofreu alterações
      if( t.equals(tzero) ){
          return false;
      }else{
          return true;
      }
  }
  
  
  // Recebe um chip e calcula o tempo que demorou a alcançar a etapa pretendida
  private long rank(Chip c, String etapa){
      
      long res = -1;
      
      // Verifica se o participante já passou na etapa
      if(passou( c.getP1() )){
          
        switch(etapa){
            case("p1"):
               
               // Calcula a diferença entre a etapa pretendida e a partida
               res = c.getP1().getTime() - c.getStart().getTime();
               return res;
               
        case("p2"):
            
            res = c.getP2().getTime() - c.getStart().getTime();
            return res;
            
        case("p3"):
            
            res = c.getP3().getTime() - c.getStart().getTime();
            return res;
            
        case("finish"):
            
            res = c.getFinish().getTime() - c.getStart().getTime();
            return res;
            
        }
      }
      return -1;
  }
  
  // Classificação Geral/parcial 
  @PostMapping(path="/Rank")
  public @ResponseBody List<String> classificacao (@RequestParam String nome, @RequestParam String etapa) {
    
    List<String> res = new ArrayList<>();
    // Auxiliares para guardar a informação de cada participante
    Chip c;
    long tempo;
    List<Long> rankF = new ArrayList<>();
    
    // Guarda o index de cada Atleta , para saber qual foi o atleta que fez 'x' tempo
    Dictionary dic = new Hashtable();
    
    System.out.println("\n -> Inscitos no Evento" + nome);
    // Procura o evento pretendido
    Evento e = EventoRepository.findByNome(nome);
    List<Participante> ip = ParticipanteRepository.findByidE(e.getId());
    
    // Procura na lista de participantes do evento
    for(int i = 0; i < ip.size(); i++) {
        
        // Retira o chip ID do participante
        int id_c = ip.get(i).getchipId();
        // Associa o chip a c para analisar o chip
        c = ChipRepository.findByChipId2(id_c);
        // Método auxiliar para calcular o tempo do atleta na etapa pretendida
        tempo = rank(c, etapa);
        
        // Se o tempo for válido atualiza o dicionário
        if(tempo > 0){
            dic.put(tempo, i);
            rankF.add(tempo);
        }              
    }
    
    // Organiza a lista de tempos por ordem , para que posteriormente possa ser usado o dicionario
    // para saber quem fez cada tempo
    Collections.sort(rankF);
    long h, min, sec, mls, milli;
    
    int y;
    for(int i = 0; i < rankF.size(); i++){
        
        mls = rankF.get(i);
        
        // Converte o timestamp em hh:mm:ss:msmsms
        h = TimeUnit.MILLISECONDS.toHours(mls);
        
        min = TimeUnit.MILLISECONDS.toMinutes(mls) % TimeUnit.HOURS.toMinutes(1);
 
        sec = TimeUnit.MILLISECONDS.toSeconds(mls) % TimeUnit.MINUTES.toMinutes(1);
        
        milli = mls % 1000;
        
        //Procura o index correspondente ao atleta que fez este tempo 
        y = (int)dic.get(mls);
        res.add( (i+1) + ". -> " + ip.get(y).getName() +
                           " Dorsal: " + ip.get(y).getDorsal() +
                           " tempo: " + h + ":" + min + ":" + sec + ":" + milli);
        
    }
    
    // Metodo auxiliar que devolve a lista de classificação consoante a etapa pedida
    return res;
  }
  
  // Numero de atletas que já passaram em Pi 
  @PostMapping(path="/Passou")
  public @ResponseBody int passou (@RequestParam String nome, @RequestParam String etapa) {
      
    Chip c;
    long tempo;
    int contador = 0;
    
    Evento e = EventoRepository.findByNome(nome);
    List<Participante> ip = ParticipanteRepository.findByidE(e.getId());
    
    // Procura na lista de participantes do evento
    for(int i = 0; i < ip.size(); i++) {
        
        // Retira o chip ID do participante
        int id_c = ip.get(i).getchipId();
        // Associa o chip a c para analisar o chip
        c = ChipRepository.findByChipId2(id_c);
        // Método auxiliar para calcular o tempo do atleta na etapa pretendida
        tempo = rank(c, etapa);
        
        // Se o tempo for válido atualiza o contador
        if(tempo > 0){
            contador ++;
        }              
    }
    
    return contador;
  }
  
  // TESTES
  
  // DEVOLVE TODOS OS CHIPS
  @GetMapping(path="/allC")
  public @ResponseBody Iterable<Chip> teste () {
      
    System.out.println("\n -> Todos os Chips do Sistema");
    
    return ChipRepository.findAll();
  }
  
  // DEVOLVE TODOS OS PARTICIPANTES NO SISTEMA
  @GetMapping(path="/allP")
  public @ResponseBody Iterable<Participante> getAllParticipantes() {
     
    System.out.println("\n -> Todos os Participantes do Sistema");
    
    // This returns a JSON or XML with the users
    return ParticipanteRepository.findAll();
  }
  
  // DEVOLVE TODOS OS EVENTOS NO SISTEMA
  @GetMapping(path="/allE")
  public @ResponseBody Iterable<Evento> getAllEventos() {
     
    System.out.println("\n -> Todos os Eventos do Sistema");
    
    return EventoRepository.findAll();
  }
}
