import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


// Servidor é do tipo thread e vai gerar um thread por cliente
public class Servidor_01 extends Thread{

    private static ArrayList<BufferedWriter>clientes;
    private static ArrayList<String>clientes_Nome = new ArrayList<String>();                                 // lista com os nomes dos clientes
    private static ArrayList<String>clientes_Off = new ArrayList<String>();                                  // Lista de clientes Ofline
    private static ArrayList< ArrayList<String> > off_mens = new ArrayList< ArrayList<String> >();           // Lista de mensagens offline
    private static ArrayList< ArrayList<String> > off_files = new ArrayList< ArrayList<String> >();          // Lista de files offline

    private static ArrayList< int[] > tabuleiro = new ArrayList< int[] >();                                  // Guarda os jogos a decorrer
    private static ArrayList<Integer> in_Game = new ArrayList<Integer>();                                    // Lista de clientes que estam a jogar

    byte[] bytes = new byte[16*1024];                                                                        // Buffer para receber ficheiros

    private static ServerSocket server;
    private String nome;
    private Socket con;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;


    /**
    * Método construtor
    * @param com do tipo Socket
    */
    public Servidor_01(Socket con){

        this.con = con;

        try {
            in  = con.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Método run
    */
    public void run(){

        try{
            String msg;
            OutputStream ou =  this.con.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw);
            clientes.add(bfw);
            nome = msg = bfr.readLine();
            clientes_Nome.add(nome);                                
            in_Game.add(-1);                                             // Adiciona -1 ao status do cliente ingame, (não está em jogo nenhum)

            if(clientes_Off.size() != 0) is_Offline(nome);
            sendToAll(bfw, "Online");
  
            while(!"Desconectado".equalsIgnoreCase(msg) && msg != null){
                System.out.println("LOOP RUN");

                if(msg.charAt(0) == '/' ){                                                      // Se começar em "/" é uma mensagem privada

                    //Se for /p é uma mensagem privada
                    if(msg.charAt(1) == 'p'){
                        
                        msg = msg.substring(3, msg.length());
                        String cliente_mp = msg.substring(0, msg.indexOf(" "));                     // cliente_mp -> nome do cliente 
                        String msg_one = msg.substring(msg.indexOf(" "), msg.length() );            // msg_one é a mensagem enviada 
                        System.out.println(cliente_mp);
                        System.out.println("PRIVATE MSG");

                        for( String n : clientes_Nome ){

                            if( n.equals(cliente_mp)){
                                System.out.println("Cliente..." + n);
                                int i = clientes_Nome.indexOf(n);
                                BufferedWriter bw = clientes.get(i);
                                sendToOne(msg_one, bw);
                            }
                        }

                        for( String n : clientes_Off){

                            if( n.equals(cliente_mp) ){
                                int i = clientes_Off.indexOf(n);
                                msg_one = this.nome + " enviou -> " + msg_one;
                                off_mens.get(i).add(msg_one);
                                System.out.println("CLIENTE off enviado");
                            }
                        }
                    msg = " ";

                    // Se o comando inserido for /g começa um jogo
                    }else if(msg.charAt(1) == 'g'){

                        msg = msg.substring(3, msg.length());
                        String cliente_mp = msg.substring(0, msg.indexOf(" "));                             // cliente_mp -> nome do cliente

                        // Verifica se os dois jogadores estam disponiveis para jogar
                        int i = clientes_Nome.indexOf(cliente_mp);                                          // index do jogador a convidar
                        int ci = clientes_Nome.indexOf(this.nome);                                          // index do jogador que convida
                        System.out.println("I: " + in_Game.get(i) + "CI: " + in_Game.get(ci) );

                        // Se ambos estiverem disponiveis para começar um jogo
                        if( in_Game.get(i) == -1 && in_Game.get(ci) == -1){
                            novo_jogo(ci, i);
                            sendToOne("Inicio de jogo", clientes.get(i));

                        // Se um deles estiver a jogar, ou ambos mas em jogos diferentes
                        }else if( in_Game.get(i) != in_Game.get(ci)) {

                            System.out.println("OCUPADO");
                            
                        }else{

                            String jogada = msg.substring(msg.indexOf(" ") + 1, msg.length() );                 // contem a jogada do jogador
                            int j = Integer.parseInt(jogada);

                            // Verifica se estão os dois a jogar o mesmo jogo
                            if( in_Game.get(i) == in_Game.get(ci) ) {
                                System.out.println("Mesmo jogo");
                                int stat = play(j, clientes.get(ci), i);    
                                // Se alguém ganhar termina o jogo e atualiza todos os dados 
                                if(stat == 1){
                                    System.out.println("ATT do jogo");
                                    tabuleiro.remove(tabuleiro.get( in_Game.get(ci)));
                                    in_Game.set(i, -1);
                                    in_Game.set(ci, -1);      
                                    clientes.get(i).write("Perdeu\n");
                                    clientes.get(i).flush();

                                }                           
                            }
                        }

                    // Se for '/f' é um ficheiro
                    }else if(msg.charAt(1) == 'f'){
                        System.out.println("F COMMAND");
                        receive_file(bfw);

                        // Se o ficheiro for para todos será "all"
                        String nome_to_send = msg.substring(msg.indexOf(" ") +1 , msg.length());
                        if(nome_to_send.equals("all")){
                            System.out.println("ENTROU");
                            // Eniva o ficheiro para todos os clientes
                            for(String n: clientes_Nome){
                                if(n != nome) send_file(n);
                            }
                        }else{
                            for(String n : clientes_Nome){
                                if(n == nome_to_send) send_file(n);
                            } 
                        }
                        msg = " ";
                        bfr.readLine();
                    }

                }else{
                    msg = bfr.readLine();
                    if(msg.charAt(0) != '/' ) sendToAll(bfw, msg);                  // Verifica de novo antes de enviar a mensagem se é mp
                    System.out.println("DAQUI " + msg);
                }

            }

            System.out.println("CLOSE");
            int i_out = clientes_Nome.indexOf(this.nome);                               // Index do cliente a desconectar
            clientes_Nome.remove(this.nome);                                            // Remove o cliente dos clientes on
            BufferedWriter bfw_out = clientes.get(i_out);                               // Procura o buffer do cliente para não tentar enviar mais mens
            clientes.remove(bfw_out);                                                   // Remove também o buffer
            System.out.println("Cliente " + this.nome + " saiu, buffer: " + bfr);       // Cliente a desligar
            this.con.close();
            clientes_Off.add(this.nome);
            off_mens.add(new ArrayList<String>());

        }catch (Exception e) {
        e.printStackTrace();
        }
    }

    /***
     * Envia o ficheiro guardado pelo servido ao cliente
     * @param nome nome do cliente a enviar o ficheiro
     * @throws IOException
     * @throws InterruptedException
     */
    public void send_file(String nome) throws IOException, InterruptedException{
        
        BufferedWriter bfw_send = clientes.get(clientes_Nome.indexOf(nome));
        bfw_send.write("/f\r\n");
        bfw_send.flush();
        Socket socket_file = null;
        String host = "127.0.0.1";

        for(int t = 0; t < 100; t++);
        socket_file = new Socket(host, 4444);
        
        File file = new File("///home/joao/Documentos/Server_38156");                       //SERVER FILE OUTPUT
        byte[] bytes = new byte[16 * 1024];
        InputStream in_file = new FileInputStream(file);
        OutputStream out_file = socket_file.getOutputStream();
        
        int count;
        while ((count = in_file.read(bytes)) > 0) {
            out_file.write(bytes, 0, count);
        }

        out_file.close();
        in_file.close();
        socket_file.close();
        System.out.println("Enviado..." + nome);   
    }

    /***
     * Recebe um ficheiro do cliente e guarda no BACKUP de ficheiros do servidor
     * @param bfw Buffer do cliente a enviar feedback para quando puder receber o ficheiro
     * @throws IOException
     */
    public static void receive_file(BufferedWriter bfw) throws IOException{
        ServerSocket serverSocket_file = null;
        serverSocket_file = new ServerSocket(4444);

        Socket socket_file = null;
        InputStream in_file = null;
        OutputStream out_file = null;

        while(socket_file == null){
            System.out.println("Aguardando_file conexão...");
            bfw.write("OK");
            bfw.flush();
            socket_file = serverSocket_file.accept();
            System.out.println("Cliente_file conectado...");
        }

        in_file = socket_file.getInputStream();
        out_file = new FileOutputStream("///home/joao/Documentos/Server_38156");        // SERVER FILE BACKUP
        
        // Get the size of the file
        byte[] bytes = new byte[16 * 1024];                                             
        
        int count;
        while ((count = in_file.read(bytes)) > 0) {
            out_file.write(bytes, 0, count);
        }

        out_file.close();
        in_file.close();
        socket_file.close(); 
        serverSocket_file.close();
    }

    /***
     * Analisa a jogada do cliente
     * @param j jogada pretendida
     * @param bw buffer do jogador para dar feedback
     * @param i index do cliente a enviar feedback
     * @return tabuleiro de jogo atualizado
     */
    public static int play(int j, BufferedWriter bw, int i) throws  IOException{

        int[] tab =  tabuleiro.get( in_Game.get(i) );

        // Se a casa pretendida já está ocupada
        if( tab[j] == 1  || tab[j] == -1 ){
            bw.write("Escolha outra casa\n");
            bw.flush();
        }else{
            tab[j] = tab[9];
            tab[9] *= -1;
            print_Game(in_Game.get(i), clientes.get(i));
            if(fim(tab)){
                System.out.println("Acabou o jogo");
                bw.write("Venceu\n");
                bw.flush();
                return 1;
            }
        }

        return 0;
    }

    /**
     * Verifica se o jogo terminou ou não
     * @param tabuleiro tabuleiro que deve ser analisado
     * @return  true se o jogo acabar 
     */
    public static boolean fim(int[] tabuleiro){

        // Verifica se exister 3 iguais em linha
        if((tabuleiro[0] == 1 && tabuleiro[4] == 1 && tabuleiro[8] == 1) ||
          (tabuleiro[6] == 1 && tabuleiro[4] == 1 && tabuleiro[2] == 1) || 
          (tabuleiro[0] == 1 && tabuleiro[3] == 1 && tabuleiro[6] == 1) ||
          (tabuleiro[1] == 1 && tabuleiro[4] == 1 && tabuleiro[7] == 1) ||
          (tabuleiro[2] == 1 && tabuleiro[5] == 1 && tabuleiro[8] == 1) ||
          (tabuleiro[0] == 1 && tabuleiro[1] == 1 && tabuleiro[2] == 1) ||
          (tabuleiro[3] == 1 && tabuleiro[4] == 1 && tabuleiro[5] == 1) ||
          (tabuleiro[6] == 1 && tabuleiro[7] == 1 && tabuleiro[8] == 1)){
    
          return true;
    
        }else if((tabuleiro[0] == -1 && tabuleiro[4] == -1 && tabuleiro[8] == -1) ||
                (tabuleiro[6] == -1 && tabuleiro[4] == -1 && tabuleiro[2] == -1) || 
                (tabuleiro[0] == -1 && tabuleiro[3] == -1 && tabuleiro[6] == -1) ||
                (tabuleiro[1] == -1 && tabuleiro[4] == -1 && tabuleiro[7] == -1) ||
                (tabuleiro[2] == -1 && tabuleiro[5] == -1 && tabuleiro[8] == -1) ||
                (tabuleiro[0] == -1 && tabuleiro[1] == -1 && tabuleiro[2] == -1) ||
                (tabuleiro[3] == -1 && tabuleiro[4] == -1 && tabuleiro[5] == -1) ||
                (tabuleiro[6] == -1 && tabuleiro[7] == -1 && tabuleiro[8] == -1)){
            
            return true;
    
        }else{
          return false;
        }
      }

    /***
     * Cria um novo jogo do galo
     * @param c1_i index do primeiro cliente
     * @param c2_i index do segundo cliente
     */
    public static void novo_jogo(int c1_i, int c2_i){
    
        int[] tab = new int[10];

        for(int i = 0; i < tab.length; i++){
            tab[i] = 0;
        }
        tab[9] = 1;                                     // Corresponde a 'x', o primeiro a jogar

        tabuleiro.add(tab);
        in_Game.set(c1_i, tabuleiro.size() - 1);        //Ambos ficam representados pelo index do seu jogo
        in_Game.set(c2_i, tabuleiro.size() - 1);
    }

    /***
     * Cada vez que é chamado imprime o jogo para o cliente
     * @param i index do jogo do cliente
     * @param bw buffer do cliente
     */
    public static void print_Game(int i, BufferedWriter bw) throws  IOException{

        int c = 0;
        int l = 3;      // linha

        // Percorre as colunas do jogo
        for(int t = 0; t < 3; t++){
            // Percorre as linhas do jogo
            while(c < l){

                int out = tabuleiro.get(i)[c];

                switch(out){
                    case -1:
                        bw.write("o  ");
                        break;
                    case 0:
                        bw.write("-  ");
                        break;
                    case 1:
                        bw.write("x  ");
                        break;
                    default:
                        bw.write("ERRO");
                }
                bw.flush();
                c++;
            }
            l += 3;            // Nova linha
            bw.write("\n");
            bw.flush();
        }
    }

    /***
     * Verifica se o cliente conectado já está registado e se tem mensagens por receber
     * @param nome  Nome do cliente conectado
     */
    public void is_Offline(String nome) throws  IOException{

        System.out.println("OFFLINE???");

        for(String n : clientes_Off){

            if(n.equals(nome)){
                System.out.println("ESTAVA OFFLINE PRECISA REPOR MSG...");
                int i = clientes_Off.indexOf(nome);                                     // Index do cliente que estava offline
                int ci = clientes_Nome.indexOf(nome);                                   // Index do cliente que agora está onine
                for(String ms : off_mens.get(i)){
                    send_OffMsg( ms, clientes.get(ci), this.nome);
                }
                off_mens.remove(off_mens.get(i));                                       // Limpa as mensagens enviadas ao cliente
            }
        }
        clientes_Off.remove(nome);  
    }

    /***
    * Método usado para enviar mensagem para todos os clients
    * @param bwSaida do tipo BufferedWriter
    * @param msg do tipo String
    * @throws IOException
    */
    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException{
    
        BufferedWriter bwS;

        for(BufferedWriter bw : clientes){

            bwS = (BufferedWriter)bw;
            System.out.println(bw);
            if(!(bwSaida == bwS)){
                System.out.println(nome + " ALL -> " + msg+"\r\n" + "PARA" + bw);
                bw.write(nome + " -> " + msg+"\r\n");
                bw.flush();
            }
        }
    }

    /***
    * Método usado para enviar mensagem para um cliente que estava offline
    * @param bwSaida do tipo BufferedWriter
    * @param msg do tipo String
    * @param nome_c cliente que tentou enviar mensagem
    * @throws IOException
    */
    public void send_OffMsg(String msg, BufferedWriter bw, String nome_c) throws  IOException{
    
        bw.write(msg + "\r\n");
        System.out.println("MESAGEM OFFLINE ENVIADA: \n" + msg + "\r\n");
        bw.flush();
    }

    /***
    * Método usado para enviar mensagem para um cliente
    * @param bwSaida do tipo BufferedWriter
    * @param msg do tipo String
    * @throws IOException
    */
    public void sendToOne(String msg, BufferedWriter bw) throws  IOException{
    
        bw.write(nome + " ,mp -> " + msg + "\r\n");
        System.out.println(nome + " ,mp -> " + msg + "\r\n");
        bw.flush();
    }

    /***
    * Método main
    * @param args
    */
    public static void main(String []args) {

        try{
            //Cria os objetos necessário para instânciar o servidor
            JLabel lblMessage = new JLabel("Porta do Servidor:");
            JTextField txtPorta = new JTextField("12345");
            Object[] texts = {lblMessage, txtPorta };
            JOptionPane.showMessageDialog(null, texts);
            server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
            clientes = new ArrayList<BufferedWriter>();
            JOptionPane.showMessageDialog(null,"Servidor ativo na porta: "+
            txtPorta.getText());
    
            while(true){
                System.out.println("Aguardando conexão...");
                Socket con = server.accept();
                System.out.println("Cliente conectado...");
                Thread t = new Servidor_01(con);
                t.start();
            }
  
        }catch (Exception e) {
  
            e.printStackTrace();
        }
    }// Fim do método main
}