package Server.Client;

public interface ClientView {

    void showMessage(String message);
    void disconnectServer();
    void setClientController(ClientController clientController);
}
