/**
 * A class to represent character.
 *
 * Used to subclass Player and NPC.
 *
 * @author Thomas Vakili
 * @version 2013.12.14
*/
public abstract class Character {
    private String name;
    private Room currentRoom;
    
    public Character(String name, Room room) {
        this.name = name;
        currentRoom = room;
    }

    /**
     * Moves the character to a new room.
     *
     * @param direction what direction to move in
     * @return whether or not the move succeeded
    */
    public boolean move(Direction direction) {
        Room newRoom = currentRoom.getExit(direction);

        if(newRoom != null) {
            setRoom(newRoom);
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

    protected void setRoom(Room room) {
        currentRoom = room;
    }
}
