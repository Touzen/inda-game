import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling, David J. Barnes and Thomas Vakili
 * @version 2013.12.12
 */

public class Room 
{
    private String description;
    private HashMap<Direction, Room> exits;       
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, Item ... items) 
    {
        this.description = description;
        exits = new HashMap<Direction, Room>();
        
        this.items = new ArrayList<Item>();
        for (Item item: items) {
            this.items.add(item);
        }
    }

    /**
     * Connects the room and it's neighbor.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void connect(Direction direction, Room neighbor) {
        // An exit should only be set once, and to one neighbor only.
        assert(exits.get(direction) == null || exits.get(direction) == neighbor):
            "Neighbors can't be redefined!";
        
        if (exits.get(direction) == null && neighbor != null) {
            exits.put(direction, neighbor);
            neighbor.connect(direction.getOpposite(), this);
        }
    }

    /**
     * Create one way connection between to rooms.
     * @param direction The direction of the exit.
     * @param target The destination of the exit.
    */
    public void setExit(Direction direction, Room target) {
        if (target != null) {
            exits.put(direction, target);
        }
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<Direction> keys = exits.keySet();
        for(Direction exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(Direction direction) 
    {
        return exits.get(direction);
    }

    /**
     * Add an item to the room.
     *
     * @param item the item to add
    */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Remove an item from the room.
     *
     * @param item to remove from the room.
    */
    public void removeItem(Item item) {
        items.remove(items.indexOf(item));
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}

