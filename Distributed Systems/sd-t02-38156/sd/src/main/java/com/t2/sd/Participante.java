
package com.t2.sd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Participante {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY) //IDENTITY para que apenas seja incrementado o ID desta tabela e nao de todas
  private Integer chipId;                           // Irá guardar o chipID sendo que este é unico, associando o chip a este participante, neste evento (idE)

  private String name;

  private char genero;
  
  private String escalao;
  
  private int idE;
  
  private int dorsal;
  
  
  public Participante(String n, char g, String escalao, int idE, int dorsal){
      
      this.name = n;
      this.genero = g;
      this.escalao = escalao;
      this.idE = idE;
      this.dorsal = dorsal;
  }
  
  public Participante(){
      
  }

  public Integer getchipId() {
    return chipId;
  }

  public void setchipId(Integer id) {
    this.chipId = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public char getGenero() {
    return genero;
  }

  public void setGenero(char g) {
    this.genero = g;
  }
  
  public String getEscalao(){
      return escalao;
  }
  
  public void setEscalao(String e){
      this.escalao = e;
  }
  
  public void setIdE(int id){
        this.idE = id;
    }
    
    public int getIdE(){
        return idE;
    }
    
    public void setDorsal(int d){
        this.dorsal = d;
    }
    
    public int getDorsal(){
        return dorsal;
    }
  
}
