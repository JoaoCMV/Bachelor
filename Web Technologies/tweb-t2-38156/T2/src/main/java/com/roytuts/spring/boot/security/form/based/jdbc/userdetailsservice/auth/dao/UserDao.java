package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.User;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Evento;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper.UserRowMapper;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUser(final String username) {
        return jdbcTemplate.queryForObject(
                "select u.user_name user_name, u.user_pass user_pass, ur.user_role user_role from my_user u, my_user_role ur where u.user_name = ? and u.user_name = ur.user_name",
                new String[]{username}, new UserRowMapper());
    }

    public void saveUser(final User u) {
        String sql = "INSERT INTO my_user VALUES ('"
                + u.getUsername() + "','"
                + u.getPassword() + "',0)";   // 0 == not enabled
        jdbcTemplate.execute(sql);
        jdbcTemplate.execute("INSERT INTO my_user_role VALUES ('" + u.getUsername() + "','" + u.getRole() + "')");
        System.out.println("UserDao - saved\n" + sql + "\n");
    }
    
    public void saveEvento(final Evento e) {
        String sql = "INSERT INTO my_evento VALUES ('"
                + e.getEventoName()+ "','"
                + e.getValor()+ "','"
                + e.getEventoData()+ "','"
                + e.getEventoDesc() + "')"; 
        jdbcTemplate.execute(sql);
        System.out.println("EventoDao - saved\n" + sql + "\n");
    }
    
    public void saveInscricao(final String nome, final String evento, final String genero, final String escalao, final String user_name, final String referencia) {
        
        List<String> dorsal = jdbcTemplate.queryForList("SELECT COUNT(evento_name) " +
                                                    "FROM my_inscricoes " +
                                                    "WHERE evento_name = '" + evento + "'", String.class);
        int d = Integer.valueOf(dorsal.get(0));
        String sql = "INSERT INTO my_inscricoes VALUES ('"
                + nome + "','"
                + escalao + "','"
                + genero + "','"
                + evento + "','"
                + referencia + "',"
                + "'nao paga','"   
                + d + "','"
                + user_name + "')";         
        jdbcTemplate.execute(sql);
        String sql2 = "INSERT INTO my_times(dorsal,evento_name) VALUES ('"
                + d + "','"  
                + evento + "')"; 
        jdbcTemplate.execute(sql2);
        
        System.out.println("Inscricao - saved\n" + sql + "\n");
    }
    

    public List<String> getUsernameList() {
        return jdbcTemplate.queryForList("select user_name FROM my_user", String.class);
    }
    
    public List<String> geteventonameList() {
        return jdbcTemplate.queryForList("select evento_name FROM my_evento", String.class);
    }
    
    
    // Compara com a CURRENT_DATE para saber a que atributo do model(eventos...hoje, passados, futuros) pertence os eventos
    public List<String> geteventonameListbyToday() {
        return jdbcTemplate.queryForList("select evento_name FROM my_evento WHERE evento_data = CURRENT_DATE", String.class);
    }
    
    public List<String> geteventonameListbyAfterToday() {
        return jdbcTemplate.queryForList("select evento_name FROM my_evento WHERE evento_data > CURRENT_DATE", String.class);
    }
    
    public List<String> geteventonameListbyBeforeToday() {
        return jdbcTemplate.queryForList("select evento_name FROM my_evento WHERE evento_data < CURRENT_DATE", String.class);
    }
    
    public List<String> getEventoDate(String n){
        return jdbcTemplate.queryForList("select evento_data FROM my_evento WHERE evento_name = '" + n +"'", String.class);
    }

    public List<String> getEventoDesc(String n) {
        return jdbcTemplate.queryForList("select evento_desc FROM my_evento WHERE evento_name = '" + n +"'", String.class);
    }
    
    public List<String> getEventoDescByDate(String d){
        return jdbcTemplate.queryForList("select evento_desc FROM my_evento WHERE evento_data = '" + d +"'", String.class);
    }

    public List<String> getEventoNome(String d) {
        System.out.println("select evento_name FROM my_evento WHERE evento_data = '" + d +"'");
        return jdbcTemplate.queryForList("select evento_name FROM my_evento WHERE evento_data = '" + d +"'", String.class);
    }

    public List<String> getListaNomesByEvento(String nomevento) {
        return jdbcTemplate.queryForList("select inscrito_name FROM my_inscricoes WHERE evento_name = '" + nomevento +"'", String.class);
    }
    
    public List<String> getListaNomesByGenero(String nomevento, String genero) {
        return jdbcTemplate.queryForList("select inscrito_name" +
                                        " FROM my_inscricoes" +
                                        " WHERE evento_name = '" + nomevento + "' "+
                                        " AND inscrito_genero = '" + genero + "' " +
                                        "ORDER BY dorsal", String.class);
    }
    
    public List<String> getTimeStart(String nomevento){
        return jdbcTemplate.queryForList("select time_start FROM my_times WHERE evento_name = '" + nomevento + "' ORDER BY dorsal", String.class);
    }
    
    public List<String> getTimeFinish(String nomevento){
        return jdbcTemplate.queryForList("select finish FROM my_times WHERE evento_name = '" + nomevento + "' ORDER BY dorsal", String.class);
    }
    
    public List<String> getTimeStartByGenero(String nomevento, String genero){
        return jdbcTemplate.queryForList("select T.time_start" +
                                        " FROM my_times T " +
                                        "INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) " +
                                        "WHERE T.evento_name = '" + nomevento + "' "+
                                        " AND I.inscrito_genero='" + genero + "' " +
                                        "ORDER BY T.dorsal", String.class);}
    
    public List<String> getTimeFinishByGenero(String nomevento, String genero){
        return jdbcTemplate.queryForList("select T.finish" +
                                        " FROM my_times T " +
                                        "INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) " +
                                        "WHERE T.evento_name = '" + nomevento + "' "+
                                        " AND I.inscrito_genero = '" + genero + "'" +
                                        " ORDER BY T.dorsal", String.class);
    }

    public List<String> getEscalaoByEvento(String nomevento) {
        return jdbcTemplate.queryForList("select inscrito_escalao" +
                                        " FROM my_inscricoes " +
                                        "WHERE evento_name = '" + nomevento + "' "+
                                        "ORDER BY dorsal", String.class);
    }
    
    public List<String> getEscalaoByGenero(String nomevento, String genero) {
        return jdbcTemplate.queryForList("select inscrito_escalao" +
                                        " FROM my_inscricoes " +
                                        "WHERE evento_name = '" + nomevento + "' "+
                                        " AND inscrito_genero='" + genero + "' " +
                                        "ORDER BY dorsal", String.class);
    }
    
    public void saveTimeStart(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET time_start= '" + d + "' WHERE dorsal = " + dorsal + " AND evento_name = '" + nomevento + "'";
        jdbcTemplate.execute(sql);
    }
    
    public void saveTimeP1(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET p1= '" + d + "' WHERE dorsal = " + dorsal + " AND evento_name = '" + nomevento + "'";
        jdbcTemplate.execute(sql);
    }
    public void saveTimeP2(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET p2= '" + d + "' WHERE dorsal = " + dorsal + " AND evento_name = '" + nomevento + "'";
        jdbcTemplate.execute(sql);
    }
    public void saveTimeP3(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET p3= '" + d + "' WHERE dorsal = " + dorsal + " AND evento_name = '" + nomevento + "'";
        jdbcTemplate.execute(sql);
    }
    public void saveTimeFinish(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET finish= '" + d + "' WHERE dorsal = " + dorsal + " AND evento_name = '" + nomevento + "'";
        jdbcTemplate.execute(sql);
    }
    
    public List<String> getEventoInscricoesByUser(String user_name){
        return jdbcTemplate.queryForList("select evento_name FROM my_inscricoes WHERE user_name = '" + user_name + "'", String.class);
    }
    
    public List<String> getEstadoInscricoesByUser(String user_name){
        return jdbcTemplate.queryForList("select estado_pagamento FROM my_inscricoes WHERE user_name = '" + user_name + "'", String.class);
    }
    
    public List<String> getParticipanteInscricoesByUser(String user_name){
        return jdbcTemplate.queryForList("select inscrito_name FROM my_inscricoes WHERE user_name = '" + user_name + "'", String.class);
    }
    
    public List<String> getParticipanteReferencias(String user_name) {
        return jdbcTemplate.queryForList("select referencia FROM my_inscricoes WHERE user_name = '" + user_name + "'", String.class);
    }

    public List<String> getEventoinscritosNome(String nomevento) {
        return jdbcTemplate.queryForList("select inscrito_name FROM my_inscricoes WHERE evento_name = '" + nomevento + "' ORDER BY dorsal", String.class);
    }

    public List<String> getEventoinscritosGenero(String nomevento) {
        return jdbcTemplate.queryForList("select inscrito_genero FROM my_inscricoes WHERE evento_name = '" + nomevento + "' ORDER BY dorsal", String.class);
    }

    public List<String> getEventoinscritosEscalao(String nomevento) {
        return jdbcTemplate.queryForList("select inscrito_escalao FROM my_inscricoes WHERE evento_name = '" + nomevento + "' ORDER BY dorsal", String.class);
    }

    public List<String> getEventoinscritosEstado(String nomevento) {
       return jdbcTemplate.queryForList("select estado_pagamento FROM my_inscricoes WHERE evento_name = '" + nomevento + "' ORDER BY dorsal", String.class);
    }

    public List<String> getFinishTime(String nomevento, String inscrito) {
        return jdbcTemplate.queryForList("select T.finish" +
                                        " FROM my_times T " +
                                        " INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) "  +
                                        " WHERE T.evento_name = '" + nomevento + "'" +
                                        " AND I.inscrito_name = '" + inscrito + "'", String.class);
    }

    public List<String> getStartTime(String nomevento, String inscrito) {
       return jdbcTemplate.queryForList("select T.time_start" +
                                        " FROM my_times T " +
                                        " INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) "  +
                                        " WHERE T.evento_name = '" + nomevento + "'" +
                                        " AND I.inscrito_name = '" + inscrito + "'", String.class);
    }

    public List<String> getP1Time(String nomevento, String inscrito) {
        return jdbcTemplate.queryForList("select T.p1" +
                                        " FROM my_times T " +
                                        " INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) "  +
                                        " WHERE T.evento_name = '" + nomevento + "'" +
                                        " AND I.inscrito_name = '" + inscrito + "'", String.class);
    }

    public List<String> getP2Time(String nomevento, String inscrito) {
        return jdbcTemplate.queryForList("select T.p2" +
                                        " FROM my_times T " +
                                        " INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) "  +
                                        " WHERE T.evento_name = '" + nomevento + "'" +
                                        " AND I.inscrito_name = '" + inscrito + "'", String.class);
    }

    public List<String> getP3Time(String nomevento, String inscrito) {
        return jdbcTemplate.queryForList("select T.p3" +
                                        " FROM my_times T " +
                                        " INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) "  +
                                        " WHERE T.evento_name = '" + nomevento + "'" +
                                        " AND I.inscrito_name = '" + inscrito + "'", String.class);
    }

    public List<String> getEventoCusto(String nomevento) {
        return jdbcTemplate.queryForList("select evento_valor FROM my_evento WHERE evento_name = '" + nomevento + "'", String.class);
    }

    public void updatePagamento(String nome, String nomevento) {
        String sql = "UPDATE my_inscricoes SET estado_pagamento='paga' WHERE inscrito_name ='" + nome + "' AND evento_name ='" + nomevento + "'";
        jdbcTemplate.execute(sql);
    }

}
