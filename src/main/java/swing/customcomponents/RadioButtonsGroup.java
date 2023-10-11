package swing.customcomponents;

import swing.settings.MainSettingsScreen;

import javax.swing.*;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class RadioButtonsGroup {
    CustomButton option1;
    CustomButton option2;
    ButtonGroup group;
    public RadioButtonsGroup(String opt1, String opt2) {
        this.option1 = new CustomButton(opt1);
        this.option2 = new CustomButton(opt2);

        group = new ButtonGroup();
        group.add(this.option1);
        group.add(this.option2);

       ActionListener listener = e -> {
           String command = e.getActionCommand();
           if (command.equals("Переменная")){
               MainSettingsScreen.rotation = true;
           } else if (command.equals("Постоянная")){
               MainSettingsScreen.rotation = false;
           }
       };
        option1.addActionListener(listener);
        option2.addActionListener(listener);
    }
    public JRadioButton getSelectedButton() {
        ArrayList<CustomButton> buttons = new ArrayList<>(Arrays.asList(option1, option2));
        for (JRadioButton button : buttons) {
            if (button.isSelected()) {
                return button;
            }
        }
        return null;
    }
    public JPanel getGroupPanel() {
        JPanel groupPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Font font = new Font("Inter", Font.PLAIN, 20);
        groupPanel.setBackground(Color.decode("#F6F6F6"));
        option1.setFont(font);
        option2.setFont(font);

        c.ipadx = 34;
        c.ipady = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        groupPanel.add(option1, c);

        c.gridx++;
        groupPanel.add(option2, c);

        return groupPanel;
    }
    public static class CustomButton extends JRadioButton {
        Color color = Color.decode("#828282");
        Color enabled;
        public CustomButton(String text){
            setOpaque(false);
            setFocusPainted(false);
            setText(text);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            enabled = Color.decode("#3AAF37");

            setUI(new BasicRadioButtonUI() {
                @Override
                protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
                    super.paintText(g, b, new Rectangle(textRect.x + 7, textRect.y, textRect.width, textRect.height), text);
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(color.darker().darker());
                    setForeground(color.darker().darker());
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setForeground(color.darker());
                    setBackground(color.darker());
                }
                @Override
                public void mousePressed(MouseEvent e){
                    setBackground(enabled);
                    setForeground(color.darker().darker().darker());
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    setForeground(color.darker());
                    setBackground(color.darker());
                }
            });
        }

        @Override
        public void paint(Graphics g){
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color circleBackground = Color.decode("#F6F6F6");

            int radius = 20;
            int y1 = (getHeight() - radius)/2;
            if(isSelected()){
                g2.setColor(isEnabled() ? enabled: color);
                g2.fillOval(1, y1, radius, radius);
                g2.setColor(circleBackground);
                g2.fillOval(2, y1 + 1 , radius-2, radius-2);
                g2.setColor(isEnabled() ? enabled : color);
                g2.fillOval(4, y1 + 3, radius-6, radius-6);
            } else {
                g2.setColor(isFocusOwner() ? getBackground() : color);
                g2.fillOval(1, y1, radius, radius);
                g2.setColor(circleBackground);
                g2.fillOval(2, y1 + 1 , radius-2, radius-2);
            }
            g2.dispose();
        }
    }
}
