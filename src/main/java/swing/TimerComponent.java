package swing;

import javax.swing.*;
import java.awt.*;

class TimerComponent extends JComponent {
    private int timeInSeconds;
    private Font font;
    private Color textColor;

    public TimerComponent(int timeInSeconds, Font font, Color textColor) {
        this.timeInSeconds = timeInSeconds;
        this.font = font;
        this.textColor = textColor;
    }

    public void setTimeInSeconds(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.setColor(textColor);

        String time = getTimeString(timeInSeconds);

        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(time)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g2d.drawString(time, x, y);
    }

    private String getTimeString(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}

