package swing.mode;

import util.SecondCondition;
import util.Timer;

import javax.swing.*;
import java.awt.*;

public class MainMode extends GameComandScreen {
    PlayerNameLabel playerNameLabel;
    TimerLabel timerLabel;
    GameCountLabel seriesLabel;
    public MainMode(Timer timer, boolean haveSignals) {
        super(timer, haveSignals);
    }
    JPanel preGame(SecondCondition secondCondition) {
        JPanel tmp = super.preGame(secondCondition);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 300, 75);
        mainPanel.add(tmp, c);
        return mainPanel;
    }
    @Override
    JPanel getMainPanel(SecondCondition... secondCondition) {
        JPanel tmp = new JPanel(new GridBagLayout());
        if (secondCondition.length != 0){
            tmp.setBackground(COLOR);
            GridBagConstraints c = setCommonBackground(COLOR);
            c.fill = GridBagConstraints.CENTER;
            c.gridx = 0;
            c.insets = new Insets(60, 0, 0, 0);
            playerNameLabel = new PlayerNameLabel(secondCondition[0]);
            tmp.add(playerNameLabel, c);

            c.gridx++;
            timerLabel = new TimerLabel(secondCondition[0]);
            timerInsetsController(secondCondition[0], c);
            tmp.add(timerLabel, c);

            c.gridx++;
            seriesLabel = new GameCountLabel(secondCondition[0]);
            c.insets = new Insets(60, 0, 0, 0);
            tmp.add(seriesLabel, c);

            tmp.setPreferredSize(new Dimension(1000, 600));
            return tmp;
        }
        GridBagConstraints c = setCommonBackground(Color.decode("#EE3939"));
        tmp.setBackground(Color.decode("#EE3939"));

        playerNameLabel = new PlayerNameLabel();
        tmp.add(playerNameLabel, c);

        c.gridx = 1;
        c.insets = new Insets(0, 170, 0, 180);
        timerLabel = new TimerLabel();
        tmp.add(timerLabel, c);

        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 0);

        seriesLabel = new GameCountLabel();
        tmp.add(seriesLabel, c);

        return tmp;
    }
    @Override
    void timerInsetsController(SecondCondition secondCondition, GridBagConstraints c){
        c.fill = GridBagConstraints.CENTER;
        if (secondCondition.getTimeLeft() < 10) {
            c.insets = new Insets(0, 240, 60, 260);
        } else {
            c.insets = new Insets(0, 140, 60, 195);
        }
    }
}
