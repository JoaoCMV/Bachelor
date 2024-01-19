/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BTree;

/**
 *
 * @author Joao
 */
public class Contacto implements Comparable<Contacto>{
    
    String nome;
    String numero;
    
    public Contacto(String n, String num){
        this.nome = n;
        this.numero = num;
    }
    

    public int compareTo(Contacto c){
        
        
        if(this.nome != c.nome){
            return this.nome.compareTo(c.nome);
            
        }else if(this.nome == c.nome && this.numero == c.numero){
            return 0;
            
        }else if(this.nome == c.nome && this.numero != c.numero){
            return -1;
        }
       
        return -1;
    }
    
    public int compareTo(String num){
        
        if(this.numero == num){
            return 0;
        }else{
            return -1;
        }
    }
    
    public String toString(){
        return this.nome + " - " + this.numero;
    }
    
}
