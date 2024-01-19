
package maquinavenda;

import java.util.Scanner;

/**
 *
 * @author Joao
 */
public class MaquinaDinheiro extends Maquina{
    
    private double dinheiroInserido;       
    
    /**
     * Construtor
     */
    public MaquinaDinheiro(){
    }
    
    /**
     * 
     * @param d Dinheiro inserido pelo utilizador
     */
    public void setDinheiroInserido(double d){                  
        this.dinheiroInserido = d;
    }
    
    
    /**
     * 
     * @param p Produto a adquirir
     * @return String das possiveis conclusões do pedido do produto
     */
    @Override
    public String getProduto(int p){
        
        Produto res = getS().getProduto(p);
        String resS = new String();
                                                                                                   
        //Se o produto existir
        if (res != null){
            if (res.getPreco() > this.getDinheiroInserido()){
                resS = ("Dinheiro Insuficiente para efetuar o pedido.\n" + getDInsert(this.getDinheiroInserido()));
            }else{
                resS = ("Produto disponibilizado: " + res.getNome() + " , " + res.getPreco() + "€" );
                getS().removeOne(p);                                                     //Remove uma unidade do produto
                
                if(res.getPreco() == this.getDinheiroInserido()){
                    resS += "\nDinheiro certo. \nObrigado!";                   
                    
                }else if(res.getPreco() < this.getDinheiroInserido()){
                    resS += ("\nTroco: " + getTroco(this.getDinheiroInserido(), res));
                }
            }
        }else{
            resS = "Produto Inválido!";
        }
        
        return resS;
    }
    
        
    /**
     * 
     * @param d dinheiro inserido
     * @param p produto pedido
     * @return Moedas necessárias ao troco ou avisa se não for possivel faze-lo
     */
    String getTroco(double d, Produto p){
        
        double tr = d - p.getPreco(); 
        tr = Math.round(tr * 100.0)/100.0;
        double r = getT().getMaior();
        double res = 0;
        String s_troco = "";
        
        int tc = 0;
        int ti = 0;
        Double l_troco[] = new Double[50];
        
        //Encotra o max a baixo do dinheiro introduzido
        while(res != tr){
            
            // Se a moeda mais alta for adicionada de novo e passar o troco necessário passa para a mais alta a baixo 
            if( r + res > tr){                         
                r = getT().getMaiorAbove(r);
                
            // Se a moeda mais alta ainda puder ser adicionada
            }else if(r+res <= tr && r != 0){
                res += r;
                s_troco += r + " ";
                
            // Se passar a encontrar apenas 0, ficou sem trocos suficientes para o troco.
            }else if(r == 0){
                res = tr;
                s_troco = "Máquina sem troco suficientes!";                  
                
                // Recoloca as moedas preparadas para troco na máquina e devolve o dinheiro inserido
                while(tc < ti){
                    getT().add(l_troco[tc]);
                    tc ++;
                }
                
                getS().addOne(p.getCompartimento());                                            //Retorna a unidade de produto retirada
                getDInsert(d);
            }
            
            res = Math.round(res * 100.0) / 100.0;                                  // ACERTAR OS ARREDONDAMENTOS 
            getT().remove(r);
            l_troco[ti] = r;
            ti ++;
        }
        
        return s_troco;
        
    }
    
    /**
     * 
     * @param d Dinheiro a devolver
     */
    String getDInsert(double d){
        
        d = Math.round(d * 100.0)/100.0;
        double r = getT().getMaior();
        double res = 0;
        String s_troco = "";       
        
        //Encotra o max a baixo do dinheiro introduzido
        while(res != d){
            
            // Se a moeda mais alta for adicionada de novo e passar o troco necessário passa para a mais alta a baixo 
            if( r + res > d){                         
                r = getT().getMaiorAbove(r);
                
            // Se a moeda mais alta ainda puder ser adicionada
            }else if(r+res <= d && r != 0){
                res += r;
                s_troco += r + " ";
                
            // Se passar a encontrar apenas 0, ficou sem trocos suficientes para o troco.
            }
        }
               
        return ("Dinheiro devolvido: " + s_troco);
    }
    
    /**
     * 
     * @return true se a maquina estiver operacional
     */
    @Override
    public boolean operacional(){
        
        // Verifica se a maquina tem stock       
        if(getS().isEmpty()){
            return false;
        }       
        return true;
    }
    
    
    /**
     *  Interface
     */
    public void linhaComandosInterface(){                      
            
        if(operacional()){    
            Scanner s = new Scanner(System.in);
            int op1 = 3;
            int op2 = 3;

            while(op1 != 0){

                //--------------------   INTERFACE -----------------
                System.out.println("0 - Sair");
                System.out.println("1 - Gestão");
                System.out.println("2 - Compra");
                // -------------------------------------------------

                op1 = s.nextInt();

                switch(op1){


                    // SAIR
                    case 0:             
                        System.out.println("Obrigado!");
                        break;


                    // GESTAO
                    case 1:

                        op2 = 3;    // Reseta o op2 para não ser logo zero no caso de já ter usado este menu uma vez   
                        while(op2 != 0){   

                            //--------------------   INTERFACE -----------------
                            System.out.println("0 - Voltar ao menu anterior");
                            System.out.println("1 - Carregamento de stock");
                            System.out.println("2 - Listagem de stock");
                            // -------------------------------------------------

                            op2 = s.nextInt();

                            //SEGUNDO MENU - GESTAO
                            switch (op2){                                              

                                // Carrega Stock
                                case 1:
                                    try{
                                        System.out.println("Insira a quantidade, nome do produto, preço unitário e compartimento: ");
                                        int q = s.nextInt();            //Quantidade
                                        String n = s.next();            // Nome
                                        double pu = s.nextDouble();     // Preco unitario
                                        int comp = s.nextInt();         // Compartimento

                                        Produto p = new Produto(n, pu, comp, q);
                                        System.out.println(super.getS().addProduto(p, p.getCompartimento()));
                                    }catch(Exception Ex){
                                        System.out.println("ERRO");
                                        s.nextLine();
                                    }
                                        
                                    break;

                                // Listagem de Stock
                                case 2:
                                    System.out.println(super.getS().toString());            
                                    break;

                                default:
                                    break;
                            }
                        }
                        break;


                    // COMPRA
                    case 2:

                        try{
                            //Pede dinheiro se for uma máquina de Dinheiro

                            System.out.println("Insira o dinheiro: " + s.nextLine());
                            String in = s.nextLine();
                            String moedas[] = in.split("\\s");

                            double d = 0;                                           // Dinheiro inserido pelo utilizador
                            int i = 0;
                            double d_insert;


                            while(i < moedas.length){

                                d_insert = Double.parseDouble(moedas[i]);               // "Cast" para double
                                super.getT().add(d_insert);                                  //Adiciona as moedas inseridas aos trocos
                                d += d_insert;                                      
                                i++;
                            }

                            setDinheiroInserido(Math.round(d * 100.0)/100.0);     //Acertar os arredondamentos                        

                            System.out.println("Insira o compartimento: ");
                            int comp = s.nextInt();

                            System.out.println(getProduto(comp));                   

                            //CONTROL PARA VOLTAR AO MENU INICIAL
                            System.out.println("\n");
                            op1 = 3; 
                            
                        }catch(Exception Ex){
                            System.out.println("Erro");
                            s.nextLine();
                        }
                        break;

                    // CONTINUE
                    default:
                        break;
                }
            }
        }else{
            System.out.println("Maquina fora de serviço!");
        }
    }

    /**
     * @return the dinheiroInserido
     */
    public double getDinheiroInserido() {
        return dinheiroInserido;
    }
}
 
    

