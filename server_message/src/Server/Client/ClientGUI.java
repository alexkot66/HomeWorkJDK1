package Server.Client;

import Server.Server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientGUI extends JFrame implements ClientView{


    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();

    private ClientController clientController;

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

    public ClientGUI() {

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
                disconnectClient();
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
        if(clientController.connectToServer(tfLogin.getText())){
            panelTop.setVisible(false);
        }

    }

    @Override
    public void showMessage(String message) {
        log.append(message);
    }

    @Override
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void disconnectServer(){
        panelTop.setVisible(true);
    }

    public void disconnectClient(){
        clientController.disconnectClient();
    }

    public void message(){
        clientController.message(tfMessage.getText());
        tfMessage.setText("");
    }



}
