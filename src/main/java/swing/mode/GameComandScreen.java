package swing.mode;

import org.jetbrains.annotations.NotNull;
import swing.RoundBorder;
import swing.RoundButton;
import swing.settings.MainSettingsScreen;
import swing.settings.SettingsScreen;
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

public abstract class GameComandScreen extends JPanel {
    private final Timer timer;
    protected JPanel panel;
    protected JPanel mainPanel;
    protected JPanel topPanel;
    protected Color COLOR;
    private boolean isPaused;
    private boolean nextMove;
    private final boolean haveSignals;
    private final int BORDER_RADIUS = 20;
    private final RoundBorder BORDER = new RoundBorder(BORDER_RADIUS, COLOR);
    private final Font BUTTON_FONT = new Font("Inter", Font.BOLD, 26);
    public GameComandScreen(Timer timer, boolean haveSignals) {
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
        startScreen();
    }
    JPanel preGame(SecondCondition secondCondition){
        GridBagConstraints c = setCommonBackground(COLOR);
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        JPanel tmp = new JPanel(new GridBagLayout());
        tmp.setBackground(COLOR);
        tmp.add(getMainPanel(secondCondition), c);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 0, 300, 0);
        tmp.add(addButtonPanel(), c);
        tmp.setPreferredSize(new Dimension(1400, 700));
        return tmp;
    }
    abstract JPanel getMainPanel(SecondCondition... secondCondition);
    abstract void timerInsetsController(SecondCondition secondCondition, GridBagConstraints c);
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
    protected JPanel addButtonPanel() {
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(COLOR);

        GridBagConstraints buttonConst = new GridBagConstraints();
        buttonConst.insets = new Insets(60, 420, 60, 60);
        buttonConst.ipadx = 100;
        buttonConst.ipady = 6;

        RoundButton nextApproach = getBottomButton("СЛ. ПОДХОД");

        buttonsPanel.add(nextApproach, buttonConst);

        nextApproach.addActionListener(e -> nextMove = true);
        nextApproach.addActionListener(e -> nextApproach.playSound(getFilePath(2), haveSignals));

        buttonConst.ipady = 6;
        buttonConst.insets = new Insets(60, 0, 60, 450);

        RoundButton pause = getBottomButton(isPaused ? "ПРОДОЛЖИТЬ" : "ПАУЗА");
        buttonConst.ipadx = isPaused ? 90 : 180;

        pause.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = !isPaused;
                buttonConst.ipadx = 90;
                pause.setText("ПРОДОЛЖИТЬ");
            }
        });
        if (isPaused){
            pause.setForeground(getBackground().darker());
            pause.setBorder(new RoundBorder(BORDER_RADIUS, Color.WHITE));
        }
        buttonsPanel.add(pause, buttonConst);
        return buttonsPanel;
    }
    private void startScreen(){
        GridBagConstraints c = new GridBagConstraints();
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.add(getSettingsButton(Color.decode("#EE3939")), c);
        c.gridy = 1;

        c.insets = new Insets(0, 0, 60, 0);
        mainPanel.add(getMainPanel(), c);

        c.gridy = 2;
        c.insets = new Insets(0, 520, 0, 540);
        c.fill = GridBagConstraints.BOTH;
        mainPanel.add(getStartButton(), c);
        panel.add(mainPanel);
    }
    private void rest(SecondCondition secondCondition) {
        GridBagConstraints c = setCommonBackground(COLOR);
        JPanel tmp = new JPanel(new GridBagLayout());
        c.fill = GridBagConstraints.CENTER;
        tmp.setBackground(COLOR);
        TimerLabel timerLabel = new TimerLabel(secondCondition);
        tmp.add(timerLabel, c);
        c.gridy++;
        c.insets = new Insets(0, 0, 200, 20);
        tmp.add(addButtonPanel(), c);
        tmp.setPreferredSize(new Dimension(1400, 560));
        c.insets = new Insets(0, 0, 200, 75);
        mainPanel.add(tmp, c);
    }
    private void paint() {
        SecondCondition currentCondition = timer.tic();
        while (currentCondition.getType() != Condition.Type.END) {
            mainPanel.removeAll();
            switchColor(currentCondition);
            playSound(currentCondition, haveSignals);
            if (currentCondition.getType() == Condition.Type.PREGAME) {
                preGame(currentCondition);
                mainPanel.revalidate();
            } else if (currentCondition.getType() == Condition.Type.GAME) {
                preGame(currentCondition);
                mainPanel.revalidate();
            } else if (currentCondition.getType() == Condition.Type.REST) {
                rest(currentCondition);
                mainPanel.revalidate();
            }
//            mainPanel.revalidate();
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
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new MainSettingsScreen());
        frame.revalidate();
    }
    private void switchColor(SecondCondition secondCondition) {
        Color RED = Color.decode("#EE3939");
        Color GREEN = Color.decode("#3AAF37");
        Color YELLOW = Color.decode("#f1dd38");
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
    private void playSound(SecondCondition secondCondition, boolean haveSignals) {
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
    @NotNull
    private RoundButton getStartButton() {
        RoundButton startButton = new RoundButton("СТАРТ", 10);
        startButton.setBorder(new RoundBorder(10, Color.decode("#EE3939")));
        startButton.setFont(BUTTON_FONT);
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.decode("#EE3939"));
        startButton.setPreferredSize(new Dimension(140, 48));

        startButton.addActionListener(e -> {
            startButton.playSound(getFilePath(1), haveSignals);
            startButton.setVisible(false);
            topPanel.setVisible(false);
            new Thread(this::paint).start();
        });
        return startButton;
    }
    private JPanel getSettingsButton(Color panelColor) {
        RoundButton settingsButton = new RoundButton("настройки", 10);
        settingsButton.setBorder(new RoundBorder(10, Color.decode("#828282")));
        settingsButton.setFont(new Font("Inter", Font.PLAIN, BORDER_RADIUS));
        Color color = Color.WHITE;
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * 0.85));
        settingsButton.setBackground(transparentColor);
        settingsButton.setForeground(Color.decode("#828282"));
        settingsButton.setPreferredSize(new Dimension(130, 39));

        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(panelColor);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 1150, 30, 0);

        topPanel.add(settingsButton, c);

        settingsButton.addActionListener(e -> {
            SettingsScreen settingsScreen = new MainSettingsScreen();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(settingsScreen);
            frame.pack();
            frame.revalidate();
        });
        return topPanel;
    }
    private RoundButton getBottomButton(String text){
        RoundButton button = new RoundButton(text, BORDER_RADIUS);
        button.setFont(BUTTON_FONT);
        button.setBorder(BORDER);
        button.setBackground(Color.WHITE);
        button.setForeground(COLOR);
        button.setPreferredSize(new Dimension(180, 15));
        return button;
    }
    class TimerLabel extends JLabel {
        public TimerLabel() {
            setText("00");
            setFont(new Font("Inter", Font.PLAIN, 500));
            setForeground(Color.WHITE);
            setPreferredSize(new Dimension(600, 390));
        }
        public TimerLabel(SecondCondition secondCondition) {
            this();
            setText(Integer.toString(secondCondition.getTimeLeft()));
            if (!secondCondition.getType().toString().equals("END") && isPaused) this.setIgnoreRepaint(true);
        }
    }
    static class GameCountLabel extends JLabel {
        String gameCount;
        public GameCountLabel() {
            setText("1п");
            setFont(new Font("Inter", Font.PLAIN, 116));
            setForeground(Color.WHITE);
        }
        public GameCountLabel(SecondCondition secondCondition) {
            this();
            gameCount = secondCondition.getType().toString().equals("PREGAME") ?
                    secondCondition.getGameCount() + "п" : secondCondition.getGameCount() + " ";
            setText(gameCount);
        }
    }
    static class PlayerNameLabel extends JLabel {
        public PlayerNameLabel() {
            setText("AB");
            setFont(new Font("Inter", Font.PLAIN, 116));
            setForeground(Color.WHITE);
        }
        public PlayerNameLabel(SecondCondition secondCondition) {
            this();
            setText(secondCondition.getPlayerName());
        }
    }
}
