package swing;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class RoundBorder implements Border {

    private int radius;
    private Color color;

    public RoundBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1)); // толщина равна 3
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    public Shape getInteriorRectangle(Component c, int x, int y, int width, int height) {
        return new RoundRectangle2D.Double(x, y, width, height, radius, radius);
    }
}