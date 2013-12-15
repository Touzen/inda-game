/**
 * Class to represent an action in a fight.
 *
 * @author Thomas Vakili
 * @version 2013.12.15
*/
public class Action {
    public static enum ActionVal {
        ATTACK("attack", "a"),
        BLOCK("block", "b"),
        SURRENDER("surrender", "s");
        
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
    private Character actor
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
                break;

            case BLOCK:
                return block();
                break;

            case SURRENDER:
                return surrender();
                break;
        }
    }

    private Result attack() {
        //IMPLEMENT PLS
    }

    private Result block() {
        //IMPLEMENT PLS
    }

    private Result surrender() {
        //IMPLEMENT PLS
    }
}
