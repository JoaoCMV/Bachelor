/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corretor;

import static corretor.HashTable.hash;
import static corretor.HashTable.tableSize;

/**
 *
 * @author Joao
 */
public class QuadHashTable extends HashTable {
    
    
    /**
     * 
     * @param key 
     */ 
    @Override
    public void add(String key){
         
        int h = hash(key); 
        int i = 1;
               
        while(h < tableSize - 1){
            
            if(tabela[h] != null){
                
                if(h + Math.pow(i, 2) < tableSize){
                    h += Math.pow(i, 2);
                    i++;
                }else{
                    h = tableSize;
                }
                
                
            }else{
                tabela[h] = key;
                h = tableSize;
            }
        }    
    }
    
    
    /**
     * 
     * @param key 
     * @return True ou false caso exista ou não essa key
     */
        @Override
    public boolean pesquisa(String key){
        
        int i_key = hash(key);
        int i = 1;
        
        
        while( i_key < tabela.length){
            
            
            if(tabela[i_key] == null){
                return false;
                
            }else{
                if(tabela[i_key].equals(key) && tabela[i_key] != null){
                    return true;

                }else{
                    i_key += Math.pow(i, 2);
                }
            }
        }
        
        return false;
    }
    
    
    
        @Override
    public void remove(String key){
        
        int i_key = hash(key);
        int i = 1;
        
        if(pesquisa(key)){
            
            while( i_key < tabela.length){
                
                if(tabela[i_key].equals(key) && tabela[i_key] != null){
                    tabela[i_key] = null;
                    break;

                }else{
                    i_key += Math.pow(i, 2);
                }
            }
        }
        
    }
}
