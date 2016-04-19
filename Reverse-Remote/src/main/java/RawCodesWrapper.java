import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Markus on 22.02.2016.
 */
public class RawCodesWrapper {

    public void readFromInfraredSensor() throws IOException {

        Runtime rt = Runtime.getRuntime();

        Process proc = rt.exec("C:\\HomeAutomation\\AirConditioner\\WinLIRC\\RawCodes.exe", null, new File("C:\\HomeAutomation\\AirConditioner\\WinLIRC\\"));

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

    }


}
