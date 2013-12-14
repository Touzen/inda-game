import java.util.HashMap;
import java.util.ArrayList;

/**
 * A class to represent the player.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public class Player extends Character {
    private int maxWeight;
    private Inventory inventory;
    
    public Player(Room room) {
        super(room);
        inventory = new Inventory();
        maxWeight = 100; // This should maybe be passed as a parameter?
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
     * @return a String describing where you are, what exits are available
     *         and that let's any NPCs state their mind.
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
        if (inventory.numberOfItems() <= 0) {
            return "Your inventory is empty.";
        }

        String string = "Inventory:";
        for (Item item: inventory.getItems()) {
            string += " " + item.getName();
        }
        string += ".";

        return string;
    }

    public boolean teleport() {
        boolean success = false;
        if (currentRoom instanceof Teleporter) {
            Teleporter teleporter = (Teleporter) currentRoom;
            currentRoom = teleporter.getDestination();
            success = true;
        }

        return success;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
