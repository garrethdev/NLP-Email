import com.google.gson.Gson;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.IOException;

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
            sentimentAnalysis analysis = new sentimentAnalysis();
            analysis.setScore(jsonWrapper);
            Boolean scorePresent = true;
            for (int i = 0; i< jsonWrapper.getResults().length; i++) {
                if (jsonWrapper.getResults()[i].getSentimentScore() == null) {
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
}
