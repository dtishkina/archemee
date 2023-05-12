import util.Condition;
import util.Game;

class GameTest {

    @org.junit.jupiter.api.Test
    void iterate() {
        System.out.println("===FIRST===");
        Game game = new Game(2, 2, 2, false);
        Condition currentStatement = game.iterate();
        System.out.println(currentStatement);
        while (currentStatement.getType() != Condition.Type.END) {
            currentStatement = game.iterate();
            System.out.println(currentStatement);
        }
        System.out.println("===SECOND===");
        Game game1 = new Game(2, 2, 3, true);
        Condition currentStatement1 = game1.iterate();
        System.out.println(currentStatement1);
        while (currentStatement1.getType() != Condition.Type.END) {
            currentStatement1 = game1.iterate();
            System.out.println(currentStatement1);
        }
        System.out.println("===THIRD===");
        Game game2 = new Game(4,4, 3, true);
        Condition currentStatement2 = game2.iterate();
        System.out.println(currentStatement2);
        while (currentStatement2.getType() != Condition.Type.END){
            currentStatement2 = game2.iterate();
            System.out.println(currentStatement2);
        }
        System.out.println("===FOURTH===");
        Game game3 = new Game(4,4, 1, false);
        Condition currentStatement3 = game3.iterate();
        System.out.println(currentStatement3);
        while (currentStatement3.getType() != Condition.Type.END){
            currentStatement3 = game3.iterate();
            System.out.println(currentStatement3);
        }
    }
}