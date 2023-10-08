package swing.mode;

import util.Timer;

public class ModeBuilder {
    public static GameComandScreen build(Timer timer, boolean haveSignals, Mode mode) {
        try {
            switch (mode) {
                case MAIN -> {
                    return new MainMode(timer, haveSignals);
                }
                case ALT -> {
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
