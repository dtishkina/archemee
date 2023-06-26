package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class RoundButton extends JButton {
    private Shape shape;
    final int cornerRadius;
    private Color originalBackground;

    public RoundButton(String label, int cornerRadius) {
        super(label);

        setOpaque(false);

        setBackground(Color.WHITE);

        this.cornerRadius = cornerRadius;

        this.setFocusPainted(false);
        this.setContentAreaFilled(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                originalBackground = getBackground();
                setBackground(Color.decode("#8E8E8E"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalBackground);
            }
        });
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
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundTwice(String filePath) {
        playSound(filePath);
        try {
            Thread.sleep(1000);  // Задержка в миллисекундах
            playSound(filePath);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void playSoundThrice(String filePath) {
        playSound(filePath);
        try {
            Thread.sleep(1000);  // Задержка в миллисекундах
            playSound(filePath);
            Thread.sleep(1000);  // Задержка в миллисекундах
            playSound(filePath);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}