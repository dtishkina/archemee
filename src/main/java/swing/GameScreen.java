package swing;

import util.Condition;
import util.SecondCondition;
import util.Timer;

import javax.swing.*;
import java.awt.*;

class GameScreen extends JPanel {

    private Timer timer;
    private JFrame frame;

    public GameScreen(Timer timer) {
        Color backColor = Color.decode("#EE3939");

        frame.setSize(1400, 700);
        frame.setMinimumSize(new Dimension(1400, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(backColor);
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
        Color backColor = Color.decode("#EE3939");
        Font topButtons = new Font("Inter", Font.PLAIN, 24);
        frame.setLayout(new GridBagLayout());

        JPanel topButtonsPanel = new JPanel(new GridBagLayout());
        topButtonsPanel.setBackground(backColor);

        GridBagConstraints buttonContains = new GridBagConstraints();
        buttonContains.gridx = 0;
        buttonContains.gridy = 0;
        buttonContains.insets = new Insets(55, 0, 500, 20);    //change bottom

        JButton soundFirst = new RoundButton("1 сигнал");
        topButtonsPanel.add(soundFirst, buttonContains);
        soundFirst.setBorder(new RoundBorder(5, backColor));
        soundFirst.setFont(topButtons);
        soundFirst.setBackground(Color.WHITE);

        buttonContains.gridx ++;

        JButton soundSecond = new RoundButton("2 сигнала");
        topButtonsPanel.add(soundSecond, buttonContains);
        soundSecond.setBorder(new RoundBorder(5, backColor));
        soundSecond.setFont(topButtons);
        soundSecond.setBackground(Color.WHITE);

        buttonContains.gridx ++;

        JButton soundThird = new RoundButton("3 сигнала");
        topButtonsPanel.add(soundThird, buttonContains);
        soundThird.setBorder(new RoundBorder(5, backColor));
        soundThird.setFont(topButtons);
        soundThird.setBackground(Color.WHITE);

        buttonContains.gridx += 2;

        JButton settingsButton = new RoundButton("настройки");
        topButtonsPanel.add(settingsButton, buttonContains);
        settingsButton.setBorder(new RoundBorder(5, backColor));
        settingsButton.setFont(topButtons);
        settingsButton.setBackground(Color.WHITE);

        buttonContains.gridx = 0;
        buttonContains.insets = new Insets(55, 80, 500, 60);

        frame.add(topButtonsPanel, buttonContains);


        JPanel bottomButtonsPanel = new JPanel(new GridBagLayout());
        topButtonsPanel.setBackground(backColor);

        buttonContains.gridx = 0;
        buttonContains.gridy = 0;
        buttonContains.insets = new Insets(55, 0, 80, 20);    //change bottom

        Font bottomButtons = new Font("Inter", Font.PLAIN, 48);

        JButton reset = new RoundButton("3 сигнала");
        topButtonsPanel.add(reset, buttonContains);
        reset.setBorder(new RoundBorder(5, Color.decode("#828282")));
        reset.setFont(bottomButtons);
        reset.setBackground(Color.WHITE);
    }

    private void game(SecondCondition secondCondition) {
    }

    private void rest() {
    }

}
