
package trabalho02;

 /**
 *
 * @author Joao
 */
public class DoubleLinkedList<T> implements Lista<T>, Iterable<T> {
    
       
    //Para nodes preciso de ter um header e um footer na lista, para saber o inicio e o fim (null)
    private Node<T> header;  
    private Node<T> footer;
    private int theSize;
    
    
    /**
     * Construtor com header e footer null
     */
    public DoubleLinkedList(){
        footer = new Node<T>();
        header = new Node<T>();
        footer.setPrevious(header);
        header.setNext(footer);
        theSize = 0;
    }
    
    
    //Cria um iterator com metodos da LinkedListIterator    
    @Override
    public DoubleLinkedListIterator<T> iterator(){
        return new DoubleLinkedListIterator<T>(header.next , footer.previous);
        
    }
    
    
    /**
     * 
     * @return Size da lista
     */
    @Override
    public int size(){
        return theSize;
    }
    
    
    /**
     * 
     * @return true se a lista estiver vazia
     */
    public boolean isEmpty(){
        return theSize == 0;
    }
    
    /**
     * 
     * @return Retorna o header da lista
     */
    public Node<T> header(){
        return header;
    }
  
    
    /**
     * 
     * @param i indice do node
     * @param x Node a adicionar
     */
    
    // Adiciona x no indice i
    @Override
    public void add(int i, T x){
        // n recebe o node no indicie i
        Node<T> n = getNode(i);
        Node<T> p = getNode(i+1);       //p recebe o node seguinte para alterar o previous
        add(n, x, p);
    }
    
    // Adiciona o novo node antes do node retirado com o indice dado
    void add(Node<T> prev, T x, Node<T> next){
        Node<T> newNode = new Node<T>( prev, x, prev.getNext()); //Cria um node depois de n e com o next de n
        prev.setNext(newNode);          //o node anterior recebe o novo como next
        next.setPrevious(newNode);      //o node next recebe o novo como previous
        theSize++;
    }
    
    @Override
    public void add(T x){
        add(this.theSize , x);
    }
    
    
    // Remove o nó seguinte
    void remove(Node<T> prev){
        prev.setNext(prev.getNext().getNext());         
        prev.getNext().setPrevious(prev);
        theSize--;
    }
    
    
    /**
     * 
     * @param ind indice a remover
     * @return 
     */
    
    @Override
    public T remove(int ind){
        Node<T> res = getNode(ind).getNext();
        remove(getNode( ind));
        return res.element();
    }
    
    
    // Procura pela lista o elemento x e retorna o seu valor
    Node<T> findPrevious(T x){
        Node<T> p = header();
        for(T v: this){
            if (v.equals(x)){
                return p; 
            }else{
                p = p.getNext();
            }
        }
        
        throw new java.util.NoSuchElementException();
    }
    
    
    // Remove o elemento x da lista
    @Override
    public void remove(T x){
        try{
            remove(findPrevious (x));
        }
        catch (java.util.NoSuchElementException e){};
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        
        for( T x : this ){
            if (x != null){
                sb.append(x);
            }
        }       
        
        return new String( sb );
    }
    
    
    /**
     * 
     * @param indx indice do node a trocar
     * @param x node colocado no indice dado
     */
    @Override
    public void set(int indx, T x){
        getNode(indx + 1).setElement(x);        //Alterei para +1, header é o zero
    }
    
    /**
     * 
     * @param ind   indice do node a retornar
     * @return  Retorna o node com indice ind
     */
    @Override
    public T get(int ind){
        if ( ind >= 0 && ind <=size() - 1){
            return getNode(ind + 1).element();          //Alterei para ind + 1 poruqe o zero é o header null
        }else{
            throw new IndexOutOfBoundsException( "getNode index: " + ind + "; size: " + size() );
        }
    }
    
    
    /**
     * 
     * @param i Indice do node a procurar
     * @return  Node a retornar
     */
    
    //Começa a procurar no header i = 0, e continua até chegar no node com a posição que queremos
    Node<T> getNode(int i){
        int ind = 0;
        Node<T> s = header();
        while(ind++ < i){
            s = s.getNext();
        }
        return s;
    }
    
    public void clear(){
        header.setNext(footer);
        footer.setPrevious(header);
        theSize = 0;
    }
}



