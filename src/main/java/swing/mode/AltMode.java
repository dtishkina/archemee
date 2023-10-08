package swing.mode;

import util.SecondCondition;
import util.Timer;

import javax.swing.*;
import java.awt.*;

public class AltMode extends GameComandScreen {
    TimerLabel timerLabel;
    GameCountLabel seriesLabel;
    public AltMode(Timer timer, boolean haveSignals) {
        super(timer, haveSignals);
    }
    JPanel preGame(SecondCondition secondCondition){
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

            timerLabel = new TimerLabel(secondCondition[0]);
            timerInsetsController(secondCondition[0], c);
            tmp.add(timerLabel, c);

            c.gridx++;
            seriesLabel = new GameCountLabel(secondCondition[0]);
            c.insets = new Insets(0, 0, 220, 0);
            tmp.add(seriesLabel, c);

            tmp.setPreferredSize(new Dimension(1000, 600));
            return tmp;
        }
        GridBagConstraints c = setCommonBackground(Color.decode("#EE3939"));
        tmp.setBackground(Color.decode("#EE3939"));

        c.gridx = 0;
        c.insets = new Insets(0, 260, 0, 150);
        timerLabel = new TimerLabel();
        tmp.add(timerLabel, c);

        c.gridx++;
        c.insets = new Insets(0, 0, 250, 0);

        seriesLabel = new GameCountLabel();
        tmp.add(seriesLabel, c);

        return tmp;
    }
    @Override
    void timerInsetsController(SecondCondition secondCondition, GridBagConstraints c) {
        c.fill = GridBagConstraints.CENTER;
        if (secondCondition.getTimeLeft() < 10) {
            c.insets = new Insets(0, 470, 60, 300);
        } else {
            c.insets = new Insets(0, 270, 60, 180);
        }
    }
}
