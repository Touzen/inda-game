/**
 * A class to represent an NPC.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public class NPC extends Character {
    private String phrase;

    public NPC(String phrase, String name, Room room) {
        super(room);
        this.phrase = phrase;
        this.name = name;
    }

    public String getPhrase() {
        return phrase;
    }
}
