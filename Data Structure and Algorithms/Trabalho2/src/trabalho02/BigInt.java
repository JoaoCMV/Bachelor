
package trabalho02;

/**
 *
 * @author Joao
 */
public class BigInt implements AstroInt {

    DoubleLinkedList<Integer> num = new DoubleLinkedList<>();
    char sinal; 
    
    
    //Construto de uma lista de int com a string dada
    BigInt(String s){
        
        char [] charArray = s.toCharArray();
        
        for( char x : charArray){
            num.add(Character.getNumericValue(x));
        }        
        
    }
    
    //Get & set
    
    public DoubleLinkedList<Integer> getNum(){
        return num;
    }
    
    public int getSize(){
        return num.size();
    }
    
    public void setSinal(char s){
        this.sinal = s;
    }
    
    
    
    @Override
    public AstroInt add(AstroInt x) {
        BigInt num2 = new BigInt(x.toString()); //Passa o argumento dado para BigInt
        
        // Vejo o size dos dois numeros e escolho o menor e o maior
        DoubleLinkedList<Integer> menor;
        DoubleLinkedList<Integer> maior;
        
        //Define o maior num
        if (num.size() <= num2.num.size()){
            menor = num;
            maior = num2.num;
        }else{
            menor = num2.num;
            maior = num;
        }          
        
        //Cria um control para os digitos do menor numero e um iterador para ambos
        int c = 0;
        DoubleLinkedListIterator<Integer> itmenor = menor.iterator();
        DoubleLinkedListIterator<Integer> itmaior = maior.iterator();
        
        //Valores das operações
        int i = 1;              //Auxiliar no calculo das sobras
        int sobra = 0;          //verifico quanto fica da soma anterior
        int dezenas = 10;       //Verifico o valor que deve ser colocado na sobra
        int n1, n2, somatemp;   //Soma temporaria de cada digito dos numeros
        
        
        //BigInt da resolução
        DoubleLinkedList<Integer> res = new DoubleLinkedList<>();
        
        while(itmenor.hasPrev()){                
            
            n1 = itmenor.prev();
            n2 = itmaior.prev();
            somatemp = n1 + n2 + sobra; //Calcula a soma temporaria de cada digito e acrescenta ao numero final           
            res.add(0, somatemp%10);    //Passa apenas o resto da divisao por 10 para passar as dezenas na sobra
            
            i = 1;                      //Dá reset ao i,dezenas,sobra sempre 
            sobra = 0;
            dezenas = 10;
                                        //Calcula quantas dezenas sobram no calculo para acrescentar às sobras da soma
            while(somatemp >= dezenas){
                sobra = i;
                dezenas += 10;
                i++;
            }                
        }
        

        while(itmaior.hasPrev()){
            n2 = itmaior.prev();
            somatemp = n2 + sobra;
            res.add(0, somatemp);
            sobra = 0;
        }
        
       //Adiciona a sobra no caso dos numeros terem o mesmo num de digitos
       if(sobra > 0){
           res.add(0, sobra);
       }
               
        BigInt soma = new BigInt(res.toString());
        return soma;              
        }
    

    @Override
    public AstroInt sub(AstroInt x) {
                
        BigInt numero  = (BigInt)(x);
        
        //Lista da resolução
        DoubleLinkedList<Integer> res = new DoubleLinkedList<>();
        
        //Guarda os dois numeros
        DoubleLinkedList<Integer> n1 = num;
        DoubleLinkedList<Integer> n2 = numero.num;
        
        //Verificar se o numero é negativo
        char s = 0;
        
        //No caso se o numero for negativo é trocado o sinal 
        if(!this.maior(numero)){
            n1 = numero.num;
            n2 = num;
            s = '-';
        }
        
        //Inicia os iteradores        
        DoubleLinkedListIterator<Integer> itn1 = n1.iterator();
        DoubleLinkedListIterator<Integer> itn2 = n2.iterator();
        
        //Auxiliares de parcelas
        int num1, num2;
        int sobra = 0;
        
               
        while(itn2.hasPrev()){
            
            num1 = itn1.prev();
            num2 = itn2.prev();
            num2 += sobra;
            
            if(num1 < num2){
                num1 += 10;
                sobra = 1;
                res.add(0,num1 - num2);
            }else{
                res.add(0, num1 - num2);
                sobra = 0;
            }
        }
        
        while(itn1.hasPrev()){
            res.add(0, itn1.prev() - sobra);
            sobra = 0;
        }
        
        
        // 
        BigInt sub = new BigInt(res.toString());
        sub.setSinal(s);
        return sub;
    }

    @Override
    public AstroInt mult(AstroInt x) {
        
        BigInt numero = (BigInt)(x);
        
        //Guarda os dois numeros no n1 e n2
        DoubleLinkedList<Integer> n1 = num;
        DoubleLinkedList<Integer> n2 = numero.num;
       
        
        // Vejo o size dos dois numeros e escolho o menor e o maior
        DoubleLinkedList<Integer> menor;
        DoubleLinkedList<Integer> maior;
        if (n1.size() <= n2.size()){
            menor = n1;
            maior = n2;
        }else{
            menor = n2;
            maior = n1;
        } 
        
        //Inicia o iterador do menor       
        DoubleLinkedListIterator<Integer> itmenor = menor.iterator();
        
        //Inicia os controladores das parcelas da soma
        int n = 0;
        int c = 0;
        
        //Inicia os numeros utilizados na soma das parcelas
        int num1, num2, st;
        int sobra = 0;
        int i = 1;
        int dezenas = 10;
        
        //Inicia as listas auxiliares e temporarias para as parcelas 
        DoubleLinkedList<Integer> somatemp = new DoubleLinkedList<>();
        
        //Inicia o BigInt final
        BigInt res = new BigInt("");        
        
        while(itmenor.hasPrev()){
            num2 = itmenor.prev();
            //Control da parcela que se está a adicionar para adicionar "0"
            while(n > c){
                somatemp.add(0,0);
                c++;
            }
            
            //Inicia um iterador novo para percorrer o n1
            DoubleLinkedListIterator<Integer> itmaior = maior.iterator();
            // Multiplica o n2 por todos os digitos de n1
            while(itmaior.hasPrev()){
                               
                num1 = itmaior.prev();         
                st = num1 * num2 + sobra;
                somatemp.add(0, st%10);
                               
                //Verifica se existe sobra para adicionar
                i = 1;                      //Dá reset ao i,dezenas,sobra sempre 
                sobra = 0;
                dezenas = 10;
                                            //Calcula quantas dezenas sobram no calculo para acrescentar às sobras da soma
                while(st >= dezenas){
                    sobra = i;
                    dezenas += 10;
                    i++;
                }                                                    
            }
            
            //No caso de ainda existir sobra da ultima operação adiciona ao numero
            if(sobra > 0){
                somatemp.add(0,sobra);
            }
            
            //Passa a soma temporaria para Big Int para adicionar ao final
            BigInt soma = new BigInt(somatemp.toString());
            res = (BigInt) res.add(soma);
            
            //Ajustas os auxiliares
            somatemp.clear();
            sobra = 0;           
            n ++;
            c = 0;
        }
                
        return res;
        
        }

    @Override
    public AstroInt div(AstroInt x) {
        
        BigInt numero = (BigInt)(x);
        
        //Guarda os dois numeros no n1 e n2
        DoubleLinkedList<Integer> n1 = num;
        DoubleLinkedList<Integer> n2 = numero.num;
        
        //Inicia o iterador de n1  
        DoubleLinkedListIterator<Integer> itn1 = n1.iterator();
        DoubleLinkedListIterator<Integer> itn2 = n2.iterator();
        
        
        //Auxiliares da divisao
        BigInt auxiliar = new BigInt("2");
        BigInt res = new BigInt("");
        int d = 1;
        int res_size = n2.size();
        
        int n_inicial = itn1.next();
        
        //No caso de o segundo numero ser maior o resultado será zero
        if(numero.maior(this)){
            BigInt zero = new BigInt("0");
            return zero;
        }
        
        //Equnato o n1 for maior que n2, ou se n1 igual a n2 (size) mas n1 continua a ser maior
        while((n1.size() > res_size) || (n1.size() == res_size && this.maior(res))){                      
            
            //Passa o divisor para o auxiliar para multiplicar por n2
            auxiliar = new BigInt(Integer.toString(d));
            
            //Multiplica o divisor pelo auxiliar
            res = (BigInt)(numero.mult(auxiliar));
            
            //Testa os possiveis resultados             
            d ++;
           
            //Reinicia o dados do n2
            itn2 = res.num.iterator(); 
            //Define o novo size a ser comparado
            res_size = res.num.size();
           
        }
        
        //Retorna o divisor atrás duas unidades pelo delay na avaliação do numero
        auxiliar = new BigInt(Integer.toString(d - 2));
        
        BigInt resf = new BigInt(auxiliar.toString());               
        return resf;
    }

    //Auxiliar para ver se o número é maior ou não
    boolean maior(BigInt x){
                
        //Guarda os dois numeros no n1 e n2
        DoubleLinkedList<Integer> n1 = num;
        DoubleLinkedList<Integer> n2 = x.num;
        
        //Inicia o iterador de n1  
        DoubleLinkedListIterator<Integer> itn1 = n1.iterator();
        DoubleLinkedListIterator<Integer> itn2 = n2.iterator();
        
        int num1, num2;
        
        //No caso de n1 ter menos digitos que o n2 retorna logo false
        if(n1.size() < n2.size()){
            return false;
        
        // Se n1 tem mais digitos é logo true    
        }else if(n1.size() > n2.size()){
            return true;
        }
        
        //Verifica se n1 é maior que n2, no caso de serem iguais passa ao seguinte
        while(itn1.hasNext()){
            
            if(!itn2.hasNext()){
                return true;
            }
            num1 = itn1.next();
            num2 = itn2.next();
            
            //Se for maior pode logo retornar true
            if(num1 > num2){
                return true;
                
            //Se for menor retorna logo false    
            }else if(num1 < num2){
                return false;
            }
        }     
        //se os digitos forem todos iguais
        return true;
    }
    
    
    @Override
    public AstroInt mod(AstroInt x) {
                
        BigInt numero = (BigInt)(x);
        
        //Guarda os dois numeros no n1 e n2
        DoubleLinkedList<Integer> n1 = num;
        DoubleLinkedList<Integer> n2 = numero.num;
        
        //Inicia o iterador de n1  
        DoubleLinkedListIterator<Integer> itn1 = n1.iterator();
        DoubleLinkedListIterator<Integer> itn2 = n2.iterator();
        
        
        //Auxiliares da divisao
        BigInt auxiliar = new BigInt("2");
        BigInt res = new BigInt("");
        int d = 1;
        int res_size = n2.size();
        
        int n_inicial = itn1.next();
        
        //Equnato o n1 for maior que n2, ou se n1 igual a n2 (size) mas n1 continua a ser maior
        while((n1.size() > res_size) || (n1.size() == res_size && this.maior(res))){                      
            
            //Passa o divisor para o auxiliar para multiplicar por n2
            auxiliar = new BigInt(Integer.toString(d));
            
            //Multiplica o divisor pelo auxiliar
            res = (BigInt)(numero.mult(auxiliar));
            
            //Testa os possiveis resultados             
            d ++;
           
            //Reinicia o dados do n2
            itn2 = res.num.iterator(); 
            //Define o novo size a ser comparado
            res_size = res.num.size();
           
        }
        
        //Retorna o divisor atrás duas unidades pelo delay na avaliação do numero
        auxiliar = new BigInt(Integer.toString(d - 2));
        
        BigInt resf = new BigInt(auxiliar.toString());               
        return this.sub( numero.mult(resf) );
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
        //Verifica se o numero é negativo ou não
        if(this.sinal == '-'){
            return "-" + num.toString();
        }else{
            return num.toString();
        }
    }
    
    
    public static void main(String[] args){
        
        BigInt n1 = new BigInt("2");
        BigInt n2 = new BigInt("23");
                  
        System.out.println(n1.mult(n2));
               
   
    }
}

