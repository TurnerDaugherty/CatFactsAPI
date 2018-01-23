import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray; 		
import org.json.JSONException;
import org.json.JSONObject;

public class CatFacts {
  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }
  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {     
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader read = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(read);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }  
}
  public static void main(String[] args) throws IOException, JSONException {
   BufferedReader input = new BufferedReader(new InputStreamReader(System.in));  //Asks and reads how many cat facts are wanted
   int catInput;								//Creates integer variable "catInput" 
   System.out.println("Enter the number of Cat Facts you want (up to 1000!): ");	  //Prints the question asking the number of cat facts
   catInput = Integer.parseInt(input.readLine());  	    			 	 //Parses the input into the variable "catInput"
   JSONObject json = readJsonFromUrl("https://catfact.ninja/facts?limit=" + (catInput));  //The number you input changes the link to where it shows how many cat facts will be produced.
   List<String> list = new ArrayList<String>();
   JSONArray array = json.getJSONArray("data");               //Looks at the arrays from the "data" field on the JSON Document (full of cat facts and fact lengths)
   for(int i = 0 ; i < array.length() ; i++){                  //Loops while getting the strings for number of specified cat facts
	   list.add(array.getJSONObject(i).getString("fact"));     //Pulls the specific facts from the "fact" field on the JSON Document 
   }
   
   
   System.out.println("Here are " + catInput + " Cat Facts!" + "\n_____________________ \n");   					 //Printing the number of cat facts you selected
   System.out.println(list.toString().replaceAll(("\\.,") , ". \n \n").replaceAll("\\[", " ").replaceAll("\\]",""));  // In the JSON api each "fact" is ended with ".,". I decided to replace all ".,"  with two spaces (\n) to spread the data out and then adding the period (.) back.																												                 //Next I removed all of the "[" "]" with a blank space to make it look cleaner
  }															  //I Also remove the [ & ] to make the response look cleaner
}