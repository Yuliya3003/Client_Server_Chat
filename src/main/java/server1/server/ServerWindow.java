package server1.server;

import server1.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextArea log = new JTextArea();
    private boolean isServerWorking;
    private static final List<ClientGUI> clients = new ArrayList<ClientGUI>();

    public ServerWindow() {
        isServerWorking = false;

        log.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(log);


        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isServerWorking){
                    log.append("Server don't working\n");
                    return;
                }
                isServerWorking = false;
                log.append("Server stopped " + isServerWorking+ "\n");
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking) {
                    log.append("Server is already working\n");
                    return;
                }
                isServerWorking = true;
                log.append("Server started "+ isServerWorking+ "\n");
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(1,2));
        panel.add(btnStart);
        panel.add(btnStop);
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void registerClient(ClientGUI client) {
        clients.add(client);
    }

    public void updateClientsLog(String login, String message) {
        if (!isServerWorking) {
            log.append("Server is not running\n");
            return;
        }
        log.append(login+ " отправил(а) сообщение\n");
        for (ClientGUI client : clients) {
            client.updateLog(login+ ": "+ message);
        }
    }

}
