import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class to hold a characters inventory.
 *
 * @author Thomas Vakili
 * @version 2013.12.14
*/
public class Inventory implements Iterable<Item> {
    private ArrayList<Item> items;
    private int size;

    public Inventory() {
        items = new ArrayList<Item>();
        size = 10;  // This should be passed as a parameter??
    }

    /**
     * Method to make it possible to iterate over
     * the inventory.
     *
     * @return iterator for the list of items
    */
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    /**
     * List the inventory.
     *
     * @return a string listing all the items
    */
    @Override
    public String toString() {
        String string = "";
        if (items.size() > 0) {
            for (Item item: items) {
                string += item.getName() + " ";
            }
            // Skip the last space
            string = string.substring(0, string.length()-1);
        }

        return string;
    }


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
