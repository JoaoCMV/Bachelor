package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.controller;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.UserDao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Evento;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.User;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import static org.postgresql.core.Oid.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringSecurityController {

    @Autowired
    private UserDao userDao;
    
    Authentication authent = SecurityContextHolder.getContext().getAuthentication();

    @GetMapping("/")
    public String defaultPage(Model model) {
        
        // Eventos Hoje
        List<String> eventToday = userDao.geteventonameListbyToday();
        // Formata os nomes para ser reconhecido na view
        for(int i = 0; i<eventToday.size(); i++ ){
            eventToday.set(i, "'" + eventToday.get(i) + "'");                
        }
        model.addAttribute("eventos_hoje", eventToday );
        
        // Eventos passados
        List<String> eventBeforeToday = userDao.geteventonameListbyBeforeToday();
        // Formata os nomes para ser reconhecido na view
        for(int i = 0; i<eventBeforeToday.size(); i++ ){
            eventBeforeToday.set(i, "'" + eventBeforeToday.get(i) + "'");
        }
        model.addAttribute("eventos_passados", eventBeforeToday );
        
        // Eventos futuros
        List<String> eventAfterToday = userDao.geteventonameListbyAfterToday();
        // Formata os nomes para ser reconhecido na view
        for(int i = 0; i<eventAfterToday.size(); i++ ){
            eventAfterToday.set(i, "'" + eventAfterToday.get(i) + "'");
        }
        model.addAttribute("eventos_futuros", eventAfterToday );
        
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been successfully logged out");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model, HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        
        try{
        model.addAttribute("title", "Administrator Control Panel");
        model.addAttribute("message", "This page demonstrates how to use Spring security");
        return "admin";
        }catch(Exception e){
            System.out.println("ERRO");
            return "index";
        }
    }
    
    @GetMapping("/user")
    public String userPage(Model model) {
        return "user";
    }
    
    @GetMapping("newuser")
    public String newuser(Model model) {
        model.addAttribute("title", "Registar");
        model.addAttribute("message", "Preencha os dados necessários");
        List<String> currentUsers = userDao.getUsernameList();
        System.out.println("\n" + currentUsers.size() + " USERS: " + currentUsers.toString());
        return "newuser";
    }
    
    @PostMapping(path = "registerUser", consumes = "application/x-www-form-urlencoded")
    public String register(User user){

        System.out.println(user.toString());
        String encodedPassword= new BCryptPasswordEncoder().encode(user.getPassword());
        User u = new User(user.getUsername(), encodedPassword, "ROLE_USER"); 
        userDao.saveUser(u);  // escrever na BD
        System.out.println("GRAVAR na BD: " + u.toString());
        //model.addAttribute("user", u);   // deixar à disposição da view ?
        
        return "login";
    }
    
    @GetMapping("/admin/RegisTime")
    public String register(@RequestParam String nomevento,
            @RequestParam String etapa,
            @RequestParam String t,
            @RequestParam String dorsal,
            Model model) {

        System.out.println(nomevento);
        System.out.println(etapa);
        System.out.println(t);
        System.out.println(dorsal);
        if(etapa.equals("start")){
            userDao.saveTimeStart(nomevento, t, dorsal);  // escrever na BD
        }
        if(etapa.equals("p1")){
            userDao.saveTimeP1(nomevento, t, dorsal);  // escrever na BD
        }
        if(etapa.equals("p2")){
            userDao.saveTimeP2(nomevento, t, dorsal);  // escrever na BD
        }
        if(etapa.equals("p3")){
            userDao.saveTimeP3(nomevento, t, dorsal);  // escrever na BD
        }
        if(etapa.equals("finish")){
            userDao.saveTimeFinish(nomevento, t, dorsal);  // escrever na BD
        }
        
        return "admin";
    }
    
    @GetMapping("/admin/registerEvento")
    public String registerEvento(@RequestParam String nomeEvento,
            @RequestParam String d,
            @RequestParam float valor,
            @RequestParam String desc,
            Model model) throws ParseException {

        System.out.println(d);
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        Date data=formatter1.parse(d); 
        Evento e = new Evento(nomeEvento, valor, data, desc); 
        userDao.saveEvento(e);  // escrever na BD
        System.out.println("GRAVAR na BD: " + e.toString());
        
        return "admin";
    }
    
    @GetMapping("/user/newinscrever")
    public String newinscricao(@RequestParam String nomevento,
            Model model) {
        model.addAttribute("title", "Inscrever");
        model.addAttribute("evento", nomevento);
        return "newinscrever";
    }
    
    @GetMapping("/inscrever")
    public String inscrever(@RequestParam String nome,
            @RequestParam String nomevento,
            @RequestParam String genero,
            @RequestParam String escalao,
            Model model, Principal principal) throws ParseException {

        List<String> custo = userDao.getEventoCusto(nomevento);
        String referencia = getReferencia("amount=" + custo.get(0));
        model.addAttribute("referencia", "'" + referencia + "'"); 
        
        userDao.saveInscricao(nome, nomevento, genero, escalao,principal.getName(), referencia);
        model.addAttribute("feedback", nome + ", inscrito com sucesso.");
        System.out.println("Inscrito... " + nome);
        model.addAttribute("p_nome","'" + nome + "'");
        model.addAttribute("p_evento", "'" + nomevento + "'");
 
        return "inscrito";
    }
    
    @GetMapping("/pagarInscrever")
    public String pagarInscrever(@RequestParam String nome,
            @RequestParam String nomevento,
            Model model) {
        
        System.out.println(nome);
        System.out.println(nomevento);
        userDao.updatePagamento(nome,nomevento);
        model.addAttribute("feedback","Pagamento com sucesso.");
        return "inscrito";
    }
    
    @GetMapping("/user/pagarLista")
    public String pagarlista(@RequestParam String nome,
            @RequestParam String nomevento,
            Principal principal,
            Model model) {
        
        userDao.updatePagamento(nome,nomevento);
        List<String> insc_evento = userDao.getEventoInscricoesByUser(principal.getName());
        List<String> insc_estado = userDao.getEstadoInscricoesByUser(principal.getName());
        List<String> insc_participante = userDao.getParticipanteInscricoesByUser(principal.getName());
        List<String> insc_ref = userDao.getParticipanteReferencias(principal.getName());
        
        // FALTA REF
        
        for(int i = 0; i<insc_evento.size(); i++ ){
            insc_evento.set(i, "'" + insc_evento.get(i) + "'");
            insc_estado.set(i, "'" + insc_estado.get(i) + "'");
            insc_participante.set(i, "'" + insc_participante.get(i) + "'");
            insc_ref.set(i, "'" + insc_ref.get(i) + "'");
        }
        model.addAttribute("insc_evento", insc_evento );
        model.addAttribute("insc_estado", insc_estado );
        model.addAttribute("insc_participante", insc_participante );
        model.addAttribute("insc_ref", insc_ref );
        
        System.out.println(nome);
        System.out.println(nomevento);
        return "inscricoes";
    }
    
    @GetMapping("/user/inscricoes")
    public String listainscricoes(Principal principal,
            Model model) {
        List<String> insc_evento = userDao.getEventoInscricoesByUser(principal.getName());
        List<String> insc_estado = userDao.getEstadoInscricoesByUser(principal.getName());
        List<String> insc_participante = userDao.getParticipanteInscricoesByUser(principal.getName());
        List<String> insc_ref = userDao.getParticipanteReferencias(principal.getName());
        
        // FALTA REF
        
        for(int i = 0; i<insc_evento.size(); i++ ){
            insc_evento.set(i, "'" + insc_evento.get(i) + "'");
            insc_estado.set(i, "'" + insc_estado.get(i) + "'");
            insc_participante.set(i, "'" + insc_participante.get(i) + "'");
            insc_ref.set(i, "'" + insc_ref.get(i) + "'");
        }
        model.addAttribute("insc_evento", insc_evento );
        model.addAttribute("insc_estado", insc_estado );
        model.addAttribute("insc_participante", insc_participante );
        model.addAttribute("insc_ref", insc_ref );
        
        return "inscricoes";
    }
    
    @GetMapping("/pesquisa")
    public String pesquisa(Model model) {
        return "pesquisa";
    }
    
    @GetMapping("/listainscritos")
    public String pesquisainscritos(@RequestParam String nomevento,
            Model model) {
        
        List<String> i_nome = userDao.getEventoinscritosNome(nomevento);
        List<String> i_genero = userDao.getEventoinscritosGenero(nomevento);
        List<String> i_escalao = userDao.getEventoinscritosEscalao(nomevento);
        List<String> i_estado = userDao.getEventoinscritosEstado(nomevento);
        List<String> i_dorsal = new ArrayList<>();
        
        for(int i = 0; i< i_nome.size(); i++ ){
                i_nome.set(i, "'" + i_nome.get(i) + "'");
                i_genero.set(i, "'" + i_genero.get(i) + "'");
                i_escalao.set(i, "'" + i_escalao.get(i) + "'");
                i_estado.set(i, "'" + i_estado.get(i) + "'");
                
                // Visto ambas as listas anteriores serem "order by" dorsal é assumido que a lista de dorsal está por ordem
                // uma vez que após atribuido o dorsal este nunca irá alterar 
                i_dorsal.add("'" + i + "'");                    
            }
        model.addAttribute("e_lista_nome", i_nome);
        model.addAttribute("e_lista_genero", i_genero);
        model.addAttribute("e_lista_escalao", i_escalao);
        model.addAttribute("e_lista_estado", i_estado);
        model.addAttribute("e_lista_dorsal", i_dorsal);
        
        return "listainscritos";
    }
    
    @GetMapping("/p_info")
    public String infoInscrito(@RequestParam String nomevento,
            @RequestParam String inscrito,
            Model model) {
        
        String evento=nomevento.replace('+',' ');
        System.out.println("EVENTO: " + evento);
        System.out.println("PARTICIPANTE: " + inscrito);
        
        List<String> start = userDao.getStartTime(evento, inscrito);
        model.addAttribute("start_time",start.get(0));
        
        List<String> p1 = userDao.getP1Time(evento, inscrito);
        model.addAttribute("p1_time",p1.get(0));
        
        List<String> p2 = userDao.getP2Time(evento, inscrito);
        model.addAttribute("p2_time",p2.get(0));
        
        List<String> p3 = userDao.getP3Time(evento, inscrito);
        model.addAttribute("p3_time", p3.get(0));
        
        List<String> finish = userDao.getFinishTime(evento, inscrito);
        model.addAttribute("finish_time",finish.get(0));
        
        model.addAttribute("nome_p", inscrito);
        model.addAttribute("evento_p", evento);
        
        return "infoperfil";
    }
    
    @GetMapping("/pesquisaNome")
    public String pesquisaNome(@RequestParam String nomevento,
            Model model) throws ParseException {
        
        List<String> data = userDao.getEventoDate(nomevento);
        List<String> desc = userDao.getEventoDesc(nomevento);

        // Verifica se o evento foi encontrado...
        if(!data.isEmpty()){
            model.addAttribute("e_data", "['" + data.get(0) + "']");
            model.addAttribute("e_nome", "['" + nomevento + "']");
            model.addAttribute("e_desc", "['" + desc.get(0) + "']");
        }
        
        return "pesquisa";
    }
    
    @GetMapping("/pesquisaData")
    public String pesquisaData(@RequestParam String d,
            Model model) throws ParseException {
        
        System.out.println(d);
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        Date data=formatter1.parse(d); 
        
        System.out.println(data);
        
        List<String> nome = userDao.getEventoNome(d);
        List<String> desc = userDao.getEventoDescByDate(d);
        
  
        // Verifica se o evento foi encontrado...
        if(!nome.isEmpty()){
            
            for(int i = 0; i< nome.size(); i++ ){
                nome.set(i, "'" + nome.get(i) + "'");
                desc.set(i, "'" + desc.get(i) + "'");
            }   
        
            model.addAttribute("e_nome", nome);
            model.addAttribute("e_data","['" + d + "']" );
            model.addAttribute("e_desc", desc);
        }
        
        return "pesquisa";
    }
    
    @GetMapping("/info")
    public String info(Model model) {
        return "info";
    }
    
    @GetMapping("/getLista")
    public String listaParticipantes(@RequestParam String nomevento,
            Model model) throws ParseException {
         
        
        List<String> lpart = userDao.getListaNomesByEvento(nomevento);
        
  
        // Verifica se o evento foi encontrado...
        if(!lpart.isEmpty()){
        
            // Adiciona ao modelo a lista de participantes
            for(int i = 0; i< lpart.size(); i++ ){
                lpart.set(i, "'" + lpart.get(i) + "'");
            }
            model.addAttribute("e_info_lpart", lpart );
            
            // Adiciona ao modelo as suas informações
        }else{
            model.addAttribute("e_info", "SEM PARTICIPANTES");
        }
        
        return "info";
    }
    
    
    // Recebe os tempos de finish e start e calcula o tempo total de prova
    private long getMls(String start, String finish) throws ParseException{
        
        Date parsedDate;
        Timestamp ts_start, ts_finish;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        
        //Time de partida
        parsedDate = dateFormat.parse(start);
        ts_start=new Timestamp(parsedDate.getTime()); 

        // Time de finish
        parsedDate = dateFormat.parse(finish);
        ts_finish=new Timestamp(parsedDate.getTime());
        
        return ( ts_finish.getTime() - ts_start.getTime() );
        
    }
    
    private String formatToTime(long mls){
        
        long h, min, sec;
        // Converte o timestamp em hh:mm:ss:msmsms
        h = TimeUnit.MILLISECONDS.toHours(mls);
        
        min = TimeUnit.MILLISECONDS.toMinutes(mls) % TimeUnit.HOURS.toMinutes(1);
 
        sec = TimeUnit.MILLISECONDS.toSeconds(mls) % TimeUnit.MINUTES.toMinutes(1);
        
        return ("'" + h + ":" + min + ":" + sec + "'");
    }
    
    @GetMapping("/getInfoABS")
    public String listaParticipantesTime(@RequestParam String nomevento,
            Model model) throws ParseException {
        
        List<String> l_part = userDao.getListaNomesByEvento(nomevento);
        List<String> l_time_start = userDao.getTimeStart(nomevento);
        List<String> l_time_finish = userDao.getTimeFinish(nomevento);
        List<String> l_escalao = userDao.getEscalaoByEvento(nomevento);
        
        List<String> l_time = new ArrayList<>();            // lista final de tempos
        List<String> l_part_final = new ArrayList<>();
        List<Long> l_time_tmp = new ArrayList<>();
        List<String> l_escalao_final = new ArrayList<>();
        
        // Guarda o index de cada Atleta , para saber qual foi o atleta que fez 'x' tempo
        Dictionary dic = new Hashtable();
        long mls;
        int y;
  
        // Verifica se o evento foi encontrado...
        if(!l_part.isEmpty()){
        
            // Adiciona ao modelo a lista de participantes
            for(int i = 0; i< l_part.size(); i++ ){
                
                // Se tiver tempos válidos
                if(l_time_start.get(i) != null && l_time_finish.get(i) != null){
                
                    mls = getMls(l_time_start.get(i), l_time_finish.get(i));
                    
                    l_time_tmp.add(mls);
                    dic.put(mls, i);   
                }
            }
            
            Collections.sort(l_time_tmp);
            String t;
            
            for(int i = 0; i < l_time_tmp.size(); i++){
                
                t = formatToTime(l_time_tmp.get(i)); 
                mls = l_time_tmp.get(i);               
        
                y = (int)dic.get(mls);
                l_part_final.add(i, "'" + l_part.get(y) + "'");
                l_time.add(i, t);
                l_escalao_final.add(i, "'" + l_escalao.get(y) + "'");
                
            }
            model.addAttribute("e_info_lpart", l_part_final );
            model.addAttribute("e_info_time", l_time );
            model.addAttribute("e_info_escalao" , l_escalao_final);
            
            // Adiciona ao modelo as suas informações
            
            
        }else{
            model.addAttribute("e_info", "SEM PARTICIPANTES");
        }
        
        return "ranks";
    }
    
    @GetMapping("/getInfoM")
    public String listaParticipantesTimeM(@RequestParam String nomevento,
            Model model) throws ParseException {
        
        List<String> l_part = userDao.getListaNomesByGenero(nomevento, "m");
        List<String> l_time_start = userDao.getTimeStartByGenero(nomevento, "m");
        List<String> l_time_finish = userDao.getTimeFinishByGenero(nomevento, "m");
        List<String> l_escalao = userDao.getEscalaoByGenero(nomevento, "m");
         
        for(int i = 0; i<l_part.size();i++){
            System.out.println(l_part.get(i));
            System.out.println(l_time_start.get(i));
            System.out.println(l_time_finish.get(i));
            System.out.println(l_escalao.get(i));
        }
        
        List<String> l_time = new ArrayList<>();            // lista final de tempos
        List<String> l_part_final = new ArrayList<>();
        List<Long> l_time_tmp = new ArrayList<>();
        List<String> l_escalao_final = new ArrayList<>();
        
        // Guarda o index de cada Atleta , para saber qual foi o atleta que fez 'x' tempo
        Dictionary dic = new Hashtable();
        long mls;
        int y;
  
        // Verifica se o evento foi encontrado...
        if(!l_time_start.isEmpty()){
        
            // Adiciona ao modelo a lista de participantes
            for(int i = 0; i< l_time_start.size(); i++ ){
                
                // Se tiver tempos válidos
                if(l_time_start.get(i) != null && l_time_finish.get(i) != null){
                
                    mls = getMls(l_time_start.get(i), l_time_finish.get(i));
                    l_time_tmp.add(mls);
                    dic.put(mls, i);   
                }
            }
            
            Collections.sort(l_time_tmp);
            String t;
            
            for(int i = 0; i < l_time_tmp.size(); i++){
                
                t = formatToTime(l_time_tmp.get(i));                                    
                
                mls = l_time_tmp.get(i);
                y = (int)dic.get(mls);
                l_part_final.add(i, "'" + l_part.get(y) + "'");
                l_time.add(i, t);
                l_escalao_final.add(i, "'" + l_escalao.get(y) + "'");
                
            }
            model.addAttribute("e_info_lpart", l_part_final );
            model.addAttribute("e_info_time", l_time );
            model.addAttribute("e_info_escalao" , l_escalao_final);
            
            // Adiciona ao modelo as suas informações
            
            
        }else{
            model.addAttribute("e_info", "SEM PARTICIPANTES");
        }
        
        return "ranks";
    }
    
    @GetMapping("/getInfoF")
    public String listaParticipantesTimeF(@RequestParam String nomevento,
            Model model) throws ParseException {
        
        List<String> l_part = userDao.getListaNomesByGenero(nomevento, "f");
        List<String> l_time_start = userDao.getTimeStartByGenero(nomevento, "f");
        List<String> l_time_finish = userDao.getTimeFinishByGenero(nomevento, "f");
        List<String> l_escalao = userDao.getEscalaoByGenero(nomevento, "f");
        
        for(int i = 0; i<l_part.size();i++){
            System.out.println(l_part.get(i));
            System.out.println(l_time_start.get(i));
            System.out.println(l_time_finish.get(i));
            System.out.println(l_escalao.get(i));
        }
        
        List<String> l_time = new ArrayList<>();            // lista final de tempos
        List<String> l_part_final = new ArrayList<>();
        List<Long> l_time_tmp = new ArrayList<>();
        List<String> l_escalao_final = new ArrayList<>();
        
        // Guarda o index de cada Atleta , para saber qual foi o atleta que fez 'x' tempo
        Dictionary dic = new Hashtable();
        long mls;
        int y;
  
        // Verifica se o evento foi encontrado...
        if(!l_time_start.isEmpty()){
        
            // Adiciona ao modelo a lista de participantes
            for(int i = 0; i< l_time_start.size(); i++ ){
                
                // Se tiver tempos válidos
                if(l_time_start.get(i) != null && l_time_finish.get(i) != null){
                
                    mls = getMls(l_time_start.get(i), l_time_finish.get(i));
                    l_time_tmp.add(mls);
                    dic.put(mls, i);   
                }
            }
            
            Collections.sort(l_time_tmp);
            String t;
            
            for(int i = 0; i < l_time_tmp.size(); i++){               
        
                mls = l_time_tmp.get(i);
                t = formatToTime(l_time_tmp.get(i));  
                
                y = (int)dic.get(mls);
                l_part_final.add(i, "'" + l_part.get(y) + "'");
                l_time.add(i, t);
                l_escalao_final.add(i, "'" + l_escalao.get(y) + "'");
                
            }
            model.addAttribute("e_info_lpart", l_part_final );
            model.addAttribute("e_info_time", l_time );
            model.addAttribute("e_info_escalao" , l_escalao_final);
            // Adiciona ao modelo as suas informações
            
            
        }else{
            model.addAttribute("e_info", "SEM PARTICIPANTES");
        }
        
        return "ranks";
    }
    
    public static String getReferencia(String urlParameters) {
            HttpURLConnection connection = null;

            try {
              //Create connection
              URL url = new URL("http://alunos.di.uevora.pt/tweb/t2/mbref4payment");
              connection = (HttpURLConnection) url.openConnection();
              connection.setRequestMethod("POST");
              connection.setRequestProperty("Content-Type", 
                  "application/x-www-form-urlencoded");

              connection.setRequestProperty("Content-Length", 
                  Integer.toString(urlParameters.getBytes().length));
              connection.setRequestProperty("Content-Language", "en-US");  

              connection.setUseCaches(false);
              connection.setDoOutput(true);

              //Send request
              DataOutputStream wr = new DataOutputStream (
                  connection.getOutputStream());
              wr.writeBytes(urlParameters);
              wr.close();

              //Get Response  
              InputStream is = connection.getInputStream();
              BufferedReader rd = new BufferedReader(new InputStreamReader(is));
              StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
              String line;
              while ((line = rd.readLine()) != null) {
                response.append(line);
              }
              rd.close();
              return response.toString();
            } catch (Exception e) {
              e.printStackTrace();
              return null;
            } finally {
              if (connection != null) {
                connection.disconnect();
            }
        }
    }
  

}
