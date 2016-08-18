import java.io.IOException;
import com.google.gson.Gson;
import org.junit.Test;
import java.io.FileNotFoundException;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dottig2-adm on 7/21/2016.
 */
public class EmailAnalysisTest {
    @Test
    // Checks to see if emails are sorted by the length of their email replies
    public void sortEmails () {
        try {
            Gson gson = new Gson();
            String jsonContents = Util.readFile("src/modResults.json");
            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);
            ArrayList<Email> cleanedDataSet = Util.cleanInput(jsonWrapper.getResults());
            EmailAnalysis emailChain = new EmailAnalysis();

            ArrayList<Email> sortedEmails = emailChain.sortEmailsByChainLength(cleanedDataSet);
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
            EmailAnalysis emailChain = new EmailAnalysis();
            ArrayList<Email> cleanedInput = Util.cleanInput(jsonWrapper.getResults());
            Double average =  emailChain.averageEmailChain(cleanedInput);
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
            EmailAnalysis emailChain = new EmailAnalysis();
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

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void sortEmailsByChainLength () {
        try {
            Gson gson = new Gson();
            String jsonContents = Util.readFile("src/modResults.json");
            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);
            EmailAnalysis emailChain = new EmailAnalysis();
//            ArrayList<Email> cleanedDataSet = Util.cleanInput(jsonWrapper.getResults());
//            ArrayList<Email> testedResults = emailChain.sortEmailsByChainLength(cleanedDataSet);
//
//            Integer currentItem = 0;
//            Integer previousItem = 0;
//            Boolean flagResults = false;
//            for (Email results : testedResults) {
//                if (previousItem >currentItem ) {
//                    flagResults = true;
//                }
//                previousItem = currentItem;
//                currentItem = results.getReferences().length;
//
//            }
//
//            assertEquals(flagResults, true);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}
