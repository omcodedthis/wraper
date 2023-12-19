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
    private String data;

    /** Constructor for this class, setting the instance variable & creates the .txt file where all
     * the data will be printed into. */
    public TextGatherer(String u) {
        url = u;
    }


    /** Gathers & parses from the URL. */
    public String gatherAndParseText() throws IOException {
        URL urlObject = new URL(url);
        BufferedReader output = getHTTPRequestOutput(urlObject);
        String outputText = convertToString(output);
        return parseText(outputText);
    }


    /** Parses the given text (HTML doc). */
    private String parseText(String response) {
        Document document = Jsoup.parse(response);
        Elements ptexts = document.select("p");
        Elements spantexts = document.select("span");

        addText(ptexts);
        addText(spantexts);
        return data;
    }


    /** Loops over all entities in the Elements variable & add its text to data. */
    private void addText(Elements group) {
        for (Element member : group) {
            String text = member.text(); // .text() returns the text within the tag.

            data += text;

            if (data.length() > 2500) {
                //System.out.println("Limit reached!");
                return;
            }
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
