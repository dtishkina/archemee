package swing;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

class AltSettingsScreen extends SettingsScreen {
    public AltSettingsScreen() {
        super();

        contentPanel.setModeLabel("\"Альтернативная стрельба\"");

        JLabel testSeriesNumber = new JLabel("Кол-во зачетных серий:  ");
        JLabel prepareTime = new JLabel("Время на изготовку:  ");
        JLabel durationSeries = new JLabel("Продолжительность серии:  ");
        JLabel completionWarning = new JLabel("Предупреждение о завершении:  ");

        ArrayList<JLabel> labels = new ArrayList<>(Arrays.asList(testSeriesNumber, prepareTime, durationSeries, completionWarning));

        mainPanel.addFields(labels);

        buttonsPanel.remove(buttonsPanel.toAltSettingsButton);
        buttonsPanel.add(buttonsPanel.saveButton);
        buttonsPanel.add(buttonsPanel.backToSettingsButton);
        buttonsPanel.add(buttonsPanel.toTeamSettingsButton);

        buttonsPanel.saveButton.addActionListener(e -> {
            boolean haveSignals = setTimerParams(hintsList);
            if (timerBuilder.isCorrect()) {
                JFrame frame = (JFrame) SwingUtilities.getRoot(this);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().removeAll();
                GameCommandScreen gameScreen = ModeBuilder.build(timerBuilder.build(), haveSignals, 2);
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