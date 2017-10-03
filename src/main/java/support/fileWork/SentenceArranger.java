package support.fileWork;

import support.AnsiColor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class SentenceArranger {

    public static void main(String[] args) throws IOException {

        // Считываем все английские слова с ретингами в мапу
        Map<String, Integer> map = readFileToMap();
        // Сортируем мапу( позволит заново итерироваться с места падения при ошибках )
        Map<String, Integer> sortedMap = FileReader.sortByValue(map);

        // Для какждого слова бежим по всей википедии по одному файлу в иттерации
        // Если нашли более 1000 предложений (для первых 100 тыс. слов) - останавливаемся
        //            иначе сколько нашли столько нашли =) если уже всю вики прочесали

        // Записываем эти предложения в файл через [ word  @@@ Sentence rank ### Sentence example ] построчно
        String fileForWrite = "C:\\Avac-beta\\src\\main\\resources\\intermediateFiles\\englishSentence";
        String wikiFile;
        String sentence;
        String[] splittedSentence;
        int rank;
        int avrRank;
        int sentenceCounter;
        int wordNum = 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileForWrite))) {
            for (Map.Entry<String, Integer> engWord : sortedMap.entrySet()) {
                wordNum++;
                if (wordNum >= 0) { // для случая падения на каком-то слове
                    Date date = new Date();

                    System.out.println(AnsiColor.BLUE + new Timestamp(date.getTime()) + "\n Word №: " + wordNum + " --> " + engWord.getKey() + AnsiColor.RESET);
                    sentenceCounter = 0;

                    search:
                    {
                        for (int i = 1; i < 4633; i++) {
                            if (i % 100 == 0) {
                                System.out.println(" Считали уже " + i + " файлов wiki: " + sentenceCounter + " предложений найдено");
                                if (i == 500 && sentenceCounter == 0) {
                                    break search;
                                }
                                if (i == 1000 && sentenceCounter == 1) {
                                    break search;
                                }
                            }
                            wikiFile = iterateByWiki(i);
                            String[] sentenceArray = wikiFile.split("\\. ");

                            for (String rs : sentenceArray) {
                                sentence = rs.trim();
                                if (isRightSentence(sentence, engWord)) {
                                    splittedSentence = sentence.split(" ");
                                    rank = 0;
                                    avrRank = calcAvrRankOfSentence(sortedMap, splittedSentence, rank);
                                    bw.write(engWord.getKey() + "   @@@   " + avrRank + "   ###   " + sentence + "\n");
                                    bw.flush();
                                    if (searchBreaker(sentenceCounter, wordNum)) {
                                        break search;
                                    }
                                    sentenceCounter++;
                                }
                            }
                        }
                        if (sentenceCounter < 1000) {
                            System.out.println(" Найдено всего " + sentenceCounter + " предложений ");
                        }
                    }
                }
            }
        } catch (Exception e) {
            //
        }
    }

    private static int calcAvrRankOfSentence(Map<String, Integer> sortedMap, String[] splittedSentence, int rank) {
        int avrRank;
        for (String ss : splittedSentence) {
            if (sortedMap.containsKey(ss.trim())) {
                rank += sortedMap.get(ss.trim());
            }
        }
        avrRank = rank / (splittedSentence.length * 10000);
        return avrRank;
    }

    private static boolean searchBreaker(int sentenceCounter, int wordNum) {
        if (wordNum < 10001) {
            if (sentenceCounter == 50) {
                System.out.println(" Найдено всего " + sentenceCounter + " предложений ");
                return true;
            }
        }
        if (wordNum > 10000 && wordNum < 50000) {
            if (sentenceCounter == 10) {
                System.out.println(" Найдено всего " + sentenceCounter + " предложений ");
                return true;
            }
        }
        if (wordNum >= 50000 && wordNum < 100000) {
            if (sentenceCounter == 5) {
                System.out.println(" Найдено всего " + sentenceCounter + " предложений ");
                return true;
            }
        }
        if (wordNum >= 100000) {
            if (sentenceCounter == 3) {
                System.out.println(" Найдено всего " + sentenceCounter + " предложений ");
                return true;
            }
        }
        return false;
    }

    private static boolean isRightSentence(String sentence, Map.Entry<String, Integer> engWord) {
        return sentence.length() > 20 &&
                sentence.length() <= 128 &&
                Character.isUpperCase(sentence.charAt(0)) &&
                isAlphaWithPunctuation(sentence) &&
                !sentence.contains("http") &&
                !sentence.contains("www") &&
                (sentence.contains(" " + engWord.getKey() + " ") || sentence.contains(" " + engWord.getKey()));
    }

    private static Map<String, Integer> readFileToMap() throws FileNotFoundException {
        Map<String, Integer> map = new HashMap<>();
        FileInputStream inputStream;
        Scanner sc;
        inputStream = new FileInputStream("C:\\Avac-beta\\src\\main\\resources\\intermediateFiles\\sortedWords");
        sc = new Scanner(inputStream, "UTF-8");
        int counter = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] a = line.split(":");
            map.put(a[0].trim(), Integer.parseInt(a[1].trim()));
            counter++;
        }
        System.out.println(" Слова считаны. Всего " + counter);
        return map;
    }

    private static String readLineByLineJava8(String... filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        for (String f : filePath) {
            try (Stream<String> stream = Files.lines(Paths.get(f), StandardCharsets.UTF_8)) {
                stream.forEach(s -> contentBuilder.append(s.trim()).append(" "));
            } catch (Exception e) {
                //
            }
        }
        return contentBuilder.toString();
    }

    private static boolean isAlphaWithPunctuation(String name) {
        return name.matches("[A-Za-z .,!\"'/$]*");
    }

    private static String iterateByWiki(Integer i) {
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
