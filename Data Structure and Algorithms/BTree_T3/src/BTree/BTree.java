
package BTree;

/**
 *
 * @author Joao
 */
public class BTree<T extends Comparable <? super T>> implements BTree_i<T> {
    
    BNode<T> root;
    
    //Inicia uma tree com o node predefinido
    public BTree(BNode n){
        root = n;
    }
    
    
    //Inicia uma tree com o elemento e
    public BTree(T e){
        root = new BNode(e);
    }
    
    
    //Inicia uma tree com elemento raiz null
    public BTree(){
        root = null;
    }
    
    //Inicia uma tree com nodes vizinhos
    public BTree(BNode ln, T e, BNode rn){
        root = new BNode(ln, e, rn);
    }
    
    //Inicia uma tree com rigth_node
    public BTree(T e, BNode rn){
        this(null, e, rn);
    }
    
    //Inicia uma tree com left_node
    public BTree(BNode ln, T e){
        this(ln, e ,null);
    }
    
    //Retorna se a root é null
    public boolean isEmpty(){
        return root == null;
    }
    
    //Print dos nodes   -- NODE, LEFTNODE, RIGHTNODE
    public void printNLR(){
        printNLR(root, 0);
    }
    
    
    //Print de todos os elementos da Tree -- NODE ,LEFTNODE, RIGHT NODE
    private void printNLR(BNode<T> n, int t){
        
        
        if(n != null){
            System.out.println(n + ";");
            printNLR(n.leftNode, t+1);
            printNLR(n.rightNode, t+1);
        }
    }
    
        //Print dos nodes -- LEFTNODE, NODE, RIGHTNODE
    public void printLNR(){
        printLNR(root, 0);
    }
    
    
    //Print de todos os elementos da Tree -- NODE ,LEFTNODE, RIGHT NODE
    private void printLNR(BNode<T> n, int t){
        
        
        if(n != null){
            printLNR(n.leftNode, t+1);
            System.out.println(n + ";");
            printLNR(n.rightNode, t+1);
        }
    }
    
        //Print dos nodes -- LEFTNODE, RIGHTNODE, NODE
    public void printLRN(){
        printLRN(root, 0);
    }
    
    
    //Print de todos os elementos da Tree -- NODE ,LEFTNODE, RIGHT NODE
    private void printLRN(BNode<T> n, int t){
        
        
        if(n != null){
            printLRN(n.leftNode, t+1);
            printLRN(n.rightNode, t+1);
            System.out.println(n + ";");
        }
    }
    
    
    // Encontra o minimo 
    @Override
    public T findMin(){
        if( isEmpty()){
            return null;
        }
        return findMin(root);
    }
    
    
    // Auxiliar para encontrar o minimo
    private T findMin(BNode<T> n){
        if(n.leftNode == null){
            return n.element;
        }else{
            return findMin(n.leftNode);
        }
    }
    
    
    // Encontra o maximo
    @Override
    public T findMax(){
        if( isEmpty()){
            return null;
        }
        return findMin(root);
    }
    
    
    // Auxiliar para encontrar o maximo
    private T findMax(BNode<T> n){
        if(n.rightNode == null){
            return n.element;
        }else{           
            return findMin(n.rightNode);
        }
    }
    
    
    @Override
    public boolean contains(T x) {
        return contains(x, root);
    }
    
    private boolean contains(T x, BNode<T> n){
        
        //Se for null então nao existe
        if(n == null){
            return false;
        }else{
            
            //Se n for menos a busca segue para a direita
            if(n.element.compareTo(x) < 0){
                return contains(x, n.rightNode);
                
            //Se n não for maior ou é igual ou segue para a esquerda
            }else{
                
                // No caso de ser maio segue para a esquerda
                if(n.element.compareTo(x) > 0){
                    return contains(x, n.leftNode);
                    
                //No caso de ser igual segue para a direita  
                }else{
                    return true;
                }
            }
        }
    }

    @Override
    public void insere(T x) {
        root = insere(x, root);
    }
    
    private BNode<T> insere(T x, BNode<T> n){
        
        //Se chegar ao fim da tree cria um node com x
        if(n == null){
            n = new BNode<T>(null, x, null);
        
        //Se x for menor que n segue para a esquerda
        }else if( (n.element).compareTo(x) > 0 ){
            n.leftNode = insere(x, n.leftNode);
            
        //Se x for maior que n segue para a direita
        }else if( (n.element).compareTo(x) < 0){
            n.rightNode = insere(x, n.rightNode);
        }    
        
        // Devolve a root com a nova arvore
        return n;
        
    }

    @Override
    public void remove(T x) {
        root = remove(x, root);
    }
    
    
    private BNode<T> remove(T x, BNode<T> n){
        
        //Se chegar ao fim da Tree devolve a root inicial
        if(n == null){
            return n;
        }
        
        //Se n for menor que x a busca segue para a direita
        if (n.element.compareTo(x) < 0){
            n.rightNode = remove(x, n.rightNode);
        
        // Se n for maior que x a busca segue para a esquerda
        }else if (n.element.compareTo(x) > 0){
            n.leftNode = remove(x, n.leftNode);
        
        // Quando encontra o valor
        
        //Se tiver dois filhos encontra o minimo da "subTree" do filho da direita e troca o elemento para esse valor
        }else if (n.leftNode != null && n.rightNode != null){
            T min = findMin(n.rightNode);
            n.element = min;
            n.rightNode = remove(min, n.rightNode);
        
        //Se tiver apenas um filho para o valor de N para o valor do filho 
        }else if (n.leftNode == null){
            n = n.rightNode;
        }else{
            n = n.leftNode;
        }
        
        return n;
        
    }
    
    public java.util.Iterator<T> iterator(){
        return new BTreeIteratorPreOrdem<T>(root);
    }
    
}
