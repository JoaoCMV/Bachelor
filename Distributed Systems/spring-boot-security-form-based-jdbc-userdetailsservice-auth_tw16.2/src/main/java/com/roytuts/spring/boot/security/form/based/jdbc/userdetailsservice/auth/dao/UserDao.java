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
                "select u.user_name user_name, u.user_pass user_pass, user_genero, ur.user_role user_role from my_user u, my_user_role ur where u.user_name = ? and u.user_name = ur.user_name",
                new String[]{username}, new UserRowMapper());
    }

    public void saveUser(final User u) {
        String sql = "INSERT INTO my_user VALUES ('"
                + u.getUsername() + "','"
                + u.getPassword() + "','"
                + u.getGenero() + "',0)";   // 0 == not enabled
        jdbcTemplate.execute(sql);
        jdbcTemplate.execute("INSERT INTO my_user_role VALUES ('" + u.getUsername() + "','" + u.getRole() + "')");
        System.out.println("UserDao - saved\n" + sql + "\n");
    }
    
    public void saveEvento(final Evento e) {
        String sql = "INSERT INTO my_evento VALUES ('"
                + e.getEventoName()+ "','"
                + e.getEventoData()+ "','"
                + e.getEventoValor()+ "','"
                + e.getEventoDesc() + "')"; 
        jdbcTemplate.execute(sql);
        System.out.println("EventoDao - saved\n" + sql + "\n");
    }
    
    public void saveInscricao(final String user, final String evento) {
        String sql = "INSERT INTO my_inscricoes VALUES ('"
                + user + "','"
                + evento + "',"
                + "'n','"   
                + new Timestamp(0) +"',"
                + "'0')"; 
        jdbcTemplate.execute(sql);
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
    
    public List<String> getEventoDesc(Date d){
        return jdbcTemplate.queryForList("select evento_desc FROM my_evento WHERE evento_data = '" + d +"'", String.class);
    }

    public List<String> getEventoNome(Date d) {
        return jdbcTemplate.queryForList("select evento_name FROM my_evento WHERE evento_data = '" + d +"'", String.class);
    }

    public List<String> getListaNomesByEvento(String nomevento) {
        return jdbcTemplate.queryForList("select user_name FROM my_inscricoes WHERE evento_name = '" + nomevento +"'", String.class);
    }
    
    public List<String> getTimeStart(String nomevento){
        return jdbcTemplate.queryForList("select time_start FROM my_times WHERE evento_name = '" + nomevento + "' ORDER BY dorsal", String.class);
    }
    
    public List<String> getTimeFinish(String nomevento){
        return jdbcTemplate.queryForList("select finish FROM my_times WHERE evento_name = '" + nomevento + "' ORDER BY dorsal", String.class);
    }
    
    public List<String> getTimeStartM(String nomevento){
        return jdbcTemplate.queryForList("select T.time_start" +
                                        " FROM my_times T " +
                                        "INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) " +
                                        "INNER JOIN my_user U ON I.user_name=U.user_name " +
                                        "WHERE T.evento_name = '" + nomevento + "' "+
                                        " AND U.user_genero='m' " +
                                        "ORDER BY T.dorsal", String.class);}
    
    public List<String> getTimeFinishM(String nomevento){
        return jdbcTemplate.queryForList("select T.finish" +
                                        " FROM my_times T " +
                                        "INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) " +
                                        "INNER JOIN my_user U ON I.user_name=U.user_name " +
                                        "WHERE T.evento_name = '" + nomevento + "' "+
                                        " AND U.user_genero='m' " +
                                        "ORDER BY T.dorsal", String.class);
    }
    
    public List<String> getTimeStartF(String nomevento){
        return jdbcTemplate.queryForList("select T.time_start" +
                                        " FROM my_times T " +
                                        "INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) " +
                                        "INNER JOIN my_user U ON I.user_name=U.user_name " +
                                        "WHERE T.evento_name = '" + nomevento + "' "+
                                        " AND U.user_genero='f' " +
                                        "ORDER BY T.dorsal", String.class);
    }
    
    public List<String> getTimeFinishF(String nomevento){
        return jdbcTemplate.queryForList("select T.finish" +
                                        " FROM my_times T " +
                                        "INNER JOIN my_inscricoes I ON (T.evento_name=I.evento_name AND T.dorsal=I.dorsal) " +
                                        "INNER JOIN my_user U ON I.user_name=U.user_name " +
                                        "WHERE T.evento_name = '" + nomevento + "' "+
                                        " AND U.user_genero='f' " +
                                        "ORDER BY T.dorsal", String.class);
    }

    public void saveTimeStart(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET p1= '" + d + "' WHERE dorsal = '" + dorsal + "' AND nome_evento = '" + nomevento + "')";
        jdbcTemplate.execute(sql);
    }
    
    public void saveTimeP1(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET p1= '" + d + "' WHERE dorsal = '" + dorsal + "' AND nome_evento = '" + nomevento + "')";
        jdbcTemplate.execute(sql);
    }
    public void saveTimeP2(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET p1= '" + d + "' WHERE dorsal = '" + dorsal + "' AND nome_evento = '" + nomevento + "')";
        jdbcTemplate.execute(sql);
    }
    public void saveTimeP3(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET p1= '" + d + "' WHERE dorsal = '" + dorsal + "' AND nome_evento = '" + nomevento + "')";
        jdbcTemplate.execute(sql);
    }
    public void saveTimeFinish(String nomevento, String d, String dorsal) {
        String sql = "UPDATE my_times SET p1= '" + d + "' WHERE dorsal = '" + dorsal + "' AND nome_evento = '" + nomevento + "')";
        jdbcTemplate.execute(sql);
    }
}
