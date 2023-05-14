package swing;

import javax.swing.*;
import java.awt.*;

public class MainSwing {
    private SettingsScreen settingsScreen;
    private JFrame frame;

    public MainSwing() {
        settingsScreen = new SettingsScreen();
        frame = new JFrame("archemee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 700);
        frame.setMinimumSize(new Dimension(1400, 700));
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(settingsScreen);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        MainSwing gameController = new MainSwing();
        gameController.frame.setVisible(true);
    }
}