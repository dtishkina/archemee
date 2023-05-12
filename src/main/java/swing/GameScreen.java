package swing;

import util.Condition;
import util.SecondCondition;
import util.Timer;

import javax.swing.JPanel;

class GameScreen extends JPanel {

    private Timer timer;


    public GameScreen(Timer timer) {
        this.timer = timer;
    }

    public void paint() {
        SecondCondition currentCondition = timer.tic();
        while (currentCondition.getType() != Condition.Type.END) {
            if (currentCondition.getType() == Condition.Type.PREGAME) {
                preGame(currentCondition);
            } else if (currentCondition.getType() == Condition.Type.GAME) {
                game(currentCondition);
            } else if (currentCondition.getType() == Condition.Type.REST) {
                rest();
            }
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentCondition = timer.tic();
        }
    }

    private void preGame(SecondCondition secondCondition) {
    }

    private void game(SecondCondition secondCondition) {
    }

    private void rest() {
    }

}
