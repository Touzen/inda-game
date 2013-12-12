/**
 * A class to represent items.
 *
 * @author Thomas Vakili
 * @version 2013.12.12
*/
public class Item {
    private int weight;
    private String name;

    public Item(int weight, String name) {
        this.weight = weight;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}
