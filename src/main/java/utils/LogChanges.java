package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogChanges {

    /**
     *  log changes in a logFile
     *
     * @param type indicates the changed entries
     * @param changeString holds the changed information
     */
    public static void LogChangesToFile(String type, String changeString){
        Logger logger = Logger.getLogger("changeLogger");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            String currentDate = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(new Date());
            fh = new FileHandler("./" + type + "-" + currentDate + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info(changeString);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
