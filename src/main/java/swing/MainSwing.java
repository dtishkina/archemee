package swing;

import javax.swing.*;
import java.awt.*;


public class MainSwing {
    private SettingsScreen settingsScreen;
    private AltSettingsScreen alternativeShootScreen;
    private GameScreen gameScreen;
    private JFrame frame;

    public MainSwing() {
//         создание экранов и настройка фрейма
        settingsScreen = new SettingsScreen();
        alternativeShootScreen = new AltSettingsScreen();
        //gameScreen = new GameScreen();
        frame = new JFrame("archemee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 700);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setMinimumSize(new Dimension(1400, 700));
        frame.setVisible(true);
        showSettingsScreen();
    }

    public void showSettingsScreen() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(settingsScreen);
        frame.revalidate();
        frame.repaint();
    }
//
//    public void showAlternativeShootScreen() {
//        frame.getContentPane().removeAll();
//        frame.getContentPane().add(alternativeShootScreen);
//        frame.revalidate();
//        frame.repaint();
//    }

//    public void showGameScreen(boolean alternativeShootMode) {
//        frame.getContentPane().removeAll();
//        frame.getContentPane().add(gameScreen);
//        gameScreen.startGame(alternativeShootMode);
//        frame.revalidate();
//        frame.repaint();
//    }

//    }

    public static void main(String[] args) {
        MainSwing gameController = new MainSwing();
        gameController.frame.setVisible(true);
    }
}