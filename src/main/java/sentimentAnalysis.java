import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

/**
 * Created by garrethdottin on 7/19/16.
 */
public class sentimentAnalysis {
    public void setScore(Results jsonWrapper) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit, pos, parse sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        int emailListLength = jsonWrapper.getResults().length;
        for ( int i = 0; i < emailListLength; i++) {
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
    public static void main(String[] args) {


    }
}
