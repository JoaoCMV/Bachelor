package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;

import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String eventoName;
    private ArrayList<Inscricao> eventoParticipantes;
    private Date eventoData;
    private float eventoValor;
    private String eventoDesc;
    

    public Evento() {
    }
    
    public Evento(String n, Date d, float p, String desc) {
        this.eventoName = n;
        this.eventoData = d;
        this.eventoDesc = desc;
        this.eventoValor = p;
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

    public float getEventoValor() {
        return eventoValor;
    }

    @Override
    public String toString() {
        return "Evento:[" + eventoName + ", " + eventoData + ", " + eventoDesc + ", " + eventoValor + " € ]";
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

    public void setEventoValor(float eventoValor) {
        this.eventoValor = eventoValor;
    }

}

