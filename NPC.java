/**
 * A class to represent an NPC.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public class NPC {
    private String phrase;
    private String name;
    private Room currentRoom;

    public NPC(String phrase, String name, Room room) {
        this.phrase = phrase;
        this.name = name;
        this.currentRoom = room;
    }

    public String getName() {
        return name;
    }

    public String getPhrase() {
        return phrase;
    }

    public Room getRoom() {
        return currentRoom;
    }
}
