/**
 * Created by garrethdottin on 7/6/16.
 */
import com.google.gson.stream.JsonReader;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreNLPProtos;
import edu.stanford.nlp.simple.*;
import edu.stanford.nlp.*;
import java.util.List;
import edu.stanford.nlp.parser.*;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.SentimentAnnotator;
import edu.stanford.nlp.ling.CoreLabel;
import java.util.Properties;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentClass;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import com.google.protobuf.GeneratedMessage;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.io.FileReader;
import javax.json.Json;
import java.lang.reflect.Array;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.*;
import org.json.simple.*;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.Map;
import com.google.gson.reflect.TypeToken;
import java.util.*;
import java.io.File;

public class Sentiment {
    private static String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    public void client(String[] args) {
        //
        DBInterface client = new DBInterface();
//        client.saveToDB();

    }
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit, pos, parse sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        try {

            Gson gson = new Gson();

            String  jsonContents = readFile("modResults.json");
            Results jsonWrapper = gson.fromJson(jsonContents, Results.class);
            int num = jsonWrapper.getResults().length;
            for ( int i = 0; i < num; i++) {
                Double sum = 0.0;
                String processResult = jsonWrapper.getResults()[i].getText();
                Annotation annotation = pipeline.process(processResult);
                for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                    int sentiment = RNNCoreAnnotations.getPredictedClass(tree);

                    sum += sentiment;

                }
                Double averageSentimentScore = sum / annotation.get(CoreAnnotations.SentencesAnnotation.class).size();
                jsonWrapper.getResults()[i].setSentimentScore(averageSentimentScore);
            }

       }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
