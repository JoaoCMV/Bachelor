
package com.t2.sd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Evento{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)         //IDENTITY para que apenas seja incrementado o ID desta tabela e nao de todas
  private Integer idE;

  private String nome;

  private Date data;
  
  public Evento(String nome,Date data){
      this.nome = nome;
      this.data = data;
  }
  
  public Evento(){
      
  }
  
  public void setId(int id){
      this.idE = id;
  }
  
  public int getId(){
      return idE;
  }
  
  public void setNome(String nome){
      this.nome = nome;
  }
  
  public String getNome(){
      return nome;
  }
  
  public void setData(Date data){
      this.data = data;
  }
  
  public Date getDate(){
      return data;
  }
  
  @Override
  public String toString(){
      return this.nome + " em, " + this.data;
  }
  
  
}
