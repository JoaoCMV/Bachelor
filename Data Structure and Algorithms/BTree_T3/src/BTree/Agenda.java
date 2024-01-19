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
public class Agenda {
    
    private BTree<Contacto> c;
    
    
    /**
     * Cria uma agenda vazia
     */
    public Agenda(){
        c = new BTree<>();
    }
    
    
    /**
     * 
     * @param s     ID
     * @param num   Numero
     */
    public void inserir(String s, String num){
        if(isNew(num)){
            Contacto cont = new Contacto(s, num);
            c.insere(cont);
            
        }else{
            System.out.println("Numero já introduzido.");
        }
    }
    
    
    /**
     * 
     * @param num numero a procurar
     * @return True se o numero é novo
     */
    private boolean isNew(String num){
        
        java.util.Iterator it = c.iterator();       
        Contacto tester;       
        
        // Enquanto existir próximo na agenda procura se o numero já existe
        while(it.hasNext()){
            
            tester = (Contacto) it.next();
            
            if(tester.numero == num){
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * 
     * @param ID ID a remover
     */
    public void removerwithID(String ID){
        
        java.util.Iterator it = c.iterator();       
        Contacto tester;       
        
        // Enquanto existir próximo na agenda se for com o mesmo ID elimina a entrada
        while(it.hasNext()){
            
            tester = (Contacto) it.next();
            
            if(tester.nome == ID){
                c.remove(tester);
            }
        }
    }
    
    
    public void removerwithNum(String num){
        
        java.util.Iterator it = c.iterator();       
        Contacto tester;       
        
        // Enquanto existir próximo na agenda se for com o mesmo numero elimina o num
        while(it.hasNext()){
            
            tester = (Contacto) it.next();
            
            if(tester.numero == num){
                c.remove(tester);
            }
        }
    }
    
    
    /**
     * 
     * @param IDantigo
     * @param IDnovo 
     * 
     * @return Altera o ID de um contacto com o ID antigo
     */
    public void editIDwithID(String IDantigo,String IDnovo ){
        
        java.util.Iterator it = c.iterator();       
        Contacto tester;       
        
        // Enquanto existir próximo na agenda se for com o mesmo ID muda para o novo
        while(it.hasNext()){
            
            tester = (Contacto) it.next();
            
            if(tester.nome == IDantigo){
                c.remove(tester);
                inserir(IDnovo, tester.numero);
            }
        }
        
    }
    
    /**
     * 
     * @param num
     * @param IDnovo 
     * 
     * @return Altera o ID de um contacto com o seu numero
     */
    public void editIDwithNum(String num,String IDnovo ){
        
        java.util.Iterator it = c.iterator();       
        Contacto tester;       
        
        
        //Enquanto houver próximo na agenda se for com o mesmo numero ele altera
        while(it.hasNext()){
            
            tester = (Contacto) it.next();
            
            if(tester.numero == num){
                c.remove(tester);
                inserir(IDnovo, tester.numero);
            }
        }
        
    }
    

    
    /**
     * 
     * @param num
     * @param numNovo
     * 
     * @return Altera o numero com o numero antigo
     */
    public void editNum(String num,String numNovo ){
        
        java.util.Iterator it = c.iterator();       
        Contacto tester;       
        
        if(isNew(numNovo)){
            while(it.hasNext()){
            
                tester = (Contacto) it.next();
            
                if(tester.numero == num){
                    c.remove(tester);
                    inserir(tester.nome, numNovo);
                }
            }
        }else{
            System.out.println("O numero que pretende substituir já está na lista.");
        }
        
    }
    
    /**
     * @return Retorna a lista por ordem alfabética
     */
    public void lista(){
        c.printLNR();
    }
    
    
    /**
     * 
     * @param n numero a procurar o ID
     * @return devolde o ID do num ou DESCONHECIDO
     */
    public String finder(String n){
        
        java.util.Iterator it = c.iterator();       
        Contacto tester;       
        
        while(it.hasNext()){
            
            tester = (Contacto) it.next();
            
            if(tester.numero == n){
                return tester.nome;
            }
        }
        
        return "DESCONHECIDO";
    }
    
    
    
    
    
    public static void main(String[] args){
        
        Agenda agend = new Agenda();
        
        agend.inserir("Maria", "+351 123 543 123");
        agend.inserir("Maria", "912 314 984");
        agend.inserir("Maria", "917 321 773");
        agend.inserir("Marco", "916 321 123");
        agend.inserir("Moura", "913 232 235");
        agend.inserir("Ana", "911 321 123");
        agend.inserir("Leonel", "916 321 123");     //Tester para numero já introduzido
        agend.inserir("Leonel", "916 331 123"); 
        agend.inserir("Rodrigo", "917 777 312");
        agend.inserir("Leonor", "962 431 978");
        agend.inserir("Rute", "964 324 546");
        
        
        //Insere
        agend.inserir("Ana", "917 767 322" );
       
        //Remover com ID
        agend.removerwithID("Leonor");
        
        //Remover com Num
        agend.removerwithNum("964 324 546");
        
        // Edita com o ID antigo muda para o novo
        agend.editIDwithID("Moura", "Joao");
        
        //Edita com o num muda o ID
        agend.editIDwithNum("916 321 123", "Afonso");
        
        //Edita com o numero antigo muda para um novo
        agend.editNum("917 777 312", "916 759 326");
        agend.editNum("916 759 326", "917 767 322");
        
        //Lista a agenda por ordem alfabética
        agend.lista();
        
        //Procura o ID do num
        System.out.println( agend.finder("123") );
        System.out.println( agend.finder("916 759 326") );
        
    }

}
