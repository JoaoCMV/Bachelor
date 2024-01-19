#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

#define MAX 10
#define QUANTUM_RR 3

#define M 10
#define N 40
#define MEM 200

int programas[M][N];                  // Matriz dos programas

int mem_count = MEM;
int mem[MEM][2];                      // Primeira linha controlo o pID, segunda o valor guardado
int estado_programa[M];                         // Estado de cada programa
int p_index[N];                                 // Guarda a instrução em que o programa está
int inst_restantes[M];                          // contem as instruções restantes de cada programa 
    
int READY[MAX];
int r_front = 0;
int r_rear = -1;
int r_itemCount = 0;

int BLCK[MAX];
int b_front = 0;
int b_rear = -1;
int b_itemCount = 0;



//          IMPLEMENTAÇÃO DA FILA   

//  Primeiro valor da fila, sem eliminar
int front(char c);
bool isEmpty(char c);
bool isFull(char c);
int size(char c);
void insert(int data, char c);
int removeData(char c);
int codigo(char c[5]);                                                  // Retorna o codigo da instruçao
void input();                                                           // Trata do input do programa 
void escalonamento();                                                   // Começa os programas
int novo(int pID);                                                      // Verifica se o processo pode ser iniciado
int mem_var(int pID);                                                   // Auxiliar para calcular a memoria necessária para as variaveis
int novo_run(int pID);                                                  // Passa o processo de New para Run ou Ready
int run(int pID, int p_index, int inst_restantates);                    // Trata um processo que acabou de correr em run 
int block_next(int pID, int inst);                                      // Verifica se o processo entra em BLOCK antes de ser retirado pelo RR 
int instrucao(int pID, int index_i[], int i);                           // Quando um processo entra em run vao ser gerado o output das instruções
int guarda_mem(int pID, int mem_necessaria);                            // Guarda o processo aceite em memória
void ini_mem();                                                         // Inicia a memoria com -1 em todos para sinalizar que está disponivel
void escreve_mem(int pID, int ini, int fim);                            // Escreve em memória toda a informação do programa
int find_memVar(int pID);                                               // Procura onde começa as variaveis de cada processo
void free_mem(int pID);                                                 // Liberta o processo que acabou de correr da memória

void teste_mem(){

    printf("MEM\n");
    for( int c = 0; c < 50; c++){

        printf("[%d] -> %d\n", c, mem[c][1]);
    }
}

int main() {

    input();

    ini_mem();
    escalonamento();

    // TESTES
    //teste_mem();

    return 0;
}


// ----------------------------------------   INSTRUÇÕES   -----------------------------------------------------

int instrucao(int pID, int index_i[], int i){

    inst_restantes[pID] -= 1;
    int pID_fork = 0;
    int print_c = 0;                        // Controla se foi inserido algum print ou nao

    switch(programas[pID][ index_i[pID] ]){

        int ini, m;

        // ZERO  -> var_0 = x
        case(0):
            ini = find_memVar(pID);                     // Começa do espaço de mem var
            mem[ini][1] = programas[pID] [ (index_i[pID] + 1) ];
            break;


        // CPY   -> var_x = var_0
        case(1):
            ini = find_memVar(pID);                         // Começa do espaço de mem var
            m = programas[pID] [(index_i[pID] + 1)] + ini;  // Numero da var a alterar
            mem[m][1] = mem[ini][1];
            break;


        // DEC  -> var_x --
        case(2):
            ini = find_memVar(pID);                         // Começa do espaço de mem var
            m = programas[pID] [(index_i[pID] + 1)] + ini;  // Numero da var a alterar
            mem[m][1] --;
            break;


        // FRK  ->  Fork
        case(3):

            // Verifica o espaço que está vazio nos programas
            for(int c = 0; c<M ; c++){
                if( programas[c][1] == 0 && pID_fork == 0){
                    pID_fork = c;
                }
            }

            // Passa para o novo programa as instruções restantes e o instante
            programas[pID_fork][1] = inst_restantes[pID];
            int y = 2;

            // Cria um novo programa consoante o pedido de fork
            for(int c = index_i[pID] + 2; c < (programas[pID][1] * 2) + 2; c++){
                programas[pID_fork][y] = programas[pID][c];
                y++;
                
            }

            // Verifica se existe memória para criar o novo programa
            if( novo(pID_fork) ){
                p_index[pID_fork] = 2;
                estado_programa[pID_fork] = 'N';
                inst_restantes[pID_fork] = programas[pID_fork][1];

            }else{
                printf("\n>Impossível fazer fork: espaço em memória insuficiente\n");
                printf("%2d |   ", i - 1);
                return 1;

            }

            break;


        // JFW  -> Jump Forward x instruções
        case(4):

            // Verifica se o salto sai do espaço alocado para o programa
            if(programas[pID][ index_i[pID] + 1] <= inst_restantes[pID] ){
                // Atualiza as inst restantes e a instrução em que está
                // Compensa a intrução em que está com -1 e -2
                inst_restantes[pID] -= programas[pID][ index_i[pID] + 1] - 1;               // Remove as inst que salta das restantes
                index_i[pID] += (programas[pID][ index_i[pID] +1 ] * 2 ) - 2;              // passa o index a frente das instruçoes
                
            }else{
                printf("\n>Erro de segmentação do processo %d\n", pID);
                printf("%2d |   ", i - 1);
                return 1;                                                       // Força a criar um hlt para terminar o programa
            }
            break;


        // JBK  -> Jump Back
        case(5):

            // Verifica se o salto sai do espaço alocado para o programa
            if(inst_restantes[pID] + programas[pID][ index_i[pID] + 1] <= programas[pID][1]){
                // Atualiza as inst restantes e a instrução em que está
                // +1 compensa porque tem de começar antes da instrução em que fica depois do salto
                inst_restantes[pID] += programas[pID][ index_i[pID] + 1] + 1;          // Adiciona as inst que salta das restantes
                index_i[pID] -= (programas[pID][ index_i[pID] +1 ] + 1) * 2;          // passa o index para trás das instruçoes
                
            }else{
                printf("\n>Erro de segmentação do processo %d\n", pID);
                printf("%2d |   ", i - 1);
                if(guarda_mem)return 1;                                                       // Termina o programa
            }
            break;
        
        // DSK (Já tratado)
        case(6):
            break;


        // JIZ  -> JFW se x = 0
        case(7):
            // Salta para a frente se x = 0
            if(programas[pID][ index_i[pID] +1 ] == 0){
                inst_restantes[pID] -= 2;
                index_i[pID] += 6;
            }
            break;


        // PRT  ->  print da var_x
        case(8):
            ini = find_memVar(pID);                         // Começa do espaço de mem var
            m = programas[pID] [(index_i[pID] + 1)] + ini;  // Numero da var a dar print
            printf("%2d", mem[m][1]);
            print_c = 1;
            break;

        
        // HLT  -> termina e pode ser qualquer num
        case(9):

            return 1;
            break;

        default:

            break;
            
    }
    
    if(print_c != 1){
        printf("  ");
    }
    index_i[pID] += 2;
    return 0;
    
}


// ----------------------------------------   MEMORIA   -----------------------------------------------------



int find_memVar(int pID){

    int ini;                                // Guarda o inicio do processo em mem

    for (int c = 0; c < MEM; c++){

        if(mem[c][0] == pID){
            ini = c;
            break;
        }
    }

    return ini + (programas[pID][1] * 2);

}



void ini_mem(){

    for(int c = 0; c < MEM; c++){
        mem[c][0] = -1;
    }
}



int mem_var(int pID){

    int ni = 0;                     // Controla o numero de instruções
    int c = 2;                      // Acede ao código da instrução
    int res = 1;                    // Memoria usada no minimo é 1 (var_0)

    // Enquanto existir instruções
    while(ni < programas[pID][1]){

        if( (programas[pID][c] == 1) && (programas[pID][c + 1] > res) ){
            res = programas[pID][c + 1];
        }
        ni++;
        c += 2;
    }

    return (res + 1);               // +1 pelo espaço de var_0
}



int guarda_mem(int pID, int mem_necessaria){


    int espaco = 0;
    int best_fit_i, best_fit_f;             // Guarda o inicio e o fim do programa guardado
    int best_fit_space = MEM + 1;           // Guarda a memoria total do melhor espaço encontrado
    int c = 0;
    int res = 0;                            // A menos que tenha espaço devolve 0 (False)

    while(c < MEM){

        // Se c esta vazio verifica se existe espaço suficiente para o programa
        if(mem[c][0] == -1){

            // Procura até onde está vazia para que seja analisado o espaço vazio
            for (int w = c ; w < MEM; w++){

                if(mem[w][0] == -1){
                    espaco++;

                }else{
                    break;
                }
            }

            // Quando deixar de existir espaço vazio analisa se o espaço encontrado é melhor que o anterior
            if(espaco < best_fit_space && espaco >= mem_necessaria){
                best_fit_space = espaco;
                best_fit_i = c;
                best_fit_f = c + mem_necessaria;
                res = 1;

            }
            
            c += espaco;
            espaco = 0;
    
        }
        c++;
    }

    escreve_mem(pID, best_fit_i, best_fit_f);

    return res; // Com espaço 

}



void escreve_mem(int pID, int ini, int fim){

    int ni = 2;                                 // Controla a instrução que deve ser copiada para memória
    int exe = (programas[pID][1] * 2) + ini;    // Corresponde ao espaço de memória necessária só para instruções

    // Guarda primeiro todas as instruções
    for(int c = ini; c < exe; c++){
        mem[c][0] = pID;
        mem[c][1] = programas[pID][ni];
        ni ++;
    }

    // Depois guarda todas as var
    for(int c = exe; c < fim; c++){
        mem[c][0] = pID;
        mem[c][1] = 0;
        ni ++;
    }

}

void free_mem(int pID){

    for( int c = 0; c < MEM; c++){

        if(mem[c][0] == pID){
            mem[c][0] = -1;
        }
    }
}
// ---------------------------------------- ESCALONAMENTO ----------------------------------------------------


void escalonamento(){

    int pID = 0;
    int inicio[M];                                  // Inicio de cada programa

    int i = 1;                                      // i = Instancia
    int t;
    int control_tR = -1;                            // controlo para o tempo de RUN
    int control_tB = -1;                            // controlo para o tempo de BLOCK
    int inicio_c = 1;                               // Permite correr o inicio sem terminar o programa

    int print_c = 0;                                // Contola o print
    // Cria um array com o inicio dos programas e intruções restantes
    while(pID < M){

        if(programas[pID][1] != 0){
            inicio[pID] = programas[pID][0] + 1;
            inst_restantes[pID] = programas[pID][1];
        }else{
            inicio[pID] = -1;
            inst_restantes[pID] = 0;
        }
        pID ++;
    }

    int index_i[M];            // Aponta para a instrução em que o processo está

    for(int c = 0; c < M; c++){
            index_i[c] = 2;
        }


    printf( " T | stdout | READY  \t\t| RUN \t| BLOCKED\n");
    while((fim() || inicio_c != 0) && (i < 40)){
        
        printf("%2d |   ", i - 1);

        // Se algum programa entrou em Exit agora será limpo da memória e do processador
        for(int c = 0; c < M; c++){
            if( estado_programa[c] == 'E'){
                estado_programa[c] = ' ';
                free_mem(c);
            }
        }


        // ------------------------ BLOCK ----------------------------
        if( !isEmpty('B') ){

            if(control_tB == i){
                pID = removeData('B');
                
                //No caso de o programa não ter acabado volta para a fila READY
                if(inst_restantes[pID] > 0){

                    // Se a fila ready não estiver vazia vai para a fila
                    if(!isEmpty('R') ){
                        estado_programa[pID] = 'r';
                        insert(pID, 'R');

                    // Se a fila estiver vazia pode passar logo para RUN
                    }else{
                        insert(pID, 'R');
                        control_tR = run(pID, p_index[pID], inst_restantes[pID]);
                        estado_programa[pID] = 'b';                     // o estado fica em block para quando entrar no run nao correr logo

                        if(control_tR == 0){
                            control_tR = QUANTUM_RR;
                        }
                        p_index[pID] += (control_tR-1)*2;

                        control_tR += i;
                    }

                }else{
                    estado_programa[pID] = 'E';
                }

                // Se a fila não estiver vazia passo para o próximo processo na fila BLOCK
                if(!isEmpty('B')){
                    pID = front('B');                 
                    control_tB = i + 5;
                }
            }
        }

        // ------------------------ READY ----------------------------
        if( !isEmpty('R') ){

            // Verifica se um processo acabou de vir de BLOCK para que não corra em BLOCK e RUN no mesmo instante
            if(estado_programa[front('R')] == 'b'){
                estado_programa[front('R')] = 'R';

            }else{
                int hlt = instrucao( front('R') , index_i, i);
                print_c = 1;

                // Se retornar um é porque a intrução hlt foi introduzida
                if(hlt == 1){
                    // termina o processo e limpa da fila atualizando o seu estado
                    estado_programa[front('R')] = 'E';
                    removeData('R');

                    // chama o próximo processo para executar
                    if(!isEmpty('R')){
                        instrucao( front('R') , index_i, i);
                        print_c = 1;
                    }
                }
            } 

            // Quando o programa deve parar de correr
            // Verifica de novo a fila ready, pois no caso de ser executada a funcção HLT o unico feedback
            // é que a fila está vazia
            if(control_tR == i && !isEmpty('R')){

                pID = removeData('R');                                                      // Retira o programa a correr no momento

                // Se o programa que terminou não vai para BLOCK e ainda tem mais instruções
                // volta para a fila READY
                if(inst_restantes[pID] > 0 && programas[pID][p_index[pID]] != 6){
                    insert(pID, 'R');       // Volta para a fila Ready
                    p_index[pID] += 2;      // Atualiza a instrução 
                    estado_programa[pID] = 'r';

                // Se onde parou é uma instrução para BLOCK então vai para BLOCK
                }else if(programas[pID][p_index[pID]] == 6){

                    // Se a fila estiver vazia vai logo correr este processo
                    if( isEmpty('B') ){
                        control_tB = i + 5;
                    }

                    estado_programa[pID] = 'B';
                    p_index[pID] += 2;
                    insert(pID, 'B');

                // Se não tiver mais instruções acabou
                }else if(inst_restantes[pID] == 0){
                    estado_programa[pID] = 'E';         
                } 

                // Atualiza o processo em run
                if( !isEmpty('R') ){

                    pID = front('R');
                    
                    control_tR = run(pID, p_index[pID], inst_restantes[pID]);
                    estado_programa[pID] = 'R';
                    
                    if(control_tR == 0){
                        control_tR = QUANTUM_RR;
                    }
                    p_index[pID] += (control_tR-1)*2;

                    control_tR += i;              // O tempo passa a ser o tempo do pŕoximo programa
                }

            }
        }

        // ------------------------ NEW ----------------------------
        for( int c = 0; c < M; c++){

            // Verifica dos programas que já entraram como devem ser tratados
            if( estado_programa[c] == 'N'){
                t = novo_run(c);                    //Tempo que o programa vai correr se for para RUN

                // Se for 0 é porque não é o primeiro da fila e fica em READY
                if(t){          
                    estado_programa[c] = 'R';       // att do estado
                    control_tR = i + t;             // att do tempo para acionar o run
                    p_index[c] += ( (t-1) *2);      // indice da instrução em que está
                }else{
                    estado_programa[c] = 'r';
                }
            }

            // Verifica os processos que estao na altura de entrar
            if(inicio[c] == i){

                inicio_c = 0;                         // Passa a não depender deste controlar para terminar o ciclo

                if( novo(c) ){
                    p_index[c] = 2;
                    estado_programa[c] = 'N';
                }else{
                    printf("\n>Impossível criar processo: espaço em memória insuficiente\n", c);
                    printf("%2d |   ", i - 1);
                }
            }

        }

        i++;
        if(print_c == 0){
            printf("  ");
        }
        estados(i, estado_programa);
        print_c = 0;
    }

}



int run(int pID, int p_index, int inst_restantes ){
    
    int t_block = block_next(pID, p_index);
    // verifico se tem mais instruções que o valor de Q_RR
    if(inst_restantes >= QUANTUM_RR){
        
        // Se tiver mas alguma delas for para passar a BLOCK então interrompe antes de Q_RR
        if(t_block){
            return t_block;                 // +1 para compensar porque tem de executar uma vez ainda no pedido IO

        // Se nenhuma passar a BLOCK corre Q_RR vezes
        }else{
            return QUANTUM_RR;
        }
    
    }else{
        if(t_block < inst_restantes && t_block != 0){
            return t_block ;                 // +1 para compensar porque tem de executar uma vez ainda no pedido IO
        }else{
            return inst_restantes;
        }
    }


}



int novo_run(int pID){

    int block_t;


    // Se a fila RUNNING estiver vazia passa logo para RUNNING
    if(isEmpty('R')){

        insert(pID, 'R');                      // Insiro o pID
        block_t = block_next(pID, 2); 

        // No caso de o programa não terminar em q ciclos
        // Devolvo o numero de ciclos máximos que pode correr
        if(programas[pID][1] > QUANTUM_RR){

            // Se o processo tiver mais instruções que o RR, certifica-se de que nenhuma das
            // futuras instruções é um pedido para entrar em BLOCK
            if(block_t == 0){
                return QUANTUM_RR;

            }else{
                return block_t;
            }
          
        // Se o progama terminar antes do tempo q 
        // Verifica que antes de terminar também não passa para BLOCK
        }else{
            if(block_t == 0){
                return programas[pID][1];

            }else{
                return block_t;
            }
                            
        }

    // Se a fila não estiver vazia passa para READY embora seja inserido na fila
    // fica a espera de vez
    }else{       
        insert(pID, 'R');                                          // Adiciono também o id do programa   
        return 0;                 
    }
}



int block_next(int pID, int inst){
    
    // Verifica se antes de chegar ao numero Q_RR existe algum pedido para entrar em BLOCK
    // cod6-> DSK (Pedido para BLOCK)
    for(int x = 1; x <= QUANTUM_RR; x++){
        if(programas[pID][inst] == 6){
            return x;
        }
        inst += 2;
    }

    return 0;
}



int novo(int pID){

    int pID_mem = programas[pID][1] * 2 + mem_var(pID);

    //Se tiver espaço em memoria
    if(mem_count >= pID_mem && guarda_mem(pID, pID_mem) ){
        mem_count -= pID_mem;       
        return 1;
    }else{
        return 0;
    }
}



void input(){

    int pID = -1;                   // Programa que está a ser preparado
    int n_instrucao = 0;            // n instrução em que o programa está
    int conta_instrucao;            // No fim de cada programa coloca o numero de instruções no array
    char c;                         // char lido  
    int i;                          // controlo do char lido na linha
    int j;                          // controlo do array de num para introduzir o input
    char str[10];                   // string da linha toda
    char instrucao[5];              // string da instruçao
    char num[5];                    // numero depois da instruçao

    FILE* file = fopen("/home/joao/Desktop/Eng_Informatica/Escola20_21_2/SO/Trabalho2/Input.txt", "r"); // r -> read only  
         
    if (! file ){  
            printf("Ficheiro não encontrado\n"); 
            exit(-1); 
        } 
    
    while(1){

        if( feof(file) ) {                                  // testa se é o fim do ficheiro
            programas[pID][1] = conta_instrucao;
            break;
        }

        if( fgets(str, 11, file) != NULL) {
            i = 1;
            c = str[0];
            

            //------------ Verfica a instrução introduzida até achar um espaço -----------------
            while(!isspace(c) && c != NULL){
                instrucao[i - 1] = c; 
                c = str[i];
                i++;
            }
            
            // Verifica o código obtido e coloca na matriz de programas
            if( codigo(instrucao) == -1){

                if(pID != -1){
                    programas[pID][1] = conta_instrucao;
                }
                conta_instrucao = 0;
                pID ++;
                n_instrucao = 0;


            }else if( codigo(instrucao) == -2){
                printf("ERRO NA INSTRUÇÃO");

            }else{
                programas[pID][n_instrucao] = codigo(instrucao);            // Guarda o código da instrução
                n_instrucao++;
                conta_instrucao++;
            }
            

            // ---------- Verifica o num introduzido depois da instrução -----------------------
            c = str[i];
            i++;
            j = 0;
            while(!isspace(c) && c != NULL){
                num[j] = c;
                c = str[i];
                i++;
                j++;
            }

            programas[pID][n_instrucao] = atoi(num);                        // Guarda o numero depois da instrução no array
            n_instrucao++;
            memset(num, 0, 5);                                              // Limpa o array depois de ser usado


            // No caso de estar no inicio do programa reserva o espaço para o num de instruçoes
            if(n_instrucao == 1){
                n_instrucao++;
            }
            
        }
    }
    fclose(file);
}



void estados(int i, int program_status[]){

    printf("   |");
    int space = 0;
    for(int c = 0; c < M ;c++){

        if(program_status[c] == 'r'){
            printf(" P%d", c);
            space ++;
        }
    }
    
    while(space < 5){
        printf("   ");
        space ++;
    }
    printf("    |");

    int r = 0;                              // Controla se existe algum processpo em run

    for(int c = 0; c < M ;c++){

        if(program_status[c] == 'R'){
            printf("  P%d   |", c);
            r ++;
        }
    }

    if(r == 0){
        printf("       |");
        r = 0;
    }

    for(int c = 0; c < M ;c++){

        if(program_status[c] == 'B'){
            printf(" P%d ", c);
        }
    }


    /*
        switch(program_status[c]){
            case('N'):
                printf("NEW\t");
                break;

            case('r'):
                printf("READY\t");
                break;

            case('R'):
                printf("RUN\t");
                break;

            case('B'):
                printf("BLOCK\t");
                break;

            case('E'):
                printf("EXIT\t");
                break;

            default:
                printf("\t");
                break;
        }
    */
    printf("\n");
}



int codigo(char *c){

    switch(c[0]){
        case('I'):
            return -1;
            break;

        case('Z'):
            return 0;
            break;
        
        case('C'):
            return 1;
            break;
        
        case('D'):

            switch(c[1]){

                case('E'):
                    return 2;
                    break;
            
                case('S'):
                    return 6;
                    break;

                default:
                    break;
            }

            break;

        case('F'):
            return 3;
            break;

        case('J'):
            
            switch(c[1]){

                case('F'):
                    return 4;
                    break;
                
                case('B'):
                    return 5;
                    break;

                case('I'):
                    return 7;
                    break;

                default:
                    break;
            }
            break;

        case('P'):
            return 8;
            break;
        
        case('H'):
            return 9;
            break;

        default:
            return -2;
            break;
    }

}

int fim(){

    for(int c = 0; c < M; c++){

        // Verdifica se algum programa ainda está a correr
        if(estado_programa[c] == 'N' || estado_programa[c] == 'r' ||
           estado_programa[c] == 'R' || estado_programa[c] == 'B' ||
           estado_programa[c] == 'E' ){
            return 1;

        }
    }

    return 0;
}
// ---------------------------------------- QUEUE  ----------------------------------------------------

int front(char c) {

    if(c == 'R'){
        return READY[r_front];
    }else{
        return BLCK[b_front];
    }
}

bool isEmpty(char c) {
   if(c == 'R'){
        return r_itemCount == 0;
    }else{
        return b_itemCount == 0;
    }
}

bool isFull(char c) {
    if(c == 'R'){
        return r_itemCount == MAX;
    }else{
        return b_itemCount == MAX;
    }
}

int size(char c) {
   if(c == 'R'){
        return r_itemCount;
    }else{
        return b_itemCount;
    }
}  

void insert(int data, char c) {

    if(c == 'R'){
        if(!isFull('R')) {
	
            if(r_rear == MAX-1) {
                r_rear = -1;            
            }       

        READY[++r_rear] = data;
        r_itemCount++;
        }
    }else{

       if(!isFull('B')) {

            if(b_rear == MAX-1) {
                b_rear = -1;            
            }       

        BLCK[++b_rear] = data;
        b_itemCount++;
        }
    }
}

int removeData(char c) {

    if(c == 'R'){
        int data = READY[r_front++];
            
        if(r_front == MAX) {
            r_front = 0;
        }
            
        r_itemCount--;
        return data; 
    }else{
        int data = BLCK[b_front++];
            
        if(b_front == MAX) {
            b_front = 0;
        }
            
        b_itemCount--;
        return data;
    }
}
