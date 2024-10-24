package Server;

import Server.Client.ClientController;
import Server.Client.ClientGUI;
import Server.Repository.FileStorage;
import Server.Server.ServerController;
import Server.Server.ServerWindow;

public class Main {

    public static void main(String[] args) {
        ServerController serverController = new ServerController(new ServerWindow(), new FileStorage());
        new ClientController(new ClientGUI(), serverController);
        new ClientController(new ClientGUI(), serverController);

    }
}
