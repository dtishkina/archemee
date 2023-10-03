package swing;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SettingsScreen extends JPanel {
    public static Color SCREEN_COLOR = Color.WHITE;
    public static int BORDER_RADIUS = 20;
    protected TimerBuilder timerBuilder;
    ContentPanel contentPanel;
    MainPanel mainPanel;
    ButtonsPanel buttonsPanel;
    ArrayList<HintTextField> hintsList;

    ArrayList <HintTextField> arr;

    int flag = 0;
    public SettingsScreen() {
        setBackground(SCREEN_COLOR);
        setPreferredSize(new Dimension(1400, 700));
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(65, 78, 0, 400);

        contentPanel = new ContentPanel();
        add(contentPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.EAST;
        constraints.insets = new Insets(47, 82, 75, 100);

        mainPanel = new MainPanel();
        add(mainPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.ipady = 46;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 60, 80, 10);

        buttonsPanel = new ButtonsPanel();
        add(buttonsPanel, constraints);
    }
    static class ContentPanel extends JPanel{
        public JLabel modeLabel;
        public ContentPanel() {
            FlowLayout layout = new FlowLayout();
            layout.setHgap(5);
            setLayout(layout);
            setBackground(SCREEN_COLOR);

            JLabel settingsLabel = new JLabel();
            settingsLabel.setHorizontalAlignment(JLabel.LEFT);
            settingsLabel.setText("Настройки");
            settingsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            settingsLabel.setFont(new Font("Inter", Font.PLAIN, 65));

            modeLabel = new JLabel();
            modeLabel.setHorizontalAlignment(JLabel.LEFT);
            modeLabel.setForeground(Color.decode("#8E8E8E"));
            modeLabel.setFont(new Font("Inter", Font.PLAIN, 40));

            add(settingsLabel);
            add(modeLabel);
        }
        public void setModeLabel(String mode){
            modeLabel.setText(mode);
            modeLabel.setBorder(BorderFactory.createEmptyBorder(17, 0, 0, 0));
            if (mode.length() <= 8) {
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 350));
            } else {
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 40));
            }
        }
    }

    class MainPanel extends JPanel{
        public static Font LABEL_FONT = new Font("Inter", Font.PLAIN, 24);
        public static Font hints = new Font("Inter", Font.PLAIN, 20);
        public static Color backgroundColor = Color.decode("#F6F6F6");
        HintTextField playersNumber_ = new HintTextField("ввод...");
        HintTextField rotation_ = new HintTextField("ввод...");
        HintTextField targetingSeriesNumber_ = new HintTextField("ввод...");
        HintTextField testSeriesNumber_ = new HintTextField("ввод...");
        HintTextField prepareTime_ = new HintTextField("ввод...");
        HintTextField durationSeries_ = new HintTextField("ввод...");
        HintTextField completionWarning_ = new HintTextField("ввод...");

        public MainPanel(){
            setLayout(new GridBagLayout());
            setBackground(backgroundColor);
            if(SettingsScreen.this instanceof MainSettingsScreen) {
                setBorder(BorderFactory.createEmptyBorder(24, 10, 0, 130));
            } else {
                setBorder(BorderFactory.createEmptyBorder(24, 10, 0, 630));
            }
        }
        public void addFields(@NotNull ArrayList<JLabel> labels){
            JPanel innerPanel = new JPanel();
            innerPanel.setBackground(backgroundColor);
            innerPanel.setPreferredSize(new Dimension(1100, 311));
            innerPanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 0;

            hintsList = new ArrayList<>(Arrays.asList(playersNumber_, rotation_, targetingSeriesNumber_,
                    testSeriesNumber_, prepareTime_, durationSeries_, completionWarning_));

            for (JLabel label : labels) {
                c.gridx = 0;
                c.anchor = GridBagConstraints.WEST;
                c.insets = new Insets(0, 0, 10, 0);
                c.ipadx = 70;
                c.fill = GridBagConstraints.WEST;

                JPanel template = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 5));
                template.setAlignmentX(Component.LEFT_ALIGNMENT);
                template.setBackground(backgroundColor);
                innerPanel.add(template, c);

                template.add(label, c);
                label.setFont(LABEL_FONT);

                c.gridx = 1;
                int index_ = flag > 0 ? labels.indexOf(label) + 4 : labels.indexOf(label);

                template.add(hintsList.get(index_), c);
                hintsList.get(index_).setFont(hints);
                hintsList.get(index_).setForeground(Color.decode("#828282"));
                hintsList.get(index_).setBorder(new RoundBorder(5, Color.decode("#828282")));
                hintsList.get(index_).setPreferredSize(new Dimension(84, 34));
                c.gridy++;
            }
            c.gridy = 0;
            c.insets = flag > 0 ? new Insets(10, 0, 70, 0) : c.insets;
            c.gridx = flag > 0 ? 1 : 0;
            add(innerPanel, c);
            flag++;
        }
    }

     public class ButtonsPanel extends JPanel{
         RoundButton saveButton;
         RoundButton toAltSettingsButton;
         RoundButton toTeamSettingsButton;
         RoundButton backToSettingsButton;
         public ButtonsPanel(){
            FlowLayout buttonLayout = new FlowLayout(FlowLayout.LEADING, 30, 0);
            setLayout(buttonLayout);
            setBackground(SCREEN_COLOR);

            saveButton = new RoundButton("СОХРАНИТЬ", BORDER_RADIUS);
            saveButton.setBorder(new RoundBorder(BORDER_RADIUS, Color.decode("#3AAF37")));
            saveButton.setBackground(Color.decode("#3AAF37"));
            saveButton.setForeground(Color.WHITE);
            saveButton.setFont(new Font("Inter", Font.PLAIN, 20));
            saveButton.setPreferredSize(new Dimension(165, 44));

            toAltSettingsButton = new RoundButton("\"Альтернативная стрельба\"", BORDER_RADIUS);
            toAltSettingsButton.setFont(new Font("Inter", Font.PLAIN, 20));
            toAltSettingsButton.setBorder(new RoundBorder(BORDER_RADIUS, Color.decode("#3AAF37")));
            Color color = new Color(0x3AAF37);
            Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * 0.13));
            toAltSettingsButton.setBackground(transparentColor);
            toAltSettingsButton.setPreferredSize(new Dimension(300, 44));

            toTeamSettingsButton = new RoundButton("\"Альтернативная стрельба (КОМАНДЫ)\"", BORDER_RADIUS);
            toTeamSettingsButton.setFont(new Font("Inter", Font.PLAIN, 20));
            toTeamSettingsButton.setBorder(new RoundBorder(BORDER_RADIUS, Color.decode("#3AAF37")));
            toTeamSettingsButton.setBackground(transparentColor);
            toTeamSettingsButton.setPreferredSize(new Dimension(440, 44));

             backToSettingsButton = new RoundButton("Перейти в основное меню настроек", 20);
             backToSettingsButton.setBorder(new RoundBorder(20, Color.decode("#3AAF37")));
             backToSettingsButton.setFont(new Font("Inter", Font.PLAIN, 20));
             backToSettingsButton.setBackground(transparentColor);
             backToSettingsButton.setPreferredSize(new Dimension(360, 44));

            toAltSettingsButton.addActionListener(e -> {
                SettingsScreen altSettingsScreen= new AltSettingsScreen();
                JFrame frame = (JFrame) SwingUtilities.getRoot(SettingsScreen.this);
                frame.getContentPane().removeAll();
                frame.getContentPane().add(altSettingsScreen);
                frame.revalidate();
                frame.repaint();
            });
            toTeamSettingsButton.addActionListener(e -> {
                SettingsScreen settingsScreen = new TeamsSettingsScreen();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.getContentPane().removeAll();
                frame.getContentPane().add(settingsScreen);
                frame.pack();
                frame.revalidate();
            });
            backToSettingsButton.addActionListener(e -> {
                SettingsScreen settingsScreen = new MainSettingsScreen();
                 JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                 frame.getContentPane().removeAll();
                 frame.getContentPane().add(settingsScreen);
                 frame.pack();
                 frame.revalidate();
            });
        }
     }

     protected boolean setTimerParams(ArrayList<HintTextField> hintsList) {
         if (this instanceof MainSettingsScreen) {
             timerBuilder = new TimerBuilder(hintsList.get(0), hintsList.get(1), hintsList.get(2),
                     hintsList.get(3), hintsList.get(4), hintsList.get(5), hintsList.get(6));
             return hintsList.get(6).getText().equalsIgnoreCase("да");
         } else if (this instanceof AltSettingsScreen){
             timerBuilder = new TimerBuilder(new HintTextField("1"), new HintTextField("постоянная"), hintsList.get(0),
                     new HintTextField("0"), hintsList.get(1), hintsList.get(2), hintsList.get(3));
             return hintsList.get(3).getText().equalsIgnoreCase("да");
         }
         return false;
     }

//    public JPanel getJPanel() {
//        boolean haveSignal = hintsList.get(6).getText().equalsIgnoreCase("да");
//        TimerDemoGUI gameScreen = new TimerDemoGUI(Integer.parseInt(hintsList.get(5).getText()),
//                Integer.parseInt(hintsList.get(2).getText()), 5,
//                Integer.parseInt(hintsList.get(4).getText()), haveSignal);
//        return gameScreen.displayGUI();
//    }
}