/**
 * Class to represent an action in a fight.
 *
 * @author Thomas Vakili
 * @version 2013.12.15
*/
public class Action {
    public static enum ActionVal {
        ATTACK("attack"),
        BLOCK("block"),
        SURRENDER("surrender");
        
        private String actionString;

        private ActionVal(String string) {
            actionString = string;
        }

        @Override
        public String toString() {
            return actionString;
        }
    }
    
    private ActionVal action;
    private Character actor;
    private Character target;

    public Action(ActionVal action, Character actor, Character target) {
        this.action = action;
        this.actor = actor;
        this.target = target;
    }
    
    public Result execute() {
        switch(action) {
            case ATTACK:
                return attack();

            case BLOCK:
                return block();

            case SURRENDER:
                return surrender();
        }

        assert 1 == 2:
            "@Action.execute(): This should never be reached."; 

        return null;
    }

    private Result attack() {
        String representation;
        int damage = actor.attack(target);

        return buildResult("attacks " + target.getName() + ", dealing " +
                           damage + " hitpoints of damage");
    }

    private Result block() {
        actor.block();
        
        return buildResult("blocks");
    }

    private Result surrender() {
        return buildResult("surrenders");
    }

    private Result buildResult(String repr) {
        repr = actor.getName() + " " + repr + ".";

        Character winner = null;
        Character loser = null;
        winner = checkForWinner();

        if (winner != null) {
            repr += " " + winner.getName() + " wins!";
            loser = winner == target ? actor : target;
        }

        return new Result(repr, winner, loser);
    }

    private Character checkForWinner() {
        if (this.action == ActionVal.SURRENDER || actor.getHP() <= 0) {
            return target;
        } else if (target.getHP() <= 0) {
            return actor;
        } else {
            return null;
        }
    }
}
