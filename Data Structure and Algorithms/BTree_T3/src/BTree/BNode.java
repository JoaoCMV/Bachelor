
package BTree;

/**
 *
 * @author Joao
 */
public class BNode<T extends Comparable<? super T>> {
    
    T element;
    BNode<T> rightNode;
    BNode<T> leftNode;
    
    
    //Cria um elemento da Tree com dois vizinhos
    public BNode(BNode ln, T e, BNode rn){
        this.element = e;
        this.leftNode = ln;
        this.rightNode = rn;
    }
    
    
    //Cria um elemento da Tree sem vizinhos
    public BNode(T e){
        this(null, e, null);
    }
    
    //Cria um elemento da Tree com um vizinho direito
    public BNode(T e, BNode rn){
        this(null, e, rn);
    }
    
    //Cria um elemento da Tree com um vizinho esquerdo
    public BNode(BNode ln, T e){        
        this(ln, e, null);       
    }
    
    
    
    public String toString(){
        return element.toString();
    }
    
    public int compareTo(T n){
        return this.element.compareTo(n);
    }
    
}
