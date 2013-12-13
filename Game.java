import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling, David J. Barnes and Thomas Vakili
 * @version 2012.12.12
 */

public class Game 
{
    private Parser parser;
    private HashMap<String, Room> rooms;
    private Player player;
    private ArrayList<NPC> npcs;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        npcs = new ArrayList<NPC>();
        rooms = new HashMap<String, Room>();
        createRooms();
        
        createNPCs();

        parser = new Parser();
        player = new Player(rooms.get("outside"));
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Item fish = new Item(10, "fish"); // Most important item

        // create the rooms
        rooms.put("outside", new Room("outside the main entrance of the university", fish));
        rooms.put("theater", new Room("in a lecture theater"));
        rooms.put("pub", new Room("in the campus pub"));
        rooms.put("lab", new Room("in a computing lab"));
        rooms.put("office", new Room("in the computing admin office"));

        // initialise room exits
        rooms.get("outside").connect(Direction.EAST, rooms.get("theater"));
        rooms.get("outside").connect(Direction.SOUTH, rooms.get("lab"));
        rooms.get("outside").connect(Direction.WEST, rooms.get("pub"));

        rooms.get("lab").connect(Direction.EAST, rooms.get("office"));

        // create the teleporter
        Teleporter teleporter = new Teleporter(new ArrayList(this.rooms.values()));
        rooms.get("lab").connect(Direction.WEST, teleporter);
        rooms.put("teleporter", teleporter);
    }
    
    /**
     * Creates a bunch of NPCs
    */
    private void createNPCs() {
        npcs.add(new NPC("Sup brah?", "Esteban", rooms.get("lab")));
        npcs.add(new NPC("<drunk> whp teh fuckryeeeh? duuuud... suuUuUup... </drunk>",
                         "Bilal the drunkard", rooms.get("theater")));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            moveNPCs();
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to KASDG!");
        System.out.println("KASDG is a game as meaningless as its name. It is like a mirror, forcing"
                           + " the player to contemplate the pointlessness of existence.");
        System.out.println("Just like in real life, the notion of progress holds no meaning. "
                           + "Feel free to explore. Fun fun fun!");
        System.out.println("(If all else fails, call it art lol)");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.look());
        NPCSpeak();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case DROP:
                dropItem(command);
                break;

            case PICKUP:
                pickupItem(command);
                break;

            case INVENTORY:
                System.out.println(player.listItems());
                break;

            case SEARCH:
                listRoomItems();
                break;
            case TELEPORT:
                teleport();
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        
        Direction direction = null;
        try {
            direction = Direction.valueOf(command.getSecondWord().toUpperCase());
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid direction!");
            return;
        }

        boolean success = player.move(direction);
        if (success) {
            System.out.println(player.look());
            NPCSpeak();
        } else {
            System.out.println("There is no door!");
        }
    }
    
    /**
     * Let the NPCs speak if they're in the same room as the player.
    */
    private void NPCSpeak() {
        if (npcs.size() > 0) {
            for (NPC npc : npcs) {
                if (npc.getRoom() == player.getRoom()) {
                    System.out.println(npc.getName() + " says: " + npc.getPhrase());
                }
            }
        }
    }

    /**
     * Move NPCs to new rooms.
    */
    private void moveNPCs() {
        for (NPC npc: npcs) {
            npc.move(Direction.getRandomDirection());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Make the player pickup an item. Print whether this succeeded or not.
    */
    private void pickupItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Pickup what?");
            return;
        }

        boolean success = false;
        Iterator<Item> it = player.getRoom().getItems().iterator();
        Item item;
        while (it.hasNext()) {
            item = it.next();
            if (item.getName().equals(command.getSecondWord())) {
                success = player.pickUp(item);
                it.remove();
                break;
            }
        }

        if (!success) {
            System.out.println("Couldn't pick up the " + command.getSecondWord() + ".");
        }
    }

    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        boolean success = false;
        Inventory inventory = player.getInventory();
        Iterator<Item> it = inventory.getItems().iterator();
        Item item;
        while (it.hasNext()) {
            item = it.next();
            if (item.getName().equals(command.getSecondWord())) {
                it.remove();
                player.getRoom().addItem(item);
                success = true;
                break;
            }
        }

        if (!success) {
            System.out.println("Silly you... You can't drop the " + command.getSecondWord()
                               + " if you don't have it!");
        }
    }

    /**
     * List the items in the room.
    */
    private void listRoomItems() {
        ArrayList<Item> items = player.getRoom().getItems();

        if (items.size() <= 0) {
            System.out.println("There are no items in this rooms.");
            return;
        }
 
        String string = "The following items where found:";
        for (Item item : items) {
            string += " " + item.getName();
        }
        string += ".";

        System.out.println(string);
    }

    /** 
     * Attempts to teleport, prints error if this fails.
    */
    private void teleport() {
        if (player.teleport()) {
            System.out.println("WUUUUUURR!");
        } else {
            System.out.println("Dammit man, you can't teleport unless you're in the teleporter.");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
