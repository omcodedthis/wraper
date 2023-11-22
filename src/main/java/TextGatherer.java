import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;


/** This class handles the gathering & parsing of text from a given URL. */
public class TextGatherer {
    private String url;
    private PrintWriter textFile;

    /** Constructor for this class, setting the instance variable & creates the .txt file where all
     * the data will be printed into. */
    public TextGatherer(String u) throws FileNotFoundException {
        url = u;
        textFile = new PrintWriter("PageData.txt");
    }


    /** Gathers text from the URL. */
    public String gatherText() throws IOException {
        URL urlObject = new URL(url);
        BufferedReader output = getHTTPRequestOutput(urlObject);
        return convertToString(output);
    }


    /** Parses the given text (HTML doc). */
    public void parseText(String response) {
        Document document = Jsoup.parse(response);
        Elements ids = document.select("[id]");
        Elements titles = document.select("[title]");
        Elements links = document.select("a[href^=\"https\"]");
        Elements divtexts = document.select("div");
        Elements ptexts = document.select("p");
        Elements atexts = document.select("a");


        // System.out.println(response); to view the response string for debugging purposes.
        // printAllEntities(titles, "title");
        // printAllEntities(links, "href");
        printText(divtexts, "div");
        printText(ptexts, "p");
        printText(atexts, "a");
        System.out.println("A PageData.txt file has been created in your current working directory which contains the webpage data.");
    }


    /** Loops over all entities in the Elements variable & prints them to the terminal. */
    private void printAllEntities(Elements group, String attribute) {
        textFile.println("+--------------------------------------------------------------------------------------------------+");
        textFile.println("List of all the " + attribute + "s from this webpage:");
        textFile.println("+--------------------------------------------------------------------------------------------------+");

        for (Element member : group) {
            String text = member.attr(attribute); // .text() returns the text within the tag.

            textFile.println(text);
        }
    }


    /** Loops over all entities in the Elements variable & prints its text to the terminal. */
    private void printText(Elements group, String attribute) {
        textFile.println("+--------------------------------------------------------------------------------------------------+");
        textFile.println("List of all the " + attribute + "s from this webpage:");
        textFile.println("+--------------------------------------------------------------------------------------------------+");

        for (Element member : group) {
            String text = member.text(); // .text() returns the text within the tag.

            textFile.println(text);
        }

    }


    /** Gets the output (a HTML doc) from by creating a HttpURLConnection & returning it as a
     * BufferedReader Object. */
    private BufferedReader getHTTPRequestOutput(URL urlObject) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) urlObject.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        InputStream is = connection.getInputStream();
        BufferedReader output = new BufferedReader(new InputStreamReader(is));

        return output;
    }


    /** Converts the given BufferedReader object to a String. */
    private String convertToString(BufferedReader output) throws IOException {
        StringBuilder response = new StringBuilder();

        String currentLine = output.readLine();
        while (currentLine != null) {
            response.append(currentLine);
            currentLine = output.readLine();
        }

        output.close();
        return response.toString();
    }
}
