package swing;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TimerDemoGUI {
    private CountdownTimer leftTimer;
    private CountdownTimer rightTimer;
    private CountdownTimer leftPrepTimer;
    private CountdownTimer rightPrepTimer;
    private JLabel leftLabel;
    private JLabel rightLabel;
    private JLabel seriesLabel;
    private RoundButton leftButton;
    private RoundButton rightButton;
    private JPanel leftPanel;
    private JPanel rightPanel;
    boolean wasTriggered = false;
    boolean prepTimeToggled = false;
    private int seriesNumber;
    private int maxSeriesNumber;
    private int initialTimerValue;
    private int warningTime;
    private int passedPrepTime;
    private boolean haveSignal;
    String currentDir = System.getProperty("user.dir");
    String soundPath = currentDir + "/src/main/java/swing/countdown-start.wav";

    public TimerDemoGUI(int passedTimerValue, int maxSeries, int warning, int prepTime, boolean haveSignal) {
        seriesNumber = 1;
        initialTimerValue = passedTimerValue;
        warningTime = warning;
        passedPrepTime = prepTime;
        this.haveSignal = haveSignal;

        leftLabel = new JLabel(String.valueOf(initialTimerValue), SwingConstants.CENTER);
        leftLabel.setFont(new Font("Inter", Font.PLAIN, 270));
        leftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        leftLabel.setForeground(Color.WHITE);
        rightLabel = new JLabel(String.valueOf(initialTimerValue), SwingConstants.CENTER);
        rightLabel.setFont(new Font("Inter", Font.PLAIN, 270));
        rightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        rightLabel.setForeground(Color.WHITE);
        seriesLabel = new JLabel("СЕРИЯ: 1", SwingConstants.CENTER);

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.decode("#EE3939"));

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Color.decode("#EE3939"));

        maxSeriesNumber = maxSeries;

        leftTimer = new CountdownTimer(initialTimerValue, leftLabel, leftPanel, warningTime);
        rightTimer = new CountdownTimer(initialTimerValue, rightLabel, rightPanel, warningTime);
        leftPrepTimer = new CountdownTimer(passedPrepTime, leftLabel, leftPanel, -1);
        rightPrepTimer = new CountdownTimer(passedPrepTime, rightLabel, rightPanel, -1);

        leftButton = new RoundButton("СТАРТ", 20);
        leftButton.setBackground(Color.WHITE);
        leftButton.setForeground(Color.decode("#EE3939"));
        leftButton.setPreferredSize(new Dimension(400, 78));
        leftButton.setFont(new Font("Inter", Font.PLAIN, 48));
        leftButton.setBorder(new RoundBorder(10, Color.decode("#EE3939")));
        leftButton.setBorderPainted(false);

        rightButton = new RoundButton("СТАРТ", 20);
        rightButton.setBackground(Color.WHITE);
        rightButton.setForeground(Color.decode("#EE3939"));
        rightButton.setPreferredSize(new Dimension(400, 78));
        rightButton.setFont(new Font("Inter", Font.PLAIN, 48));
        rightButton.setBorder(new RoundBorder(10, Color.decode("#EE3939")));
        rightButton.setBorderPainted(false);

        leftButton.addActionListener(e -> {
            if (leftTimer.isPaused() & !wasTriggered) {
                playSoundMultipleTimes("src/countdown-start.wav", 1);
                Timer delayTimer = new Timer(1000, e1 -> toggleLeftTimer());
                delayTimer.setRepeats(false);
                delayTimer.start();
                leftButton.setText("ПАУЗА");
                wasTriggered = true;
            }
            else if (leftTimer.isPaused() & wasTriggered) {
                playSoundMultipleTimes("src/countdown-start.wav", 0);
                toggleLeftTimer();
                leftButton.setText("ПАУЗА");
            }
            else {
                toggleLeftTimer();
                leftButton.setText("ПРОДОЛЖИТЬ");
            }
        });

        rightButton.addActionListener(e -> {
            if (rightTimer.isPaused() & !wasTriggered) {
                playSoundMultipleTimes(soundPath, 1);
                Timer delayTimer = new Timer(1000, e1 -> toggleRightTimer());
                delayTimer.setRepeats(false);
                delayTimer.start();
                rightButton.setText("ПАУЗА");
                wasTriggered = true;
            }
            else if (rightTimer.isPaused() & wasTriggered) {
                playSoundMultipleTimes(soundPath, 0);
                toggleRightTimer();
                rightButton.setText("ПАУЗА");
            }
            else {
                toggleRightTimer();
                rightButton.setText("ПРОДОЛЖИТЬ");
            }
        });

        leftPrepTimer.setTimerFinishedCallback(() -> {
            playSoundOnce(soundPath);
            leftPrepTimer.stop();
            leftTimer.start();
            leftPanel.setBackground(Color.decode("#3AAF37"));
            prepTimeToggled = true;
            leftLabel.setText(String.valueOf(initialTimerValue));
            rightLabel.setText(String.valueOf(initialTimerValue));
            leftButton.setForeground(Color.decode("#3AAF37"));
        });

        rightPrepTimer.setTimerFinishedCallback(() -> {
            playSoundOnce(soundPath);
            rightPrepTimer.stop();
            rightTimer.start();
            rightPanel.setBackground(Color.decode("#3AAF37"));
            prepTimeToggled = true;
            rightLabel.setText(String.valueOf(initialTimerValue));
            leftLabel.setText(String.valueOf(initialTimerValue));
            rightButton.setForeground(Color.decode("#3AAF37"));
        });

        leftTimer.setTimerFinishedCallback(() -> {
            leftPanel.setBackground(Color.decode("#EE3939"));
            if (rightTimer.isTimeOver()){
                playEndingSoundMultipleTimes(soundPath, 3);
            }
            else {
                playSoundOnce(soundPath);
            }
        });

        rightTimer.setTimerFinishedCallback(() -> {
            rightPanel.setBackground(Color.decode("#EE3939"));
            if (leftTimer.isTimeOver()){
                playEndingSoundMultipleTimes(soundPath, 3);
            }
            else {
                playSoundOnce(soundPath);
            }
        });

    }

    public void toggleLeftTimer() {
        if (!prepTimeToggled) {
            if (leftPrepTimer.isTimeOver()) {
                // If prep timer is over, toggle the main timer
                if (leftTimer.isPaused()) {
                    playSoundOnce(soundPath);
                    leftPanel.setBackground(Color.decode("#3AAF37"));
                    leftButton.setText("ПАУЗА");
                    leftButton.setForeground(Color.decode("#3AAF37"));
                    leftTimer.start();
                } else {
                    leftButton.setText("ПРОДОЛЖИТЬ");
                    leftPanel.setBackground(Color.decode("#EE3939"));
                    leftButton.setForeground(Color.decode("#EE3939"));
                    leftTimer.pause();
                }
            } else {
                // If prep timer is not over, toggle the prep timer
                if (leftPrepTimer.isPaused()) {
                    playSoundOnce(soundPath);
                    leftPanel.setBackground(Color.decode("#EE3939")); // Use appropriate color for prep timer
                    leftButton.setForeground(Color.decode("#EE3939"));
                    leftButton.setText("ПАУЗА");
                    leftPrepTimer.start();
                } else {
                    leftButton.setText("ПРОДОЛЖИТЬ");
                    leftPanel.setBackground(Color.decode("#EE3939"));
                    leftButton.setForeground(Color.decode("#EE3939"));
                    leftPrepTimer.pause();
                }
            }
        }
        else {
            if (leftTimer.isPaused()) {
                playSoundOnce(soundPath);
                leftPanel.setBackground(Color.decode("#3AAF37"));
                leftButton.setText("ПАУЗА");
                leftButton.setForeground(Color.decode("#3AAF37"));
                leftTimer.start();
            } else {
                leftButton.setText("ПРОДОЛЖИТЬ");
                leftPanel.setBackground(Color.decode("#EE3939"));
                leftButton.setForeground(Color.decode("#EE3939"));
                leftTimer.pause();
            }
        }
    }

    public void toggleRightTimer() {
        if (!prepTimeToggled) {
            if (rightPrepTimer.isTimeOver()) {
                // If prep timer is over, toggle the main timer
                if (rightTimer.isPaused()) {
                    playSoundOnce(soundPath);
                    rightPanel.setBackground(Color.decode("#3AAF37"));
                    rightButton.setText("ПАУЗА");
                    rightButton.setForeground(Color.decode("#3AAF37"));
                    rightTimer.start();
                } else {
                    rightButton.setText("ПРОДОЛЖИТЬ");
                    rightPanel.setBackground(Color.decode("#EE3939"));
                    rightButton.setForeground(Color.decode("#EE3939"));
                    rightTimer.pause();
                }
            } else {
                // If prep timer is not over, toggle the prep timer
                if (rightPrepTimer.isPaused()) {
                    playSoundOnce(soundPath);
                    rightPanel.setBackground(Color.decode("#EE3939")); // Use appropriate color for prep timer
                    rightButton.setText("ПАУЗА");
                    rightButton.setForeground(Color.decode("#EE3939"));
                    rightPrepTimer.start();
                } else {
                    rightButton.setText("ПРОДОЛЖИТЬ");
                    rightPanel.setBackground(Color.decode("#EE3939"));
                    rightButton.setForeground(Color.decode("#EE3939"));
                    rightPrepTimer.pause();
                }
            }
        }
        else {
            if (rightTimer.isPaused()) {
                playSoundOnce(soundPath);
                rightPanel.setBackground(Color.decode("#3AAF37"));
                rightButton.setText("ПАУЗА");
                rightButton.setForeground(Color.decode("#3AAF37"));
                rightTimer.start();
            } else {
                rightButton.setText("ПРОДОЛЖИТЬ");
                rightPanel.setBackground(Color.decode("#EE3939"));
                rightButton.setForeground(Color.decode("#EE3939"));
                rightTimer.pause();
            }
        }
    }

    public void nextSeries() {
        seriesNumber++;
        if (seriesNumber > maxSeriesNumber) {
            return;
        }
        seriesLabel.setText("СЕРИЯ: " + seriesNumber);

        leftButton.setText("СТАРТ");
        rightButton.setText("СТАРТ");

        rightPanel.setBackground(Color.decode("#EE3939"));
        leftPanel.setBackground(Color.decode("#EE3939"));

        leftTimer.pause();
        rightTimer.pause();
        leftTimer.reset(initialTimerValue);
        rightTimer.reset(initialTimerValue);
        rightPrepTimer.reset(passedPrepTime);
        leftPrepTimer.reset(passedPrepTime);
        leftLabel.setText(String.valueOf(0));
        rightLabel.setText(String.valueOf(0));
        wasTriggered = false;
        prepTimeToggled = false;
        playEndingSoundMultipleTimes(soundPath, 3);

    }

    public void playSoundOnce(String soundFilePath) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(soundFilePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.setFramePosition(0);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playSoundMultipleTimes(String soundFilePath, int times) {
        int delayBetweenBeeps = 0;
        int[] count = {0};

        Timer soundTimer = new Timer( delayBetweenBeeps, e -> {
            if (count[0] < times && haveSignal) {
                playSoundOnce(soundFilePath);
                count[0]++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        soundTimer.start();
    }

    public void playEndingSoundMultipleTimes(String soundFilePath, int times) {
        int delayBetweenBeeps = 500;
        int[] count = {0};

        Timer soundTimer = new Timer( delayBetweenBeeps, e -> {
            if (count[0] < times && haveSignal) {
                playSoundOnce(soundFilePath);
                count[0]++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        soundTimer.start();
    }

    public JPanel displayGUI() {
        JPanel frame = new JPanel();
        frame.setSize(1400, 700);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JButton nextSeriesButton = new JButton("СЛЕД. СЕРИЯ");
        nextSeriesButton.addActionListener(e -> nextSeries());
        topPanel.add(seriesLabel);
        topPanel.add(nextSeriesButton);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(0, 30, 0, 30);
        c.fill = GridBagConstraints.BOTH;

        leftPanel.add(leftLabel, c);
        rightPanel.add(rightLabel, c);

        c.gridy = 1;
        leftPanel.add(leftButton, c);
        rightPanel.add(rightButton, c);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        leftLabel.setText(String.valueOf(0));
        rightLabel.setText(String.valueOf(0));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);

        return frame;
    }
}