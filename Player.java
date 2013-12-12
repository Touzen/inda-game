/**
 * A class to represent the player.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public class Player {
    private String name;
    private Room currentRoom;
    private maxWeight;
    
    /**
     * Moves the player to a new room.
     *
     * @param newRoom room to move to
    */
    public void move(Room newRoom) {
        currentRoom = newRoom;
    }

    /**
     * Picks up an item.
     *
     * @param item item to pick up
     * @return true if player can carry more, otherwise false
    */
    public boolean pickUp(Item item) {
        if (inventory.getWeight() + item.getWeight() <= maxWeight) {
            return inventory.add(item);
        } else {
            return false:
        }
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return currentRoom;
    }
}
