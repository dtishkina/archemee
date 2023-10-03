package swing;

import util.SecondCondition;
import util.Timer;

import javax.swing.*;
import java.awt.*;

public class AltMode extends GameCommandScreen{
    public AltMode(Timer timer, boolean haveSignals) {
        super(timer, haveSignals);
    }

    public void startScreen(){
        super.startScreen();
        GridBagConstraints c = new GridBagConstraints();

        TimerLabel infoLabel = new TimerLabel();
        infoLabel.setText("1п");

        c.insets = new Insets(40, 218, 40, 301);
        mainPanel.add(infoLabel, c);
    }

    public void preGame(SecondCondition secondCondition){
        super.preGame(secondCondition);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 80, 0, 200);
        JPanel tmp = new JPanel(new GridBagLayout());
        TimerLabel timerLabel = new TimerLabel(secondCondition);
        tmp.add(timerLabel, c);

        c.gridx++;
        JLabel gameCountLabel = new JLabel();
        gameCountLabel.setFont(new Font("Inter", Font.PLAIN, 270));
        gameCountLabel.setText(secondCondition.getGameCount() + "");
        gameCountLabel.setForeground(Color.WHITE);
        if (secondCondition.getTimeLeft() < 10){
            c.insets = new Insets(0, 200, 0, 80);
        }else {
            c.insets = new Insets(0, 190, 0, 80);
        }

        tmp.add(gameCountLabel, c);
        tmp.setBackground(COLOR);

        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(tmp, c);
        c.gridy++;
        mainPanel.add(addButtonPanel(secondCondition), c);
    }
}
