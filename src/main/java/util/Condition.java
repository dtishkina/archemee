package util;

public class Condition {
    public enum Type {
        PREGAME,
        GAME,
        REST,
        END
    }

    private Type type;
    private String playerName;
    private int gameCount;

    public Condition() {

    }

    public Condition(Type type, String playerName, int gameCount) {
        this.type = type;
        this.playerName = playerName;
        this.gameCount = gameCount;
    }

    public Condition(Condition other) {
        this.type = other.type;
        this.playerName = other.playerName;
        this.gameCount = other.gameCount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.type.toString());
        if (this.type != Type.END && this.type != Type.REST) {
            sb
                    .append(" №")
                    .append(this.gameCount)
                    .append(" player ")
                    .append(this.playerName);
        }
        return sb.toString();
    }
}
