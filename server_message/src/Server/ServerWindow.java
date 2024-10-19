package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    public static final String PATH_LOG = "src/Server/log.txt";
    private final JPanel panelBottom = new JPanel(new GridLayout(1,2));
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextArea log = new JTextArea();
    private boolean isServerWorking;

    public List <ClientGUI> clientGUIList;

    public ServerWindow() {
        isServerWorking = false;
        clientGUIList = new ArrayList<>();


        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking) {
                    logger("Server is already stopped " + isServerWorking + "\n");
                } else {
                    isServerWorking = false;
                    while (!clientGUIList.isEmpty()){
                        disconnectClient(clientGUIList.get(clientGUIList.size() - 1));
                    }
                    logger("Server stopped " + isServerWorking + "\n");
                }
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking){
                   logger("Server is already started " + isServerWorking + "\n");
                } else {
                    isServerWorking = true;
                    logger("Server started " + isServerWorking + "\n");
                };

            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        //setLayout(new GridLayout(1,2));
        panelBottom.add(btnStart);
        panelBottom.add(btnStop);
        add(log);
        add(panelBottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    public boolean connectClient (ClientGUI clientGUI){
        if(!isServerWorking){
            return false;
        }
        clientGUIList.add(clientGUI);
        return true;
    }

    public void disconnectClient(ClientGUI clientGUI){
        clientGUIList.remove(clientGUI);
        if (clientGUI != null) {
            clientGUI.disconnectServer();
        }
    }

    private void logger(String text){
        log.append(text + "\n");
    }

    public void message(String text){
        if(!isServerWorking){
            return;
        }
        logger(text);
        answerClients(text);
        saveLog(text);
    }

    private void answerClients(String text) {
        for (ClientGUI clientGUI: clientGUIList){
            clientGUI.answer(text);
        }
    }

    private void saveLog(String text){
        try (FileWriter writer = new FileWriter(PATH_LOG, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String readLog(){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_LOG))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Чтобы видеть в другом месте метод для чтения

    public String getLog(){
        return readLog();
    }

}
