package swing;

import javax.swing.*;
import java.awt.*;

class AltSettingsScreen extends JPanel {
    public AltSettingsScreen() {

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.WEST;

        constraints.insets = new Insets(60, 0, 0, 394);

        FlowLayout layout = new FlowLayout();
        JPanel contentPane = new JPanel(layout);
        contentPane.setBackground(Color.WHITE);

        JLabel label1 = new JLabel("Настройки", JLabel.LEFT);
        label1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        label1.setFont(new Font("Inter", Font.PLAIN, 65));

        JLabel label2 = new JLabel("“Альтернативная стрельба”", JLabel.LEFT);
        label2.setBorder(BorderFactory.createEmptyBorder(17, 10, 0, 0));
        label2.setForeground(Color.decode("#8E8E8E"));
        label2.setFont(new Font("Inter", Font.PLAIN, 40));

        contentPane.add(label1);
        contentPane.add(label2);

        add(contentPane, constraints);

        /////////////////////////////////////////////////////////////////////////////////////////////////////

        FlowLayout buttonLayout = new FlowLayout(FlowLayout.LEADING, 30, 0);
        JPanel buttonPanel = new JPanel(buttonLayout);

        JButton saveButton = new RoundButton("СОХРАНИТЬ");
        saveButton.setBorder(new RoundBorder(5, Color.decode("#3AAF37")));
        saveButton.setBackground(Color.decode("#3AAF37"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Inter", Font.PLAIN, 20));

        JButton shootingButton1 = new RoundButton("Перейти в основное меню настроек");
        shootingButton1.setFont(new Font("Inter", Font.PLAIN, 20));
        shootingButton1.setBorder(new RoundBorder(5, Color.decode("#3AAF37")));
        Color color = new Color(0x3AAF37);
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * 0.13));
        shootingButton1.setBackground(transparentColor);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 3;
        buttonConstraints.ipady = 46;
        buttonConstraints.insets = new Insets(0, 60, 80, 10);
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;

        buttonPanel.add(saveButton);
        saveButton.setPreferredSize(new Dimension(165, 44));
        buttonPanel.add(shootingButton1);
        shootingButton1.setPreferredSize(new Dimension(379, 44));
        add(buttonPanel, buttonConstraints);

        buttonPanel.setBackground(Color.WHITE);
//
//        /////////////////////////////////////////////////////////////////////////////////////////////
//
        JPanel mainPane = new JPanel(new GridBagLayout());
        mainPane.setBorder(BorderFactory.createEmptyBorder(24, 10, 0, 706));
        mainPane.setBackground(Color.decode("#F6F6F6"));

        JLabel playersNumber = new JLabel("Кол-во зачетных серий:  ");
        JLabel firstSeries = new JLabel("Номер  первой серии:  ");
        JLabel prepareTime = new JLabel("Время на изготовку:  ");
        JLabel durationSeries = new JLabel("Продолжительность серии:  ");
        JLabel completionWarning = new JLabel("Предупреждение о завершении:  ");
        JLabel emptyLabel = new JLabel("ofgbkpfgo");
        JLabel emptyLabel1 = new JLabel("ofgbkpfgo");

        String inputText = "ввод...";

        HintTextField testSeriesNumber_ = new HintTextField(inputText);
        HintTextField firstSeries_ = new HintTextField(inputText);
        HintTextField prepareTime_ = new HintTextField(inputText);
        HintTextField durationSeries_ = new HintTextField(inputText);
        HintTextField completionWarning_ = new HintTextField(inputText);

        JLabel[] labels1 = {playersNumber, firstSeries, prepareTime, durationSeries, completionWarning};
        HintTextField[] text1 = {testSeriesNumber_, firstSeries_, prepareTime_, durationSeries_, completionWarning_};

        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 20);
        c.gridx = 0;
        c.gridy = 0;

        mainPane.add(panel1, c);

        Font font = new Font("Inter", Font.PLAIN, 24);
        Font inputFont = new Font("Inter", Font.PLAIN, 20);

        for (int i = 0; i < labels1.length; i++) {
            c.gridx = 0;
            JPanel template = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 5));

            template.setAlignmentX(Component.LEFT_ALIGNMENT);
            c.anchor = GridBagConstraints.WEST;
            c.insets = new Insets(0, 0, 10, 0);
            c.ipadx = 70;
            c.fill = GridBagConstraints.WEST;

            panel1.add(template, c);

            template.add(labels1[i], c);
            labels1[i].setFont(font);

            c.gridx = 1;

            template.add(text1[i], c);
            text1[i].setFont(inputFont);
            text1[i].setForeground(Color.decode("#828282"));
            text1[i].setBorder(new RoundBorder(5, Color.decode("#828282")));

            text1[i].setPreferredSize(new Dimension(84, 34));

            template.setBackground(Color.decode("#F6F6F6"));
            c.gridy++;
        }


        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 2;
        mainConstraints.insets = new Insets(40, 82, 75, 100);

        add(mainPane, mainConstraints);
        setBorder(new RoundBorder(15, Color.WHITE));

        panel1.setBackground(Color.decode("#F6F6F6"));

        c.fill = GridBagConstraints.HORIZONTAL;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1400, 700));

        saveButton.addActionListener(e -> {
                    TimerBuilder timerBuilder = new TimerBuilder(new HintTextField("1"), new HintTextField("Нет"), testSeriesNumber_,
                            new HintTextField("0"), firstSeries_, prepareTime_, durationSeries_, completionWarning_);
                    if (timerBuilder.isCorrect()) {
                        // Создаем экземпляр класса AlternativeFireSettingsScreen и отображаем его на экране
                        JFrame frame = (JFrame) SwingUtilities.getRoot(AltSettingsScreen.this);
                        frame.getContentPane().removeAll();
                        frame.getContentPane().add(new GameScreen(timerBuilder.build()));
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        timerBuilder.applyHintsChanges();
                    }

                }
        );

        // добавляем обработчик нажатия на кнопку backButton
        shootingButton1.addActionListener(e -> {
            // создаем новый экран меню основных настроек
            SettingsScreen settingsScreen = new SettingsScreen();

            // устанавливаем экран меню основных настроек в качестве главного содержимого фрейма
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(settingsScreen);
            frame.pack();
            frame.revalidate();
        });
    }
}