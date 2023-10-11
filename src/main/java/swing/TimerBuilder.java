package swing;

import swing.customcomponents.HintTextField;
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
    private HintTextField targetingSeriesNumberHint;
    private HintTextField testSeriesNumberHint;
    private HintTextField prepareTimeHint;
    private HintTextField durationSeriesHint;
    private Integer playersNumber;
    private Boolean rotation;
    private Integer targetingSeriesNumber;
    private Integer testSeriesNumber;
    private Integer prepareTime;
    private Integer durationSeries;
    private Boolean completionWarning;

    List<Executor> problems;

    public TimerBuilder(HintTextField playersNumberHint, boolean rotation,
                        HintTextField targetingSeriesNumberHint, HintTextField testSeriesNumberHint,
                        HintTextField prepareTimeHint, HintTextField durationSeriesHint,
                        boolean completionWarning) {
        this.playersNumberHint = playersNumberHint;
        this.rotation = rotation;
        this.targetingSeriesNumberHint = targetingSeriesNumberHint;
        this.testSeriesNumberHint = testSeriesNumberHint;
        this.prepareTimeHint = prepareTimeHint;
        this.durationSeriesHint = durationSeriesHint;
        this.completionWarning = completionWarning;
        this.problems = new ArrayList<>();
    }

    public boolean isCorrect() {
        problems.clear();
        playersNumber = setValueOrAddProblemForInt(playersNumberHint);

        targetingSeriesNumber = setValueOrAddProblemForInt(targetingSeriesNumberHint);

        testSeriesNumber = setValueOrAddProblemForInt(testSeriesNumberHint);

        prepareTime = setValueOrAddProblemForInt(prepareTimeHint);

        durationSeries = setValueOrAddProblemForInt(durationSeriesHint);

        return problems.isEmpty();
    }

    private Integer setValueOrAddProblemForInt(HintTextField currentHint) {
        try {
            return Integer.parseInt(currentHint.getText());
        } catch (NumberFormatException e) {
            problems.add(() -> currentHint.setText("не число"));
            currentHint.setForeground(Color.RED);
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
