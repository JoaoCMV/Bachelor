
package com.t2.sd;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Chip{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)         //IDENTITY para que apenas seja incrementado o ID desta tabela e nao de todas
  private Integer idC;
  
  private Timestamp start;
  
  private Timestamp p1;
  
  private Timestamp p2;
  
  private Timestamp p3;
  
  private Timestamp finish;
  
  private int chipId2;                                       // Obtido da tabela Participante
  
  public Chip(int iD){
    this.chipId2 = iD;
    this.start = new Timestamp(0);
    this.p1 = new Timestamp(0);
    this.p2 = new Timestamp(0);
    this.p3 = new Timestamp(0);
    this.finish = new Timestamp(0);
}
  
  public Chip(){
      
  }
  
  public Timestamp getStart(){
      return start;
  }
  
  public void setStart(Timestamp t){
      this.start = t;
  }
  
  public Timestamp getP1(){
      return p1;
  }
  
  public void setP1(Timestamp t){
      this.p1 = t;
  }
  
  public Timestamp getP2(){
      return p2;
  }
  
  public void setP2(Timestamp t){
      this.p2 = t;
  }
  
  public Timestamp getP3(){
      return p3;
  }
  
  public void setP3(Timestamp t){
      this.p3 = t;
  }
  
  public Timestamp getFinish(){
      return finish;
  }
  
  public void setFinish(Timestamp t){
      this.finish = t;
  }
  
  public int getChipId2(){
      return chipId2;
  }
  
  public void setChipId2(int id){
      this.chipId2 = id;
  }
  
  public int getId(){
      return idC;
  }
  
  public void setId(int id){
      this.idC = id;
  }
  
}
