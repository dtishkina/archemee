package util;

public class Timer extends Game {
    private int secondsLimit;
    private int restSecondsLimit;
    private SecondCondition secondCondition;

    public Timer(int secondsLimit, int restSecondsLimit, int preSeriesCount, int mainSeriesCount,
                 int commandsCount, boolean switchPlayer) {
        super(preSeriesCount, mainSeriesCount, commandsCount, switchPlayer);
        this.restSecondsLimit = restSecondsLimit;
        this.secondsLimit = secondsLimit;
        this.secondCondition = new SecondCondition(secondsLimit, iterate());
    }

    public SecondCondition tic() {
        if (secondCondition.iterate()) {
            if (secondCondition.getType() == Condition.Type.REST) {
                secondCondition = new SecondCondition(restSecondsLimit, iterate());
            } else {
                secondCondition = new SecondCondition(secondsLimit, iterate());
            }
        }
        return this.secondCondition;
    }
}
