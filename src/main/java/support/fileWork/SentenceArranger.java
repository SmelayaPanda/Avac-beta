package support.fileWork;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class SentenceArranger {

    public static void main(String[] args) throws IOException {

        // ---------------------------------------------------------------
        // Считываем все слова английские слова с ретингами в мапу
        FileInputStream inputStream = null;
        Scanner sc = null;
        inputStream = new FileInputStream("/Users/panda/IdeaProjects/Avac-beta-0.0.12/src/main/resources/intermediateFiles/sortedWords");
        sc = new Scanner(inputStream, "UTF-8");
        Map<String, Integer> map = new HashMap<>();
        int counter = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] a = line.split(":");
            map.put(a[0].trim(), Integer.parseInt(a[1].trim()));
            counter++;
        }
        System.out.println(" Слова считаны. Всего " + counter);
        // ---------------------------------------------------------------
        Map<String, Integer> sortedMap = FileReader.sortByValue(map);
        String addFileName = "/Users/panda/IdeaProjects/Avac-beta-0.0.12/src/main/resources/intermediateFiles/englishSentense";

        // Для какждого слова бежим по всей википедии по одному файлу
        // Если нашли более 100 предложений (для первых 100 тыс. слов) - останавливаемся
        //            более 10 предложений  (для последующих слов)

        // Записываем эти предложения в файл через - word @@@ Sentence example построчно
        int sentenceCounter = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(addFileName))) {
            for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                String fullFile;
                for (int i = 1; i < 4633; i++) {
                    fullFile = iterateByWiki(i);
                    String[] sentenceArray = fullFile.split("\\. ");
                    String sentence;
                    for (String rs : sentenceArray) {
                        sentence = rs.trim();
                        if (sentence.length() > 21 &&
                                sentence.length() <= 128 &&
                                Character.isUpperCase(sentence.charAt(0)) &&
                                isAlphaWithPunctuation(sentence) &&
                                !sentence.contains("http") &&
                                sentence.contains(entry.getKey())
                                )
                            sentenceCounter++;
                        if (sentenceCounter > 100)
                        {
                            if (entry.getValue() > 30) {
                                bw.write(entry.getKey() + "@@@" + sentence + "\n");
                            }
                        }
                        System.out.println(sentence + "\n");
                    }
                }


            }

            // для каждого слова из мапы ищем 100 предложений

            //
        }

    }

    private static String readLineByLineJava8(String... filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        for (String f : filePath) {
            try (Stream<String> stream = Files.lines(Paths.get(f), StandardCharsets.UTF_8)) {
                stream.forEach(s -> contentBuilder.append(s.trim()).append(" "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contentBuilder.toString();
    }

    private static boolean isAlphaWithPunctuation(String name) {
        return name.matches("[A-Za-z .,!\"'/$]*");
    }

    public static String iterateByWiki(Integer i) {
        if (i >= 1 && i < 10) {
            return readLineByLineJava8("C:\\wiki\\20140615-wiki-en_00000" + i + ".txt\\20140615-wiki-en_00000" + i + ".txt");
        }
        if (i >= 10 && i < 100) {
            return readLineByLineJava8("C:\\wiki\\20140615-wiki-en_0000" + i + ".txt\\20140615-wiki-en_0000" + i + ".txt");
        }
        if (i >= 100 && i < 1000) {
            return readLineByLineJava8("C:\\wiki\\20140615-wiki-en_000" + i + ".txt\\20140615-wiki-en_000" + i + ".txt");
        }
        if (i >= 1000 && i < 4633) {
            return readLineByLineJava8("C:\\wiki\\20140615-wiki-en_00" + i + ".txt\\20140615-wiki-en_00" + i + ".txt");
        } else return "";
    }
}
