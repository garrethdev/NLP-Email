import com.google.gson.Gson;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
/**
 * Created by dottig2-adm on 7/21/2016.
 */
public class sentimentAnalysisTest {
    @Test
    // Tests whether a value is set on an email
    public void setScore() {
        try {
            Gson gson = new Gson();
            String jsonContents = Util.readFile("src/modResults.json");
            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);
            ArrayList<Email> cleanedDataSet = Util.cleanInput(jsonWrapper.getResults());
            sentimentAnalysis analysis = new sentimentAnalysis();
            analysis.setScore(cleanedDataSet);
            Boolean scorePresent = true;
            for (int i = 0; i< cleanedDataSet.size(); i++) {
                if (cleanedDataSet.get(i).getSentimentScore() == null) {
                    scorePresent = false;
                }
            }
            assertEquals(scorePresent, true);
        }
        catch(FileNotFoundException e) {
            System.out.println(e);
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
    @Test
    // Check to make sure the to email is always the same
    public void stripInBoundEmails() {
        try {
            // Get file
            // Look for first input
            // Make sure it matches everything else in the input
            // AssertTrue

        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
