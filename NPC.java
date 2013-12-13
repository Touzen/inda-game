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

    /**
     * Moves the NPC to a new room.
     *
     * @param direction what direction to move in
     * @return whether or not the move succeeded
    */
    public boolean move(Direction direction) {
        Room newRoom = currentRoom.getExit(direction);

        if(newRoom != null) {
            currentRoom = newRoom;
            return true;
        }

        return false;
    }
}
