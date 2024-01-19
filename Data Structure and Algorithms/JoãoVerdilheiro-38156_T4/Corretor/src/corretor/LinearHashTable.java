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
public class LinearHashTable extends HashTable{
      
    
    /**
     * 
     * @param key 
     */ 
        @Override
    public void add(String key){
         
        int h = hash(key); 
               
        while(tabela[h] != null && h < tableSize - 1){
            h ++;
        }
        
        tabela[h] = key;
  
    }
    
    
    /**
     * 
     * @param key 
     * @return True ou false caso exista ou não essa key
     */
        @Override
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
    
    
    
        @Override
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
}
 
    

