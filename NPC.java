import java.util.Random;

/**
 * A class to represent an NPC.
 *
 * @author Thomas Vakili
 * @version 2013.12.14
*/
public class NPC extends Character {
    private String phrase;

    public NPC(String phrase, String name, Room room) {
        super(name, room);
        this.phrase = phrase;
    }

    /**
     * Choose an action to perform in a fight.
     * Right now the AI is pretty stupid.
     *
     * @param enemy the enemy the NPC faces
     * @return the action chosen
    */
    @Override
    public Action getAction(Character enemy) {
        Action.ActionVal action;

        int random = (new Random()).nextInt(101) - getHP();
        if (random > 70) {
            action = Action.ActionVal.BLOCK;
        } else {
            action = Action.ActionVal.ATTACK;
        }

        return new Action(action, this, enemy);
    }

    public String getPhrase() {
        return phrase;
    }
}
