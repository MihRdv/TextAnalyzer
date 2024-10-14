import java.io.*;
import java.util.*;

public class Main {

    private static List<String> words = new ArrayList<>();

    private static void storeWords(){
        Map<String, List<Integer>> wordIndex = new HashMap<>();
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/Sample1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] splitWords = line.split("\\s+");
                for (String splitWord : splitWords) {
                    String word = splitWord.toLowerCase().replaceAll("[^a-zA-Z]", "");
                    wordIndex.putIfAbsent(word, new ArrayList<>());
                    wordIndex.get(word).add(lineNumber);
                    words.add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(words);
        System.out.println(wordIndex);
    }

    public static void main(String[] args) {
        storeWords();


    }
}

