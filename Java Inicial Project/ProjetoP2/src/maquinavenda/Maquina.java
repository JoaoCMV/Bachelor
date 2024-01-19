
package maquinavenda;

import java.util.Scanner;

/**
 *
 * @author Joao
 */
public abstract class Maquina {
        
    private Stock s = new Stock();
    private Troco t = new Troco();
         

    /**
     * 
     * @param st Stock a adicionar á maquina
     */
    public void setS(Stock st){
        this.s = st;
    }
    
    
    /**
     * 
     * @return Stock
     */
    public Stock getS(){
        return s;
    }
    
    /**
     * @return the t
     */
    public Troco getT() {
        return t;
    }

    /**
     * @param t the t to set
     */
    public void setT(Troco t) {
        this.t = t;
    }
    
    
    /**
     *  Interface
     */
    protected void mInterface() {
        Inter inter = new Inter(this);
        inter.mInterface(this);
    }
    
    
    /**
     *  Cria uma BD para testes
     */
    public void insertBD(){
        
        Produto teste01 = new Produto("Nata", 2.30, 0, 2);
        Produto teste02 = new Produto("Agua", 0.80, 1, 4);
        Produto teste03 = new Produto("Cafe", 0.55, 2, 5);
        Produto teste04 = new Produto("Gomas", 0.01, 3, 4);
        Produto teste05 = new Produto("Pastilha", 0.1, 4, 2);
        Produto teste06 = new Produto("Ovo", 0.35, 5, 3);
        Produto teste07 = new Produto("Carne", 11.99, 6, 1);
        Produto teste08 = new Produto("Camarao", 14.67, 7, 3);
        Produto teste09 = new Produto("Alface", 1, 8, 4);
        Produto teste10 = new Produto("Tomate", 1.50, 9, 7);
        
        getS().addProduto(teste01, 0);
        getS().addProduto(teste02, 1);
        getS().addProduto(teste03, 2);
        getS().addProduto(teste04, 3);
        getS().addProduto(teste05, 4);
        getS().addProduto(teste06, 5);
        getS().addProduto(teste07, 6);
        getS().addProduto(teste08, 7);
        getS().addProduto(teste09, 8);
        getS().addProduto(teste10, 9);
    
    }
    
    public abstract boolean operacional();
    
    public abstract String getProduto(int p);
    
    public abstract void setDinheiroInserido(double d);
    
    

        
    public static void main(String[] args){
                
        //MaquinaCartao m = new MaquinaCartao(true);
        MaquinaDinheiro m = new MaquinaDinheiro();
        
        m.insertBD();
        
        m.mInterface();
        //m.linhaComandosInterface();

        
    }

}
