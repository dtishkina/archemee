package swing;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MainSettingsScreen extends SettingsScreen{
    public MainSettingsScreen(){
        super();
        contentPanel.setModeLabel("Основные");

        JLabel playersNumber = new JLabel("Кол-во игроков:  ");
        JLabel rotation = new JLabel("Ротация:  ");
        JLabel targetingSeriesNumber = new JLabel("Кол-во пристрелочных серий:  ");
        JLabel testSeriesNumber = new JLabel("Кол-во зачётных серий:  ");
        JLabel prepareTime = new JLabel("Время на изготовку:  ");
        JLabel durationSeries = new JLabel("Продолжительность серии:  ");
        JLabel completionWarning = new JLabel("Предупреждение о завершении:  ");

        ArrayList<JLabel> labels1 = new ArrayList<>(Arrays.asList(playersNumber, rotation, targetingSeriesNumber, testSeriesNumber));
        ArrayList<JLabel> labels2 = new ArrayList<>(Arrays.asList(prepareTime, durationSeries, completionWarning));

        mainPanel.addFields(labels1);
        mainPanel.addFields(labels2);

        buttonsPanel.add(buttonsPanel.saveButton);
        buttonsPanel.add(buttonsPanel.toAltSettingsButton);
        buttonsPanel.add(buttonsPanel.toTeamSettingsButton);

        buttonsPanel.saveButton.addActionListener(e -> {
            boolean haveSignals = setTimerParams(hintsList);
            if (timerBuilder.isCorrect()) {
                JFrame frame = (JFrame) SwingUtilities.getRoot(this);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().removeAll();
                GameCommandScreen gameScreen = ModeBuilder.build(timerBuilder.build(), haveSignals, 1);
                frame.getContentPane().add(gameScreen);
                gameScreen.startScreen();
                frame.revalidate();
                frame.repaint();
            } else {
                timerBuilder.applyHintsChanges();
            }
        });
    }
}
