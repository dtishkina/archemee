package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class RoundButton extends JButton {
    private Shape shape;
    private int cornerRadius;

    public RoundButton(String label, int cornerRadius) {
        super(label);

        setOpaque(false);

        setBackground(Color.WHITE);

        this.cornerRadius = cornerRadius;
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.gray);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, cornerRadius, cornerRadius);
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        }
        return shape.contains(x, y);
    }

    public void playSound(String filePath) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(RoundButton.class.getResource(filePath)));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}