import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

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

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<Direction, Room>();
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
        
        if (exits.get(direction) == null) {
            exits.put(direction, neighbor);
            neighbor.connect(direction.getOpposite(), this);
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
}

