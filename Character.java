/**
 * A class to represent character.
 *
 * Used to subclass Player and NPC.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public abstract class Character {
    private String name;
    private Room currentRoom;
    
    public Character(Room room) {
        currentRoom = room;
    }

    /**
     * Moves the player to a new room.
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

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return currentRoom;
    }
}
