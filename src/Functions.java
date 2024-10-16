import java.io.*;
import java.util.*;

public class Functions {
    public static String filePath;
    private static final List<String> words = new ArrayList<>();
    private static final Map<String, List<Integer>> wordIndex = new HashMap<>();

    public static final Scanner scanner = new Scanner(System.in);

    private static String normalizeWord(String word){
        return word.toLowerCase().replaceAll("[^a-zA-Z]", "");
    }

    public static void storeWords() {
        words.clear();
        wordIndex.clear(); // Clear previous file's data

        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] splitWords = line.split("\\s+");
                for (String splitWord : splitWords) {
                    String word = normalizeWord(splitWord);
                    if (!word.isEmpty()) {
                        wordIndex.putIfAbsent(word, new ArrayList<>());
                        wordIndex.get(word).add(lineNumber);
                        words.add(word);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void selectFile() {
        System.out.println("Choose a file to load:");
        System.out.println("1. Sample1.txt");
        System.out.println("2. Sample2.txt");
        System.out.println("3. Sample3.txt");
        System.out.println("4. Sample4.txt");

        int fileChoice = Integer.parseInt(scanner.nextLine());
        switch (fileChoice) {
            case 1:
                filePath = "src/Sample1.txt";
                break;
            case 2:
                filePath = "src/Sample2.txt";
                break;
            case 3:
                filePath = "src/Sample3.txt";
                break;
            case 4:
                filePath = "src/Sample4.txt";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Sample1.txt.");
                filePath = "src/Sample1.txt";
        }

        storeWords(); // Load the file data
    }

    public static void printFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void printMostUsed() {
        List<Map.Entry<String, List<Integer>>> sortedEntries = new ArrayList<>(wordIndex.entrySet());
        sortedEntries.sort((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()));

        for (Map.Entry<String, List<Integer>> entry : sortedEntries) {
            System.out.printf("%s: %d%n", entry.getKey(), entry.getValue().size());
        }
    }

    public static void searchWordFrequency() {
        System.out.println("Enter the word you are searching for: ");
        String targetWord = scanner.nextLine();
        targetWord = normalizeWord(targetWord);

        if (wordIndex.containsKey(targetWord)) {
            List<Integer> occurrences = wordIndex.get(targetWord);
            System.out.printf("The word '%s' appears %d times on lines: %s%n", targetWord, occurrences.size(), occurrences);
        } else {
            System.out.printf("The word '%s' is not found in the text.%n", targetWord);
        }
    }

    public static void printTotalWordCount() {
        System.out.printf("Total number of words: %d%n", words.size());
        System.out.printf("Total number of unique words: %d%n", wordIndex.size());
    }

    public static void searchByLine() {
        System.out.println("Enter the line you are looking for: ");
        int targetLine = Integer.parseInt(scanner.nextLine());
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;
            while ((line = br.readLine()) != null) {
                currentLine++;
                if (currentLine == targetLine) {
                    System.out.printf("Line %d: %s%n", targetLine, line);
                    return;
                }
            }
            System.out.println("Line not found.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void searchForPhrase(){
        System.out.println("Enter the phrase you are looking for: ");
        String targetPhrase = scanner.nextLine();
        targetPhrase = targetPhrase.toLowerCase();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            int lineNumber = 0;
            boolean found = false;

            while ((line = br.readLine()) != null){
                lineNumber++;
                if (line.toLowerCase().contains(targetPhrase)) {
                    System.out.printf("Line %d: %s%n", lineNumber, line);
                    found = true;
                }
            }
            if (!found) {
                System.out.printf("The phrase '%s' was not found in the text.%n", targetPhrase);
            }
        } catch (IOException e){
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void printFileStatistics() {
        int lineCount = 0;
        int characterCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                characterCount += line.length();
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        System.out.printf("Total lines: %d%n", lineCount);
        System.out.printf("Total characters: %d%n", characterCount);
        System.out.printf("Average words per line: %.2f%n", (double) words.size() / lineCount);
    }

    public static void exportFileAnalysis(){
        System.out.println("Enter the name of the file you want to analyze without the extensions: ");
        String fileName =  scanner.nextLine();
        String exportPath = "src/exports/" + fileName + ".txt";

        File exportDir = new File("src/exports");
        if(!exportDir.exists()){
            exportDir.mkdirs();

            try(FileWriter writer = new FileWriter(exportPath)){
                writer.write("File: " + filePath + "\n");
                writer.write("Total words: " + words.size() + "\n");
                writer.write("Unique words: " + wordIndex.size() + "\n");
                writer.write("\nWord Frequency:\n");

                List<Map.Entry<String, List<Integer>>> sortedEntries = new ArrayList<>(wordIndex.entrySet());
                sortedEntries.sort((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()));

                for (Map.Entry<String, List<Integer>> entry : sortedEntries) {
                    writer.write(String.format("%s: %d times, on lines %s%n", entry.getKey(), entry.getValue().size(), entry.getValue()));
                }

                System.out.println("Analysis exported to: " + exportPath);
            } catch (IOException e){
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
    }
}
