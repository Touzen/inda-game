import java.util.Random;
import java.util.ArrayList;

/**
 * A room that teleports you to a random room.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public class Teleporter extends Room {
    private Room destination;
    private ArrayList<Room> rooms;
    private Random rand;

    public Teleporter(ArrayList<Room> rooms) {
        super("at a teleporter. Wonder where it leads...");
        rand = new Random();
        this.rooms = rooms;
        randomizeDestination();
    }

    /**
     * Generate a random destination.
    */
    public void randomizeDestination() {
        destination = rooms.get(rand.nextInt(rooms.size()));
    }   

    /**
     * Returns the teleporters destination.
    */
    public Room getDestination() {
        return destination;
    }
}
