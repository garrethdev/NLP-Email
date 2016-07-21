import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.gson.Gson;
import org.junit.Test;

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

            emailAnalysis emailChain = new emailAnalysis();
            Email[] sortedEmails = emailChain.sortEmails(jsonWrapper.getResults());

            System.out.println(sortedEmails);

            for (int i = 0; i < sortedEmails.length; i++) {
                // String[] currentEmail = sortedEmails[i];

            }

            // Check to make sure the getReference works and is equal to or greater than the length
            //
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
