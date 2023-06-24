package swing;

import util.Condition;
import util.SecondCondition;

import javax.swing.*;
import java.awt.*;

class SettingsScreen extends JPanel {
    public SettingsScreen() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1400, 700));

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.WEST;

        constraints.insets = new Insets(65, 78, 0, 739);

        FlowLayout layout = new FlowLayout();
        layout.setHgap(5);
        JPanel contentPane = new JPanel(layout);
        contentPane.setBackground(Color.WHITE);

        JLabel label1 = new JLabel("Настройки", JLabel.LEFT);
        label1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        label1.setFont(new Font("Inter", Font.PLAIN, 65));

        JLabel label2 = new JLabel("Основные", JLabel.LEFT);
        label2.setBorder(BorderFactory.createEmptyBorder(17, 10, 0, 0));
        label2.setForeground(Color.decode("#8E8E8E"));
        label2.setFont(new Font("Inter", Font.PLAIN, 40));

        contentPane.add(label1);
        contentPane.add(label2);

        add(contentPane, constraints);

        FlowLayout buttonLayout = new FlowLayout(FlowLayout.LEADING, 30, 0);
        JPanel buttonPanel = new JPanel(buttonLayout);

        int borderRadius = 10;

        JButton saveButton = new RoundButton("СОХРАНИТЬ", borderRadius);
        saveButton.setBorder(new RoundBorder(borderRadius, Color.decode("#3AAF37")));
        saveButton.setBackground(Color.decode("#3AAF37"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Inter", Font.PLAIN, 20));
        saveButton.setPreferredSize(new Dimension(165, 44));

        JButton shootingButton1 = new RoundButton("Перейти в меню настроек \"Альтернативная стрельба\"", borderRadius);
        shootingButton1.setFont(new Font("Inter", Font.PLAIN, 20));
        shootingButton1.setBorder(new RoundBorder(borderRadius, Color.decode("#3AAF37")));
        Color color = new Color(0x3AAF37);
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * 0.13));
        shootingButton1.setBackground(transparentColor);
        shootingButton1.setPreferredSize(new Dimension(573, 44));

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 3;
        buttonConstraints.ipady = 46;
        buttonConstraints.insets = new Insets(0, 60, 80, 10);
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;

        buttonPanel.add(saveButton);
        buttonPanel.add(shootingButton1);
        add(buttonPanel, buttonConstraints);
        buttonPanel.setBackground(Color.WHITE);

        JPanel mainPane = new JPanel(new GridBagLayout());
        mainPane.setBorder(BorderFactory.createEmptyBorder(24, 10, 0, 200));
        mainPane.setBackground(Color.decode("#F6F6F6"));

        JLabel playersNumber = new JLabel("Кол-во игроков:  ");
        JLabel rotation = new JLabel("Ротация:  ");
        JLabel targetingSeriesNumber = new JLabel("Кол-во пристрелочных серий:  ");
        JLabel testSeriesNumber = new JLabel("Кол-во зачётных серий:  ");
        JLabel prepareTime = new JLabel("Время на изготовку:  ");
        JLabel durationSeries = new JLabel("Продолжительность серии:  ");
        JLabel completionWarning = new JLabel("Предупреждение о завершении:  ");

        String inputText = "ввод...";

        HintTextField playersNumber_ = new HintTextField(inputText);
        HintTextField rotation_ = new HintTextField(inputText);
        HintTextField targetingSeriesNumber_ = new HintTextField(inputText);
        HintTextField testSeriesNumber_ = new HintTextField(inputText);
        HintTextField firstSeries_ = new HintTextField(inputText);
        HintTextField prepareTime_ = new HintTextField(inputText);
        HintTextField durationSeries_ = new HintTextField(inputText);
        HintTextField completionWarning_ = new HintTextField(inputText);

        JLabel[] labels1 = {playersNumber, rotation, targetingSeriesNumber, testSeriesNumber};
        HintTextField[] text1 = {playersNumber_, rotation_, targetingSeriesNumber_, testSeriesNumber_};

        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 5, 20, 20);
        c.gridx = 0;
        c.gridy = 0;
        panel1.setBackground(Color.decode("#F6F6F6"));
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

        c.gridx = 1;
        c.gridy = 0;

        JPanel panel2 = new JPanel(new GridLayout(0, 1, 10, 10));
        panel2.setBackground(Color.decode("#F6F6F6"));
        c.insets = new Insets(0, 20, 60, 0);
        mainPane.add(panel2, c);

        JLabel[] labels2 = {prepareTime, durationSeries, completionWarning};
        HintTextField[] text2 = {prepareTime_, durationSeries_, completionWarning_};

        c.gridx = 0;
        c.gridy = 0;
        for (int i = 0; i < labels2.length; i++) {
            JPanel template = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 5));
            template.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel2.add(template, c);
            template.add(labels2[i]);
            labels2[i].setFont(font);
            template.add(text2[i]);
            text2[i].setFont(inputFont);
            text2[i].setForeground(Color.decode("#828282"));
            text2[i].setColumns(5);
            text2[i].setBorder(new RoundBorder(10, Color.decode("#828282")));
            template.add(Box.createHorizontalStrut(1));
            template.setBackground(Color.decode("#F6F6F6"));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy++;
        }

        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 2;
        mainConstraints.insets = new Insets(47, 82, 75, 100);
        add(mainPane, mainConstraints);

        shootingButton1.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getRoot(SettingsScreen.this);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new AltSettingsScreen());
            frame.revalidate();
            frame.repaint();
        });
        saveButton.addActionListener(e -> {
            TimerBuilder timerBuilder = new TimerBuilder(playersNumber_, rotation_, testSeriesNumber_, targetingSeriesNumber_,
                    prepareTime_, durationSeries_, completionWarning_);
            if (timerBuilder.isCorrect()) {
                JFrame frame = (JFrame) SwingUtilities.getRoot(SettingsScreen.this);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().removeAll();
                GameScreen gamescreen = new GameScreen(timerBuilder.build());
                frame.getContentPane().add(gamescreen);
                gamescreen.startScreen();
                frame.revalidate();
                frame.repaint();
            } else {
                timerBuilder.applyHintsChanges();
            }
        });
    }
}