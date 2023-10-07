package swing;

import swing.settings.MainSettingsScreen;

import javax.swing.*;
import java.awt.*;

public class MainSwing {
    private final JFrame frame;
    public MainSwing() {
        frame = new JFrame("archemee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 700);
        frame.setLocation(600, 400);
//        frame.setLocation(20, 100);
        frame.setMinimumSize(new Dimension(1400, 700));
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);
        frame.getContentPane().removeAll();
        frame.getContentPane().add( new MainSettingsScreen());
        frame.revalidate();
    }

    public static void main(String[] args) {
        MainSwing gameController = new MainSwing();
        gameController.frame.setVisible(true);
    }
}