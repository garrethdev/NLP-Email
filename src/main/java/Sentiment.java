/**
 * Created by garrethdottin on 7/6/16.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.util.Arrays;


public class Sentiment {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //TODO Create Methods for Each State you want
        // TODO Get tests working
        try {
            // Testing comments
            Results jsonWrapper = mapper.readValue(new File(Constants.HOME_ROUTE), Results.class);
            ArrayList<Email> cleanedDataSet = Util.cleanInput(jsonWrapper.getResults());
            EmailAnalysis emailChain = new EmailAnalysis();
            emailChain.sortEmailsByChainLength(cleanedDataSet);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
