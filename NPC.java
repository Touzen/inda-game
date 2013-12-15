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
     *
     * @param enemy the enemy the NPC faces
     * @return the action chosen
    */
    @Override
    public Action getAction(Character enemy) {
        // IMPLEMENT PLS
    }

    public String getPhrase() {
        return phrase;
    }
}
