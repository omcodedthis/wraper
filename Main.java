import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TextGatherer tg = new TextGatherer("https://en.wikipedia.org/wiki/Scrape");
        String text = tg.gatherText();
        tg.parseText(text);
    }
}
