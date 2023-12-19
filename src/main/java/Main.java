import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;
import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import io.github.amithkoujalgi.ollama4j.core.models.OllamaResult;
import io.github.amithkoujalgi.ollama4j.core.types.OllamaModelType;


/** The Main class gets the requested URL from the user & passes it to the TextGatherer Class. */
public class Main {

    public static void main(String[] args) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {
        // this segment starts the Docker Engine & starts the ollama container.
        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd.exe /c start C:\\\"Program Files\"\\Docker\\Docker\\\"Docker Desktop.exe\"");
        Thread.sleep(15000); // added so that below will run after the Docker Engine is started.
        rt.exec("cmd.exe /c docker start ollama");

        String host = "http://localhost:11434/";
        OllamaAPI ollamaAPI = new OllamaAPI(host);

        // scans for the user's URL
        Scanner scan = new Scanner(System.in);
        System.out.println("> Enter the URL: ");
        String url = scan.nextLine();
        System.out.println("> Generating response, please wait. This may take some time depending on the webpage & your device.");

        // passes the URL to TextGatherer where it parses the relevant data from the webpage.
        TextGatherer tg = new TextGatherer(url);
        String data = tg.gatherAndParseText();

        // sends a prompt to Ollama's orca-mini model & prints the summarized response.
        OllamaResult answer = ollamaAPI.ask(OllamaModelType.ORCAMINI, "Summarize this text in less than 50 words. Include the overall sentiment (positive, neutral or negative) of the text at the end of your response. Here is the text: " + data);
        String cleanResponse = ">" + " Webpage Summary: \n" + answer.getResponse().replace("null", "");
        System.out.println(cleanResponse);
    }
}
