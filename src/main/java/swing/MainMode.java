package swing;

import util.SecondCondition;
import util.Timer;

import javax.swing.*;
import java.awt.*;

public class MainMode extends GameCommandScreen{
    public MainMode(Timer timer, boolean haveSignals) {
        super(timer, haveSignals);
    }

    public void startScreen() {
        super.startScreen();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(84, 30, 0, 0);

        PlayerNameLabel playerNameLabel = new PlayerNameLabel();
        playerNameLabel.setText("AB");
        mainPanel.add(playerNameLabel, c);

        c.gridx = 1;

        c.insets = new Insets(40, 238, 40, 271);
        JLabel timerLabel = new TimerLabel();
        mainPanel.add(timerLabel, c);

        c.gridx = 2;
        c.insets = new Insets(84, 0, 0, 80);

        GameCountLabel seriesLabel = new GameCountLabel();
        seriesLabel.setText("1п");
        mainPanel.add(seriesLabel, c);
    }

    public void preGame(SecondCondition secondCondition) {
        super.preGame(secondCondition);
        JPanel tmp = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(124, 30, 0, 0);

        PlayerNameLabel playerName = new PlayerNameLabel(secondCondition);
        tmp.add(playerName.getPlayerName(), c);

        c.gridx++;
        timerInsetsController(secondCondition, c);

        TimerLabel timerLabel = new TimerLabel(secondCondition);
        tmp.add(timerLabel, c);

        c.gridx ++;
        c.insets = new Insets(124, 0, 0, 80);

        GameCountLabel gameCount = new GameCountLabel(secondCondition);
        tmp.add(gameCount.getGameCount(), c);

        c.gridy++;
        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 0);
        tmp.setBackground(COLOR);
        mainPanel.add(tmp, c);
        c.gridy++;
        mainPanel.add(addButtonPanel(secondCondition), c);
    }
}
