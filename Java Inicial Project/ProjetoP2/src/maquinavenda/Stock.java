
package maquinavenda;

/**
 *
 * @author Joao
 */
public class Stock {
    
    private Produto[] stock;
    private int limite;
    private String[] s_Stock;
    
    
    /**
     * Cria um stock de 100 produtos
     */
    public Stock(){
        this.stock = new Produto[100];
        this.limite = 100;
        this.s_Stock = new String[limite];
    }
    
    /**
     * 
     * @param n Numero de produtos do stock 
     */
    public Stock(int n){
        this.stock = new Produto[n];
        this.limite = n;
        this.s_Stock = new String[limite];
    }
    
    /**
     * 
     * @param p Produto a ser adicionado
     * @param c Compartimento
     */
    public String addProduto(Produto p, int c){
        
        String res;
        
        if(c > getLimite()){
            res = "ERRO!  Compartimento inexistente!";
        }else{
            
            //Se o compartimento não tiver produto nenhum adiciona
            if(getStock()[c] == null){
                getStock()[c] = p;
                res = "Produto adicionado com sucesso";               
                
            //Se tiver um produto mas ser o mesmo que o produto adicionado aumenta a quantidade
            }else if(p.getNome().equals(getStock()[c].getNome())){
                getStock()[c].setQuantidade(getStock()[c].getQuantidade() + p.getQuantidade());
                getStock()[c].setPreco(p.getPreco());
                res = "Produto atualizado com sucesso";
                
            // Se for diferente elimina o presente e coloca o novo
            }else{
                res = ("Foi removido " + getStock()[c].getNome() + " do compartimento " + c);
                getStock()[c] = p;
                
            }
         
            getS_Stock()[c] = getStock()[c].getQuantidade() + " x " + getStock()[c].getNome() + " compartimento: " + getStock()[c].getCompartimento() + " preço: " + getStock()[c].getPreco() + "\n";            
        }  
        return res;
        
    }
    
    /**
     * 
     * @param p Compartimento
     * @return retorna o produto ou null se este não existir 
     */
    public Produto getProduto(int p){
        
        //Se estiver vazio retorna logo null
        if(!haveStock()){
            System.out.println("Stock vazio.");
            return null;
        }      
        
        // Procuro no stock o produto do compartimento p e vejo a quantidade que existe             
        if(getStock()[p].getQuantidade() != 0){
            return getStock()[p]; 
            
        }else if (getStock()[p].getQuantidade() == 0){
            System.out.print("Produto esgotado.");
            return null;
            
        }else{       
            System.out.print("Produto não encontrado.");
            return null;
        }
    }
        
    /**
     * 
     * @return true se tiver stock
     */
    boolean haveStock(){
        return (this.getStock()[getLimite()-1] == null);
    }
    
    public boolean isEmpty(){
        
        for(int i = 0; i < getStock().length; i++){
            if(getStock()[i] != null && getStock()[i].getQuantidade() != 0){
                return false;
            }
        }
        
        return true;
    }
    
    
    /**
     * 
     * @param c Parametro a remover uma unidade
     */
    public void removeOne(int c){
        getStock()[c].setQuantidade(getStock()[c].getQuantidade() - 1);
        getS_Stock()[c] = getStock()[c].getQuantidade() + " x " + getStock()[c].getNome() + " compartimento: " + getStock()[c].getCompartimento() + " preço: " + getStock()[c].getPreco() + "\n";
    }
    
    
    /**
     * 
     * @param c Parametro a adicionar uma unidade
     */
    public void addOne(int c){
        getS_Stock()[c] = (getStock()[c].getQuantidade() + 1) + " x " + getStock()[c].getNome() + " compartimento: " + getStock()[c].getCompartimento() + " preço: " + getStock()[c].getPreco() + "\n";
    }
    
    
    
    @Override
    public String toString(){
        
        String toS = "";
        int i = 0;
        
        while(i < getS_Stock().length){
            if(getS_Stock()[i] != null){
                toS += getS_Stock()[i];
                i++;
            }else{
                i++;
            }
        }
        
        return toS;
    }

    /**
     * @return the stock
     */
    public Produto[] getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Produto[] stock) {
        this.stock = stock;
    }

    /**
     * @return the limite
     */
    public int getLimite() {
        return limite;
    }

    /**
     * @param limite the limite to set
     */
    public void setLimite(int limite) {
        this.limite = limite;
    }

    /**
     * @return the s_Stock
     */
    public String[] getS_Stock() {
        return s_Stock;
    }

    /**
     * @param s_Stock the s_Stock to set
     */
    public void setS_Stock(String[] s_Stock) {
        this.s_Stock = s_Stock;
    }
}
