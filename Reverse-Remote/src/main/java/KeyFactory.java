import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 22.02.2016.
 */
public class KeyFactory {

    public static List<Key> createKeys(List<String> keyDesc, List<String> output) {

        int index = 0;
        List<Key> keys = new ArrayList<Key>();

        for(String desc : keyDesc) {
            Key key = null;
            long lastDuration = 0;

            for(;index<output.size();index++) {
                String line = output.get(index);
                if(line.startsWith("SPACE") || line.startsWith("PULSE")) {
                    continue;
                }

                String[] parts = line.split(" ");
                Type type = Type.valueOf(parts[0]);
                long time = Long.valueOf(parts[1]);
                if(time > 20000) {
                    key = new Key(desc);
                    lastDuration = 0;
                    continue;
                } else if(time < 2000) {
                    //Commands
                    if(type == Type.SPACE) {
                        if(isShort(time)) {
                            key.addShort();
                        } else{
                            key.addLong();
                        }
                    }
                }

            }
            keys.add(key);
        }
        return null;
    }

    private static boolean isShort(long time) {
        if(time > Constants.SHORT - Constants.DELTA && time < Constants.LONG + Constants.DELTA) {
            return true;
        }

        return false;
    }
}
