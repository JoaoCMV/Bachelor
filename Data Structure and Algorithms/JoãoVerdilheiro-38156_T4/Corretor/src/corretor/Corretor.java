/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corretor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Joao
 */
public class Corretor {

    HashTable l;
    
    public Corretor(char c){
        
        if( c == 'L'){
            l = new LinearHashTable();
        } else if( c == 'Q'){
            l = new QuadHashTable();
        }else{
            System.out.println("Chave Errada");
        }
    }
    
   
    
    
    public void eval(String text, int m){
        
        String[] s = text.split(" ");
        String[] res;
        String restxt;
        
        for(int i = 0; i < s.length; i++){  
            
            restxt = "";
            
            if(!l.pesquisa(s[i])){
                
                String sug = s_Add(s[i]);
                sug += s_Switch(s[i]);
                sug += s_Remove(s[i]);
                
                res = sug.split(";");

                for( int c = 0; c < res.length - 1 ; c++){
                    restxt += res[c] + ", ";
                }
                restxt += res[res.length - 1];    
                
                if(!restxt.equals("")){              
                    System.out.println("Erro na palavra: " + s[i] +  ", linha " + m + " ,sugestões: " + restxt);
                } else{
                    System.out.println("Erro na palavra: " + s[i] +  ", linha " + m + " ,não existe sugestões no corretor");
                }
            }
        }
    }
    
    /**
     * 
     * @param key
     * @return String separa por ";" das sugestões
     */
    String s_Remove(String key){
        
        StringBuilder n_key;
        String res = "";
        
        for( int i = 0; i < key.length() ; i++){   
            
            n_key = new StringBuilder(key);
            n_key.deleteCharAt(i);
            
            if(l.pesquisa( n_key.toString() )){
                res += n_key.toString() + ";";
            }
        }
    
    return res;
    }
    
    
    /**
     * 
     * @param key
     * @return String separa por ";" das sugestões
     */
    String s_Add(String key){
        
        StringBuilder n_key;
        String res = "";
        int c;
        
        for( int i = 0; i < key.length() ; i++){   
            
            c = 65;
            
            while (c < 166){
                
                n_key = new StringBuilder(key);
                n_key.insert(i, (char)c);
                

                if(l.pesquisa( n_key.toString() )){
                    res += n_key.toString() + ";";
                }
                
                c++;
                
                if(c == 91){
                    c = 97;
                }
                
                if(c == 123){
                    c = 127;
                }

            }
        }

        
        c = 65;
            
        while (c < 123){
                
            n_key = new StringBuilder(key);
            n_key.append((char)c);
                

            if(l.pesquisa( n_key.toString() )){
                res += n_key.toString() + ";";
            }
                
            c++;
                
            if(c == 91){
                c = 97;
            }
        }
    
    return res;
    }
    
    
    /**
     * 
     * @param key
     * @return String separa por ";" das sugestões
     */
    String s_Switch(String key){
        
        StringBuilder n_key;
        String res = "";
        char c;
        
        for( int i = 0; i < key.length() - 1 ; i++){   
            
            n_key = new StringBuilder(key);
            
            c = key.charAt(i);
            n_key.deleteCharAt(i);
            n_key.insert(i + 1, c);
            
            
            if(l.pesquisa( n_key.toString() )){
                res += n_key.toString() + ";";
            }
        }            
    
    return res;
    }    
    
    
    public void getDictionary(String FileToBeRead){         
        try {
            File myObj = new File(FileToBeRead);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for(String s : data.split(" ")){
                    s = s.toLowerCase();
                    l.add(s);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        } catch (Exception ex){
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }       
    }
    
    public void corrigir(String FileToBeRead){
        
        int l = 1;
        try {
            File myObj = new File(FileToBeRead);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = data.toLowerCase();
                eval(data, l);
                l++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        } catch (Exception ex){
            System.out.println("An error occurred.");
            ex.printStackTrace();
        } 
    }
    
    public static void main (String args[]){
                       
        // Q ->  QuadHash
        // L ->  LinearHash
        Corretor c = new Corretor('Q');
        
        // Com scanner
        Scanner s = new Scanner(System.in);

        System.out.println("Insira o diretório do dicionário: ");
        String d = s.nextLine(); 
        //C:\Users\Joao\Desktop\Escola20_21\EDA\Trabalho04\wordlist-ao-2020.txt
        c.getDictionary(d);
        
        
        System.out.println("Insira o diretório do ficheiro a corrigir: ");
        String t = s.nextLine();       
        //C:\Users\Joao\Desktop\Escola20_21\EDA\Trabalho04\texto.txt               
        s.close();
               
        c.corrigir(t); 
        
        // Diretorio direto
        
        /*
        c.getDictionary("C:\\Users\\Joao\\Desktop\\Escola20_21\\EDA\\Trabalho04\\wordlist-ao-2020.txt");
        
        c.corrigir("C:\\Users\\Joao\\Desktop\\Escola20_21\\EDA\\Trabalho04\\texto.txt");
        */
        
    }
    
}

