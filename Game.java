import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

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
 * @version 2012.12.14
 */

public class Game 
{
    private Parser parser;
    private HashMap<String, Room> rooms;
    private Player player;
    private ArrayList<NPC> npcs;
    private boolean continuePlaying;

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
        player = new Player(rooms.get("cell"));

        continuePlaying = true;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Item fish = new Item(10, "fish"); // Most important item

        // create the rooms
        rooms.put("cell", new Room("in your prison cell"));

        rooms.put("corridor0,2", new Room("in a long, long corridor"));
        rooms.put("corridor0,3", new Room("in a long, long corridor"));
        rooms.put("corridor0,4", new Room("in a long, long corridor"));
                                           
        rooms.put("corridor1,0", new Room("in a long, long corridor"));
        rooms.put("corridor1,1", new Room("in a long, long corridor"));
        rooms.put("corridor1,2", new Room("in a long, long corridor"));
        rooms.put("corridor1,4", new Room("in a long, long corridor"));
        rooms.put("corridor1,5", new Room("in a long, long corridor"));
        
        rooms.put("corridor2,0", new Room("in a long, long corridor"));
        rooms.put("corridor2,2", new Room("in a long, long corridor"));
        rooms.put("corridor2,3", new Room("in a long, long corridor"));
        rooms.put("corridor2,4", new Room("in a long, long corridor"));
        
        rooms.put("corridor3,0", new Room("in a long, long corridor"));
        rooms.put("corridor3,3", new Room("in a long, long corridor"));
        
        rooms.put("corridor4,0", new Room("in a long, long corridor"));
        rooms.put("corridor4,1", new Room("in a long, long corridor"));
        rooms.put("corridor4,2", new Room("in a long, long corridor"));
        rooms.put("corridor4,3", new Room("in a long, long corridor"));

        rooms.put("corridor5,1", new Room("in a long, long corridor"));

        rooms.put("win_room", new Room("outside. The wind is blowing. " +
                                       "It is raining. You are free"));

        // initialise room exits
        rooms.get("cell").setExit(Direction.EAST, rooms.get("corridor1,0"));

        for (int x = 0; x <= 5; x++) {
            for (int y = 0; y <= 5; y++) {
                Room room = rooms.get("corridor" + x + "," + y);
                
                Room roomSouth = rooms.get("corridor" + x + "," + (y + 1));
                Room roomNorth = rooms.get("corridor" + x + "," + (y - 1));
                Room roomEast = rooms.get("corridor" + (x + 1) + "," + y); 
                Room roomWest = rooms.get("corridor" + (x - 1) + "," + y); 

                if (room != null) {
                    room.setExit(Direction.SOUTH, roomSouth);
                    room.setExit(Direction.NORTH, roomNorth);
                    room.setExit(Direction.EAST, roomEast);
                    room.setExit(Direction.WEST, roomWest);
                }
            }
        }
                    
        rooms.get("corridor5,1").setExit(Direction.EAST, rooms.get("win_room"));

        // create the teleporter
        Teleporter teleporter = new Teleporter(new ArrayList(this.rooms.values()));
        rooms.get("corridor1,5").connect(Direction.SOUTH, teleporter);
        rooms.put("teleporter", teleporter);
    }
    
    /**
     * Creates a bunch of NPCs
    */
    private void createNPCs() {
        npcs.add(new NPC("Huh... What's that noise?", "Guard 104", rooms.get("corridor0,4")));

        npcs.add(new NPC("I feel like I'm starting to hear shit. " + 
                         "Hope they put the lights on again soon...",
                         "Guard 109", rooms.get("corridor2,4")));

        npcs.add(new NPC("What was that?! I'm getting all jumpy...",
                         "Guard 112", rooms.get("corridor4,0")));

        npcs.add(new NPC("WHO'S THERE?! ... Jesus, I'm getting all paranoid...",
                         "Guard 42", rooms.get("corridor5,1")));

        npcs.add(new NPC("... My boss is a fucking jerk... I just wanna go home",
                         "Guard 93", rooms.get("corridor4,2")));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        while (! gameEnded()) {
            moveNPCs();
            npcSpeak();
            listNPCs();
            Command command = parser.getCommand();
            processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Has the game ended?
     * @return whether or not the game has ended
    */
    private boolean gameEnded() {
        return !continuePlaying || !player.isAlive()
               || player.getRoom() == rooms.get("win_room");
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
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private void processCommand(Command command) 
    {
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
                listInventory();
                break;

            case SEARCH:
                listRoomItems();
                break;

            case TELEPORT:
                teleport();
                break;

            case LOOK:
                System.out.println(player.look());
                break;

            case FIGHT:
                fight(command);
                break;
            
            case HIDE:
                System.out.println("Hide from what?");
                break;

            case QUIT:
                quit(command);
                break;
        }
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
     * room, otherwise print an error message. If NPCs are encountered
     * attempt to hide or attack them.
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

        ArrayList<NPC> inRoom = npcsInRoom(player.getRoom());
        if (inRoom.size() > 0) {
            hideOrFight(inRoom);
        }
    }
    
    /**
     * List the NPCs in the player's room.
    */
    private void listNPCs() {
        ArrayList<NPC> inRoom = npcsInRoom(player.getRoom());
        if (inRoom.size() > 0) {
            System.out.print("NPCs in the room:");
            for (NPC npc : npcsInRoom(player.getRoom())) {
                System.out.print(" " + npc.getName());
            }
            System.out.println();
        } else {
            System.out.println("No NPCs here...");
        }
    }

    /**
     * Returns the NPCs that are in a room.
     
     * @param room the room to check
     * @return a list of NPCs in the room
    */
    private ArrayList<NPC> npcsInRoom(Room room) {
        ArrayList<NPC> inRoom = new ArrayList<NPC>();
        for (NPC npc : npcs) {
            if (npc.getRoom() == room) {
                inRoom.add(npc);
            }
        }

        return inRoom;
    }

    /** 
     * Start a fight with an NPC.
     * 
     * @param command a command in the form "fight [NPC name]"
    */
    private void fight(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Fight who?");
        }

        String nameInput = command.getSecondWord();
        NPC target = null;
        for (NPC npc : npcs) {
            String npcName = npc.getName().split(" ")[0];
            if (npcName.toUpperCase().equals(nameInput.toUpperCase())
                && npc.getRoom() == player.getRoom()) {
                target = npc;
                break;
            }
        }

        if (target != null) {
            Fight fight = new Fight(player, target);
            fight.start();
        } else {
            System.out.println("There's nobody named " + nameInput + " here.");
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
     * Let all NPCs in the player's room speak their mind.
    */
    private void npcSpeak() {
        for (NPC npc : npcsInRoom(player.getRoom())) {
            String phrase = npc.getPhrase();
            if (phrase != null) {
                System.out.println(npc.getName() + ": " + phrase);
            }
        }
    }

    /**
     * Determine whether the player hides or fights.
    */
    private void hideOrFight(ArrayList<NPC> inRoom) {
        int randomVal = (new Random()).nextInt(100);

        System.out.print(inRoom.get(0).getName());
        if (inRoom.size() > 2) {
            for (NPC guard : inRoom.subList(1, inRoom.size() - 1)) {
                System.out.print(", " + guard.getName());
             }
        }
        if (inRoom.size() > 1) {
            System.out.print(" and " + inRoom.get(inRoom.size() - 1).getName());
            System.out.print(" are ");
        } else {
            System.out.print(" is ");
        }

        System.out.println(" in the room. Do you hide or fight?");
        CommandWord word = null;
        boolean validCommand = false;
        while (!validCommand) {
            Command command = parser.getCommand();
            word = command.getCommandWord();

            if (word == CommandWord.FIGHT || word == CommandWord.HIDE) {
                validCommand = true;
            } else {
                System.out.println("Invalid command. Either hide or fight!");
            }
        }

        if (word == CommandWord.FIGHT) {
            System.out.print("You attempt to sneak and kill... ");
            if (randomVal < 60/(inRoom.size())) {    // more guards -> harder
                System.out.println("But you fail.");
                getAttacked(inRoom);
            } else {
                System.out.println("And you succeed!");
                for (NPC npc : inRoom) {
                    npc.die();
                }
            }
        } else if (word == CommandWord.HIDE) {
            if (randomVal < 70/(inRoom.size())) {
                System.out.println("You manage to hide in the shadows.");
            } else {
                System.out.println("You fail to hide and have been detected!");
                getAttacked(inRoom);
            }
        }
    }

    /**
     * Get attacked by NPCs.
    */
    private void getAttacked(ArrayList<NPC> attackers) {
        for (NPC attacker : attackers) {
            System.out.println("You are attacked by " +
                               attacker.getName() + ".");
            Fight fight = new Fight(player, attacker);
            fight.start();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
        }
        else {
            continuePlaying = false;  // signal that we want to quit
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

    /**
     * Drop a user specified item, if it's in the player's inventory.
    */
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item toRemove = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equals(itemName)) {
                toRemove = item;
                break;
            }
        }

        if (toRemove != null) {
            player.dropItem(toRemove);
        } else {
            System.out.println("Silly you... You can't drop the " + itemName
                               + " if you don't have it!");
        }
    }

    /**
     * Lists the items in the players inventory.
    */
    private void listInventory() {
        if (player.getInventory().numberOfItems() == 0) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory: " + 
                               player.getInventory() + ".");
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
            System.out.println(player.look());
        } else {
            System.out.println("Dammit man, you can't teleport unless you're in the teleporter.");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
