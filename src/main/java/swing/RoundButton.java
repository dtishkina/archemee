package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class RoundButton extends JButton {

    private Shape shape;

    public RoundButton(String label) {
        super(label);

        // Устанавливаем непрозрачность кнопки
        setOpaque(false);

        // Устанавливаем цвет фона кнопки
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            // Кнопка нажата
            g.setColor(Color.gray);
        } else {
            // Кнопка не нажата
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 5, 5);

        // Вызываем суперкласс для рисования текста на кнопке
        super.paintComponent(g);
    }

    // Проверяем, попадает ли клик на кнопку
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        }
        return shape.contains(x, y);
    }
}