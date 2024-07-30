package server1.server;

import server1.client.ClientController;
import server1.server.repository.Repository;
import server1.server.view.ServerView;
import server1.server.view.ServerWindow;


import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private boolean work;
    private ServerView serverView;
    private List<ClientController> clientControllerList;
    private Repository repository;

    public ServerController(ServerView serverView, Repository repository) {
        this.serverView = serverView;
        this.repository = repository;
        clientControllerList = new ArrayList<>();
        serverView.setServerController(this);
    }


    public void start(){
        if (work){
            appendLog("Сервер уже был запущен");
        } else {
            work = true;
            appendLog("Сервер запущен!");
        }
    }

    public void stop(){
        if (!work){
            appendLog("Сервер уже был остановлен");
        } else {
            work = false;
            while (!clientControllerList.isEmpty()){
                disconnectUser(clientControllerList.get(clientControllerList.size() - 1));
            }
            appendLog("Сервер остановлен!");
        }
    }

    public String getLog() {
        return repository.read();
    }
    public void message(String text){
        if (!work){
            return;
        }
        appendLog(text);
        answerAll(text);
        saveInLog(text);
    }

    private void appendLog(String text) {
        serverView.showMessage(text + "\n");
    }

    private void saveInLog(String text) {
        repository.save(text);
    }

    private void answerAll(String text) {
        for (ClientController clientController: clientControllerList){
            clientController.answerFromServer(text);
        }
    }

    public void disconnectUser(ClientController clientController){
        clientControllerList.remove(clientController);
        if (clientController != null){
            clientController.disconnectedFromServer();
        }
    }

    public boolean connectUser(ClientController clientController){
        if (!work){
            return false;
        }
        clientControllerList.add(clientController);
        return true;
    }


    public void setServerView(ServerWindow serverWindow) {
        this.serverView = serverWindow;
    }
}
