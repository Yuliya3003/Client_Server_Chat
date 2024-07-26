package server1;

import server1.client.ClientGUI;
import server1.server.ServerWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        new ClientGUI(serverWindow, "artem");
        new ClientGUI(serverWindow, "yuliya");
    }
}
