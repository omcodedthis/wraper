import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


/** This class handles the gathering & parsing of text from a given URL. */
public class TextGatherer {
    private String url;

    /** Constructor for this class, setting the instance variable. */
    public TextGatherer(String u) {
        url = u;
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
        Elements links = document.select("a[href]");

        for (Element link : links) {
            String linkText = link.attr("href");
            System.out.println(linkText);
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