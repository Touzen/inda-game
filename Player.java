import java.util.HashMap;
import java.util.ArrayList;

/**
 * A class to represent the player.
 *
 * @author Thomas Vakili
 * @version 2013.12.14
*/
public class Player extends Character {
    private int maxWeight;
    private Inventory inventory;
    
    public Player(Room room) {
        super("Placeholder", room); // A name should be passed
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
        getRoom().addItem(item);
    }

    /**
     * Look around.
     *
     * @return a String describing where you are, what exits are available
     *         and that let's any NPCs state their mind.
    */
    public String look() {
        return getRoom().getLongDescription();
    }

    public boolean teleport() {
        boolean success = false;
        if (getRoom() instanceof Teleporter) {
            Teleporter teleporter = (Teleporter) getRoom();
            setRoom(teleporter.getDestination());
            success = true;
        }

        return success;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
