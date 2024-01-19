import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.*;

public class Cliente_01 extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private JTextArea texto;
    private JTextField txtMsg;
    private JButton btnSend;
    private JButton btnSair;
    private JLabel lblHistorico;
    private JLabel lblMsg;
    private JPanel pnlContent;
    private Socket socket;
    private OutputStream ou ;
    private Writer ouw;
    private BufferedWriter bfw;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JTextField txtNome;

    InputStream in;
    InputStreamReader inr;
    BufferedReader bfr;

    public Cliente_01() throws IOException{
        JLabel lblMessage = new JLabel("Verificar!");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("12345");
        txtNome = new JTextField("Cliente");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome };
        JOptionPane.showMessageDialog(null, texts);
        pnlContent = new JPanel();
        texto = new JTextArea(10,20);
        texto.setEditable(false);
        texto.setBackground(new Color(240,240,240));
        txtMsg = new JTextField(20);
        lblHistorico = new JLabel("Histórico");
        lblMsg = new JLabel("Mensagem");
        btnSend = new JButton("Enviar");
        btnSend.setToolTipText("Enviar Mensagem");
        btnSair = new JButton("Sair");
        btnSair.setToolTipText("Sair do Chat");
        btnSend.addActionListener(this);
        btnSair.addActionListener(this);
        btnSend.addKeyListener(this);
        txtMsg.addKeyListener(this);
        JScrollPane scroll = new JScrollPane(texto);
        texto.setLineWrap(true);
        pnlContent.add(lblHistorico);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(txtMsg);
        pnlContent.add(btnSair);
        pnlContent.add(btnSend);
        pnlContent.setBackground(Color.LIGHT_GRAY);
        texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE));
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        setTitle(txtNome.getText());
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(250,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        
    }


    /***
    * Método usado para conectar no server socket, retorna IO Exception caso dê algum erro.
    * @throws IOException
    */
    public void conectar() throws IOException{

        socket = new Socket(txtIP.getText(),Integer.parseInt(txtPorta.getText()));
        in = socket.getInputStream();
        inr = new InputStreamReader(in);
        bfr = new BufferedReader(inr);

        ou = socket.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        bfw.write(txtNome.getText()+"\r\n");
        bfw.flush();
    }

    /***
     * Método usado para enviar mensagem para o server socket
    * @param msg do tipo String
    * @throws IOException retorna IO Exception caso dê algum erro.
    */
    public void enviarMensagem(String msg) throws IOException{

        if(msg.equals("Sair")){
            bfw.write("Desconectado");
            texto.append("Desconectado \r\n");

        // Envia um ficheiro
        }else if(msg.charAt(0) == '/' && msg.charAt(1) == 'f'){
            
            String d = msg.substring(3, msg.length());
            String pathfile = d.substring(d.indexOf(" ") + 1, d.length());
            d = d.substring(0, d.indexOf(" "));
            bfw.write("/f " + d + "\r\n");
            bfw.flush();
            Socket socket_file = null;
            String host = "127.0.0.1";

            while(!bfr.ready()){
                System.out.println("NOT READY");
            }
            socket_file = new Socket(host, 4444);
            
            File file = new File("///home/joao/Desktop/" + pathfile);                       //CLIENT FILE INPUT
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
            System.out.println("Ficheiro Enviado");
            
        }else{
            bfw.write(msg+"\r\n");
            texto.append( txtNome.getText() + " diz -> " + txtMsg.getText()+"\r\n");
        }

        bfw.flush();
        txtMsg.setText("");
    }



    /**
    * Método usado para receber mensagem do servidor
    * @throws IOException retorna IO Exception caso dê algum erro.
    */
    public void escutar() throws IOException{

        String msg = "";
 
        while(!"Sair".equalsIgnoreCase(msg)){
 
            if(bfr.ready()){
                
                msg = bfr.readLine();
                System.out.println("MENSAGEM " + msg);

                if(msg.equals("Sair")){
                    texto.append("Servidor caiu! \r\n");

                }else if(msg.charAt(0) == '/' && msg.charAt(1) == 'f'){
                    System.out.println("A Receber file");
                    receive_file();
                    
                }else{
                    texto.append(msg+"\r\n");
                }
            }
        }
    }

    /***
     * Recebe um ficheiro do cliente e guarda no BACKUP de ficheiros do servidor
     * @param bfw Buffer do cliente a enviar feedback para quando puder receber o ficheiro
     * @throws IOException
     */
    public void receive_file() throws IOException{
        ServerSocket serverSocket_file = null;
        serverSocket_file = new ServerSocket(4444);

        Socket socket_file = null;
        InputStream in_file = null;
        OutputStream out_file = null;

        while(socket_file == null){
            System.out.println("Aguardando conexão..2222.");
            bfw.write("OK");
            bfw.flush();
            socket_file = serverSocket_file.accept();
            System.out.println("Cliente conectado.2222..");
        }
        //socket_file = serverSocket_file.accept();
        in_file = socket_file.getInputStream();
        out_file = new FileOutputStream("///home/joao/Documentos/Cliente_38156" + txtNome.getText());        // Output Cliente
        
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
    * Método usado quando o usuário clica em sair
    * @throws IOException retorna IO Exception caso dê algum erro.
    */
    public void sair() throws IOException{

        enviarMensagem("Sair");
        bfw.close();
        ouw.close();
        ou.close();
        socket.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if(e.getActionCommand().equals(btnSend.getActionCommand())){
                enviarMensagem(txtMsg.getText());
            }else{
                if(e.getActionCommand().equals(btnSair.getActionCommand())){
                    sair();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                enviarMensagem(txtMsg.getText());
            }catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    public static void main(String []args) throws IOException{
        Cliente_01 app = new Cliente_01();
        app.conectar();
        app.escutar();
    }
}