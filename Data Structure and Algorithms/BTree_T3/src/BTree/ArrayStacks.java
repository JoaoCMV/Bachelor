package BTree;

import java.lang.reflect.Array;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joao
 * @param <E>
 */
public class ArrayStacks<E> implements Stacks<E> {   //Tenho de tb por parametro na interface

    private E [] stack;
    public int i = 1;
    private int max;

    
    public ArrayStacks(int tamanho){
        this.stack = (E[]) new Object[tamanho];
        max = tamanho;
    }
    
    
    public ArrayStacks(){
        this(100);
    }
    
    
    @Override
    public void push(E o){
        
        if(i < max) {
            
            stack[i] = o;
             i++;
             
        } else{
            System.out.print("Máximo limite");
            
        }           
    }

    @Override
    public E top(){

        int max = stack.length - 1;
        return stack[max];

    }

    @Override
    public E pop(){

        i--;
        return stack[i];

    }

    @Override
    public int size(){

        return max;

    }

    public boolean empty(){

        return stack.length == 0;

    }
        
}
