package swing;

import util.Timer;

public class ModeBuilder {
    public static GameCommandScreen build(Timer timer, boolean haveSignals, int selectedGameMode) {
        try {
            switch (selectedGameMode) {
                case 1 -> {
                    return new MainMode(timer, haveSignals);
                }
                case 2 -> {
                    return new AltMode(timer, haveSignals);
                }
                default -> throw new Exception("no such mode");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
