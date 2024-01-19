-------- Abertura do Servidor -----------------

Janela do servido deve ser introduzido a porta a abrir

-------- Abertura do cliente ------------------

Janela irá ter três campos
1-> ip do host
2-> porta do host
3-> nome do cliente

--------- Comandos ----------------------------

/p <nome de cliente> <mensagem>
 * Envia uma mensagem privada ao cliente
 
/g <Cliente> + " " 
* Deve ser inserido um espaço no fim, irá pedir ao cliente para começar um jogo

/g <CLiente> <Jogada>
* Irá enviar a jogada em questão para o jogador

/f <CLiente> <Ficheiro>
* Irá enviar o para o cliente o ficheiro em questão
** <Cliente> o argumento "all" irá enviar o ficheiro para todos os clientes
** O pathfile tanto do cliente como do servidor deverá ser verificado e alterado
para que possa ser válido

---------- Desligar ----------------------------

Para sair o cliente deve sempre clicar em desligar ou enviar a mensagem "sair",
para ser devidamente desligado
