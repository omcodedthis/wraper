import java.io.IOException;
import java.util.Scanner;


/** The Main class gets the requested URL from the user & passes it to the TextGatherer Class. */
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the URL: ");
        String url = scan.nextLine();

        TextGatherer tg = new TextGatherer(url);
        String text = tg.gatherText();
        tg.parseText(text);
    }
}
