package swing;

import javax.swing.*;


public class TeamsSettingsScreen extends AltSettingsScreen {
    public TeamsSettingsScreen() {
        super();
        contentPanel.setModeLabel("Команды");

//        buttonsPanel.saveButton.addActionListener(e -> {
//                JFrame frame = (JFrame) SwingUtilities.getRoot(this);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.getContentPane().removeAll();
//                frame.getContentPane().add(getJPanel());
//                frame.revalidate();
//                frame.repaint();
//        });
    }
}
