
package maquinavenda;

/**
 *
 * @author Joao
 */
public class Troco {
    
    private double[] trocosIniciais = { 0.01, 0.02, 0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00, 10.00, 20.00, 50.00, 100.00, 200.00, 500.00 };
    private double[] troco;
    private double max = 500;
            
    
    /**
     * 
     * @param n, numero de notas/moedas de cada tipo
     * @param limit, Numero máximo de notas/moedas possiveis na caixa
     */
    public Troco(long n, int limit){
        
        troco = new double[limit];
        int t = 0;
        int c = 0;
        int f = 0;
        
        while(c < trocosIniciais.length){
            while(f < n){
                troco[t] = trocosIniciais[c];
                f++;
                t++;
            }
            f = 0;
            c++;
        }       
    }
    
    /**
     * 
     * @param n, numero de notas/moedas de cada
     */
    public Troco(long n){
        this(n, 300);
    }
    
    /**
     * Cria 5 notas/moedas de cada tipo com uma capacidade de 300 na caixa de trocos
     */
    public Troco(){
        this(5, 300);                                                   
    }
    
    /**
     * 
     * @param limit Capacidade total da caixa de trocos
     */
    public Troco(int limit){
        this(5, limit);
    }
    
    /**
     * 
     * @param n valor da moeda a remover
     */
    public void remove(double n){
        
        int c = 0;
        while(c < this.getTroco().length){
            if(n == getTroco()[c]){
                getTroco()[c] = 0;
                c = this.getTroco().length;
            }
            c++;
        }
    }
    
    /**
     * 
     * @param n adiciona a moeda/nota de valor n á caixa
     */
    public void add(double n){
        
        int c = 0;
        if(notFull()){
            while(c < this.getTroco().length){
                if(0 == getTroco()[c]){
                    getTroco()[c] = n;
                    c = this.getTroco().length;
                }
                c++;
            }
        }else{
            System.out.print("A caixa de trocos está cheia, é necessário esvaziar a mesma");
        }
    }
    
    
    /**
     * 
     * @return Maior moeda/nota na caixa de trocos
     */
    public double getMaior(){
        int c = 0;
        double m = 0;
        
        while(c < this.getTroco().length){
            if(m < getTroco()[c]){
                m = getTroco()[c];
            }
            c++;
        }
        return m;  
    }
    
    /**
     * 
     * @param n Maior valor a baixo de n
     * @return Valor mais alto a baixo de n
     */
    public double getMaiorAbove(double n){
        int c = 0;
        double m = 0;
        
        while(c < this.getTroco().length){
            if(m < getTroco()[c] && getTroco()[c] < n){
                m = getTroco()[c];
            }
            c++;
        }
        return m; 
    }
    
    
    /**
     * 
     * @param n moeda/nota a ser contada
     * @return quantidade da moeda/nota pedida
     */
    public int count(double n){
        int c = 0;
        int m = 0;
        
        while(c < this.getTroco().length){
            if(n == getTroco()[c]){
                m ++;
            }
            c++;
        }
        return m;  
    }
    
    /**
     * 
     * @return True se a caixa de trocos não estiver cheia
     */
    boolean notFull(){
        
        int c = 0;
        while(c < this.getTroco().length){
                if(0 == getTroco()[c]){
                    return true;
                }
                c++;
            }
        return false;
    }

    /**
     * @return the trocosIniciais
     */
    public double[] getTrocosIniciais() {
        return trocosIniciais;
    }

    /**
     * @param trocosIniciais the trocosIniciais to set
     */
    public void setTrocosIniciais(double[] trocosIniciais) {
        this.trocosIniciais = trocosIniciais;
    }

    /**
     * @return the troco
     */
    public double[] getTroco() {
        return troco;
    }

    /**
     * @param troco the troco to set
     */
    public void setTroco(double[] troco) {
        this.troco = troco;
    }

    /**
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(double max) {
        this.max = max;
    }
    
}
