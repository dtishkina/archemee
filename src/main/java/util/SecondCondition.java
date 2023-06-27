package util;

public class SecondCondition extends Condition {
    private int timeLeft;

    public SecondCondition(int timeLeft, Condition condition) {
        super(condition.getType(), condition.getPlayerName(), condition.getGameCount());
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public boolean iterate() {
        this.timeLeft--;
        return timeLeft > 0;
    }

    @Override
    public String toString() {
        return "SecondCondition{" +
                "timeLeft=" + timeLeft +
                '}'+"\n" + super.toString();
    }
}
