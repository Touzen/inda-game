import java.util.ArrayList;

/**
 * A class to hold a characters inventory.
 *
 * @author Thomas Vakili
 * @version 2013.12.10
*/
public class Inventory {
    private ArrayList<Item> items;
    private int size;

    /**
     * Adds an item to the inventory.
     *
     * @param item The Item to add
     * @return true if there is room, false if the inventory is full
    */
    public boolean addItem(Item item) {
        if (items.size() < size) {
            items.add(item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove an item from the inventory.
     *
     * @param item the item to remove
    */
    public void removeItem(Item item) {
        items.remove(items.indexOf(item));
    }

    /**
     * Gets the number of items in the inventory
     *
     * @return The number of items
    */
    public int numberOfItems() {
        return items.size();
    }

    public void setSize(int newSize) {
        size = newSize;
    }
    
    public ArrayList<Item> getItems() {
        return items;
    }

    public int getWeight() {
        int total = 0;
        for (Item item: items) {
            total += item.getWeight();
        }

        return total;
    }

}
