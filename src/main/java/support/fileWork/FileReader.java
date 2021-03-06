package support.fileWork;

import java.io.*;
import java.util.*;

public class FileReader {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream("/Users/panda/downloads/words");
            sc = new Scanner(inputStream, "UTF-8");
            Map<String, Integer> map = new HashMap<>();
            int counter = 0;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] a = line.split(":");
                map.put(a[0], Integer.parseInt(a[1].trim()));
                counter++;
                if (0 == counter % 1000000) {
                    System.out.println(counter);
                }
            }
            System.out.println(map.size());
            Map<String, Integer> sortedMap = sortByValue(map);
            String addFileName = "/Users/panda/IdeaProjects/Avac-beta/src/main/resources/sortedWords";

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(addFileName))) {
                for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                    if (entry.getValue() > 30) {
                        bw.write(entry.getKey() + ": " + entry.getValue() + "\n");
                    }
                }
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

    }

    static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}