/**
 * Created by garrethdottin on 7/6/16.
 */
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreNLPProtos;
import edu.stanford.nlp.simple.*;
import edu.stanford.nlp.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
import java.util.HashMap;
import java.util.Collections;
public class Senti {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("annotators","tokenize,ssplit, pos, parse sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation annotation = pipeline.process("e");

        Email em1 = new Email();
        em1.setReferenceId("id1");
        em1.setReferences(new String[]{"id3", "id10", "id4", "id2"  });

        Email em2 = new Email();
        em2.setReferenceId("id2");
        em2.setReferences(new String[]{"id5", "id10"});

        Email em3 = new Email();
        em1.setReferenceId("id3");
        em3.setReferences( new String[]{"id11", "id14", "id44"});

        ArrayList<Email> theEmailList = new ArrayList<Email>();
        theEmailList.add(em1);
        theEmailList.add(em2);
        theEmailList.add(em3);

        ArrayList uniqueObj = new ArrayList();
        uniqueObj.add("id555");
        sortEmails(theEmailList);
        emailswithReplies(theEmailList);

        for(CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            System.out.println(sentiment);

        }
    }

    public static ArrayList<Email> sortEmails(ArrayList<Email> theEmailList) {
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


    public static ArrayList<Email> emailswithReplies(ArrayList<Email> theEmaiLList) {
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


