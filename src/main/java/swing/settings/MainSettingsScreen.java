package swing.settings;

import swing.TimerBuilder;
import swing.mode.Mode;

import javax.swing.*;
import java.awt.*;

public class MainSettingsScreen extends SettingsScreen {
    public static boolean rotation = false;
    public MainSettingsScreen(){
        super();
    }
    void getHeaderPanel(){
        super.getHeaderPanel();
        modeLabel.setText("Основные");
    }
    void getMainPanel(){
        super.getMainPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(24, 20, 24, 0));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.NORTH;
        mainPanel.add(fillInnerPanel(defineContent().get(0)), c);
        c.gridx++;
        c.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(fillInnerPanel(defineContent().get(1)), c);
    }
    void getButtonsPanel() {
        super.getButtonsPanel();
        MODE = Mode.MAIN;
        buttonsPanel.add(getButton("\"Альтернативная стрельба\"", 300, Mode.ALT));
        buttonsPanel.add(getButton("\"Альтернативная стрельба (КОМАНДЫ)\"", 440, Mode.TEAMS));
        saveButton.addActionListener(event -> haveSignal = signalOption.getSelectedButton().getText().equalsIgnoreCase("да"));
    }
    @Override
    void setTimerParams() {
        timerBuilder = new TimerBuilder(playersNumber_, rotation, targetingSeriesNumber_, testSeriesNumber_,
                prepareTime_, durationSeries_, haveSignal);
    }

}