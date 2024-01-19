
package BTree;

/**
 *
 * @author Joao
 */
public interface BTree_i<T extends Comparable<? super T>> {
    
    public boolean contains(T x);
    public T findMax();
    public T findMin();
    public void insere(T x);
    public void remove(T x);
}
