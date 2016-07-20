import java.util.ArrayList;
import java.util.List;

/**
 * Created by garrethdottin on 7/19/16.
 */
public class replyChain {

    public static void main(String[] args) {

    }

    private ArrayList<Email> sortEmails(ArrayList<Email> theEmailList) {
        for (int i = 1; i < theEmailList.size(); i++) {
            Email temp;
            int currentItem = theEmailList.get(i).getReferences().length;
            int previousItem = theEmailList.get(i - 1).getReferences().length;
            if (currentItem > previousItem) {
                temp = theEmailList.get(i - 1);
                theEmailList.set(i - 1, theEmailList.get(i));
                theEmailList.set(i, temp);
            }

        }
        return theEmailList;
    }

    private  ArrayList<Email> emailswithReplies(ArrayList<Email> theEmaiLList) {
        List<String> uniqueObjList = new ArrayList<String>();
        ArrayList<Email> endOfChainEmails = new ArrayList<Email>();

        for (int i= 0; i < theEmaiLList.size(); i++) {
            Email currentEmail = theEmaiLList.get(i);

            for( int v = 0; v < theEmaiLList.get(i).getReferences().length; v++) {
                String referenceCheck = theEmaiLList.get(i).getReferences()[v];
                for( int z = 0; z < uniqueObjList.size(); z++){
                    if (uniqueObjList.get(z) == referenceCheck) {
                        theEmaiLList.get(i).setEndOfChain(false);
                    }
                }
                uniqueObjList.add(referenceCheck);

            }
        }
        theEmaiLList.forEach((k) -> {
            if (k.isEndOfChain()) {
                endOfChainEmails.add(k);
            }
        });
        return endOfChainEmails;
    }

}
