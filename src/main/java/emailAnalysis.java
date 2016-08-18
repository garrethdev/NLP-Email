import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * Created by garrethdottin on 7/19/16.
 */
public class EmailAnalysis {

    public static void main(String[] args) {

    }

    public int firstEmailSize (ArrayList<Email> emailList) {
        int numberFirstEmails = 0;
        ArrayList<Email> cleanedDataSet = new ArrayList();
            for (int i = 0; i < emailList.size(); i++) {
                Email currentEmail = emailList.get(i);
                for (int v = 0; v < cleanedDataSet.size(); v++) {
                    Email currentCleanedEmail = cleanedDataSet.get(v);
                        if (!currentEmail.getReplyTo().equals(currentCleanedEmail.getReplyTo())) {
                            cleanedDataSet.add(currentEmail);
                        }
                }
            }

        numberFirstEmails = cleanedDataSet.size();

        return numberFirstEmails;
    }

    public ArrayList<Integer> emailLengthArr(ArrayList<Email> emailList) {
        ArrayList<Integer> emailLengths = new ArrayList<>();
        for (int i = 0; i < emailList.size(); i++) {
            Email currentEmail = emailList.get(i);
            Integer emailLength = currentEmail.getText().length();
            emailLengths.add(emailLength);
        }
        Collections.sort(emailLengths);
        return emailLengths;
    }

    // Enhancement figure out how many sentences they have in each email
    //
    public HashMap<Double, Integer> percentageBreakdownEmailLength(ArrayList<Integer> emailList) {
        Integer sizeofArr = emailList.size();
        HashMap<Double,Integer> percentileResults = new HashMap<Double, Integer>();
        ArrayList<Double> percentiles = new ArrayList<Double>();
        percentiles.addAll(Arrays.asList(.25, .5, .75, .9));

        for (int i = 0; i < percentiles.size(); i++) {
            Integer percentVal = emailList.get((int) Math.floor(sizeofArr * percentiles.get(i)));
            Double percentKey = percentiles.get(i);
            percentileResults.put(percentKey, percentVal);
        }

        return percentileResults;
    }

    public ArrayList<Integer> emailLengthBySentence(ArrayList<Email> emailList) {
        // go over each and grab the sentences
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit, pos, parse sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Integer numberOfSentences = 0;
        ArrayList<Integer> sentenceLength = new ArrayList<Integer>();
        for (Integer i = 0; i < emailList.size(); i++) {
            String processText = emailList.get(i).getText();
            Annotation annotation = pipeline.process(processText);
            Integer Counter = 0;
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Counter++;
            }
            sentenceLength.add(Counter);
        }
        return sentenceLength;
    }



    public ArrayList<Email> firstEmailinChain (ArrayList<Email> emailList) {
        ArrayList<Email> firstEmails = new ArrayList<Email>();
        for (int i = 0; i < emailList.size(); i++) {
            Email currentEmail = emailList.get(i);
            if (currentEmail.getReferences().length == 0) {
                firstEmails.add(currentEmail);
            }
        }
        return firstEmails;
    }

    public  ArrayList<Email> returnOutgoingEmails(ArrayList<Email> emailList, ArrayList input) {
        ArrayList<Email> outgoingEmails = new ArrayList<>();

        for (int i = 0; i < emailList.size(); i++) {
            Email currentEmail = emailList.get(i);
            for (int v = 0; v < input.size(); v++) {
                if (currentEmail.getFromName().equals(input.get(v))) {
                    outgoingEmails.add(currentEmail);
                }
            }
        }

        return outgoingEmails;
    }

    public int firstInChain(ArrayList<Email> emailList) {
        ArrayList<Email> firstInChain  = new ArrayList();

        for (int i = 0; i < emailList.size(); i++) {
            Email currentEmail = emailList.get(i);
            if (currentEmail.getReferences().length == 0) {
                firstInChain.add(currentEmail);
            }
        }

        return firstInChain.size();
    }

    public ArrayList<Email> sortEmailsByChainLength (ArrayList<Email> theEmailList) {
        // Broken should be bubble sort
        for (int i = 1; i < theEmailList.size(); i++) {
            Email temp;
            int currentItemLength = theEmailList.get(i).getReferences().length;
            int previousItemLength = theEmailList.get(i-1).getReferences().length;
            String[] currentItem = theEmailList.get(i).getReferences();
            String[] previousItem = theEmailList.get(i - 1).getReferences();

            if (currentItemLength > previousItemLength) {
                temp = theEmailList.get(i -1);
                theEmailList.set(i - 1,theEmailList.get(i));
                theEmailList.set(i, temp);
            }
        }
        return theEmailList;
    }

    public ArrayList<Email> emailswithReplies(ArrayList<Email> theEmaiLList) {
        List<String> uniqueObjList = new ArrayList<String>();
        ArrayList<Email> endOfChainEmails = new ArrayList<Email>();

        for (int i= 0; i < theEmaiLList.size(); i++) {
            Email currentEmail = theEmaiLList.get(i);
            int currentEmailLength = theEmaiLList.get(i).getReferences().length;
            for( int v = 0; v < currentEmailLength; v++) {
                String referenceCheck = theEmaiLList.get(i).getReferences()[v];
                for( int z = 0; z < uniqueObjList.size(); z++){
                    if (uniqueObjList.get(z) == referenceCheck) {
                        theEmaiLList.get(i).setEndOfChain(false);
                    }
                }
                uniqueObjList.add(referenceCheck);
            }
        }
        for (int i = 0; i < theEmaiLList.size(); i++) {
            if (theEmaiLList.get(i).isEndOfChain()) {
                endOfChainEmails.add(theEmaiLList.get(i));
            }
        }

        return endOfChainEmails;
    }

    public Double averageEmailChain(ArrayList<Email> theEmailList) {
        Double averageEmailCount = 0.0;
        Double sum = 0.0;

        for (int i = 0; i <theEmailList.size(); i++) {
            sum += theEmailList.get(i).getReferences().length;
        }

        averageEmailCount = sum / theEmailList.size();
        return averageEmailCount;
    }

    public Double averageEmailChainReferences(ArrayList<Email> theEmailList) {
        Double averageEmailCount = 0.0;
        Double sum = 0.0;
        Double counter = 0.0;
        for (int i = 0; i <theEmailList.size(); i++) {
            if (theEmailList.get(i).getReferences().length > 0) {
                counter++;
                sum += theEmailList.get(i).getReferences().length;
            }
        }
        averageEmailCount = sum / counter;
        return averageEmailCount;
    }

    public HashMap wordFrequency(ArrayList<Email> theEmailList) {
        HashMap<String, ArrayList<String>> wordFrequency = new HashMap<String, ArrayList<String>>();

        Map<String, ArrayList> flaggedWords = new HashMap<>();

        initializeWordFrequency(wordFrequency, "like", "maybe", "perhaps", "dont know", "unsure", "sorry");

        for (int i = 0; i <theEmailList.size(); i++) {
            String[] emailText = theEmailList.get(i).getText().split(" ");
            Integer emailLength = emailText.length;
            for (int v = 1; v < emailLength; v++) {
                String currentWord = emailText[v];
                for (Map.Entry<String, ArrayList<String>> entry : wordFrequency.entrySet()) {
                    String flaggedWord = entry.getKey();
                    if (currentWord.toLowerCase().equals(flaggedWord)) {
                        String flaggedWordContext = emailText[v -1] + " " + emailText[v] + " " + emailText[v +1];
                        wordFrequency.get(flaggedWord).add(flaggedWordContext);
                    }
                }
            }
        }

        return wordFrequencyPercentange(wordFrequency,theEmailList);
    }

    private void initializeWordFrequency(HashMap<String, ArrayList<String>> map, String... keys) {
        for(String key: keys) {
            map.put(key, new ArrayList<String>());
        }
    }

    private HashMap<String, ArrayList<Double>> wordFrequencyPercentange(HashMap<String, ArrayList<String>>  wordFrequency, ArrayList<Email> emailList) {
        ArrayList<Double> firstFlaggedWordFrequency = new ArrayList<Double>();
        ArrayList<Double> secondFlaggedWordFrequency = new ArrayList<Double>();
    // TODO refactor this with creation of hashmap and arrays
        Double perHundred = (double) 100 / emailList.size();
        int likeFrequency = wordFrequency.get("like").size();
        double d = (double) likeFrequency;
        Double likeFrequencyVal = d  * perHundred;

        int maybeFrequency = wordFrequency.get("maybe").size();
        double m = (double) maybeFrequency;
        Double maybeFrequencyVal = m  * perHundred;

        firstFlaggedWordFrequency.add(likeFrequencyVal);
        secondFlaggedWordFrequency.add(maybeFrequencyVal);

        HashMap<String, ArrayList<Double>> result = new HashMap<String, ArrayList<Double>>();

        result.put("likeFrequency", firstFlaggedWordFrequency);
        result.put("maybeFrequency",secondFlaggedWordFrequency);

        return result;
    }
}
