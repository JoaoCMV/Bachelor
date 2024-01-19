
package maquinavenda;

import java.util.Scanner;

/**
 *
 * @author Joao
 */
public class MaquinaCartao extends Maquina {
    
    private boolean net;
    
    
    /**
     * @param n Acesso á net
     *  Cria uma maquina com n acesso á net
     */
    public MaquinaCartao(boolean n){
        this.net = n;
    }
    
    
    /**
     * Cria uma maquina com net
     */
    public MaquinaCartao(){
        this(true);
    }
    
    
    /**
     * 
     * @param p Produto a adquirir
     * @return String das possiveis conclusões do pedido do produto
     */
    @Override
    public String getProduto(int p){
        
        Produto res = getS().getProduto(p);
                                                                                                   
        //Se o produto existir
        if (res != null){
            
            getS().removeOne(p);                                                     //Remove uma unidade do produto                
            return("Produto disponibilizado: " + res.getNome() + " , " + res.getPreco() + "€" + "\nFoi retirado o valor do produto ao seu cartão. \nObrigado!");
                
        }  
        return null;
    }
    
    /**
     * 
     * @param b acesso á net
     */
    public void setNet(boolean b){
        this.net = b;
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
        }else if(isNet() == false){
            return false;
        }
        
        return true;
    }
    
    
    /**
     *  Interface com a Linha de Comandos
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

                        System.out.println("Insira o cartão...");                       


                        System.out.println("Insira o compartimento: ");
                        int comp = s.nextInt();

                        System.out.println(getProduto(comp));                   

                        //CONTROL PARA VOLTAR AO MENU INICIAL
                        System.out.println("\n");
                        op1 = 3;              
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

    @Override
    public void setDinheiroInserido(double d) {        
    }

    /**
     * @return the net
     */
    public boolean isNet() {
        return net;
    }
}
