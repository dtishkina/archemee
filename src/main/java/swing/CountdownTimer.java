package swing;

import javax.swing.*;
import java.awt.*;

public class CountdownTimer {
    private int time;
    protected boolean warningTriggered = false;
    private boolean paused;
    private Timer countdownTimer;
    private JLabel timerLabel;
    private int warningTime;
    private Runnable timerFinishedCallback;

    public CountdownTimer(int initialTime, JLabel timerLabel, JPanel timerPanel, int warning) {
        warningTime = warning;
        this.time = initialTime;
        this.timerLabel = timerLabel;
        this.paused = true;
        countdownTimer = new Timer(1000, e -> {
            if (time > 0) {
                time--;
                updateLabel();
                if (time <= warningTime){
                    timerPanel.setBackground(Color.decode("#FFF600"));

                }
            }

            else {
                countdownTimer.stop();
                if (timerFinishedCallback != null) {
                    timerFinishedCallback.run();
                }
            }
        });
    }

    public void setTimerFinishedCallback(Runnable callback) {
        this.timerFinishedCallback = callback;
    }
    public void toggle() {
        if (paused) {
            start();
        } else {
            pause();
        }
    }


    public boolean isPaused() {
        return paused;
    }

    public boolean isTimeOver() {
        return time <= 0;
    }

    public void start() {
        countdownTimer.start();
        paused = false;
    }

    public int getCurrentTime() {
        return time;
    }

    public void pause() {
        countdownTimer.stop();
        paused = true;
    }

    public void stop() {
        countdownTimer.stop();
        paused = true;
    }

    public void reset(int newTime) {
        time = newTime;
        updateLabel();
    }

    private void updateLabel() {
        timerLabel.setText(String.valueOf(time));
    }
}