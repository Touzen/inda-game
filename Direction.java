/**
 * An enum to handle directions.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public enum Direction {
    NORTH("north", "n"),
    SOUTH("south", "s"),
    EAST("east", "e"),
    WEST("west", "w"),
    UP("up", "u"),
    DOWN("down", "d");

    private Direction opposite;
    private String longDirection;
    private String shortDirection;

    static {
        NORTH.opposite = SOUTH;
        SOUTH.opposite = NORTH;
        EAST.opposite = WEST;
        WEST.opposite = EAST;
        UP.opposite = DOWN;
        DOWN.opposite = UP;
    }

    private Direction (final String longDirection, final String shortDirection) {
        this.longDirection = longDirection;
        this.shortDirection = shortDirection;
    }

    @Override
    public String toString() {
        return longDirection;
    }

    public Direction getOpposite() {
        return opposite;
    }
}
