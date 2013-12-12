import java.util.HashMap;

/**
 * A class to represent the player.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public class Player {
    private String name;
    private Room currentRoom;
    private int maxWeight;
    private Inventory inventory;
    
    public Player(Room room) {
        currentRoom = room;
        inventory = new Inventory();
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

    /**
     * Picks up an item.
     *
     * @param item item to pick up
     * @return true if player can carry more, otherwise false
    */
    public boolean pickUp(Item item) {
        if (inventory.getWeight() + item.getWeight() <= maxWeight) {
            return inventory.addItem(item);
        } else {
            return false;
        }
    }

    /**
     * Drop an item in the current room.
     *
     * @param item item to drop.
    */
    public void dropItem(Item item) {
        inventory.removeItem(item);
        currentRoom.addItem(item);
    }

    /**
     * Look around.
     *
     * @return a String describing where you are and what exits are available.
    */
    public String look() {
        return currentRoom.getLongDescription();
    }

    /**
     * List the inventory.
     *
     * @return a string presenting the items in the inventory
    */
    public String listItems() {
        String string = "Inventory:";
        for (Item item: inventory.getItems()) {
            string += " " + item.getName();
        }
        string += ".";

        return string;
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return currentRoom;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
}
