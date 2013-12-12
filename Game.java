import java.util.HashMap;

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
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        rooms = new HashMap<String, Room>();
        createRooms();

        parser = new Parser();
        player = new Player(rooms.get("outside"));
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.connect(Direction.EAST, theater);
        outside.connect(Direction.SOUTH, lab);
        outside.connect(Direction.WEST, pub);

        lab.connect(Direction.EAST, office);

        office.connect(Direction.WEST, lab);

        rooms.put("outside", outside);
        rooms.put("theater", theater);
        rooms.put("pub", pub);
        rooms.put("lab", lab);
        rooms.put("office", office);
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
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.look());
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

            case PICKUP:
                pickupItem(command);
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
        } else {
            System.out.println("There is no door!");
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

        Iterator<Item> it = items.iterator();
        Item item;
        while (it.hasNext()) {
            item = it.next();
            if (item.getName() == command.getSecondWord()) {
                success = player.pickUp(item);
                it.remove();
                break;
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
