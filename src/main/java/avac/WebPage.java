package avac;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class WebPage {

    static Set<String> getPageWords(String address) {

        Set<String> wordList = new HashSet<>();
        String[] line;
        Document doc = null;
        try {
            doc = Jsoup.connect(address).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements paragraphs = null;
        if (doc != null) {
            paragraphs = doc.select("p");
        }
        if (paragraphs != null) {
            for (Element p : paragraphs) {
                line = p
                        .text()
                        .replaceAll("[^a-zA-Z\\s]", "")  // excluding of the punctuation and numbers
                        .replaceAll("\\s+", " ")
                        .toLowerCase()
                        .split(" ");
                Collections.addAll(wordList, line);
            }
        }
        return wordList;
    }
}