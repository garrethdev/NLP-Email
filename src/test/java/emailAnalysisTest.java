import java.io.IOException;
import com.google.gson.Gson;
import org.junit.Test;
import java.io.FileNotFoundException;

import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by dottig2-adm on 7/21/2016.
 */
public class emailAnalysisTest {
    @Test
    // Checks to see if emails are sorted by the length of their email replies
    public void sortEmails () {
        try {
            Gson gson = new Gson();
            String jsonContents = Util.readFile("src/modResults.json");
            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);
            ArrayList<Email> cleanedDataSet = Util.cleanInput(jsonWrapper.getResults());
            emailAnalysis emailChain = new emailAnalysis();
            ArrayList<Email> sortedEmails = emailChain.sortEmails(cleanedDataSet);
            Boolean outOfOrder = false;

            for (int i = 1; i < sortedEmails.size(); i++) {
                Integer nextEmailLength = sortedEmails.get(i).getReferences().length;
                Integer currentEmailLength = sortedEmails.get(i-1).getReferences().length;
                if (currentEmailLength < nextEmailLength) {
                    outOfOrder = true;
                }
            }
            assertEquals(outOfOrder, false);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void averageEmailChain() {
        try {
            Gson gson = new Gson();
            String jsonContents = Util.readFile("src/modResults.json");
            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);
            emailAnalysis emailChain = new emailAnalysis();
            Double average =  emailChain.averageEmailChain(jsonWrapper.getResults());
            assertNotNull(average);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wordFrequency () {
        try {
            Gson gson = new Gson();
            String jsonContents = Util.readFile("src/modResults.json");
            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);

            emailAnalysis emailChain = new emailAnalysis();
            ArrayList<Email> cleanedDataSet = Util.cleanInput(jsonWrapper.getResults());
            HashMap<String, ArrayList<String>> wordFreq = emailChain.wordFrequency(cleanedDataSet);
            ArrayList<ArrayList> arrayOFFlaggedWords = new ArrayList();
            Integer flaggedWordCount = 0;
            Integer testFlaggedCount = 0;
            for (String word : wordFreq.keySet()) {
                flaggedWordCount += wordFreq.get(word).size();
            }
            for (Email email : cleanedDataSet) {
                    String[] currentText = email.getText().split(" ");
                    for( String text : currentText) {
                        if (text.equals("like") || text.equals("maybe"))
                            testFlaggedCount++;
                    }
            }

            // Should check each Key Value  not just hardcoded versions

            assertEquals(testFlaggedCount, flaggedWordCount);

            // if the input text doesnt have any matches the return arrays should be empty
            // if the sample text does have any matches the arrays count should match the expected
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
    //emailswithReplies check whether the references are unique across collection

    // wordFrequency

}
