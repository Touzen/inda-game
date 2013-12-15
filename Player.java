import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

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

    /**
     * Prompts the player for an action in a fight.
     *
     * @param enemy the enemy the player faces
     * @return the action chosen by the player
    */
    @Override
    public Action getAction(Character enemy) {
        Action.ActionVal action = null;
        Scanner input = new Scanner(System.in);

        // Print available commands
        System.out.print("Available commands:");
        for (Action.ActionVal command : Action.ActionVal.values()) {
            System.out.print(" " + command);
        }

        while (action == null) {
            System.out.print("\nEnter action: ");

            // Get first word entered
            try {
                String command = input.nextLine().split(" ")[0];
                action = Action.ActionVal.valueOf(command.toUpperCase());
            } catch(Exception e) {
                System.out.println("Invalid action!");
            }
        }

        return new Action(action, this, enemy);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
