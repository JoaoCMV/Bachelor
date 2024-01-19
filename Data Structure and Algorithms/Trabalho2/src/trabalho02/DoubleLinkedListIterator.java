
package trabalho02;

/**
 *
 * @author Joao
 * @param <E>
 */
public class DoubleLinkedListIterator<E> implements java.util.Iterator<E> {
    
        
    //node em que está a lista
    private Node<E> currentnext;
    private Node<E> currentprev;
    
    
    /**
     * 
     * @param c elemento em que está na lista
     */
    public DoubleLinkedListIterator(Node<E> cn, Node<E> cp){
        
        currentnext = cn;
        currentprev = cp;
    }
    
    /**
     * 
     * @return false se não existir elemento
     */
    public boolean hasNext(){
        return currentnext.getNext() != null;
    }
    
    public boolean hasPrev(){
        return currentprev.getPrevious() != null;
    }
    
    /**
     * 
     * @return Valor do elemento 
     */
    
    //Current passa a ser o seguinte elemento e retorna o atual
    public E next(){
        if(!hasNext()){
            throw new java.util.NoSuchElementException();
        }
        E nextItem = currentnext.element();
        currentnext = currentnext.getNext();
        return nextItem;
    }
    
    public E prev(){
        if(!hasPrev()){                                     //ALTERAR
            throw new java.util.NoSuchElementException();
        }
        E previousItem = currentprev.element();
        currentprev = currentprev.getPrevious();
        return previousItem;
    }
   
    
    
    /**
     * Remove o elemento da lista
     */
    public void remove(){
        throw new UnsupportedOperationException();
    }
}

