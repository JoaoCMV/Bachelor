package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;

import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String eventoName;
    private float valor;
    private ArrayList<Inscricao> eventoParticipantes;
    private Date eventoData;
    private String eventoDesc;
    

    public Evento() {
    }
    
    public Evento(String n, float valor, Date d, String desc) {
        this.eventoName = n;
        this.valor = valor;
        this.eventoData = d;
        this.eventoDesc = desc;
        this.eventoParticipantes = new ArrayList<>();
    }

    public ArrayList<Inscricao> getEventoParticipantes() {
        return eventoParticipantes;
    }

    public Date getEventoData() {
        return eventoData;
    }

    public String getEventoDesc() {
        return eventoDesc;
    }

    @Override
    public String toString() {
        return "Evento:[" + eventoName + ", " + valor + ", " + eventoData + ", " + eventoDesc + " ]";
    }

    public String getEventoName() {
        return eventoName;
    }

    public void setEventoName(String eventoName) {
        this.eventoName = eventoName;
    }

    public void setEventoParticipantes(ArrayList<Inscricao> eventoParticipantes) {
        this.eventoParticipantes = eventoParticipantes;
    }

    public void setEventoData(Date eventoData) {
        this.eventoData = eventoData;
    }

    public void setEventoDesc(String eventoDesc) {
        this.eventoDesc = eventoDesc;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
    

}

