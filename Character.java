/**
 * A class to represent character.
 *
 * Used to subclass Player and NPC.
 *
 * @author Thomas Vakili
 * @version 2013.12.14
*/
public abstract class Character {
    private String name;
    private Room currentRoom;

    // Fighting variables
    private boolean blocking;
    private int hp;
    
    public Character(String name, Room room) {
        this.name = name;
        currentRoom = room;
        hp = 100; // MAGICAL CONSTANT
    }

    /**
     * Moves the character to a new room.
     *
     * @param direction what direction to move in
     * @return whether or not the move succeeded
    */
    public boolean move(Direction direction) {
        Room newRoom = currentRoom.getExit(direction);

        if(newRoom != null) {
            setRoom(newRoom);
            return true;
        }

        return false;
    }

    /**
     * This method is used in fights to get an action.
     *
     * @param enemy the enemy the Character is fighting
     * @return the action the Character chooses
    */
    public abstract Action getAction(Character enemy) { }

    /**
     * Deal damage to a target.
     *
     * TODO: The amount of damage "sent" should be calculated
     *       from some form of "strength".
     *
     * @param target the target to attack
     * @return the amount of damage dealt
    */
    public int attack(Character target) {
        blocking = false; // Is this necessary? Unsure.
        damageDealt = target.takeDamage(10);
        
        return damageDealt;
    }

    /**
     * Deal damage to a target.
     *
     * TODO: The amount of damage taken should be calculated
     *       from some form of "strength".
     *
     * @param amount the amount of damage "sent" by the dealer
     * @return the amount of damage taken
    */
    public int takeDamage(int amount) {
        int oldHP = hp;

        if (blocking) {
            amount /= 2;
            blocking = false;
        }

        hp -= amount;

        if (hp < 0) {
            hp = 0;
        }

        return oldHP - hp;
    }

    /**
     * Sets the character's blocking field to true. This lowers the amount
     * of damage taken when attacked.
    */
    public void block() {
        blocking = true;
    }

    /**
     * Method to run after a battle is finished.
    */
    public void battleOver() {
        blocking = false;
        hp = 100; // MAGICAL CONSTANT
    }

    public int getHP() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return currentRoom;
    }

    protected void setRoom(Room room) {
        currentRoom = room;
    }
}
