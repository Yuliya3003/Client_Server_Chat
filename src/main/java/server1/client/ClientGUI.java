package server1.client;

import server1.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2,3));
    private final JTextField tfIPAdress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8080");
    private final JTextField tfLogin;
    private final JPasswordField tfPassword = new JPasswordField("1232312");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JList<String> userList = new JList<>();

    private final String logFile = "chat_log.txt";

    private ServerWindow serverWindow;

    public ClientGUI(ServerWindow serverWindow, String tfLogin1){
        this.serverWindow = serverWindow;
        this.tfLogin = new JTextField(tfLogin1);
        serverWindow.registerClient(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH,HEIGHT);
        setTitle("Chat Client");

        panelTop.add(tfIPAdress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        log.setEditable(false);
        JScrollPane scrolllog = new JScrollPane(log);
        add(scrolllog, BorderLayout.CENTER);

        String[] users = {"User1", "User2", "User3"};
        userList.setListData(users);
        JScrollPane scrollUserList = new JScrollPane(userList);
        add(scrollUserList, BorderLayout.EAST);

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        setVisible(true);
        loadLog();

    }

    private void loadLog() {
        File file = new File(logFile);
        if (file.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))){
                String line;
                while((line = reader.readLine()) != null){
                    log.append(line+ "\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage() {
        String message = tfMessage.getText();
        if(!message.isEmpty()){
            appendToLogFile(message);
            serverWindow.updateClientsLog(getLogin().getText(), message);
            tfMessage.setText("");
        }
    }

    private void appendToLogFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateLog(String message) {
        log.append(message + "\n");
    }

    public JTextField getLogin() {
        return tfLogin;
    }
}
