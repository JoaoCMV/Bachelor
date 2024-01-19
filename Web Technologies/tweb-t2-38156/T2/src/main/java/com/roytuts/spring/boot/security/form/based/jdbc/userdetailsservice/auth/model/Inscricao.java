
package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;

import java.sql.Timestamp;

public class Inscricao {
    
    private String nomeE;
    private String nomeU;
    private char pagamento;
    private Timestamp time;
    private int dorsal;
    
    public Inscricao(){
        
    }
    
    public Inscricao(String nomeE, String nomeU, char p, Timestamp t, int d){
        this.nomeE = nomeE;
        this.nomeU = nomeU;
        this.pagamento = p;
        this.time = t;
        this.dorsal = d;
    }

    public String getNomeE() {
        return nomeE;
    }

    public String getNomeU() {
        return nomeU;
    }

    public char getPagamento() {
        return pagamento;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setNomeE(String nomeE) {
        this.nomeE = nomeE;
    }

    public void setNomeU(String nomeU) {
        this.nomeU = nomeU;
    }

    public void setPagamento(char pagamento) {
        this.pagamento = pagamento;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
    
    
    
}
