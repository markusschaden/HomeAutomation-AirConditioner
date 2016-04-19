import java.io.IOException;

/**
 * Created by Markus on 22.02.2016.
 */
public class Main {

    public static void main(String [] args) {

        RawCodesWrapper rawCodesWrapper = new RawCodesWrapper();
        try {
            rawCodesWrapper.readFromInfraredSensor();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
