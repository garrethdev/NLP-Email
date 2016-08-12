/**
 * Created by garrethdottin on 7/6/16.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.json.*;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.sun.tools.internal.jxc.ap.Const;

import java.util.List;
import java.util.Arrays;


public class Sentiment {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        try {
            Results jsonWrapper = mapper.readValue(new File(Constants.HOME_ROUTE), Results.class);
            ArrayList<Email> cleanedDataSet = Util.cleanInput(jsonWrapper.getResults());
            emailAnalysis emailChain = new emailAnalysis();
            ArrayList<String> testSubjects = new ArrayList(Arrays.asList("harukahaoki@gmail.com", "<harukahaoki@gmail.com>","philipcr@miamioh.edu", "<philipcr@miamioh.edu>","ariellacentlivre@gmail.com", "<ariellacentlivre@gmail.com>"  ));
            emailChain.returnOutgoingEmails(cleanedDataSet, testSubjects);

            ArrayList<Email> sortedEmails = emailChain.sortEmailsByChainLength(cleanedDataSet);
            emailChain.emailswithReplies(sortedEmails);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
