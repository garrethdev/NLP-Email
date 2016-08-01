//import com.google.gson.Gson;
//import org.junit.Test;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
///**
// * Created by dottig2-adm on 7/21/2016.
// */
//public class SentimentAnalysisTest {
//    @Test
//    // Tests whether a value is set on an email
//    public void setScore() {
//        try {
//            Gson gson = new Gson();
//            String jsonContents = Util.readFile("src/modResults.json");
//            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);
//            ArrayList<Email> cleanedDataSet = Util.cleanInput(jsonWrapper.getResults());
//            SentimentAnalysis analysis = new SentimentAnalysis();
//            analysis.setScore(cleanedDataSet);
//            Boolean scorePresent = true;
//            for (int i = 0; i< cleanedDataSet.size(); i++) {
//                if (cleanedDataSet.get(i).getSentimentScore() == null) {
//                    scorePresent = false;
//                }
//            }
//            assertEquals(scorePresent, true);
//        }
//        catch(FileNotFoundException e) {
//            System.out.println(e);
//        }
//        catch(IOException e) {
//            System.out.println(e);
//        }
//    }
//}
