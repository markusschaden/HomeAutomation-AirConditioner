import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 22.02.2016.
 */
public class Key {

    String desc;
    List<Boolean> breaks = new ArrayList<Boolean>();

    public Key(String desc) {
        this.desc = desc;
    }

    public void addShort() {
        breaks.add(false);
    }

    public void addLong() {
        breaks.add(true);
    }
}
