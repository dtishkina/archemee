package swing;

import util.Condition;
import util.SecondCondition;
import util.Timer;

import javax.security.auth.callback.LanguageCallback;
import javax.swing.*;
import java.awt.*;

class GameScreen extends JPanel {
    private Timer timer;

    public GameScreen(Timer timer) {
        this.timer = timer;
    }
    public void paint() {
        SecondCondition currentCondition = timer.tic();
        while (currentCondition.getType() != Condition.Type.END) {
            if (currentCondition.getType() == Condition.Type.PREGAME) {
                preGame(currentCondition);
            } else if (currentCondition.getType() == Condition.Type.GAME) {
                game(currentCondition);
            } else if (currentCondition.getType() == Condition.Type.REST) {
                rest();
            }
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentCondition = timer.tic();
        }
    }

    private void preGame(SecondCondition secondCondition) {
        setLayout(new GridBagLayout());

        setSize(1400, 700);
        setMinimumSize(new Dimension(1400, 700));
        Color backColor = Color.decode("#EE3939");

        setBackground(backColor);

        Font topButtons = new Font("Inter", Font.PLAIN, 24);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel topButtonsPanel = new JPanel(new GridBagLayout());
        topButtonsPanel.setBackground(backColor);

        GridBagConstraints buttonContains = new GridBagConstraints();
        buttonContains.gridx = 0;
        buttonContains.gridy = 0;
        buttonContains.insets = new Insets(0, 0, 0, 20);    //change bottom

        JButton soundFirst = new RoundButton("1 сигнал");
        topButtonsPanel.add(soundFirst, buttonContains);
        soundFirst.setBorder(new RoundBorder(5, backColor));
        soundFirst.setFont(topButtons);
        soundFirst.setBackground(Color.WHITE);
        soundFirst.setPreferredSize(new Dimension(161, 49));

        buttonContains.gridx ++;

        JButton soundSecond = new RoundButton("2 сигнала");
        topButtonsPanel.add(soundSecond, buttonContains);
        soundSecond.setBorder(new RoundBorder(5, backColor));
        soundSecond.setFont(topButtons);
        soundSecond.setBackground(Color.WHITE);
        soundSecond.setPreferredSize(new Dimension(161, 49));

        buttonContains.gridx ++;

        JButton soundThird = new RoundButton("3 сигнала");
        topButtonsPanel.add(soundThird, buttonContains);
        soundThird.setBorder(new RoundBorder(5, backColor));
        soundThird.setFont(topButtons);
        soundThird.setBackground(Color.WHITE);
        soundThird.setPreferredSize(new Dimension(161, 49));

        buttonContains.gridx ++;
        buttonContains.insets = new Insets(0, 477, 0, 0);

        JButton settingsButton = new RoundButton("настройки");
        topButtonsPanel.add(settingsButton, buttonContains);
        settingsButton.setBorder(new RoundBorder(5, backColor));
        settingsButton.setFont(topButtons);
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setPreferredSize(new Dimension(233, 49));

        settingsButton.addActionListener(e -> {
            SettingsScreen settingsScreen = new SettingsScreen();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(settingsScreen);
            frame.pack();
            frame.revalidate();
        });

        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(55, 20, 0, 40);
        add(topButtonsPanel, c);

        JPanel bottomButtonsPanel = new JPanel(new GridBagLayout());
        topButtonsPanel.setBackground(backColor);

        buttonContains.gridx = 0;
        buttonContains.gridy = 0;
        buttonContains.insets = new Insets(0, 0, 0, 40);    //change bottom

        Font bottomButtons = new Font("Inter", Font.PLAIN, 48);

        JButton reset = new RoundButton("СБРОС");
        bottomButtonsPanel.add(reset, buttonContains);
        reset.setBorder(new RoundBorder(10, Color.decode("#828282")));
        reset.setFont(bottomButtons);
        reset.setBackground(Color.WHITE);
        reset.setPreferredSize(new Dimension(280, 78));

        buttonContains.gridx ++;

        JButton pause = new RoundButton("ПАУЗА");
        bottomButtonsPanel.add(pause, buttonContains);
        pause.setBorder(new RoundBorder(10, Color.decode("#D9A900")));
        pause.setFont(bottomButtons);
        pause.setBackground(Color.WHITE);
        pause.setForeground(Color.decode("#D9A900"));
        pause.setPreferredSize(new Dimension(280, 78));

        buttonContains.gridx ++;

        JButton start = new RoundButton("СТАРТ");
        bottomButtonsPanel.add(start, buttonContains);
        start.setBorder(new RoundBorder(10, Color.decode("#3AAF37")));
        start.setFont(bottomButtons);
        start.setBackground(Color.WHITE);
        start.setForeground(Color.decode("#3AAF37"));
        start.setPreferredSize(new Dimension(280, 78));

        buttonContains.gridx ++;

        JButton next = new RoundButton("СЛ. ПОД.");
        bottomButtonsPanel.add(next, buttonContains);
        next.setBorder(new RoundBorder(10, Color.decode("#AC0707")));
        next.setFont(bottomButtons);
        next.setBackground(Color.WHITE);
        next.setForeground(Color.decode("#AC0707"));
        next.setPreferredSize(new Dimension(280, 78));

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 60, 60, 40);
        bottomButtonsPanel.setBackground(backColor);
        add(bottomButtonsPanel, c);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints mainContains = new GridBagConstraints();
        mainContains.gridx = 0;
        mainContains.gridy = 0;
        mainContains.insets = new Insets(30, 20, 0, 0);

        Label playersLabel = new Label("AB");
        playersLabel.setFont(new Font("Inter", Font.PLAIN, 116));
        playersLabel.setForeground(Color.WHITE);
        mainPanel.add(playersLabel, mainContains);

        c.fill = GridBagConstraints.BOTH;

        Label timerLabel = new Label("00");
        timerLabel.setFont(new Font("Inter", Font.PLAIN, 270));
        timerLabel.setForeground(Color.WHITE);

        mainContains.gridx = 1;
        mainContains.insets = new Insets(30, 200, 0, 0);
        mainPanel.add(timerLabel, mainContains);

        Label seriesLabel = new Label("1п");
        seriesLabel.setFont(new Font("Inter", Font.PLAIN, 116));
        seriesLabel.setForeground(Color.WHITE);

        mainContains.gridx = 2;
        mainContains.insets = new Insets(30, 200, 0, 60);
        mainPanel.add(seriesLabel, mainContains);

        c.gridx = 0;
        c.gridy = 2;
        add(mainPanel, c);
        mainPanel.setBackground(backColor);
    }

    private void game(SecondCondition secondCondition) {

    }

    private void rest() {
    }

}
