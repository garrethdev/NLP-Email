import edu.stanford.nlp.util.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by garrethdottin on 7/19/16.
 */
public class Util {
    public static String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    public  static ArrayList<Email> cleanInput(Email[] theEmailList) {
        ArrayList<Email> cleanedDataSet = new ArrayList<Email>();

        for (int i = 0; i < theEmailList.length; i++) {
            if (theEmailList[i].getText() != null) {
                cleanedDataSet.add(theEmailList[i]);
            }
        }

        return cleanedDataSet;
    }
}
