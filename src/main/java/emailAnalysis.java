import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
/**
 * Created by garrethdottin on 7/19/16.
 */
public class emailAnalysis {

    public static void main(String[] args) {

    }

    public ArrayList<Email> sortEmailsByChainLength (ArrayList<Email> theEmailList) {
        // Broken should be bubble sort
        for (int i = 1; i < theEmailList.size(); i++) {
            Email temp;
            int currentItemLength;
            int previousItemLength;
            String[] currentItem = theEmailList.get(i).getReferences();
            String[] previousItem = theEmailList.get(i - 1).getReferences();
            if (currentItem == null) {
                currentItemLength = 0;
            }else {
                currentItemLength = theEmailList.get(i).getReferences().length;
            }
            if (previousItem == null) {
                previousItemLength = 0;
            }
            else {
                previousItemLength = theEmailList.get(i-1).getReferences().length;
            }
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
            int currentEmailLength;
            if (currentEmail.getReferences() == null ) {
                currentEmailLength = 0;
            }
            else {
                currentEmailLength = theEmaiLList.get(i).getReferences().length;
            }
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


    public HashMap wordFrequency(ArrayList<Email> theEmailList) {
        HashMap<String, ArrayList> wordFrequency = new HashMap<>();
        ArrayList<String> wordsToCheck = new ArrayList<>();
        ArrayList<String> firstFlaggedWord = new ArrayList<String>();
        ArrayList<String> secondFlaggedWord = new ArrayList<String>();
        ArrayList<Double> firstFlaggedWordFrequency = new ArrayList<Double>();
        ArrayList<Double> secondFlaggedWordFrequency = new ArrayList<Double>();

        wordFrequency.put("like",firstFlaggedWord);
        wordFrequency.put("maybe", secondFlaggedWord);
        wordsToCheck.add("like");
        wordsToCheck.add("maybe");
        for (int i = 0; i <theEmailList.size(); i++) {
            // one email
            String[] emailText = theEmailList.get(i).getText().split(" ");
            Integer emailLength = emailText.length;
            for (int v = 1; v < emailLength; v++) {
                // one word
                String currentWord = emailText[v];
                for (int z = 0; z < wordsToCheck.size(); z++) {
                    String flaggedWord = wordsToCheck.get(z);
                    if (currentWord.equals(flaggedWord)) {
                        String flaggedWordContext = emailText[v -1] + " " + emailText[v] + " " + emailText[v +1];
                        wordFrequency.get(flaggedWord).add(flaggedWordContext);
                    }
                }
            }
        }

        // Put in new function
        int likeFrequency = wordFrequency.get("like").size();
        double d = (double) likeFrequency;
        Double likeFrequencyVal = d /100;

        int maybeFrequency = wordFrequency.get("maybe").size();
        double m = (double) maybeFrequency;
        Double maybeFrequencyVal = m / 100;

        firstFlaggedWordFrequency.add(likeFrequencyVal);
        secondFlaggedWordFrequency.add(maybeFrequencyVal);
        wordFrequency.put("likeFrequency", firstFlaggedWordFrequency);
        wordFrequency.put("maybeFrequency",secondFlaggedWordFrequency);

        return wordFrequency;
    }
}
