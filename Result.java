/**
 * Class to represent the result of an action in a fight.
 *
 * @author Thomas Vakili
 * @version 2013.14.15
*/
public class Result {
    String representation;
    Character winner;
    Character loser;

    public Result(String representation, Character winner, Character loser) {
        this.representation = representation;
        this.winner = winner;
        this.loser = loser;
    }

    /**
     * Return whether or not the fight has ended.
    */
    public boolean fightOver() {
        return winner != null;
    }

    @Override
    public String toString(){
        return representation;
    }

    public Character getWinner() {
        return winner;
    }

    public Character getLoser() {
        return loser;
    }
}
