package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.controller;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.UserDao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Evento;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.User;
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
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import static org.postgresql.core.Oid.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("title", "Administrator Control Panel");
        model.addAttribute("message", "This page demonstrates how to use Spring security");
        return "admin";
    }
    
    @GetMapping("/user")
    public String userPage(Model model) {
        return "user";
    }
    
    @GetMapping("newuser")
    public String newuser(Model model) {
        model.addAttribute("title", "New User");
        model.addAttribute("message", "fill new user's details");
        //DESCOMENTAR ADIANTE
        List<String> currentUsers = userDao.getUsernameList();
        model.addAttribute("message", "(we have now " + currentUsers.size() + " users) fill new user's details");
        System.out.println("\n" + currentUsers.size() + " USERS: " + currentUsers.toString());
        return "newuser";
    }
    
    @GetMapping("registerUser")
    public String register(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String genero,
            Model model) {

        // check if email1.equals(email2)
        String encodedPassword= new BCryptPasswordEncoder().encode(password);
        User u = new User(username, encodedPassword, genero, "ROLE_USER"); 
        userDao.saveUser(u);  // escrever na BD
        System.out.println("GRAVAR na BD: " + u.toString());
        model.addAttribute("user", u);   // deixar à disposição da view ?
        
        model.addAttribute("title", "registration page");
        model.addAttribute("message", "registration is OK");
        return "registerUser";
    }
    
    @GetMapping("/admin/Time")
    public String newTime(Model model) {
        model.addAttribute("title", "Set Time");
        List<String> currentEventos = userDao.geteventonameList();
        model.addAttribute("message", "(we have now " + currentEventos.size() + " events)");
        return "time";
    }
    
    @GetMapping("/admin/RegisTime")
    public String register(@RequestParam String username,
            @RequestParam String nomevento,
            @RequestParam String etapa,
            @RequestParam String t,
            @RequestParam String dorsal,
            Model model) {

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
        
        return "index";
    }
    
    @GetMapping("/admin/newevento")
    public String newevento(Model model) {
        model.addAttribute("title", "New Evento");
        model.addAttribute("message", "fill new evento details");
        List<String> currentEventos = userDao.geteventonameList();
        model.addAttribute("message", "(we have now " + currentEventos.size() + " events)");
        return "newevento";
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
        Evento e = new Evento(nomeEvento, data, valor, desc); 
        userDao.saveEvento(e);  // escrever na BD
        System.out.println("GRAVAR na BD: " + e.toString());
        
        return "index";
    }
    
    @GetMapping("/user/newinscrever")
    public String newinscricao(Model model) {
        model.addAttribute("title", "Inscrever");
        return "newinscrever";
    }
    
    @GetMapping("/user/inscrever")
    public String inscrever(@RequestParam String nomevento,
            Model model, Principal principal) throws ParseException {

        userDao.saveInscricao(principal.getName(), nomevento);
        return "inscrever";
    }
    
    @GetMapping("/pesquisa")
    public String pesquisa(Model model) {
        return "pesquisa";
    }
    
    @GetMapping("/error")
    public String erro(Model model) {
        return "index";
    }
    
    @GetMapping("/pesquisaNome")
    public String pesquisaNome(@RequestParam String nomevento,
            Model model) throws ParseException {
        
        List<String> data = userDao.getEventoDate(nomevento);
        List<String> desc = userDao.getEventoDesc(nomevento);

        // Verifica se o evento foi encontrado...
        if(!data.isEmpty()){
            model.addAttribute("e_data", data.get(0));
            model.addAttribute("e_nome", nomevento);
            model.addAttribute("e_desc", desc.get(0));
        }else{
            model.addAttribute("e_nome", "EVENTO NÃO ENCONTRADO");
        }
        
        return "pesquisa";
    }
    
    @GetMapping("/pesquisaData")
    public String pesquisaData(@RequestParam String d,
            Model model) throws ParseException {
        
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        Date data=formatter1.parse(d); 
        
        List<String> nome = userDao.getEventoNome(data);
        List<String> desc = userDao.getEventoDesc(data);
        
  
        // Verifica se o evento foi encontrado...
        if(!nome.isEmpty()){
        
            model.addAttribute("e_nome", nome.get(0));
            model.addAttribute("e_data", d );
            model.addAttribute("e_desc", desc.get(0));
        }else{
            model.addAttribute("e_data", "EVENTO NÃO ENCONTRADO");
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
    
    @GetMapping("/getInfoABS")
    public String listaParticipantesTime(@RequestParam String nomevento,
            Model model) throws ParseException {
        
        List<String> l_part = userDao.getListaNomesByEvento(nomevento);
        List<String> l_time_start = userDao.getTimeStart(nomevento);
        List<String> l_time_finish = userDao.getTimeFinish(nomevento);
        
        List<String> l_time = new ArrayList<>();            // lista final de tempos
        List<String> l_part_final = new ArrayList<>();
        List<Long> l_time_tmp = new ArrayList<>();
        
        // Guarda o index de cada Atleta , para saber qual foi o atleta que fez 'x' tempo
        Dictionary dic = new Hashtable();
        Timestamp ts_start, ts_finish;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate;
        long h, min, sec, mls, milli;
        int y;
  
        // Verifica se o evento foi encontrado...
        if(!l_part.isEmpty()){
        
            // Adiciona ao modelo a lista de participantes
            for(int i = 0; i< l_part.size(); i++ ){
                
                // Se tiver tempos válidos
                if(l_time_start.get(i) != null && l_time_finish.get(i) != null){
                
                    //Time de partida
                    parsedDate = dateFormat.parse(l_time_start.get(i));
                    ts_start=new Timestamp(parsedDate.getTime()); 

                    // Time de finish
                    parsedDate = dateFormat.parse(l_time_finish.get(i));
                    ts_finish=new Timestamp(parsedDate.getTime()); 

                    mls = ts_finish.getTime() - ts_start.getTime();
                    l_time_tmp.add(mls);
                    dic.put(mls, i);   
                }
            }
            
            Collections.sort(l_time_tmp);
            
            for(int i = 0; i < l_time_tmp.size(); i++){
                
                mls = l_time_tmp.get(i);               
        
                // Converte o timestamp em hh:mm:ss:msmsms
                h = TimeUnit.MILLISECONDS.toHours(mls);
        
                min = TimeUnit.MILLISECONDS.toMinutes(mls) % TimeUnit.HOURS.toMinutes(1);
 
                sec = TimeUnit.MILLISECONDS.toSeconds(mls) % TimeUnit.MINUTES.toMinutes(1);
                
                y = (int)dic.get(mls);
                l_part_final.add(i, "'" + l_part.get(y) + "'");
                l_time.add(i, "'" + h + ":" + min + ":" + sec + "'");
                
            }
            model.addAttribute("e_info_lpart", l_part_final );
            model.addAttribute("e_info_time", l_time );
            
            // Adiciona ao modelo as suas informações
            
            
        }else{
            model.addAttribute("e_info", "SEM PARTICIPANTES");
        }
        
        return "ranks";
    }
    
    @GetMapping("/getInfoM")
    public String listaParticipantesTimeM(@RequestParam String nomevento,
            Model model) throws ParseException {
        
        List<String> l_part = userDao.getListaNomesByEvento(nomevento);
        List<String> l_time_start = userDao.getTimeStartM(nomevento);
        List<String> l_time_finish = userDao.getTimeFinishM(nomevento);
        
        List<String> l_time = new ArrayList<>();            // lista final de tempos
        List<String> l_part_final = new ArrayList<>();
        List<Long> l_time_tmp = new ArrayList<>();
        
        // Guarda o index de cada Atleta , para saber qual foi o atleta que fez 'x' tempo
        Dictionary dic = new Hashtable();
        Timestamp ts_start, ts_finish;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate;
        long h, min, sec, mls, milli;
        int y;
  
        // Verifica se o evento foi encontrado...
        if(!l_time_start.isEmpty()){
        
            // Adiciona ao modelo a lista de participantes
            for(int i = 0; i< l_time_start.size(); i++ ){
                
                // Se tiver tempos válidos
                if(l_time_start.get(i) != null && l_time_finish.get(i) != null){
                
                    //Time de partida
                    parsedDate = dateFormat.parse(l_time_start.get(i));
                    ts_start=new Timestamp(parsedDate.getTime()); 

                    // Time de finish
                    parsedDate = dateFormat.parse(l_time_finish.get(i));
                    ts_finish=new Timestamp(parsedDate.getTime()); 

                    mls = ts_finish.getTime() - ts_start.getTime();
                    l_time_tmp.add(mls);
                    dic.put(mls, i);   
                }
            }
            
            Collections.sort(l_time_tmp);
            
            for(int i = 0; i < l_time_tmp.size(); i++){
                
                mls = l_time_tmp.get(i);               
        
                // Converte o timestamp em hh:mm:ss:msmsms
                h = TimeUnit.MILLISECONDS.toHours(mls);
        
                min = TimeUnit.MILLISECONDS.toMinutes(mls) % TimeUnit.HOURS.toMinutes(1);
 
                sec = TimeUnit.MILLISECONDS.toSeconds(mls) % TimeUnit.MINUTES.toMinutes(1);
                
                y = (int)dic.get(mls);
                l_part_final.add(i, "'" + l_part.get(y) + "'");
                l_time.add(i, "'" + h + ":" + min + ":" + sec + "'");
                
            }
            model.addAttribute("e_info_lpart", l_part_final );
            model.addAttribute("e_info_time", l_time );
            
            // Adiciona ao modelo as suas informações
            
            
        }else{
            model.addAttribute("e_info", "SEM PARTICIPANTES");
        }
        
        return "ranks";
    }
    
    @GetMapping("/getInfoF")
    public String listaParticipantesTimeF(@RequestParam String nomevento,
            Model model) throws ParseException {
        
        List<String> l_part = userDao.getListaNomesByEvento(nomevento);
        List<String> l_time_start = userDao.getTimeStartF(nomevento);
        List<String> l_time_finish = userDao.getTimeFinishF(nomevento);
        
        List<String> l_time = new ArrayList<>();            // lista final de tempos
        List<String> l_part_final = new ArrayList<>();
        List<Long> l_time_tmp = new ArrayList<>();
        
        // Guarda o index de cada Atleta , para saber qual foi o atleta que fez 'x' tempo
        Dictionary dic = new Hashtable();
        Timestamp ts_start, ts_finish;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate;
        long h, min, sec, mls, milli;
        int y;
  
        // Verifica se o evento foi encontrado...
        if(!l_time_start.isEmpty()){
        
            // Adiciona ao modelo a lista de participantes
            for(int i = 0; i< l_time_start.size(); i++ ){
                
                // Se tiver tempos válidos
                if(l_time_start.get(i) != null && l_time_finish.get(i) != null){
                
                    //Time de partida
                    parsedDate = dateFormat.parse(l_time_start.get(i));
                    ts_start=new Timestamp(parsedDate.getTime()); 

                    // Time de finish
                    parsedDate = dateFormat.parse(l_time_finish.get(i));
                    ts_finish=new Timestamp(parsedDate.getTime()); 

                    mls = ts_finish.getTime() - ts_start.getTime();
                    l_time_tmp.add(mls);
                    dic.put(mls, i);   
                }
            }
            
            Collections.sort(l_time_tmp);
            
            for(int i = 0; i < l_time_tmp.size(); i++){
                
                mls = l_time_tmp.get(i);               
        
                // Converte o timestamp em hh:mm:ss:msmsms
                h = TimeUnit.MILLISECONDS.toHours(mls);
        
                min = TimeUnit.MILLISECONDS.toMinutes(mls) % TimeUnit.HOURS.toMinutes(1);
 
                sec = TimeUnit.MILLISECONDS.toSeconds(mls) % TimeUnit.MINUTES.toMinutes(1);
                
                y = (int)dic.get(mls);
                l_part_final.add(i, "'" + l_part.get(y) + "'");
                l_time.add(i, "'" + h + ":" + min + ":" + sec + "'");
                
            }
            model.addAttribute("e_info_lpart", l_part_final );
            model.addAttribute("e_info_time", l_time );
            
            // Adiciona ao modelo as suas informações
            
            
        }else{
            model.addAttribute("e_info", "SEM PARTICIPANTES");
        }
        
        return "ranks";
    }
    
    

}
