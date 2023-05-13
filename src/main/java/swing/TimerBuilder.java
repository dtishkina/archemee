package swing;

import util.Timer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TimerBuilder {

    @FunctionalInterface
    public interface Executor {
        void execute();
    }

    private HintTextField playersNumberHint;
    private HintTextField rotationHint;
    private HintTextField targetingSeriesNumberHint;
    private HintTextField testSeriesNumberHint;
    private HintTextField firstSeriesNumberHint;
    private HintTextField prepareTimeHint;
    private HintTextField durationSeriesHint;
    private HintTextField completionWarningHint;

    private Integer playersNumber;
    private Boolean rotation;
    private Integer targetingSeriesNumber;
    private Integer testSeriesNumber;
    private Integer firstSeriesNumber;
    private Integer prepareTime;
    private Integer durationSeries;
    private Boolean completionWarning;

    List<Executor> problems;

    public TimerBuilder(HintTextField playersNumberHint, HintTextField rotationHint,
                        HintTextField targetingSeriesNumberHint, HintTextField testSeriesNumberHint,
                        HintTextField firstSeriesNumberHint, HintTextField prepareTimeHint, HintTextField durationSeriesHint,
                        HintTextField completionWarningHint) {
        this.playersNumberHint = playersNumberHint;
        this.rotationHint = rotationHint;
        this.targetingSeriesNumberHint = targetingSeriesNumberHint;
        this.testSeriesNumberHint = testSeriesNumberHint;
        this.firstSeriesNumberHint = firstSeriesNumberHint;
        this.prepareTimeHint = prepareTimeHint;
        this.durationSeriesHint = durationSeriesHint;
        this.completionWarningHint = completionWarningHint;
        this.problems = new ArrayList<>();
    }

    public boolean isCorrect() {
        problems.clear();
        playersNumber = setValueOrAddProblemForInt(playersNumberHint);

        if (rotationHint.getText().equalsIgnoreCase("постоянная")) {
            rotation = false;
        } else if (rotationHint.getText().equalsIgnoreCase("переменная")) {
            rotation = true;
        } else {
            problems.add(() -> rotationHint.setErrorText("переменная/постоянная"));
        }

        targetingSeriesNumber = setValueOrAddProblemForInt(targetingSeriesNumberHint);

        testSeriesNumber = setValueOrAddProblemForInt(testSeriesNumberHint);

        firstSeriesNumber = setValueOrAddProblemForInt(firstSeriesNumberHint);

        prepareTime = setValueOrAddProblemForInt(prepareTimeHint);

        durationSeries = setValueOrAddProblemForInt(durationSeriesHint);

        if (completionWarningHint.getText().equalsIgnoreCase("Да")) {
            completionWarning = true;
        } else if (completionWarningHint.getText().equalsIgnoreCase("Нет")) {
            completionWarning = false;
        } else {
            problems.add(() -> completionWarningHint.setErrorText("Да/Нет"));
        }
        return problems.isEmpty();
    }

    private Integer setValueOrAddProblemForInt(HintTextField currentHint) {
        try {
            return Integer.parseInt(currentHint.getText());
        } catch (NumberFormatException e) {
            problems.add(() -> currentHint.setErrorText("не число"));
        }
        return null;
    }

    public boolean applyHintsChanges() {
        if (problems.size() == 0) {
            return false;
        } else {
            for (int i = 0; i < problems.size(); i++) {
                problems.get(i).execute();
            }
            return true;
        }
    }


    public Timer build() {
        //TODO проверить растановку параметров
        return new Timer(durationSeries, prepareTime, testSeriesNumber, targetingSeriesNumber, playersNumber, rotation);
    }
}