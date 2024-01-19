.globl main

.data
command:		.string 
text:			.space		128	
current_Line:		.word 		1
n_Lines:		.word		0
file_Name:		.asciz		
file_Name02:		.asciz		
new_Line: 		.asciz 		"\n"
line:			.space		1024
saved:			.byte		0

.text

#----------------------------  INSERT ------------------------
# Insere antes da linha a string 
# a0 -> endereço da string
# a1 -> tamanho da string
# a2 -> linha a inserir
insert_Before:		
		addi sp, sp, -16
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		
		la s0, n_Lines
		lw s1, 0(s0)
		mv s2, a0
WHILE_6:	
		blt s1, a2, F_7		# while( n_Lines >= line )
		mv a0, s1
		jal realoc		# Realoca as strings para ter espaço para inserir um nova
		addi s1, s1, -1
		j WHILE_6
F_7:		
		mv a0, s2
		addi a1, a1, 1		# para compensar o new line '\0'
		jal strcpy
		
		lw s1, 0(s0)		# lê de novo o valor em n_Lines porque foi alterado anteriormente
		addi s1, s1, 1		# atualiza o num de linhas
		sw s1, 0(s0)		# guarda o valor em n_Lines
				
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		addi sp, sp, 16
		ret
		
#-------------------------------------------------------------		
# Insere depois da linha a string 
# a0 -> endereço da string
# a1 -> tamanho da string
# a2 -> linha a inserir
insert_After:		
		addi sp, sp, -16
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		
		addi a2, a2, 1		# Adiciona 1 a linha a inserir porque vai inserir depois dessa linha
		la s0, n_Lines
		lw s1, 0(s0)
		mv s2, a0
WHILE_7:	
		blt s1, a2, F_9		# while( n_Lines >= line )
		mv a0, s1
		jal realoc		# Realoca as strings para ter espaço para inserir um nova
		addi s1, s1, -1
		j WHILE_7
F_9:		
		mv a0, s2
		addi a1, a1, 1		# para compensar o new line '\0'
		jal strcpy
		
		lw s1, 0(s0)		# lê de novo o valor em n_Lines porque foi alterado anteriormente
		addi s1, s1, 1		# atualiza o num de linhas
		sw s1, 0(s0)		# guarda o valor em n_Lines
				
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		addi sp, sp, 16
		ret
		
#-------------------------------------------------------------	
# Subtitui a linha l por uma nova
# a0 -> endereço da string
# a1 -> tamanho da string
# a2 -> linha a substituir
replace:		
		addi sp, sp, -4
		sw ra, 0(sp)
			
		addi a1, a1, 1		# para compensar o new line '\0'
		jal strcpy
					
		lw ra, 0(sp)
		addi sp, sp, 4
		ret
		
#-------------------------------------------------------------	
# Apaga as linhas entre a0 e a1
# a0 -> Primeira linha a apagar
# a1 -> Linha até onde deve apagar
clear:
		mv t0, a0		# t0 = start (i)
		mv t1, a1		# t1 = end (f)
		addi t1, t1, 1
		la t2, n_Lines
		lb t2, 0(t2)		# t2 = n_Lines
		li t4, 4		# Para puder calcular o endereço de gp
While_10:	
		bgt t1, t2, F_13	# while( f < = n_Lines )
		mul t3, t1, t4		# indica o endereço de gp a ser modificado (end // f)
		sub gp, gp, t3		# aponta para o endereço que deve ser alterado
		lw t5, 0(gp)		# guarda em t5 o endereço de gp[f]
		sw zero, 0(gp)		# limpa o endereço
		add gp, gp, t3		# repoe gp
		mul t3, t0, t4		# indica o endereço de gp a ser modificado (start // i)
		sub gp, gp, t3		# aponta para o endereço que deve ser alterado
		sw t5, 0(gp)		# guarda o endereço em t5(gp[f]) no endereço de gp[i]
		add gp, gp, t3		# repoe gp
		addi t1, t1, 1		# f++
		addi t0, t0, 1		# i++
		j While_10
		
F_13:					# f > n_Lines 
		sub t0, a1, a0		# t0 = end - start
		addi t0, t0, 1		# t0 = end - start + 1
		sub t2, t2, t0		# Atualiza o valor de n_Lines
		la t1, n_Lines
		sb t2, 0(t1)
		ret
#-------------------------------------------------------------
# Imprime da linha a0 a a1
# a0 -> Primeira linha a dar print
# a1 -> Linha até onde deve dar print
Printing:
		li t1, 4
WHILE_5:				# em cada ciclo vai imprimir um linha
		mul t0, a0, t1		# indica o endereço de gp a que deve ser imprimido
		sub gp, gp, t0		# aponta para o endereço que deve ser imprimido
		lw t2, -4(gp)		# guarda em t2 o endereço de gp
		mv t3, a0
		mv a0, t2
		li a7, 4
		ecall
		addi a0, t3, 1
		add gp, gp, t0
		blt a0, a1, WHILE_5
		ret
#----------------------------  INPUT  ------------------------
# a0 -> endereço da string
# a1 -> tamanho da string
# return -> letra equivalente ao comando inserido
input_Comand:	
		addi sp, sp, -12
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		 
		mv s1, zero		# s1 = 0
		mv s0, a0		# s0 = endereço da string
While_1:		
		bge s1, a1, F_1		# while s1 < length
		lbu a0, 0(s0)
		jal is_Comand
		bne a0, zero, F_1	# if( a0 != 0 ) -> F1
		addi s0, s0, 1		# próximo char da string
		addi s1, s1, 1		# t0++
		j While_1
F_1:		
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		addi sp, sp, 12
		ret
#-------------------------------------------------------------
# a0 -> char c
# return: 1 se for um comando, 0 se não for
is_Comand:
		li t0, 0x69		# t0 = 'i'
		beq a0, t0, F_2
		li t0, 0x61		# t0 = 'a'
		beq a0, t0, F_2
		li t0, 0x63		# t0 = 'c'
		beq a0, t0, F_2
		li t0, 0x64		# t0 = 'd'
		beq a0, t0, F_2
		li t0, 0x70		# t0 = 'p'
		beq a0, t0, F_2
		li t0, 0x65		# t0 = 'e'
		beq a0, t0, F_2
		li t0, 0x66		# t0 = 'f'
		beq a0, t0, F_2
		li t0, 0x77		# t0 = 'w'
		beq a0, t0, F_2
		li t0, 0x71		# t0 = 'q'
		beq a0, t0, F_2
		li t0, 0x51		# t0 = 'Q'
		beq a0, t0, F_2
		
		li a0, 0		# se não for nenhuma dos comandos retorna 0
		ret
F_2:
		ret		
#----------------------------   AUX   ------------------------
# Recebe uma sting de input e guarda no endereço a0
# a0 -> endereço do buffer
gets:	
		li a7, 8		
		li a1, 124
		ecall
		ret
#-------------------------------------------------------------
# Verfica o tamanho da string
# a0 -> String		
# return -> tamanho da string		
sizeOf:		
		mv t0, a0
		li t1, 0		# contador
		li t2, 0x0a		# t2 = '\0'
While_2:	
		lbu t3, 0(t0)		# le o char da string e gaurda em t3
		beq t3, t2, F_3		# t3 == '\0'
		addi t0, t0, 1
		addi t1, t1, 1
		j While_2
F_3:		
		mv a0, t1
		ret
		
#-------------------------------------------------------------
# Retira o valor da linha antes da instrução
# a0 -> string
# a1 -> length	
# return -> inteiro correspondente á linha na string
get_Line:	
		addi t0, a1, -2		# t0 = length-=2
		li t1, 1		# t1 controla as unidades
		li t3, 10		# t3 == 10	(Para multiplicar o t1 a cada ciclo)
		mv t6, zero		# res
		add a0, a0, t0		# para começar a ler a string de tras para a frente por causa das unidades
		
		lbu t4, 0(a0)		# le o char da string
		li t2, 0x24		# t0 = '$'
		beq t4, t2, F_$
		li t2, 0x25		# t0 = '%'
		beq t4, t2, F_5
		
While_3:	bltz t0, F_6		# while (t0 >= 0 )
		addi t5, t4, -0x30	# subtrai '0' ao char para saber o seu valor int
		mul t5, t5, t1		# t5 * t1(controla as unidades)
		add t6, t5, t6		# res
		mul t1, t1, t3
		addi a0, a0, -1
		addi t0, t0, -1
		lbu t4, 0(a0)		# le o char da string
		j While_3
		
		
		
F_$:					# no caso de ser $ 
		la t3, n_Lines
		lw a0, 0(t3)			
		ret
F_5:					# no caso de ser %
		li a0, -1
		ret
F_6:
		mv a0, t6
		ret

#-------------------------------------------------------------
#copia uma string para um endereço
# a0 -> endereço da string
# a1 -> tamanho da string
# a2 -> l, linhas a copiar
strcpy:		
		li a7, 9		# Aloca memória para guardar a string
		mv t0, a0
		mv a0, a1
		addi a0, a0, 1		# Para separar sempre as strings		
		ecall			# Aloca memória com tamanho igual ao da string
		
		li t4, 4
		mul t3, a2, t4		# indica o endereço de gp a ser modificado
		sub gp, gp, t3		# aponta para o endereço que deve ser alterado
		sw a0, 0(gp)
		
		li t2, 0
WHILE_4:	lb t1, 0(t0)		# Copia a string para o espaço de memória
		sb t1, 0(a0)
		addi t0, t0, 1
		addi a0, a0, 1
		addi t2, t2, 1
		bne t2, a1, WHILE_4
		add gp, gp, t3
		ret

#-------------------------------------------------------------
# Realoca as string para que possa ser inserida uma nova linha de texto
# Assume que é feito po ordem assim apenas é necessário passar a linha atual para o próximo
# endereço de gp
# a0 -> linha a ser realocada
realoc:
		li t1, 4
		mul t0, a0, t1		# indica o endereço de gp a ser modificado
		sub gp, gp, t0		# aponta para o endereço que deve ser realocado
		lw t2, 0(gp)
		sw t2, -4(gp)
		add gp, gp, t0
		ret
#-------------------------------------------------------------
# Verifica se existe uma virgula na string e devolve a sua posição
# a0 -> string
# a1 -> length
# return -> index da virgula na string
get_Comma:
		li t0, 0
		li t2, 0x2c
WHILE_8:
		lb t1, 0(a0)
		beq t1, t2, F_12	# Se encontrar uma virgula acaba e devolve a posiçao da virgula
		addi t0, t0, 1
		addi a0, a0, 1
		blt t0, a1, WHILE_8	# Se não encontrar continua a procurar enquanto a string nao acabar
		li a0, 0		# Se a string acabar e não encontrar então devolve 0
		ret
F_12:	
		mv a0, t0
		ret
		
#-------------------------------------------------------------
# Copia o texto de um ficheiro
# a0 -> endereço do ficheiro
# a1 -> endereço do buffer
# a2 -> length
copy:
		addi sp, sp, -44
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		sw s3, 16(sp)
		sw s4, 20(sp)
		sw s5, 24(sp)
		sw s6, 28(sp)
		sw s7, 32(sp)
		sw s8, 36(sp)
		sw s9, 40(sp)
		
		li a7, 63		# Guarda todas as linhas no buffer
		ecall
		mv s0, a0		# s0 = length
		la s1, line
		la s4, text		
		li s2, 0x0a		# s2 = '\0'
		li s5, 0		# s5 = contador
		li s6, 0		# size de cada linha
		li s9, 0		# controla se é a primeira linha
		j WHILE_9
WHILE_10:	
		addi s7, s7, 1
		sb s7, 0(s8)
WHILE_9:	
		addi s5, s5, 1		# atualiza o contador
		addi s6, s6, 1
		lb s3, 0(s1)		# lê o char do texto do ficheiro
		beq s3, s2, F_14	# Se for lido um "new line" passa para a próxima linha
		sb s3, 0(s4)		# guarda o char no text
		addi s1, s1 , 1		# proximo char do line
		addi s4, s4, 1		# proximo char do text
		j WHILE_9
	
F_14:		
		sb s2, 0(s4)		# guarda o new line
		addi s6, s6, -1		# compensa o espaço para o new line
		la s4, text		# volta para o inicio do texto
		la s8, current_Line
		lb s7, 0(s8)	
		mv a0, s4		# endereço da string
		mv a1, s6		# tamanho
		mv a2, s7		# linha onde adiciona
		beqz s9, First_Line	# Se for 0 é a primeira linha
		jal insert_After
Next_Lines:				# Se for a primeira linha tem de ser inserido o texto antes
		li s6, 0		# limpa o contador de char de cada linha
		addi s1, s1 , 1		# proximo char do line
		bne s5, s0, WHILE_10	# enquanto nao acabar o length do texto do ficheiro volta para o ciclo
		j F_15
First_Line:
		jal insert_Before
		addi s9 ,s9 ,1
		addi s7, s7, -1		# Compensa a linha onde começa
		j Next_Lines
		
F_15:
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		lw s3, 16(sp)
		lw s4, 20(sp)
		lw s5, 24(sp)
		lw s6, 28(sp)
		lw s7, 32(sp)
		lw s8, 36(sp)
		lw s9, 40(sp)
		addi sp, sp, 44
		ret
#-------------------------------------------------------------
# Escreve o texto no ficheiro
# a0 -> endereço do ficheiro
write:
		addi sp, sp, -24
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		sw s3, 16(sp)
		sw s4, 20(sp)
		
		li a7, 64
		la s0, n_Lines
		lb s1, 0(s0)		# guarda em t2 o num. de linhas
		li s3, 0		# contador
		mv s2, a0		# guarda o ficheiro em t2
WHILE_11:				# em cada ciclo vai escrever uma linha
		addi gp, gp, -4		# aponta para o endereço que deve ser escrito
		lw a1, 0(gp)		# guarda em a1 o endereço de gp
		mv a0, a1
		jal sizeOf		# Guarda o length da linha que deve ser inserida
		mv a2, a0
		mv a0, s2
		ecall			# Copia a linha para o ficheiro	
		la a1, new_Line		# guarda "new line" em a1 para introduzir no ficheiro	
		li a2, 1	
		mv a0, s2
		ecall			# Intoduz uma nova linha no ficheiro
		mv a0, t2
		addi s3, s3, 1
		blt s3, s1, WHILE_11
		li s4, 4
		mul s3, s3, s4
		add gp, gp, s3
		
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		lw s3, 16(sp)
		lw s4, 20(sp)
		addi sp, sp, 24
		ret
			
#------------------------  COMMANDS  -------------------------
# Adiciona antes da linha
# a0 -> String
# a1 -> length
command_i:
		addi sp, sp, -20
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		sw s3, 16(sp)
		
		li s3, 0x2e		# s3 = '.'
		li s1, 1					
		beq a1, s1, Else_1	# Verifica se a instrução tem argumentos
		jal get_Line		# length != 1 preciso de saber a linha a que se refere	
		mv a2, a0		# linha obtida em get_Line
		j INSERT_i		
Else_1:					# se for na linha atual
		la s2, current_Line
		lw a2, 0(s2)
		j INSERT_i			
INSERT_i:		
		la a0, text		# Input de texto
		jal gets
		lb s0, 0(a0)
		beq s0, s3, F_4		# Verifica se o char inserido é '.'
		mv s0, a0		# Guarda o endereço para nao perder
		jal sizeOf		# Tamanho do texto
		mv a1, a0		# tamanho da string
		mv a0, s0		# endereço da string
		jal insert_Before
		j INSERT_i
F_4:		
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		lw s3, 16(sp)
		addi sp, sp, 20
		ret
#-------------------------------------------------------------	
# Adiciona depois da linha
# a0 -> String
# a1 -> length
command_a:
		addi sp, sp, -20
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		sw s3, 16(sp)
		
		li s3, 0x2e		# s3 = '.'
		li s1, 1					
		beq a1, s1, Else_2	# Verifica se a instrução tem argumentos
		jal get_Line		# length != 1 preciso de saber a linha a que se refere	
		mv a2, a0		# linha obtida em get_Line
		j INSERT_a		
Else_2:					# se for na linha atual
		la s2, current_Line
		lw a2, 0(s2)
		j INSERT_a			
INSERT_a:		
		la a0, text		# Input de texto
		jal gets
		lb s0, 0(a0)
		beq s0, s3, F_8		# Verifica se o char inserido é '.'
		mv s0, a0		# Guarda o endereço para nao perder
		jal sizeOf		# Tamanho do texto
		mv a1, a0		# tamanho da string
		mv a0, s0		# endereço da string
		jal insert_After
		j INSERT_a
F_8:		
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		lw s3, 16(sp)
		addi sp, sp, 20
		ret
#-------------------------------------------------------------
# Modifica uma linha
# a0 -> String
# a1 -> length
command_c:
		addi sp, sp, -20
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		sw s3, 16(sp)
		
		li s1, 1					
		beq a1, s1, Else_3	# Verifica se a instrução tem argumentos
		jal get_Line		# length != 1 preciso de saber a linha a que se refere	
		mv a2, a0		# linha obtida em get_Line
		j INSERT_c		
Else_3:					# se for na linha atual
		la s2, current_Line
		lw a2, 0(s2)
		j INSERT_c			
INSERT_c:		
		la a0, text		# Input de texto
		jal gets
		lb s0, 0(a0)
		mv s0, a0		# Guarda o endereço para nao perder
		jal sizeOf		# Tamanho do texto
		mv a1, a0		# tamanho da string
		mv a0, s0		# endereço da string
		jal replace
F_10:		
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		lw s3, 16(sp)
		addi sp, sp, 20
		ret
#-------------------------------------------------------------
# Apaga linhas
# a0 -> String
# a1 -> length
command_d:
		addi sp, sp, -24
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		sw s3, 16(sp)
		sw s4, 20(sp)
		
		li s0, 1	
		mv s4, a1		# Guarda o length original em s4				
		beq a1, s0, Else_4	# Verifica se a instrução tem argumentos
		mv s1, a0		# guarda o endereço da string 
		jal get_Comma		# Verifica se existe uma virgula para saber quantos argumentos tem
		beqz a0, Else_5		# Se for 0 não existe virgula e tem apenas um argumento
		mv s2, a0		# Guarda em s2 o index da virgula
		mv a0, s1		# Volta a colocar o endereço da string em a0
		addi a1, s2, 1		# Guarda o comprimento do primeiro argumento
		jal get_Line		# length != 1 preciso de saber a linha a que se refere	
		mv s3, a0		# Guarda em s3 a linha que começa a apagar
		mv a0, s1		# Volta a colocar o endereço da linha em a0
		add a0, a0, s2		# aponta para o char seguinte á virgula
		addi a0, a0, 1		# Compensa o endereço que aponta para a vrigula
		sub a1, s4, s2		# Passa o tamanho do comando
		addi a1, a1, -1		# Compensa a virgula
		jal get_Line		# descobre até onde deve apagar
		mv a1, a0		# Passa o fim para o segundo argumento
		mv a0, s3		# Passa o inicio para o primeiro argumento
		j Delete		
Else_4:					# se for na linha atual
		la s2, current_Line	# ambos os argumentos sao a linha atual
		lw a1, 0(s2)
		lw a0, 0(s2)
		j Delete
Else_5:					# Tem um argumento 
		mv a0, s1		# Define o primeiro argumento (String)
		mv a1, s4		# Define o segundo argumento (length)
		jal get_Line
		mv a1, a0		# a0 e a1 sao a linha encontrada em get_Line
		j Delete		
Delete:		
		jal clear
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		lw s3, 16(sp)
		lw s4, 20(sp)
		addi sp, sp, 24
		ret
#-------------------------------------------------------------
# Imprime as linhas , p
# a0 -> String
# a1 -> length
command_p:
		addi sp, sp, -28
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		sw s3, 16(sp)
		sw s4, 20(sp)
		sw s5, 24(sp)
		
		li s0, 1	
		li s5, -1		# para comparar com o input de getline
		mv s4, a1		# Guarda o length original em s4				
		beq a1, s0, Else_6	# Verifica se a instrução tem argumentos
		mv s1, a0		# guarda o endereço da string 
		jal get_Comma		# Verifica se existe uma virgula para saber quantos argumentos tem
		beqz a0, Else_7		# Se for 0 não existe virgula e tem apenas um argumento
		mv s2, a0		# Guarda em s2 o index da virgula
		mv a0, s1		# Volta a colocar o endereço da string em a0
		addi a1, s2, 1		# Guarda o comprimento do primeiro argumento
		jal get_Line		# length != 1 preciso de saber a linha a que se refere	
		mv s3, a0		# Guarda em s3 a linha que começa a apagar
		mv a0, s1		# Volta a colocar o endereço da linha em a0
		add a0, a0, s2		# aponta para o char seguinte á virgula
		addi a0, a0, 1		# Compensa o endereço que aponta para a vrigula
		sub a1, s4, s2		# Passa o tamanho do comando
		addi a1, a1, -1		# Compensa a virgula
		jal get_Line		# descobre até onde deve apagar
		bne a0, s5, Else_8	# se for -1 então foi inserido '%' e deve printar todas as linhas
		la s2, n_Lines		# ...
		mv a0, s3
		lw a1, 0(s2)		# até á ultima linha
		j Print
Else_8:					# Se não for inserido '%' da print ate a linha inserida
		mv a1, a0		# Passa o fim para o segundo argumento
		mv a0, s3		# Passa o inicio para o primeiro argumento
		j Print		
Else_6:					# se for na linha atual
		la s2, current_Line	# ambos os argumentos sao a linha atual
		lw a1, 0(s2)
		lw a0, 0(s2)
		j Print
Else_7:					# Tem um argumento 
		mv a0, s1		# Define o primeiro argumento (String)
		mv a1, s4		# Define o segundo argumento (length)
		jal get_Line
		bne a0, s5, Else_9	# se for -1 então foi inserido '%' e deve printar todas as linhas
		li a0, 0		# Imprime da primeira linha ...
		la s2, n_Lines		# ...
		lw a1, 0(s2)		# até á ultima linha
		j Print
Else_9:					# Caso nao seja '%' passa a mesma linha em ambos os argumentos
		mv a1, a0		# a0 e a1 sao a linha encontrada em get_Line
		j Print		
Print:		
		jal Printing
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		lw s3, 16(sp)
		lw s4, 20(sp)
		lw s5, 24(sp)
		addi sp, sp, 28
		ret
#-------------------------------------------------------------	
# Abre um ficheiro existente para edição
command_e:
		addi sp, sp, -16
		sw ra, 0(sp)
		sw s0, 4(sp)
		sw s1, 8(sp)
		sw s2, 12(sp)
		
		la a0, file_Name02
		jal gets
		mv s0, a0		# Guarda o endereço inicial
		jal sizeOf		# Guarda em tamanho do filepath para apagar o "new line"
		li s1, 0		
		add s2, s0, a0		# Aponta para o "newline"
		sb s1, 0(s2)		# Apaga o "newLine"
		li a7, 1024
		li a1, 0		# Read-Only
		mv a0, s0
		ecall
		li a2, 100
		la a1, line
		jal copy		# a0 -> ficheiro, a1 -> buffer, a2 -> length
		
		lw ra, 0(sp)
		lw s0, 4(sp)
		lw s1, 8(sp)
		lw s2, 12(sp)
		addi sp, sp, 16
		ret
#-------------------------------------------------------------	
# Muda o nome do ficheiro
command_f:
		addi sp, sp, -4
		sw ra, 0(sp)
		
		la a0, file_Name
		jal gets
		
		lw ra, 0(sp)
		addi sp, sp, 4
		ret
#-------------------------------------------------------------	
# Cria e escreve no ficheiro
command_w:
		addi sp, sp, -8
		sw ra, 0(sp)
		sw s0, 4(sp)
		
		la a0, file_Name	# carrega o nome do novo ficheiro
		li a1, 1		# write-only flag, cria o ficheiro caso este não exista
		li a7, 1024
		ecall
		mv s0, a0       	# save the file descriptor
		jal write		# Escreve o texto no ficheiro
		li   a7, 57       	# system call for close file
		mv a0, s0
  		ecall            	# close file
  		
  		la s0, saved		# atualiza o estado "saved" para confirmar a saida do programa
  		lb s1, 0(s0)
  		addi s1, s1, 1
  		sb s1, 0(s0)
		
		lw ra, 0(sp)
		lw s0, 4(sp)
		addi sp, sp, 8
		ret
#-------------------------------------------------------------
# Sai do programa se o texto estiver guardado
command_q:
		la s0, saved		# Verifica qual o valor de "saved"
  		lb t1, 0(s0)
  		li t0, 1
  		beq t1, t0, END		# Se estiver guardado acaba o programa
  		ret
#-------------------------------------------------------------	
main:		

WHILE_Main:	
		la a0, command
		jal gets		# scanf
		
		jal sizeOf
		mv t1, a0		# s0 == length
		
		mv a1, a0
		la a0, command	
		jal input_Comand	# verifica qual é a instrução utilizada
		beqz a0, Line		# Se não for nenhuma intrução é para alterar a linha
		
		# Se for uma instrução de inserção
		li t0, 0x69		# t0 = 'i'	
		bne a0, t0, a
		la a0, command
		jal command_i
		j Update		# Salta para o fim e atualiza o main para nao correr tudo de novo
a:
		li t0, 0x61		# t0 = 'a'
		bne a0, t0, c
		la a0, command
		jal command_a
		j Update
c:		
		li t0, 0x63		# t0 = 'c'
		bne a0, t0, d
		la a0, command
		jal command_c
		j Update
d:
		li t0, 0x64		# t0 = 'd'
		bne a0, t0, p
		la a0, command
		jal command_d
		j Update
p:
		li t0, 0x70		# t0 = 'p'
		bne a0, t0, e
		la a0, command
		jal command_p
		j Update
e:
		li t0, 0x65		# t0 = 'e'
		bne a0, t0, f
		jal command_e
		j Update
f:
		li t0, 0x66		# t0 = 'f'
		bne a0, t0, w
		jal command_f
		j Update
w:
		li t0, 0x77		# t0 = 'w'
		bne a0, t0, q
		jal command_w
		j WHILE_Main		# Se acabou de ser guardado nao passa por update para nao alterar o saved
q:
		li t0, 0x71		# t0 = 'q'
		bne a0, t0, Q
		jal command_q
		j Update
Q:					# Acaba sem verificar se está guardado
		j END
Line:					# Muda de linha
		mv a1, t1		# t1, tamanho da string
		addi a1, a1, 1
		la a0, command		
		jal get_Line	
		la t1, current_Line
		sw a0, 0(t1)
Update:
		la t0, saved
		li t1, 0
		sb t1, 0(t0)		# Se o programa não acabou nem acabou de guardar então precisa de ser guardado
					# de novo
		j WHILE_Main		
END:
