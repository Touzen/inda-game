import java.util.Random;

/**
 * Class to represent a fight between two characters.
 *
 * @author Thomas Vakili
 * @version 2013.12.15
*/
public class Fight {
    private static final int N_COMBATANTS = 2;

    private Character[N_COMBANTANTS] combatant;
    private Character winner;

    private Random rand;

    public Fight(Character combatant1, Character combatant2) {
        combatant = {combatant1, combatant2};
        rand = new Random();
    }

    /**
     * Main loop of the fight.
    */
    public void start() {
        int round;

        for (round = 1; winner == null; round++) {
            int firstIndex = rand.nextInt(N_COMBANTANTS);

            // Randomly select who gets to start
            Character first = combatant[firstIndex];
            Character second = combatant[firstIndex == 0 ? 1 : 0]
            
            System.out.println("\n=============");
            System.out.println("ROUND " + round);
            System.out.println("=============");
            round(first, second);
        }

        System.out.println(winner.getName() + " wins!");

        combatant[0].battleOver();
        combatant[1].battleOver();
    }

    /**
     * Run a round.
    */
    private void round(first, second) {
        Action firstAction = first.getAction(second);
        executeAction(first, firstAction);

        if (winner == null) {
            Action secondAction = second.getAction(first);
            executeAction(second, secondAction);
        }
    }

    /**
     * Execute the action chosen by the character.
    */
    private void executeAction(Character combatant, Action action) {
        System.out.println(combatant.getName() + " chooses to " +
                           action + "!");
        Result result = action.execute();
        
        System.out.println(result);

        if (result.fightOver()) {
            this.winner = result.getWinner();
        }
    }
}
