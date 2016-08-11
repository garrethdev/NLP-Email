import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    public  ArrayList<Email> returnOutgoingEmails(ArrayList<Email> emailList, ArrayList input) {
        ArrayList<Email> outgoingEmails = new ArrayList<>();

        for (int i = 0; i < emailList.size(); i++) {
            Email currentEmail = emailList.get(i);
            for (int v = 0; v < input.size(); v++) {
                if (currentEmail.getFromEmail().equals(input.get(v))) {
                    outgoingEmails.add(currentEmail);
                }
            }
        }

        return outgoingEmails;
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

    public int firstEmails (ArrayList<Email> emailList) {
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
        // Pass to user object
        averageEmailCount = sum / counter;
        return averageEmailCount;
    }

    public HashMap wordFrequency(ArrayList<Email> theEmailList) {
        HashMap<String, ArrayList> wordFrequency = new HashMap<>();
        ArrayList<String> wordsToCheck = new ArrayList<>();
        ArrayList<String> firstFlaggedWord = new ArrayList<String>();
        ArrayList<String> secondFlaggedWord = new ArrayList<String>();
        ArrayList<String> thirdFlaggedWord = new ArrayList<String>();
        ArrayList<String> fourthFlaggedWord = new ArrayList<String>();
        ArrayList<String> fifthFlaggedWord = new ArrayList<String>();
        ArrayList<String> sixthFlaggedWord = new ArrayList<String>();

        wordFrequency.put("like",firstFlaggedWord);
        wordFrequency.put("maybe", secondFlaggedWord);
        wordFrequency.put("perhaps", thirdFlaggedWord);
        wordFrequency.put("dont know", fourthFlaggedWord);
        wordFrequency.put("unsure", fifthFlaggedWord);
        wordFrequency.put("sorry", sixthFlaggedWord);
        wordsToCheck.add("like");
        wordsToCheck.add("maybe");
        wordsToCheck.add("perhaps");
        wordsToCheck.add("don't know");
        wordsToCheck.add("unsure");
        wordsToCheck.add("sorry");
        for (int i = 0; i <theEmailList.size(); i++) {
            String[] emailText = theEmailList.get(i).getText().split(" ");
            Integer emailLength = emailText.length;
            for (int v = 1; v < emailLength; v++) {
                String currentWord = emailText[v];
                for (int z = 0; z < wordsToCheck.size(); z++) {
                    String flaggedWord = wordsToCheck.get(z);
                    if (currentWord.toLowerCase().equals(flaggedWord)) {
                        String flaggedWordContext = emailText[v -1] + " " + emailText[v] + " " + emailText[v +1];
                        wordFrequency.get(flaggedWord).add(flaggedWordContext);
                    }
                }
            }
        }
        wordFrequencyPercentange(wordFrequency,theEmailList);
        return wordFrequency;
    }

    private HashMap wordFrequencyPercentange(HashMap<String, ArrayList>  wordFrequency, ArrayList<Email> emailList) {
        ArrayList<String> wordsToCheck = new ArrayList(Arrays.asList("like", "maybe", "perhaps", "unsure", "sorry", "dont know"));
        HashMap<String, Double> results = new HashMap<>();

        Double perHundred = (double) 100 / emailList.size();

        for (String word : wordsToCheck) {
            double Frequency = wordFrequency.get(word).size();
            Double FrequencyVal = Frequency  * perHundred;
            String key = word + "Frequency";
            results.put(key, FrequencyVal);
        }

        Double sum = 0.0;
        for (String result : results.keySet()) {
            sum += results.get(result);
        }

        results.put("totalFrequency",sum);
        return results;
    }
}
