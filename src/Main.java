
public class Main extends Functions {

    private static void printInstructions() {
        System.out.printf("You are reading %s",filePath);
        System.out.printf("%n-1. Return to file selection%n0. Exit Program%n1. Print File%n2. List words in order of usage%n3. Search for a word%n4. Print total word count%n" +
                "5. Search by line%n6. Search for phrase%n");
    }

    public static void main(String[] args) {

        selectFile(); // Select a file first

        boolean loop = true;
        while (loop) {
            printInstructions();
            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case -1:
                    selectFile(); // Return to file selection
                    break;
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
                case 4:
                    printTotalWordCount();
                    break;
                case 5:
                    searchByLine();
                    break;
                case 6:
                    searchForPhrase();
                    break;
                case 7:
                    printFileStatistics();
                    break;
                default:
                    System.out.println("Error, Invalid input.");
                    break;
            }
        }
    }
}
