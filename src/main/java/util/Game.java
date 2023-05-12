package util;

import java.util.ArrayList;

public class Game {
    private int preSeriesCount;
    private int mainSeriesCount;
    private int commandsCount;
    private boolean switchPlayer;
    private ArrayList<String> playersNames;

    private int iterationNumber;


    public Game(int preSeriesCount, int mainSeriesCount, int commandsCount, boolean switchPlayer) {
        this.preSeriesCount = preSeriesCount;
        this.mainSeriesCount = mainSeriesCount;
        this.commandsCount = commandsCount;
        this.switchPlayer = switchPlayer;
        playersNames = new ArrayList<String>(commandsCount);
        for (int i = 0; i < commandsCount * 2; i += 2) {
            playersNames.add(String.valueOf((char) ('A' + i)) + String.valueOf((char) ('A' + i + 1)));

        }
        this.iterationNumber = 1;
    }

    public Condition iterate() {
        int resultSumOfIteration = (preSeriesCount + mainSeriesCount) * 2 * commandsCount - 1;
        Condition resultCondition = new Condition();
        if (resultSumOfIteration >= iterationNumber) {
            if (iterationNumber % 2 == 0) {
                resultCondition.setType(Condition.Type.REST);
            } else {
                String currentPlayerNumber;
                int gameNumber;
                if (preSeriesCount * commandsCount * 2 > iterationNumber) {
                    gameNumber = (iterationNumber / commandsCount / 2) + 1;
                } else {
                    gameNumber = (iterationNumber / commandsCount / 2) - preSeriesCount + 1;
                }
                if (switchPlayer) {
                    currentPlayerNumber = playersNames.get((((iterationNumber % (commandsCount * 2) + 1) / 2) + gameNumber - 2) % commandsCount);
                } else {
                    currentPlayerNumber = playersNames.get((iterationNumber % (commandsCount * 2) + 1) / 2 - 1);
                }
                resultCondition.setGameCount(gameNumber);
                resultCondition.setPlayerName(currentPlayerNumber);
                if (preSeriesCount * commandsCount * 2 > iterationNumber) {
                    resultCondition.setType(Condition.Type.PREGAME);
                } else {
                    resultCondition.setType(Condition.Type.GAME);
                }
            }
            iterationNumber++;
        } else {
            resultCondition.setType(Condition.Type.END);
        }
        return resultCondition;
    }
}
