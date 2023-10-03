package swing;

import util.Condition;
import util.SecondCondition;
import util.Timer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Objects;

class GameCommandScreen extends JPanel {
    private final Timer timer;
    protected JPanel panel;
    protected JPanel mainPanel;
    private boolean isPaused;
    private boolean nextMove;
    private final boolean haveSignals;
    protected Color COLOR;
    protected RoundBorder BORDER = new RoundBorder(20, COLOR);
    protected static Font BUTTON_FONT = new Font("Inter", Font.PLAIN, 48);

    public GameCommandScreen(Timer timer, boolean haveSignals) {
        this.timer = timer;
        this.isPaused = false;
        this.nextMove = false;
        this.haveSignals = haveSignals;
        setLayout(new GridBagLayout());
        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(1400, 700));
        panel.setMinimumSize(new Dimension(1400, 700));

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;

        add(panel, c);
    }

    public void paint() {
        SecondCondition currentCondition = timer.tic();
        while (currentCondition.getType() != Condition.Type.END) {
            mainPanel.removeAll();
            playSound(currentCondition, haveSignals);
            switchColor(currentCondition);
            if (currentCondition.getType() == Condition.Type.PREGAME) {
                preGame(currentCondition);
            } else if (currentCondition.getType() == Condition.Type.GAME) {
                preGame(currentCondition);
            } else if (currentCondition.getType() == Condition.Type.REST) {
                switchColor(currentCondition);
                rest(currentCondition);
            }
            mainPanel.revalidate();
            mainPanel.setVisible(true);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!isPaused) {
                currentCondition = timer.tic();
            }
            if (nextMove) {
                while (currentCondition.getTimeLeft() > 1) {
                    currentCondition = timer.tic();
                }
                currentCondition = timer.tic();
                nextMove = false;
            }
        }
        SettingsScreen settingsScreen = new MainSettingsScreen();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(settingsScreen);
        frame.pack();
        frame.revalidate();
    }

    protected void switchColor(SecondCondition secondCondition) {
        Color RED = Color.decode("#EE3939");
        Color GREEN = Color.decode("#3AAF37");
        Color YELLOW = Color.decode("#FFF600");
        if (secondCondition.getType().toString().equals("PREGAME") ||
                secondCondition.getType().toString().equals("GAME")) {
            COLOR = GREEN;
            if (secondCondition.getTimeLeft() < 6) {
                COLOR = YELLOW;
            }
        }
        if (secondCondition.getType().toString().equals("REST")) {
            COLOR = RED;
        }
    }

    protected void playSound(SecondCondition secondCondition, boolean haveSignals) {
        if (haveSignals) {
            File soundFile = null;
            try {
                if (secondCondition.getTimeLeft() == 1) {
                    if (secondCondition.getType().toString().equals("PREGAME") ||
                            (secondCondition.getType().toString().equals("GAME"))) {
                        soundFile = new File(Objects.requireNonNull(getFilePath(2)));
                    }
                    if (secondCondition.getType().toString().equals("REST")) {
                        soundFile = new File(Objects.requireNonNull(getFilePath(1)));
                    }
                }
                if (secondCondition.getTimeLeft() == 5) {
                    soundFile = new File(Objects.requireNonNull(getFilePath(1)));
                }
                if (soundFile != null) {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                    clip.open(audioInputStream);
                    clip.start();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String getFilePath(int repeat) {
        String currentDir = System.getProperty("user.dir");
        String soundPath = currentDir + "/src/main/java/swing/countdown-start.wav";
        String soundPath2 = currentDir + "/src/main/java/swing/countdown-start-2.wav";
        switch (repeat) {
            case 1 -> {
                return soundPath;
            }
            case 2 -> {
                return soundPath2;
            }
            default -> {
                return null;
            }
        }
    }

    public void startScreen() {
        mainPanel = new JPanel(new GridBagLayout());

        Color background = Color.decode("#EE3939");
        GridBagConstraints c = setCommonBackground(background);
        c.insets = new Insets(20, 1120, 0, 40);

        JButton settingsButton = new RoundButton("настройки", 10);
        settingsButton.setBorder(new RoundBorder(10, Color.decode("#828282")));
        settingsButton.setFont(new Font("Inter", Font.PLAIN, 24));
        Color color = Color.WHITE;
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * 0.85));
        settingsButton.setBackground(transparentColor);
        settingsButton.setForeground(Color.decode("#828282"));
        settingsButton.setPreferredSize(new Dimension(193, 49));

        panel.add(settingsButton, c);

        settingsButton.addActionListener(e -> {
            SettingsScreen settingsScreen = new MainSettingsScreen();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(settingsScreen);
            frame.pack();
            frame.revalidate();
        });

        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(mainPanel, c);

        RoundButton startButton = new RoundButton("СТАРТ", 10);
        startButton.setBorder(new RoundBorder(10, background));
        startButton.setFont(BUTTON_FONT);
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(background);
        startButton.setPreferredSize(new Dimension(280, 78));

        startButton.addActionListener(e -> {
            startButton.playSound(getFilePath(1), haveSignals);
            startButton.setVisible(false);
            settingsButton.setVisible(false);
            new Thread(this::paint).start();
        });

        c.gridy = 2;
        c.insets = new Insets(0, 500, 80, 550);
        panel.add(startButton, c);
    }

    public void preGame(SecondCondition secondCondition){
        GridBagConstraints c = setCommonBackground(COLOR);
    }

    public void rest(SecondCondition secondCondition){
        GridBagConstraints c = setCommonBackground(COLOR);

        JLabel timerLabel = new TimerLabel(secondCondition);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(40, 500, 40, 0);

        mainPanel.add(timerLabel, c);

        c.gridy++;
        c.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(addButtonPanel(secondCondition), c);
    }

    public void timerInsetsController(SecondCondition secondCondition, GridBagConstraints c){
        if (secondCondition.getTimeLeft() < 6) {
            c.insets = new Insets(40, 352, 40, 354);
        } else if (secondCondition.getTimeLeft() < 10) {
            c.insets = new Insets(40, 352, 40, 354);
        } else {
            c.insets = new Insets(40, 238, 40, 271);
        }
    }
    protected GridBagConstraints setCommonBackground(Color color) {
        mainPanel.setBackground(color);
        panel.setBackground(color);
        setBackground(color);

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;

        return c;
    }

    protected JPanel addButtonPanel(SecondCondition secondCondition) {
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(COLOR);

        GridBagConstraints buttonConst = new GridBagConstraints();
        buttonConst.insets = new Insets(20, 40, 80, 60);
        buttonConst.ipadx = 100;
        buttonConst.ipady = 11;

        RoundButton nextApproach = new RoundButton("СЛ. ПОДХОД", 20);
        nextApproach.setFont(BUTTON_FONT);
        nextApproach.setBorder(BORDER);
        nextApproach.setBackground(Color.WHITE);
        nextApproach.setForeground(COLOR);
        nextApproach.setPreferredSize(new Dimension(410, 78));

        buttonsPanel.add(nextApproach, buttonConst);

        nextApproach.addActionListener(e -> nextMove = true);
        nextApproach.addActionListener(e -> nextApproach.playSound(getFilePath(2), haveSignals));

        buttonConst.ipadx = 130;
        buttonConst.ipady = 11;
        buttonConst.insets = new Insets(20, 0, 80, 0);

        RoundButton pause = new RoundButton("ПАУЗА", 20);
        pause.setBorder(BORDER);
        pause.setFont(BUTTON_FONT);
        pause.setBackground(Color.WHITE);
        pause.setForeground(COLOR);
        pause.setPreferredSize(new Dimension(410, 78));

        pause.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = !isPaused;
                if (isPaused) {
                    pause.setText("ВОЗОБНОВИТЬ");
                } else {
                    pause.setText("ПАУЗА");
                }
            }
        });

        if (secondCondition.getType().toString().equals("PREGAME") ||
                (secondCondition.getType().toString().equals("GAME"))) {
            if (secondCondition.getTimeLeft() < 6) {
                panel.setBackground(COLOR);
                mainPanel.setBackground(COLOR);
                buttonsPanel.setBackground(COLOR);
                setBackground(COLOR);
                pause.setForeground(COLOR);
                pause.setBorder(BORDER);
                nextApproach.setForeground(COLOR);
                nextApproach.setBorder(BORDER);
            } else if (secondCondition.getTimeLeft() < 10) {
                nextApproach.setForeground(COLOR);
                nextApproach.setBorder(BORDER);
            } else {
                pause.setForeground(COLOR);
                pause.setBorder(BORDER);
                nextApproach.setForeground(COLOR);
                nextApproach.setBorder(BORDER);
            }
        }
        buttonsPanel.add(pause, buttonConst);
        return buttonsPanel;
    }

    class TimerLabel extends JLabel {
        public TimerLabel() {
            setText("00");
            setFont(new Font("Inter", Font.PLAIN, 270));
            setForeground(Color.WHITE);
            setBackground(COLOR);
        }
        public TimerLabel(SecondCondition secondCondition) {
            this();
            setText(Integer.toString(secondCondition.getTimeLeft()));
        }
    }

    public static class GameCountLabel extends JLabel {
        String gameCount;
        public GameCountLabel() {
            setFont(new Font("Inter", Font.PLAIN, 116));
            setForeground(Color.WHITE);
        }
        public GameCountLabel(SecondCondition secondCondition) {
            this();
            gameCount = secondCondition.getType().toString().equals("PREGAME") ?
                    secondCondition.getGameCount() + "п" : secondCondition.getGameCount() + " ";
        }
        public JLabel getGameCount() {
            setText(gameCount);
            return this;
        }
    }

    public static class PlayerNameLabel extends JLabel{
        String playerName;

        public PlayerNameLabel() {
            setFont(new Font("Inter", Font.PLAIN, 116));
            setForeground(Color.WHITE);
        }
        public PlayerNameLabel(SecondCondition secondCondition){
            this();
            playerName = secondCondition.getPlayerName();
        }
        public JLabel getPlayerName() {
            setText(playerName);
            return this;
        }
    }
}