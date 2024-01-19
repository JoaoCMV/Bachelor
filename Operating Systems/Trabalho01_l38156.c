#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

#define MAX 10

// Fila Ready
int READY[MAX];
int r_front = 0;
int r_rear = -1;
int r_itemCount = 0;

// Fila Block
int BLCK[MAX];
int b_front = 0;
int b_rear = -1;
int b_itemCount = 0;

// Fila AUX
int AUX[MAX];
int a_front = 0;
int a_rear = -1;
int a_itemCount = 0;



//          IMPLEMENTAÇÃO DA FILA   

//  Primeiro valor da fila, sem eliminar
int front(char c);
bool isEmpty(char c);
bool isFull(char c);
int size(char c);
void insert(int data, char c);
int removeData(char c);



int main() {
   
    int programas[5][10] = {

        {0, 3, 1, 2, 2, 4, 1, 1, 1, 1 } ,

        {1, 2, 4, 2, 4, 2, 0, 0, 0, 0 } ,

        {3, 1, 6, 1, 6, 1, 6, 1, 0, 0 } ,

        {3, 6, 1, 6, 1, 6, 1, 6, 0, 0 } ,

        {5, 9, 1, 9, 0, 0, 0, 0, 0, 0 } 

        };


    int n_programas = sizeof(programas)/sizeof(programas[0]);       //Nº programas a considerar

    char program_status [n_programas];                               // Controla os estados dos programas
    int program_timer [n_programas];                                 // Tempo do programa
    int start_timer [n_programas];                                   // Lista com os tempos de chegada dos programas
    int p_index[n_programas];                                        // Index do processo em que o programa esta

    //Adiciona os primeiros timers ao array de tempo
    for(int p_id = 0; p_id < n_programas; p_id++){
        start_timer[p_id] = programas[p_id][0];
        program_status[p_id] = " ";
        p_index[p_id] = 0;
    }

    int end = 0;
    int instance = 0;
    int not_new = 0;
    int new_remaining = 1;
    int end_c = 0;                                                  // Se algum programa sair será depois tratado para ser "limpo"
    int r_id, b_id, a_id;                                           // pID dos programas nas filas
    int f_r, f_idr, f_b, f_idb, end_pID;
    int controltime_AR, controltime_B, tr_remaining;

    int quantum_RR = 3;
    int quantum_VRR = 3;
    int r_finish = 0;
    int a_finish = 0;
    int run_control = 0;

    while(end < n_programas){

        // ------------------------ END ---------------------------------------

        if(end_c != 0){
            program_status[end_pID] = " ";
            end_c = 0;
            end ++;

        }

        // ------------------------ \END  --------------------------------------



        // ------------------------ BLOCK -------------------------------------

        //Se a fila BLOCK não estiver vazia
        if(!isEmpty('B')){

            //Se o processo acabar o tempo em BLOCK
            if(controltime_B == instance){

                b_id = removeData('B');         //Retira o pID do processo que correu

                // Se o processo tiver tempo 0 então termina
                if( programas[b_id] [ p_index[b_id] ] == 0){
                    program_status[b_id] = 'E';
                    end_c = 1;
                    end_pID = b_id;

                // Se não for 0 passa para a fila AUX
                }else{

                    // No caso da lista AUX estar vazia 
                    if(isEmpty('A') && run_control == 0){

                        // No caso de o programa não terminar em q ciclos
                        if(programas[b_id][ p_index[b_id] ] > quantum_RR){
                            controltime_AR = quantum_VRR + instance;
          
                        // Se o progama terminar antes do tempo q 
                        }else{
                            controltime_AR = programas[b_id][ p_index[b_id] ] + instance;
                            a_finish = 1;                  
                        }
                        tr_remaining = programas[b_id][ p_index[b_id] ];      // Insiro o tempo do programa
                        insert(b_id, 'A');                                    // Insiro o pID 
                        program_status[b_id] = 'R';


                    //Se lá estiver outro programa posso apenas adicionar e o delay é tratado depois
                    }else{
                        insert( programas[b_id] [ p_index[b_id] ], 'A');
                        insert( b_id, 'A');
                        p_index[b_id]++;
                        program_status[ b_id ] = 'A';
                    }
                }

                //ATUALIZA O ESTADO DA FILA BLOCK

                // Se a fila BLOCK não estiver vazia adiciona o tempo de BLOCK ao proximo
                if(!isEmpty('B')){

                    // A primeira atualização é diferente porque somo o tempo que decorreu 
                    controltime_B = removeData('B') + instance;

                }
            }

        }

        // ------------------------ \BLOCK -------------------------------------



        // ------------------------ RUN ---------------------------------------

        // Se a fila RUN não estiver vazia e a AUX estiver corre a fila RUN
        // Mas se já estiver a correr um processo em RUN primeiro acaba esse processo
        if( (!isEmpty('R') && isEmpty('A') ) || (run_control != 0) ){

            // Se o programa acabar o tempo em RUN
            if(controltime_AR == instance){

                r_id = removeData('R');

                //Verifico se o programa terminou o tempo em RUNNING para saber se tenho de verificar o seu estado
                if(r_finish != 0){

                    // Se o programa tiver tempo 0 então termina
                    if( programas[r_id][ p_index[r_id] ] == 0){
                        program_status[r_id] = 'E';
                        end_c = 1;
                        end_pID = r_id;      
                    }else{
                        
                        //ATUALIZA O ESTADO DA FILA BLOCK

                        program_status[r_id] = 'B';
                        
                        // No caso da lista BLOCK estar vazia uso apenas o delay da instance
                        if(isEmpty('B')){
                            controltime_B = programas[r_id] [ p_index[r_id] ] + instance;          // Delay da instance
                            insert( r_id, 'B');
                            p_index[r_id]++;

                        //Se lá estiver outro programa posso apenas adicionar e o delay é tratado depois
                        }else{
                            insert( programas[r_id] [ p_index[r_id] ], 'B');
                            insert( r_id, 'B');
                            p_index[r_id]++;
                        }
                    }
                    r_finish = 0;

                // Se não terminar volta a ficar READY
                }else{
                    program_status[r_id] = 'r'; 
                    insert(tr_remaining - quantum_RR, 'R');
                    insert(r_id, 'R');
                }

                //ATUALIZA O ESTADO DA FILA RUNNING OU AUX

                // Se a fila RUNNING não estiver vazia e AUX estiver adiciona o tempo de RUN ao proximo
                if(!isEmpty('R') && isEmpty('A')){

                    tr_remaining = removeData('R');
                    run_control = 1;

                    // No caso de o programa seguinte ainda não terminar
                    if(tr_remaining > quantum_RR){
                        controltime_AR = quantum_RR + instance;      // Somo apenas q

                    // Se o progama terminar antes do tempo q não é adicionado de novo á fila 
                    }else{

                        controltime_AR = tr_remaining + instance;      // Somo o tempo do programa
                        r_finish = 1;
                    }

                    program_status[ front('R')] = 'R';

                // Se a fila AUX não estiver vazia então tem prioridade a fila READY 
                }else if(!isEmpty('A')){

                    tr_remaining = removeData('A');

                    // No caso de o programa seguinte ainda não terminar
                    if(tr_remaining > quantum_VRR){
                        controltime_AR = quantum_VRR + instance;      // Somo apenas q

                    // Se o progama terminar antes do tempo q não é adicionado de novo á fila 
                    }else{

                        controltime_AR = tr_remaining + instance;      // Somo o tempo do programa
                        a_finish = 1;
                    }

                    program_status[ front('A')] = 'R';
                    run_control = 0;

                // Se ambas estiverem vazias o runing passa a ser falso    
                }else{
                    run_control = 0;
                }
            }
        }

        // ------------------------ \RUN ---------------------------------------


        // ------------------------ AUX ---------------------------------------

        //Se a fila AUX não estiver vazia 
        if( !isEmpty('A') ){

            // Se o programa acabar o tempo em RUN
            if(controltime_AR == instance){

                a_id = removeData('A');

                //Verifico se o programa terminou o tempo em AUX para saber se tenho de verificar o seu estado
                if(a_finish != 0){

                    // Se o programa tiver tempo 0 então termina
                    if( programas[a_id][ p_index[a_id] ] == 0){
                        program_status[a_id] = 'E';
                        end_c = 1;
                        end_pID = a_id;      
                    }else{
                        
                        //ATUALIZA O ESTADO DA FILA BLOCK

                        program_status[a_id] = 'B';
                        
                        // No caso da lista BLOCK estar vazia uso apenas o delay da instance
                        if(isEmpty('B')){
                            controltime_B = programas[a_id] [ p_index[a_id] ] + instance;          // Delay da instance
                            insert( a_id, 'B');
                            p_index[a_id]++;

                        //Se lá estiver outro programa posso apenas adicionar e o delay é tratado depois
                        }else{
                            insert( programas[a_id] [ p_index[a_id] ], 'B');
                            insert( a_id, 'B');
                            p_index[a_id]++;
                        }
                    }
                    a_finish = 0;

                // Se não terminar volta a ficar AUX
                }else{
                    program_status[a_id] = 'A'; 
                    insert(tr_remaining - quantum_VRR, 'A');
                    insert(a_id, 'A');
                }

                //ATUALIZA O ESTADO DA FILA AUX

                // Se a fila AUX não estiver vazia adiciona o tempo de RUN ao proximo
                if(!isEmpty('A')){

                    tr_remaining = removeData('A');

                    // No caso de o programa seguinte ainda não terminar
                    if(tr_remaining > quantum_VRR){
                        controltime_AR = quantum_VRR + instance;      // Somo apenas q

                    // Se o progama terminar antes do tempo q não é adicionado de novo á fila 
                    }else{

                        controltime_AR = tr_remaining + instance;      // Somo o tempo do programa
                        a_finish = 1;
                    }

                    program_status[ front('A')] = 'R';
                     
                // Se a fila AUX estiver vazia mas existir processos em READY então passa a correr 
                // os processos em READY
                }else if( isEmpty('A') && !isEmpty('R') ){

                    tr_remaining = removeData('R');
                    run_control = 1;

                    // No caso de o programa seguinte ainda não terminar
                    if(tr_remaining > quantum_RR){
                        controltime_AR = quantum_RR + instance;      // Somo apenas q

                    // Se o progama terminar antes do tempo q não é adicionado de novo á fila 
                    }else{

                        controltime_AR = tr_remaining + instance;      // Somo o tempo do programa
                        r_finish = 1;
                    }

                    program_status[ front('R')] = 'R';
                    run_control = 1;
                }
            }
        }

        // ------------------------ \AUX ---------------------------------------

    
        // ------------------------ NEW ---------------------------------------

        // Se existir algum programa que ainda possa estar em NEW é analisado 
        if(new_remaining != 0){
            for(int p_id = 0; p_id < n_programas;p_id++){

                // Verifica qual é o programa ainda com status new
                if(program_status[p_id] == 'N'){

                    // Se a fila RUNNING e AUX estiver vazia passa logo para RUNNING
                    if(isEmpty('R') && isEmpty('A') ){

                        // No caso de o programa não terminar em q ciclos
                        if(programas[p_id][1] > quantum_RR){
                            controltime_AR = quantum_RR + instance;
          
                        // Se o progama terminar antes do tempo q 
                        }else{
                            controltime_AR = programas[p_id][1] + instance;
                            r_finish = 1;
                            
                        }
                        tr_remaining = programas[p_id][1];      // Insiro o tempo do programa
                        insert(p_id, 'R');                      // Insiro o pID 
                        program_status[p_id] = 'R';
                        run_control = 1;

                    // Se a fila não estiver vazia passa para READY embora seja inserido na fila
                    // fica a espera de vez
                    }else{
                        program_status[p_id] = 'r';
                        insert(programas[p_id][1], 'R');         
                        insert(p_id, 'R');                       // Adiciono também o id do programa
                        
                    }

                    p_index[p_id]++;
                }
            }
        }

        // Se existir programas novos verifica se está na altura de serem corridos
        if(not_new < n_programas){

            for(int p_id = 0; p_id < n_programas;p_id++){

                //Percorre os programas e verifica se existe algum programa novo 
                if(instance == start_timer[p_id]){

                    program_status[p_id] = 'N';
                    not_new ++;

                    p_index[p_id]++;            // Atualiza a faze do programa em questão
                }
            }
        }else{
            new_remaining = 0;
        }

        // ------------------------ \NEW ---------------------------------------


        // ------------------------ FEEDBACK  ---------------------------------

        //Print das linhas do estado atual do processo
        printf("%d\t", instance + 1);
        for(int c = 0; c < n_programas;c++){

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

                case('A'):
                    printf("AUX\t");
                    break;

                case('E'):
                    printf("EXIT\t");
                    break;

                default:
                    printf("\t");
                    break;
            }
        }
        printf("\n");

        //update de tempo e controlos
        instance ++;
    }
}


int front(char c) {

    if(c == 'R'){
        return READY[r_front];

    }else if(c == 'B'){
        return BLCK[b_front];

    }else{
        return AUX[a_front];
    }
}

bool isEmpty(char c) {
   if(c == 'R'){
        return r_itemCount == 0;

    }else if(c == 'B'){
        return b_itemCount == 0;

    }else{
        return a_itemCount == 0;
    }
}

bool isFull(char c) {
    if(c == 'R'){
        return r_itemCount == MAX;

    }else if(c == 'B'){
        return b_itemCount == MAX;

    }else{
        return a_itemCount == MAX;
    }
}

int size(char c) {
   if(c == 'R'){
        return r_itemCount;

    }else if(c == 'B'){
        return b_itemCount;

    }else{
        return a_itemCount;
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

    }else if(c == 'B'){

       if(!isFull('B')) {

            if(b_rear == MAX-1) {
                b_rear = -1;            
            }       

            BLCK[++b_rear] = data;
            b_itemCount++;
        }

    }else{

        if(!isFull('A')) {

            if(a_rear == MAX-1) {
                a_rear = -1;            
            }       

            AUX[++a_rear] = data;
            a_itemCount++;
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

    }else if(c == 'B'){
        int data = BLCK[b_front++];
            
        if(b_front == MAX) {
            b_front = 0;
        }
            
        b_itemCount--;
        return data;

    }else{
        int data = AUX[a_front++];
            
        if(a_front == MAX) {
            a_front = 0;
        }
            
        a_itemCount--;
        return data;

    }
}
