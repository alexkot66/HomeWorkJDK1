package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientGUI extends JFrame {

    private ServerWindow server;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private boolean connect;
    private String nameClient;

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2,3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField("Aleks");
    private final JPasswordField tfPassword = new JPasswordField("12345678");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JButton btnDisconnect = new JButton("Disconnect");

    ClientGUI(ServerWindow server) {

        this.server = server;
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");

        createPanelForClient();
        setVisible(true);
    }

    private void createPanelForClient() {
        createPanelTop();
        createPanelBottom();

    }

    private void createPanelBottom() {
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    message();
                }
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });

        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnectServer();
            }
        });

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void createPanelTop() {

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(log));
    }

    public void connectToServer() {
        if(server.connectClient(this)){
            logger("Подключение выполнено");
            connect = true;
            nameClient = tfLogin.getText();
            String logClient = server.getLog();
            if (logClient != null) {
                logger(logClient);
            }
            panelTop.setVisible(false);
        } else {
            logger("Подключение не выполнено");
        }

    }

    public void disconnectServer() {
        if (connect) {
            panelTop.setVisible(true);
            connect = false;
            server.disconnectClient(this);
            logger("Отключен от сервера");
        }

    }

    private void logger(String text){
        log.append(text + "\n");
    }

    public void answer (String text){
        logger(text);
    }

    public void message(){
        if(connect){
            String messageClient = tfMessage.getText();
            if(!messageClient.isEmpty()){
                server.message(nameClient + ": " + messageClient);
                tfMessage.setText("");
            } else {
                logger("Отсутствует подключение к серверу");
            }
        }
    }



}
