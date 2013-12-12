/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  Michael Kölling, David J. Barnes and Thomas Vakili
 * @version 2013.12.12
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"),
    QUIT("quit"),
    HELP("help"), 
    PICKUP("pickup"), 
    DROP("drop"),
    INVENTORY("inventory"),
    SEARCH("search"),
    UNKNOWN("?");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
