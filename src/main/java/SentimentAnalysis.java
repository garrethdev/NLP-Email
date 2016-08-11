import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.lang.reflect.Array;
import java.util.Properties;
import java.util.ArrayList;

/**
 * Created by garrethdottin on 7/19/16.
 */
public class SentimentAnalysis {
    public void setScore(ArrayList<Email> theEmailList) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit, pos, parse sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        int emailListLength = theEmailList.size();
        for ( int i = 0; i < emailListLength; i++) {
            Double sum = 0.0;
            String processResult = theEmailList.get(i).getText();
            Annotation annotation = pipeline.process(processResult);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                sum += sentiment;
            }
            Double averageSentimentScore = sum / annotation.get(CoreAnnotations.SentencesAnnotation.class).size();
            theEmailList.get(i).setSentimentScore(averageSentimentScore);
        }
    }
    public ArrayList<Double> sentimentScoreIntervals(ArrayList<Email> emailList) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit, pos, parse sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        ArrayList<Double> intervalSentimentScores = new ArrayList<Double>();
        Double numberOfSentences = 0.0;
        Double sum = 0.0;
        Double interval = Math.floor(emailList.size() * .20);
        for (Integer i = 0; i < emailList.size(); i++) {
            String processResult = emailList.get(i).getText();
            Annotation annotation = pipeline.process(processResult);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence. get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                sum += sentiment;
            }
            numberOfSentences += annotation.get(CoreAnnotations.SentencesAnnotation.class).size();
            if (i % interval == 0) {
                Double sumForInterval = sum / numberOfSentences;
                intervalSentimentScores.add(sumForInterval);
                numberOfSentences = 0.0;
                sum = 0.0;
            }
        }
        return intervalSentimentScores;
    }



    public static void main(String[] args) {


    }
}
