import java.io.*;
import java.util.*;

public class Main {

    private static String filePath = "src/Sample1.txt";
    private static final List<String> words = new ArrayList<>();
    private static final Map<String, List<Integer>> wordIndex = new HashMap<>();

    private static final Scanner scanner = new Scanner(System.in);

    private static void storeWords() {
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] splitWords = line.split("\\s+");
                for (String splitWord : splitWords) {
                    String word = splitWord.toLowerCase().replaceAll("[^a-zA-Z]", "");
                    if (!word.isEmpty()) {  // Check if the word is not empty
                        wordIndex.putIfAbsent(word, new ArrayList<>());
                        wordIndex.get(word).add(lineNumber);
                        words.add(word);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static void printMostUsed() {
        List<Map.Entry<String, List<Integer>>> sortedEntries = new ArrayList<>(wordIndex.entrySet());
        sortedEntries.sort((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()));

        for (Map.Entry<String, List<Integer>> entry : sortedEntries) {
            System.out.printf("%s: %d%n", entry.getKey(), entry.getValue().size());
        }
    }

    private static void searchWordFrequency(){
        System.out.println("Enter the word you are searching for: ");
        String targetWord = scanner.nextLine();
        targetWord = targetWord.toLowerCase().replaceAll("[^a-zA-Z]", ""); //Re-Normalize word

        if(wordIndex.containsKey(targetWord)){
            List<Integer> occurrences = wordIndex.get(targetWord);
            System.out.printf("The word '%s' appears %d times on lines: %s%n", targetWord, occurrences.size(), occurrences);
        } else {
            System.out.printf("The word '%s' is not found in the text.%n", targetWord);
        }
    }

    private static void printInstructions() {
        System.out.printf("%n0.Exit Program%n1.Print File%n2.List words in order of usage%n3.Search for a word%n");
    }

    public static void main(String[] args) {

        storeWords();

        System.out.printf("%nYou're currently reading %s", filePath);

        boolean loop = true;
        while (loop) {
            printInstructions();
            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    printFile();
                    break;
                case 2:
                    printMostUsed();
                    break;
                case 3:
                    searchWordFrequency();
                    break;
                default: {
                    System.out.println("Error, Invalid input.");
                    break;
                }
            }
        }
    }
}
