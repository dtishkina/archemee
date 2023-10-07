package swing.settings;

import swing.HintTextField;
import swing.TimerBuilder;
import swing.mode.Mode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class AltSettingsScreen extends SettingsScreen {
    public AltSettingsScreen() {
        super();
    }
    void getHeaderPanel(){
        super.getHeaderPanel();
        modeLabel.setText("\"Альтернативная стрельба\"");
    }
    void getMainPanel(){
        super.getMainPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(24, 20, 24, 692));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridy=0;
        c.gridx=0;
        mainPanel.add(fillInnerPanel(defineContent().get(0)), c);
        c.gridy++;
        mainPanel.add(fillInnerPanel(defineContent().get(1)), c);
    }
    @Override
    void getButtonsPanel(){
        super.getButtonsPanel();
        MODE = Mode.ALT;
        buttonsPanel.add(getButton("Перейти в основное меню настроек", 360, Mode.MAIN));
        buttonsPanel.add(getButton("\"Альтернативная стрельба (КОМАНДЫ)\"", 440, Mode.TEAMS));
    }
    @Override
    boolean setTimerParams() {
        timerBuilder = new TimerBuilder(new HintTextField("1"),
                new HintTextField("постоянная"),
                new HintTextField("0"),
                testSeriesNumber_,
                prepareTime_,
                durationSeries_,
                completionWarning_);
        return completionWarning_.getText().equalsIgnoreCase("да");
    }
    @Override
    ArrayList<LinkedHashMap<HintTextField, JLabel>> defineContent(){
        ArrayList<LinkedHashMap<HintTextField, JLabel>> content = super.defineContent();
        content.get(0).remove(playersNumber_);
        content.get(0).remove(rotation_);
        content.get(0).remove(targetingSeriesNumber_);
        return content;
    }
}