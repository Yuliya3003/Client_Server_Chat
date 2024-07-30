package server1.server.view;

import server1.server.ServerController;

public interface ServerView {
    void showMessage(String text);

    void setServerController(ServerController serverController);

}
