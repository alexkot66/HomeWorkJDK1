package Server.Client;

import Server.Server.ServerController;

public class ClientController {

    private boolean connect;
    private String nameClient;
    private ClientView clientView;
    private ServerController serverController;

    public ClientController(ClientView clientView, ServerController serverController) {
        this.clientView = clientView;
        this.serverController = serverController;
        clientView.setClientController(this);
    }

    public boolean connectToServer(String nameClient) {
        if(serverController.connectClient(this)){
            showMessage("Подключение выполнено");
            connect = true;
            this.nameClient = nameClient;
            String logClient = serverController.getLog();
            if (logClient != null) {
                showMessage(logClient);
            }
            return true;
        } else {
            showMessage("Подключение не выполнено");
            return false;
        }

    }

    public void disconnectServer() {
        if (connect) {
            connect = false;
            clientView.disconnectServer();
            showMessage("Отключен от сервера");
        }
    }

    public void disconnectClient(){
        serverController.disconnectClient(this);
    }

    public String getName() {
        return nameClient;
    }

    public void answerServer(String text){
        showMessage(text);
    }

    public void message(String messageClient){
        if(connect){
            if(!messageClient.isEmpty()){
                serverController.message(nameClient + ": " + messageClient);

            } else {
                showMessage("Отсутствует подключение к серверу");
            }
        }
    }

    private void showMessage(String text){
        clientView.showMessage(text + "\n");
    }
}
