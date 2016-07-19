/**
 * Created by garrethdottin on 7/13/16.
 */

import java.io.FileReader;
import javax.json.Json;
import java.lang.reflect.Array;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class parseJson {


    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("modResults.json"));
            Object modObj = ((org.json.simple.JSONObject) obj).get("results");
            // pipe this result to the Sentiment analysis
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
