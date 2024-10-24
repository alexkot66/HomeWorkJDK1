package Server.Server;

import Server.Client.ClientController;
import Server.Client.ClientGUI;
import Server.Repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private ServerView serverView;
    private boolean isServerWorking;
    private List<ClientController> clientControllerList;
    private Repository<String> repository;

    public ServerController(ServerView serverView, Repository<String> repository) {
        this.serverView = serverView;
        this.repository = repository;
        clientControllerList = new ArrayList<>();
        serverView.setServerController(this);
    }

    public void start(){
        if(isServerWorking){
            showMessage("Server is already started " + isServerWorking + "\n");
        } else {
            isServerWorking = true;
            showMessage("Server started " + isServerWorking + "\n");
        }
    }

    public void stop(){
        if (!isServerWorking) {
            showMessage("Server is already stopped " + isServerWorking + "\n");
        } else {
            isServerWorking = false;
            while (!clientControllerList.isEmpty()){
                disconnectClient(clientControllerList.get(clientControllerList.size() - 1));
            }
            showMessage("Server stopped " + isServerWorking + "\n");
        }
    }

    public boolean connectClient (ClientController clientController){
        if(!isServerWorking){
            return false;
        }
        clientControllerList.add(clientController);
        return true;
    }

    public void disconnectClient(ClientController clientController){
        clientControllerList.remove(clientController);
        if (clientController != null) {
            clientController.disconnectServer();
        }
    }

    public void message(String text){
        if(!isServerWorking){
            return;
        }
        showMessage(text);
        answerClients(text);
        saveLog(text);
    }

    private void showMessage(String text){
        serverView.showMessage(text + "\n");
    }

    private void answerClients(String text) {
        for (ClientController clientController: clientControllerList){
            clientController.answerServer(text);
        }
    }

    private void saveLog(String text){
        repository.save(text);
    }

    public String getLog(){
        return repository.load();
    }
}
