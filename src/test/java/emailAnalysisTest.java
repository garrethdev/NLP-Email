import java.io.IOException;
import com.google.gson.Gson;
import org.junit.Test;
import java.io.FileNotFoundException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;

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
    //emailswithReplies check whether the references are unique across collection

    // wordFrequency

}
