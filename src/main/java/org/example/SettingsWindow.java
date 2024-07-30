package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;

    GameWindow gameWindow;

    JButton btnStart = new JButton("Start new game");
    JSlider sizeF = new JSlider(3, 10);
    JSlider sizeW = new JSlider(3, 10);
    JRadioButton btn1 = new JRadioButton("Человек против компьютера");
    JRadioButton btn2 = new JRadioButton("Человек против человека");

    SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel settings = new JPanel(new GridLayout(3, 1));

        JPanel typeGame = new JPanel(new GridLayout(3, 1));
        typeGame.add(new JLabel("Выберите режим игры"));
        ButtonGroup group1 = new ButtonGroup();

        btn1.setSelected(true);

        group1.add(btn1);
        group1.add(btn2);
        typeGame.add(btn1);
        typeGame.add(btn2);

        JPanel sizeWin = new JPanel(new GridLayout(3, 1));
        sizeWin.add(new JLabel("Выберите длину для победы"));
        JLabel currentWin = new JLabel("Установленная длина: ");
        sizeWin.add(currentWin);

        sizeW.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int x = sizeW.getValue();
                currentWin.setText("Установленная длина: " + x);
            }
        });

        sizeWin.add(sizeW);

        JPanel sizeField = new JPanel(new GridLayout(3, 1));
        sizeField.add(new JLabel("Выберите размер поля"));
        JLabel currentSize = new JLabel("Выбранный размер поля: ");
        sizeField.add(currentSize);

        sizeF.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int size = sizeF.getValue();
                currentSize.setText("Выбранный размер поля: " + size);
                sizeW.setMaximum(size);
            }
        });

        sizeField.add(sizeF);

        settings.add(typeGame);
        settings.add(sizeField);
        settings.add(sizeWin);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        add(settings);
        add(btnStart, BorderLayout.SOUTH);
    }

    private void startNewGame() {
        int mode = 0;
        if (btn1.isSelected()) {
            mode = 1;
        } else if (btn2.isSelected()) {
            mode = 2;
        }
        int sizeField = sizeF.getValue();
        int sizeWin = sizeW.getValue();
        gameWindow.startNewGame(mode, sizeField, sizeField, sizeWin);
        setVisible(false);
    }
}
