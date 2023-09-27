package swing;

import util.Condition;
import util.SecondCondition;
import util.Timer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

import javax.swing.*;
import java.awt.*;
import java.io.File;

class GameScreen extends JPanel {

    private Timer timer;
    private JPanel panel;
    private JPanel mainPanel;

    private String currentDir = System.getProperty("user.dir");
    private String soundPath = currentDir + "/main/java/swing/countdown-start.wav";
    private String soundPath2 = currentDir + "/main/java/swing/countdown-start-2.wav";
    public GameScreen(Timer timer) {
        this.timer = timer;
        setLayout(new GridBagLayout());
        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(1400, 700));
        panel.setMinimumSize(new Dimension(1400, 700));

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 1;
        c.gridx = 1;
        c.fill = GridBagConstraints.BOTH;

        add(panel, c);
    }
    public void paint() {
        SecondCondition currentCondition = timer.tic();
        while (currentCondition.getType() != Condition.Type.END) {
            mainPanel.removeAll();
            System.out.println(currentCondition.getType());
            System.out.println(Condition.Type.END);
            if (currentCondition.getType() == Condition.Type.PREGAME) {
                preGame(currentCondition);
            } else if (currentCondition.getType() == Condition.Type.GAME) {
                preGame(currentCondition);
            } else if (currentCondition.getType() == Condition.Type.REST) {
                rest(currentCondition);
            }

            mainPanel.revalidate();
            mainPanel.setVisible(true);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentCondition = timer.tic();
        }
    }

    public void startScreen() {
        Color backColor = Color.decode("#EE3939");
        panel.setBackground(backColor);
        setBackground(backColor);

        Font topButtons = new Font("Inter", Font.PLAIN, 24);

        JPanel topButtonsPanel = new JPanel(new GridBagLayout());

        GridBagConstraints buttonContains = new GridBagConstraints();
        buttonContains.gridx = 0;
        buttonContains.gridy = 0;
        buttonContains.fill = GridBagConstraints.BOTH;

        buttonContains.insets = new Insets(0, 1100, 0, 0);

        JButton settingsButton = new RoundButton("настройки", 10);
        topButtonsPanel.add(settingsButton, buttonContains);
        settingsButton.setBorder(new RoundBorder(10, Color.decode("#828282")));
        settingsButton.setFont(topButtons);
        Color color = Color.WHITE;
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * 0.85));
        settingsButton.setBackground(transparentColor);
        settingsButton.setForeground(Color.decode("#828282"));
        settingsButton.setPreferredSize(new Dimension(193, 49));

        settingsButton.addActionListener(e -> {
            SettingsScreen settingsScreen = new SettingsScreen();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(settingsScreen);
            frame.pack();
            frame.revalidate();
        });

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(20, 20, 0, 40);
        panel.add(topButtonsPanel, c);

        JPanel bottomButtonsPanel = new JPanel(new GridBagLayout());

        buttonContains.gridx = 0;
        buttonContains.gridy = 0;
        buttonContains.fill = GridBagConstraints.BOTH;
        buttonContains.insets = new Insets(0, 0, 0, 50);

        Font bottomButtons = new Font("Inter", Font.PLAIN, 48);

//        JButton reset = new RoundButton("СБРОС", 10);
//        bottomButtonsPanel.add(reset, buttonContains);
//        reset.setBorder(new RoundBorder(10, Color.decode("#828282")));
//        reset.setFont(bottomButtons);
//        reset.setBackground(Color.WHITE);
//        reset.setPreferredSize(new Dimension(280, 78));

//        buttonContains.gridx ++;

//        JButton pause = new RoundButton("ПАУЗА", 10);
//        bottomButtonsPanel.add(pause, buttonContains);
//        pause.setBorder(new RoundBorder(10, Color.decode("#D9A900")));
//        pause.setFont(bottomButtons);
//        pause.setBackground(Color.WHITE);
//        pause.setForeground(Color.decode("#D9A900"));
//        pause.setPreferredSize(new Dimension(280, 78));

//        buttonContains.gridx ++;

        RoundButton start = new RoundButton("СТАРТ", 10);
        bottomButtonsPanel.add(start, buttonContains);
        start.setBorder(new RoundBorder(10, Color.decode("#3AAF37")));
        start.setFont(bottomButtons);
        start.setBackground(Color.WHITE);
        start.setForeground(Color.decode("#3AAF37"));
        start.setPreferredSize(new Dimension(280, 78));

        start.addActionListener(e -> {
            start.playSoundTwice(soundPath);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    paint();
                }
            }).start();
            topButtonsPanel.removeAll();
            bottomButtonsPanel.removeAll();
        });

        buttonContains.gridx ++;

//        JButton next = new RoundButton("СЛ. ПОД.", 10);
//        bottomButtonsPanel.add(next, buttonContains);
//        next.setBorder(new RoundBorder(10, Color.decode("#AC0707")));
//        next.setFont(bottomButtons);
//        next.setBackground(Color.WHITE);
//        next.setForeground(Color.decode("#AC0707"));
//        next.setPreferredSize(new Dimension(280, 78));


        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 60, 60, 40);
        bottomButtonsPanel.setBackground(backColor);
        panel.add(bottomButtonsPanel, c);

        mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints mainContains = new GridBagConstraints();
        mainContains.gridx = 0;
        mainContains.gridy = 0;
        mainContains.insets = new Insets(30, 20, 0, 0);

        Label playersLabel = new Label("AB");
        playersLabel.setFont(new Font("Inter", Font.PLAIN, 116));
        playersLabel.setForeground(Color.WHITE);
        playersLabel.setBackground(backColor);
        mainPanel.add(playersLabel, mainContains);

        c.fill = GridBagConstraints.BOTH;

        Label timerLabel = new Label("00");
        timerLabel.setFont(new Font("Inter", Font.PLAIN, 270));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBackground(backColor);

        mainContains.gridx = 1;
        mainContains.insets = new Insets(30, 238, 0, 0);
        mainPanel.add(timerLabel, mainContains);

        Label seriesLabel = new Label("1п");
        seriesLabel.setFont(new Font("Inter", Font.PLAIN, 116));
        seriesLabel.setForeground(Color.WHITE);
        seriesLabel.setBackground(backColor);

        mainContains.gridx = 2;
        mainContains.insets = new Insets(30, 271, 0, 60);
        mainPanel.add(seriesLabel, mainContains);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(mainPanel, c);
        mainPanel.setBackground(backColor);
        topButtonsPanel.setBackground(backColor);
    }

    public void altStartScreen() {
        setLayout(new GridBagLayout());

        setSize(1400, 700);
        setMinimumSize(new Dimension(1400, 700));
        Color backColor = Color.decode("#EE3939");

        setBackground(backColor);

        Font topButtons = new Font("Inter", Font.PLAIN, 24);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel topButtonsPanel = new JPanel(new GridBagLayout());

        GridBagConstraints buttonContains = new GridBagConstraints();
        buttonContains.gridx = 0;
        buttonContains.gridy = 0;
        buttonContains.insets = new Insets(0, 0, 0, 20);    //change bottom

        JButton soundFirst = new RoundButton("1 сигнал", 5);
        topButtonsPanel.add(soundFirst, buttonContains);
        soundFirst.setBorder(new RoundBorder(5, backColor));
        soundFirst.setFont(topButtons);
        soundFirst.setBackground(Color.WHITE);
        soundFirst.setPreferredSize(new Dimension(161, 49));

        buttonContains.gridx ++;

        JButton soundSecond = new RoundButton("2 сигнала", 5);
        topButtonsPanel.add(soundSecond, buttonContains);
        soundSecond.setBorder(new RoundBorder(5, backColor));
        soundSecond.setFont(topButtons);
        soundSecond.setBackground(Color.WHITE);
        soundSecond.setPreferredSize(new Dimension(161, 49));

        buttonContains.gridx ++;

        JButton soundThird = new RoundButton("3 сигнала", 5);
        topButtonsPanel.add(soundThird, buttonContains);
        soundThird.setBorder(new RoundBorder(5, backColor));
        soundThird.setFont(topButtons);
        soundThird.setBackground(Color.WHITE);
        soundThird.setPreferredSize(new Dimension(161, 49));

        buttonContains.gridx ++;
        buttonContains.insets = new Insets(0, 477, 0, 0);

        JButton settingsButton = new RoundButton("настройки", 5);
        topButtonsPanel.add(settingsButton, buttonContains);
        settingsButton.setBorder(new RoundBorder(5, backColor));
        settingsButton.setFont(topButtons);
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setPreferredSize(new Dimension(233, 49));

        settingsButton.addActionListener(e -> {
            AltSettingsScreen settingsScreen = new AltSettingsScreen();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(settingsScreen);
            frame.pack();
            frame.revalidate();
        });

        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(55, 20, 0, 40);
        add(topButtonsPanel, c);

        JPanel bottomButtonsPanel = new JPanel(new GridBagLayout());

        buttonContains.gridx = 0;
        buttonContains.gridy = 0;
        buttonContains.insets = new Insets(0, 0, 0, 40);    //change bottom

        Font bottomButtons = new Font("Inter", Font.PLAIN, 48);

        JButton reset = new RoundButton("СБРОС", 10);
        bottomButtonsPanel.add(reset, buttonContains);
        reset.setBorder(new RoundBorder(10, Color.decode("#828282")));
        reset.setFont(bottomButtons);
        reset.setBackground(Color.WHITE);
        reset.setPreferredSize(new Dimension(280, 78));

        buttonContains.gridx ++;

        JButton pause = new RoundButton("ПАУЗА", 10);
        bottomButtonsPanel.add(pause, buttonContains);
        pause.setBorder(new RoundBorder(10, Color.decode("#D9A900")));
        pause.setFont(bottomButtons);
        pause.setBackground(Color.WHITE);
        pause.setForeground(Color.decode("#D9A900"));
        pause.setPreferredSize(new Dimension(280, 78));

        buttonContains.gridx ++;

        JButton start = new RoundButton("СТАРТ", 10);
        bottomButtonsPanel.add(start, buttonContains);
        start.setBorder(new RoundBorder(10, Color.decode("#3AAF37")));
        start.setFont(bottomButtons);
        start.setBackground(Color.WHITE);
        start.setForeground(Color.decode("#3AAF37"));
        start.setPreferredSize(new Dimension(280, 78));

        start.addActionListener(e -> {
            GameScreen preGameScreen = new GameScreen(timer);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(preGameScreen);
            preGameScreen.setPreferredSize(new Dimension(1400, 700));
            frame.pack();
            frame.revalidate();
            frame.repaint();
            preGameScreen.paint();
        });

        buttonContains.gridx ++;

        JButton next = new RoundButton("СЛ. ПОД.", 10);
        bottomButtonsPanel.add(next, buttonContains);
        next.setBorder(new RoundBorder(10, Color.decode("#AC0707")));
        next.setFont(bottomButtons);
        next.setBackground(Color.WHITE);
        next.setForeground(Color.decode("#AC0707"));
        next.setPreferredSize(new Dimension(280, 78));

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0, 60, 60, 40);
        bottomButtonsPanel.setBackground(backColor);
        add(bottomButtonsPanel, c);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints mainContains = new GridBagConstraints();
        mainContains.gridx = 0;
        mainContains.gridy = 0;
        mainContains.insets = new Insets(30, 20, 0, 0);

        c.fill = GridBagConstraints.BOTH;

        Label seriesLabel = new Label("1п");
        seriesLabel.setFont(new Font("Inter", Font.PLAIN, 270));
        seriesLabel.setForeground(Color.WHITE);
        seriesLabel.setBackground(backColor);

        mainContains.gridx = 1;
        mainContains.insets = new Insets(30, 200, 0, 200);
        mainPanel.add(seriesLabel, mainContains);

        c.gridx = 0;
        c.gridy = 2;
        add(mainPanel, c);
        mainPanel.setBackground(backColor);
        topButtonsPanel.setBackground(backColor);
    }

    public void preGame(SecondCondition secondCondition) {
        panel.setBackground(Color.decode("#3AAF37"));
        setBackground(Color.decode("#3AAF37"));
        mainPanel.setBackground(Color.decode("#3AAF37"));
        if (secondCondition.getPlayerName().isEmpty()){
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 0;
            c.gridx = 0;
            c.insets = new Insets(20, 20, 0, 40);
            c.fill = GridBagConstraints.HORIZONTAL;

            JLabel timerLabel = new JLabel(Integer.toString(secondCondition.getTimeLeft()));
            timerLabel.setFont(new Font("Inter", Font.PLAIN, 270));
            timerLabel.setForeground(Color.WHITE);

            mainPanel.add(timerLabel, c);
            if (secondCondition.getTimeLeft() < 10) {
                c.insets = new Insets(30, 240, 0, 160);
                panel.setBackground(Color.decode("#FFF600"));
                mainPanel.setBackground(Color.decode("#FFF600"));
                setBackground(Color.decode("#FFF600"));
            }
            else {
                c.insets = new Insets(30, 160, 0, 160);
            }

            c.gridy++;

            JLabel seriesLabel = new JLabel(secondCondition.getGameCount() + "п");
            seriesLabel.setFont(new Font("Inter", Font.PLAIN, 116));
            seriesLabel.setForeground(Color.WHITE);

            mainPanel.add(seriesLabel, c);
        }
        else {

            if (secondCondition.getTimeLeft() == 1){
                try {
                    File soundFile = new File(soundPath2);
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 0;
            c.gridx = 0;
            c.insets = new Insets(124, 30, 0, 0);
            c.fill = GridBagConstraints.BOTH;

            JLabel playersNameLabel = new JLabel(secondCondition.getPlayerName());
            playersNameLabel.setFont(new Font("Inter", Font.PLAIN, 116));
            playersNameLabel.setForeground(Color.WHITE);
            mainPanel.add(playersNameLabel, c);

            c.gridx++;

            JLabel timerLabel = new JLabel(Integer.toString(secondCondition.getTimeLeft()));
            timerLabel.setFont(new Font("Inter", Font.PLAIN, 270));
            timerLabel.setForeground(Color.WHITE);

            JButton pause = new RoundButton("ПАУЗА", 20);

            if (secondCondition.getTimeLeft() == 5){
                try {
                    File soundFile = new File(soundPath);
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (secondCondition.getTimeLeft() < 5 ) {
                c.insets = new Insets(80, 352, 0, 354);
                panel.setBackground(Color.decode("#FFF600"));
                mainPanel.setBackground(Color.decode("#FFF600"));
                setBackground(Color.decode("#FFF600"));
                pause.setForeground(Color.decode("#FFF600"));
                pause.setBorder(new RoundBorder(20, Color.decode("#FFF600")));
            }
            else {
                c.insets = new Insets(80, 238, 0, 271);
                pause.setForeground(Color.decode("#3AAF37"));
                pause.setBorder(new RoundBorder(20, Color.decode("#3AAF37")));

            }

            mainPanel.add(timerLabel, c);

            c.gridx++;

            JLabel seriesLabel = new JLabel(secondCondition.getGameCount() + "п");
            seriesLabel.setFont(new Font("Inter", Font.PLAIN, 116));
            seriesLabel.setForeground(Color.WHITE);
            c.insets = new Insets(124, 0, 0, 80);

            mainPanel.add(seriesLabel, c);

            c.gridx--;
            c.gridy++;

            pause.setFont(new Font("Inter", Font.PLAIN, 48));
            pause.setBackground(Color.WHITE);
            c.insets = new Insets(0, 0, 0, 0);

            c.ipadx = 100;
            c.ipady = 7;

            c.fill = GridBagConstraints.NONE;
            mainPanel.add(pause, c);
        }
   }

    private void rest(SecondCondition secondCondition) {
        mainPanel.setBackground(Color.decode("#EE3939"));
        setBackground(Color.decode("#EE3939"));
        panel.setBackground(Color.decode("#EE3939"));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        if (secondCondition.getTimeLeft() < 10)
        {
            c.insets = new Insets(80, 615, 0, 616);
        }
        else{
            c.insets = new Insets(80, 531, 0, 531);
        }

        if (secondCondition.getTimeLeft() == 1){
            try {
                File soundFile = new File(soundPath);
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JLabel timerLabel = new JLabel(Integer.toString(secondCondition.getTimeLeft()));
        timerLabel.setFont(new Font("Inter", Font.PLAIN, 270));
        timerLabel.setForeground(Color.WHITE);
        mainPanel.add(timerLabel, c);

        c.gridy++;

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonConst = new GridBagConstraints();
        buttonConst.insets = new Insets(0, 0, 40, 60);
        buttonsPanel.setBackground(Color.decode("#EE3939"));

        buttonConst.ipadx = 100;
        buttonConst.ipady = 11;

        RoundButton nextShoot = new RoundButton("СЛ. ВЫСТРЕЛ", 20);
        nextShoot.setBorder(new RoundBorder(20, Color.decode("#EE3939")));
        nextShoot.setFont( new Font("Inter", Font.PLAIN, 48));
        nextShoot.setBackground(Color.WHITE);
        nextShoot.setForeground(Color.decode("#EE3939"));
        buttonsPanel.add(nextShoot, buttonConst);

        nextShoot.addActionListener(e -> {
            nextShoot.playSoundTwice(soundPath);
        });



        buttonConst.gridy++;
        buttonConst.ipadx = 200;
        buttonConst.ipady = 11;

        buttonConst.insets = new Insets(0, 0, 40, 0);
        JButton pause = new RoundButton("ПАУЗА", 20);
        pause.setBorder(new RoundBorder(20, Color.decode("#D9A900")));
        pause.setFont(new Font("Inter", Font.PLAIN, 48));
        pause.setBackground(Color.WHITE);
        pause.setForeground(Color.decode("#D9A900"));
        buttonsPanel.add(pause, buttonConst);

        c.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(buttonsPanel, c);

//        JButton nextSeries = new RoundButton("СЛ. СЕРИЯ", 20);
//        nextSeries.setBorder(new RoundBorder(10, Color.decode("#EE3939")));
//        nextSeries.setFont( new Font("Inter", Font.PLAIN, 48));
//        nextSeries.setBackground(Color.WHITE);
//        nextSeries.setForeground(Color.decode("#EE3939"));
//        nextSeries.setPreferredSize(new Dimension(410, 78));
//        add(nextSeries, c);

//        JButton nextApproach = new RoundButton("СЛ. ПОДХОД", 20);
//        nextApproach.setBorder(new RoundBorder(10, Color.decode("#EE3939")));
//        nextApproach.setFont( new Font("Inter", Font.PLAIN, 48));
//        nextApproach.setBackground(Color.WHITE);
//        nextApproach.setForeground(Color.decode("#EE3939"));
//        nextApproach.setPreferredSize(new Dimension(410, 78));
//        add(nextApproach, c);
    }
}
