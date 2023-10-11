package swing.settings;

import swing.TimerDemoGUI;
import swing.mode.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TeamsSettingsScreen extends AltSettingsScreen{
    public TeamsSettingsScreen() {
        super();
    }
    void getHeaderPanel(){
        super.getHeaderPanel();
        modeLabel.setText("Команды");
    }

    void getButtonsPanel() {
        super.getButtonsPanel();
        MODE = Mode.TEAMS;
        buttonsPanel.removeAll();
        buttonsPanel.add(saveButton);
        buttonsPanel.add(getButton("\"Альтернативная стрельба\"", 300, Mode.ALT));
        buttonsPanel.add(getButton("Перейти в основное меню настроек", 360, Mode.MAIN));
        saveButton.addActionListener(this::actionPerformed);
    }
    protected void actionPerformed(ActionEvent e) {
        setTimerParams();
        if (timerBuilder.isCorrect()) {
            JFrame frame = (JFrame) SwingUtilities.getRoot(this);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().removeAll();
            TimerDemoGUI gameScreen = new TimerDemoGUI(Integer.parseInt(durationSeries_.getText()),
                    Integer.parseInt(testSeriesNumber_.getText()), 5,
                    Integer.parseInt(prepareTime_.getText()), haveSignal);
            frame.setSize(new Dimension(1400, 700));
            frame.add(gameScreen.displayGUI());
            frame.revalidate();
        } else {
            timerBuilder.applyHintsChanges();
        }
    }
}
