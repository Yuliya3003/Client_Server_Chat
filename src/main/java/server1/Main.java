package server1;

import server1.client.ClientController;
import server1.client.ClientGUI;
import server1.server.ServerController;
import server1.server.repository.ServerRepository;
import server1.server.view.ServerWindow;


public class Main {
    public static void main(String[] args) {
        ServerController serverController = new ServerController(new ServerWindow(), new ServerRepository());

        new ClientController(new ClientGUI(), serverController);
        new ClientController(new ClientGUI(), serverController);
    }
}
