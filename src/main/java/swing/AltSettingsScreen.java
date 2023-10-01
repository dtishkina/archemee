package swing;

import javax.swing.*;
import java.awt.*;

class AltSettingsScreen extends JPanel {
    public AltSettingsScreen() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1400, 700));

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.WEST;

        constraints.insets = new Insets(60, 20, 0, 394);

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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 30, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = new RoundButton("СОХРАНИТЬ", 20);
        saveButton.setBorder(new RoundBorder(20, Color.decode("#3AAF37")));
        saveButton.setBackground(Color.decode("#3AAF37"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Inter", Font.PLAIN, 20));
        saveButton.setPreferredSize(new Dimension(165, 44));

        JButton settingsButton = new RoundButton("Перейти в основное меню настроек", 20);

        settingsButton.setBorder(new RoundBorder(20, Color.decode("#3AAF37")));
        settingsButton.setFont(new Font("Inter", Font.PLAIN, 20));
        Color color = new Color(0x3AAF37);
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * 0.13));
        settingsButton.setBackground(transparentColor);
        settingsButton.setPreferredSize(new Dimension(379, 44));

        JButton shootingButton2 = new RoundButton("\"Альтернативная стрельба (КОМАНДЫ)\"", 20);
        shootingButton2.setFont(new Font("Inter", Font.PLAIN, 20));
        shootingButton2.setBorder(new RoundBorder(20, Color.decode("#3AAF37")));
        shootingButton2.setBackground(transparentColor);
        shootingButton2.setPreferredSize(new Dimension(440, 44));

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.ipady = 46;
        constraints.insets = new Insets(0, 60, 80, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        buttonPanel.add(saveButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(shootingButton2);
        add(buttonPanel, constraints);

        JPanel mainPane = new JPanel(new GridBagLayout());
        mainPane.setBorder(BorderFactory.createEmptyBorder(24, 10, 0, 706));
        mainPane.setBackground(Color.decode("#F6F6F6"));

        JLabel testSeriesNumber = new JLabel("Кол-во зачетных серий:  ");
        JLabel prepareTime = new JLabel("Время на изготовку:  ");
        JLabel durationSeries = new JLabel("Продолжительность серии:  ");
        JLabel completionWarning = new JLabel("Предупреждение о завершении:  ");

        String inputText = "ввод...";

        HintTextField testSeriesNumber_ = new HintTextField(inputText);
        HintTextField prepareTime_ = new HintTextField(inputText);
        HintTextField durationSeries_ = new HintTextField(inputText);
        HintTextField completionWarning_ = new HintTextField(inputText);

        JLabel[] labels1 = {testSeriesNumber, prepareTime, durationSeries, completionWarning};
        HintTextField[] text1 = {testSeriesNumber_, prepareTime_, durationSeries_, completionWarning_};

        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 10, 20, 20);
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

        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 2;
        mainConstraints.insets = new Insets(40, 82, 75, 100);
        add(mainPane, mainConstraints);

        saveButton.addActionListener(e -> {
            TimerBuilder timerBuilder = new TimerBuilder(new HintTextField("1"), new HintTextField("постоянная"), testSeriesNumber_,
                    new HintTextField("0"), prepareTime_, durationSeries_, completionWarning_);
            if (timerBuilder.isCorrect()) {
                JFrame frame = (JFrame) SwingUtilities.getRoot(this);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().removeAll();
                boolean haveSignals = false;
                if (completionWarning_.getText().equalsIgnoreCase("да")){
                    haveSignals = true;
                }
                GameCommandScreen gameScreen = new GameCommandScreen(timerBuilder.build(), haveSignals);
                frame.getContentPane().add(gameScreen);
                gameScreen.startScreen();
                frame.revalidate();
                frame.repaint();
            } else {
                timerBuilder.applyHintsChanges();
            }});
        settingsButton.addActionListener(e -> {
            SettingsScreen settingsScreen = new SettingsScreen();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(settingsScreen);
            frame.pack();
            frame.revalidate();
        });
        shootingButton2.addActionListener(e -> {
            AltSettingsTeams settingsScreen = new AltSettingsTeams();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(settingsScreen);
            frame.pack();
            frame.revalidate();
        });
    }
}