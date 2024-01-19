
package trabalho02;

/**
 *
 * @author Joao
 */
public class Node<T> {
    
        
    // Variaveis
    T elemento;
    Node<T> next;
    Node<T> previous;
    
    //Adiciona um só elemento á lista
    //construtor
    /**
     * 
     * @param x Elemento do nodo
     * @param n Next do elemento
     */
    public Node(T x, Node<T> n){
        elemento = x;
        previous = null;
        next = n;
    }
    
    /**
     * 
     * @param n Previous do nodo
     * @param x Elemento de nodo
     */
    public Node(Node<T> n, T x){
        elemento = x;
        previous = n;
        next = null;      
    }
    
    public Node(T x){
        elemento = x;
        next = null;
        previous = null;
    }
    
    public Node(){
        this(null);
    }

    //construtor com elemento e next
    public Node( Node<T> antes, T x, Node<T> depois){
        elemento = x;
        next = depois;
        previous = antes;
    }
    
    
    //
    //      MÉTODOS
    //
    
    
    /**
     * 
     * @return  elemento do elemento do node
     */
    
    public T element(){ //throws InvalidNodeException{
        
        if(this == null){
            
            return null;
            //throw new InvalidNodeException("Null node");
        }
        return elemento;
    }

    /**
     *
     * @return próximo node <T> 
     */
    
    public Node<T> getNext(){
        return next;
    }
    
    public Node<T> getPrevious(){
        return previous;
    }
        
    
    /**
     * 
     * @param x elemento a fixar no node
     */
    
    public void setElement(T x){
        this.elemento = x;
    }
    
    /**
     * 
     * @param n, elemento <T> seguinte
     */
    public void setNext(Node<T> n){
        next = n;
    }
    
    public void setPrevious(Node<T> n){
        previous = n;
    }
}
