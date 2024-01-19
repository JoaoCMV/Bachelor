
package maquinavenda;

/**
 *
 * @author Joao
 */
public class Produto {
    
    
    private String nome;
    private int compartimento;
    private double preco;
    private int quantidade;
    
    
    /**
     * 
     * @param n Nome do produto
     * @param p Preço do produto
     * @param comp Compartimento do produto
     * @param q quantidade
     */
    public Produto(String n, double p, int comp, int q){
        this.nome = n;
        this.compartimento = comp;
        this.preco = p;
        this.quantidade = q;
    }
    
    
    /**
     * 
     * @return  compartimento
     */
    public int getCompartimento(){
        return compartimento;
    }
    
    
    /**
     * 
     * @return nome do produto
     */
    public String getNome(){
        return nome;
    }
    
    
    /**
     * 
     * @return preço
     */
    public double getPreco(){
        return preco;
    }
    
    
    /**
     * 
     * @return quantidade
     */
    public int getQuantidade(){
        return quantidade;
    }
    
    
    
    /**
     * 
     * @param q quantidade
     */
    public void setQuantidade(int q){
        this.quantidade = q;
    }
    
    
    
    /**
     * 
     * @param c compartimento
     */
    public void setCompartimento(int c){
        this.compartimento = c;
    }
    
    
    /**
     * 
     * @param n nome
     */
    public void setNome(String n){
        this.nome = n;
    }
    
    
    /**
     * 
     * @param p preço
     */
    public void setPreco(double p){
        this.preco = p;
    }
}
