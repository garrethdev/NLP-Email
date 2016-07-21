import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by garrethdottin on 7/19/16.
 */
public class emailAnalysis {

    public static void main(String[] args) {

    }

    public Email[] sortEmails(Email[] theEmailList) {
        for (int i = 1; i < theEmailList.length; i++) {
            Email temp;
            int currentItem = theEmailList[i].getReferences().length;
            int previousItem = theEmailList[i - 1].getReferences().length;
            if (currentItem > previousItem) {
                temp = theEmailList[i - 1];
                theEmailList[i -1] = theEmailList[i];
                theEmailList[i] = temp;
            }
        }
        return theEmailList;
    }

    public ArrayList<Email> emailswithReplies(Email[] theEmaiLList) {
        List<String> uniqueObjList = new ArrayList<String>();
        ArrayList<Email> endOfChainEmails = new ArrayList<Email>();

        for (int i= 0; i < theEmaiLList.length; i++) {
            Email currentEmail = theEmaiLList[i];

            for( int v = 0; v < theEmaiLList[i].getReferences().length; v++) {
                String referenceCheck = theEmaiLList[i].getReferences()[v];
                for( int z = 0; z < uniqueObjList.size(); z++){
                    if (uniqueObjList.get(z) == referenceCheck) {
                        theEmaiLList[i].setEndOfChain(false);
                    }
                }
                uniqueObjList.add(referenceCheck);

            }
        }
        for (int i = 0; i < theEmaiLList.length; i++) {
            if (theEmaiLList[i].isEndOfChain()) {
                endOfChainEmails.add(theEmaiLList[i]);
            }
        }

        return endOfChainEmails;
    }

    public Double averageEmailChain(Email[] theEmailList) {
        Double averageEmailCount = 0.0;
        Double sum = 0.0;
        for (int i = 0; i <theEmailList.length; i++) {
            sum += theEmailList[i].getReferences().length;
        }

        averageEmailCount = sum / theEmailList.length;
        return averageEmailCount;
    }


    public HashMap wordFrequency(Email[] theEmailList) {
        HashMap<String,Integer> wordFrequency = new HashMap<>();
        ArrayList<String> wordsToCheck = new ArrayList<>();
        wordsToCheck.add("like");
        wordsToCheck.add("maybe");
        for (int i = 0; i <theEmailList.length; i++) {
            // one email
            String[] text = theEmailList[i].getText().split(" ");
            Integer emailLength = text.length;
            for (int v = 0; v < emailLength; v++) {
                for (int z = 0; z < emailLength; z++) {
                    // text[v]
                    // increment count
                    //
                }
            }
            // Set Value on Email Object
            System.out.print(theEmailList[i]);
        }

        return wordFrequency;
    }

}
