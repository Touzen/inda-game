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

    public String getPhrase() {
        return phrase;
    }
}
