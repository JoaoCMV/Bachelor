/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corretor;

/**
 *
 * @author Joao
 */
public class HashTable {
    
    protected String[] tabela;
    protected static int tableSize;
    
    
    /**
     * 
     * @param n espaços da tabela gerada
     */
    public HashTable(int n){
        tabela = new String[n];
        tableSize = n;
    }
    
    /**
     * Cria uma tabela default com 997 espaços
     */
    public HashTable(){
        this(998651);
    }
    
    
    /**
     * 
     * @param key
     * @return valor do sum do peso de cada char da key
     */
     public static int hash(String key){
        
        int hashVal = 0;
        
        for ( int i = 0; i < key.length(); i++){
            hashVal += 31*hashVal + key.charAt(i);
        }
        
        hashVal %= tableSize;
        if( hashVal < 0){
            hashVal += tableSize;
        }
        
        return hashVal % tableSize;
    }
    
     
    /**
     * 
     * @param key 
     */ 
    public void add(String key){
         
        int h = hash(key);                     
        tabela[h] = key;
    }
    
    
    /**
     * 
     * @param key 
     * @return True ou false caso exista ou não essa key
     */
    public boolean pesquisa(String key){
        
        int i_key = hash(key);
        
        
        while( i_key < tabela.length){
            
            
            if(tabela[i_key] == null){
                return false;
                
            }else{
                if(tabela[i_key].equals(key) && tabela[i_key] != null){
                    return true;

                }else{
                    i_key ++;
                }
            }
        }
        
        return false;
    }    
    
    
    public void remove(String key){
        
        int i_key = hash(key);
        
        if(pesquisa(key)){
            
            while( i_key < tabela.length){
                
                if(tabela[i_key].equals(key) && tabela[i_key] != null){
                    tabela[i_key] = null;
                    break;

                }else{
                    i_key ++;
                }
            }
        }
        
    }
    
    
    public String toString(){
        
        String res = "";
        
        for(int i = 0; i < tabela.length; i++){
            
            if(tabela[i] != null){
                res += tabela[i];
            }      
        }     
        return res;
    }
}
