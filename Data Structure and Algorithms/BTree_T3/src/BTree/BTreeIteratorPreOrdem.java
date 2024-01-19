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
public class BTreeIteratorPreOrdem<T extends Comparable<? super T>> implements java.util.Iterator<T> {
    
    BNode<T> atual;
    ArrayStacks<BNode<T>> choice_points = new ArrayStacks <BNode<T>>();

    
    public BTreeIteratorPreOrdem(BNode<T> n){
        atual = n;
    }
    
    
    @Override
    public boolean hasNext() {
        return atual !=null;
    }


    @Override
    public T next() {
        if( !hasNext() ){
            throw new java.util.NoSuchElementException();
        }
        
        T to_return = atual.element;
        
        //Salvaguardar a direita
        if (atual.rightNode != null){
            choice_points.push(atual.rightNode);
        }
        
        //Atualizar a esquerda
        if( atual.leftNode != null){
            atual = atual.leftNode;
            
        }else{
            if(!choice_points.empty()){
                atual = choice_points.pop();
            }else{
                atual = null;   //its over
            }
        }
        
        return to_return;
    }
    
}
