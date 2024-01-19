/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BTree;

/**
 *
 * @author Joao
 * @param <E>
 */
public interface Stacks<E> {
    
    void push(E o);
    E top ();
    E pop();
    int size();
    boolean empty();
    
}
