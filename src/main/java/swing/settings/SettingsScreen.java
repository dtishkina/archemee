package swing.settings;

import swing.*;
import swing.mode.GameComandScreen;
import swing.mode.Mode;
import swing.mode.ModeBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SettingsScreen extends JPanel {
    protected TimerBuilder timerBuilder;
    protected RoundButton saveButton;
    JPanel buttonsPanel;
    JPanel mainPanel;
    JPanel headerPanel;
    JLabel modeLabel;
    static Color SCREEN_COLOR = Color.WHITE;
    static int BORDER_RADIUS = 20;
    static Color BUTTON_COLOR = Color.decode("#3AAF37");
    Mode MODE;
    boolean haveSignal;
    String input = " ввод...";
    HintTextField playersNumber_ = new HintTextField(input);
    HintTextField rotation_ = new HintTextField(input);
    HintTextField targetingSeriesNumber_ = new HintTextField(input);
    HintTextField testSeriesNumber_ = new HintTextField(input);
    HintTextField prepareTime_ = new HintTextField(input);
    HintTextField durationSeries_ = new HintTextField(input);
    HintTextField completionWarning_ = new HintTextField(input);
    RadioButtonsGroup signalOption;
    RadioButtonsGroup rotationOption;
    public SettingsScreen() {
        setLayout(new GridBagLayout());
        setBackground(SCREEN_COLOR);
        setPreferredSize(new Dimension(1400, 700));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SCREEN_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        getHeaderPanel();
        panel.add(headerPanel, constraints);

        constraints.gridy = 1;
        constraints.insets = new Insets(47, 0, 0, 0);

        getMainPanel();
        panel.add(mainPanel, constraints);

        constraints.gridy = 2;
        constraints.insets = new Insets(75, 0, 0, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        getButtonsPanel();
        panel.add(buttonsPanel, constraints);
        constraints.insets = new Insets(80,80, 80, 80);
        add(panel, constraints);
    }
    void getHeaderPanel() {
        headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(SCREEN_COLOR);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.SOUTH;
        c.ipadx = 25;

        JLabel settingsLabel = new JLabel("Настройки");
        settingsLabel.setHorizontalAlignment(JLabel.LEFT);
        settingsLabel.setFont(new Font("Inter", Font.PLAIN, 65));

        modeLabel = new JLabel();
        modeLabel.setHorizontalAlignment(JLabel.LEFT);
        modeLabel.setForeground(Color.decode("#8E8E8E"));
        modeLabel.setFont(new Font("Inter", Font.PLAIN, 40));

        headerPanel.add(settingsLabel, c);
        headerPanel.add(modeLabel, c);
    }
    void getMainPanel() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#F6F6F6"));
    }
    void getButtonsPanel() {
        buttonsPanel = new JPanel();
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.LEADING, 30, 0);
        buttonsPanel.setLayout(buttonLayout);
        buttonsPanel.setBackground(SCREEN_COLOR);

        saveButton = new RoundButton("СОХРАНИТЬ", BORDER_RADIUS);
        saveButton.setBorder(new RoundBorder(BORDER_RADIUS, BUTTON_COLOR));
        saveButton.setBackground(BUTTON_COLOR);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Inter", Font.PLAIN, 20));
        saveButton.setPreferredSize(new Dimension(165, 44));
        saveButton.setMnemonic('\n');

        saveButton.addActionListener(event -> {
            setTimerParams();
            if (timerBuilder.isCorrect()) {
                JFrame frame = (JFrame) SwingUtilities.getRoot(this);
                GameComandScreen gameScreen = ModeBuilder.build(timerBuilder.build(), haveSignal, MODE);
                if (frame!= null) {
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(gameScreen);
                    frame.revalidate();
                }
            } else {
                timerBuilder.applyHintsChanges();
            }
        });
        buttonsPanel.removeAll();
        buttonsPanel.add(saveButton);
    }
    abstract void setTimerParams();
    ArrayList<LinkedHashMap<HintTextField, JLabel>> defineContent(){
        rotationOption = new RadioButtonsGroup("Постоянная", "Переменная");
        signalOption = new RadioButtonsGroup("Да", "Нет");

        LinkedHashMap<HintTextField, JLabel> column1 = new LinkedHashMap<>();
        LinkedHashMap<HintTextField, JLabel> column2 = new LinkedHashMap<>();

        column1.put(playersNumber_, new JLabel("Кол-во игроков:  "));
        column1.put(rotation_, new JLabel("Ротация:  "));
        column1.put(targetingSeriesNumber_, new JLabel("Кол-во пристрелочных серий:  "));
        column1.put(testSeriesNumber_, new JLabel("Кол-во зачётных серий:  "));
        column2.put(prepareTime_, new JLabel("Время на изготовку:  "));
        column2.put(durationSeries_, new JLabel("Продолжительность серии:  "));
        column2.put(completionWarning_, new JLabel("Предупреждение о завершении:  "));

        return new ArrayList<>(Arrays.asList(column1, column2));
    }
    JPanel fillInnerPanel(LinkedHashMap<HintTextField, JLabel> column) {
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setBackground(mainPanel.getBackground());

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        for (Map.Entry<HintTextField, JLabel> entry : column.entrySet()) {
            c.gridx = 0;
            c.anchor = GridBagConstraints.WEST;
            c.insets = new Insets(0, 0, 10, 0);
            c.ipadx = 70;
            c.fill = GridBagConstraints.EAST;

            JPanel tmp = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 5));
            tmp.setAlignmentX(Component.LEFT_ALIGNMENT);
            inner.add(tmp, c);

            tmp.setBackground(mainPanel.getBackground());

            tmp.add(setLabelStyle(entry.getValue()));
            c.gridx = 1;

            if (entry.getKey() == rotation_){
               tmp.add(rotationOption.getGroupPanel(), c);
            }else if (entry.getKey() == completionWarning_){
                tmp.add(signalOption.getGroupPanel(), c);
            }else {
                tmp.add(setTextFieldStyle(entry.getKey()));
            }
            c.gridy++;
        }
        return inner;
    }
    protected RoundButton getButton(String text, int width, Mode MODE){
        RoundButton button = new RoundButton(text, BORDER_RADIUS);
        button.setFont(new Font("Inter", Font.PLAIN, 20));
        button.setBorder(new RoundBorder(BORDER_RADIUS, Color.decode("#3AAF37")));
        button.setBackground( new Color(BUTTON_COLOR.getRed(), BUTTON_COLOR.getGreen(),
                BUTTON_COLOR.getBlue(), (int) (255 * 0.13)));
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(width, 44));

        button.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SettingsScreen.this);
            frame.getContentPane().removeAll();
            SettingsScreen settingsScreen;
            switch (MODE) {
                case MAIN -> settingsScreen = new MainSettingsScreen();
                case ALT -> settingsScreen = new AltSettingsScreen();
                case TEAMS -> settingsScreen = new TeamsSettingsScreen();
                default -> throw new IllegalStateException("Unexpected value: " + MODE);
            }
            frame.getContentPane().add(settingsScreen);
            frame.revalidate();
        });
        return button;
    }
    private HintTextField setTextFieldStyle(HintTextField field){
        field.setFont(new Font("Inter", Font.PLAIN, 20));
        field.setForeground(Color.decode("#828282"));
        field.setBorder(new RoundBorder(5, Color.decode("#828282")));
        field.setPreferredSize(new Dimension(84, 34));
        return field;
    }
    private JLabel setLabelStyle(JLabel label){
        label.setFont(new Font("Inter", Font.PLAIN, 24));
        return label;
    }
}