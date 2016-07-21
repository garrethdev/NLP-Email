/**
 * Created by garrethdottin on 7/6/16.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.gson.Gson;

public class Sentiment {
    public static void main(String[] args) {

        try {
            Gson gson = new Gson();
            String  jsonContents = Util.readFile("src/modResults.json");
            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);
//            sentimentAnalysis sentiment = new sentimentAnalysis();
//            sentiment.setScore(jsonWrapper);
            emailAnalysis emailChain = new emailAnalysis();
            Email[] sortedEmails = emailChain.sortEmails(jsonWrapper.getResults());
//            emailChain.emailswithReplies(sortedEmails);
            emailChain.wordFrequency(sortedEmails);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
