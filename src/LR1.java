import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LR1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File myFile = new File("C:\\Users\\Ksenia\\IdeaProjects\\untitled\\file.txt");

        if (myFile.exists() && myFile.canRead() && myFile.canWrite()) {

            System.out.print("Введите длину, числом: ");
            int length = sc.nextInt();
            sc.nextLine();

            List<String> foundWords = new ArrayList<>(); // Список для найденных слов
            List<String> remainingLines = new ArrayList<>(); // Список оставшихся строк

            try (Scanner fileScanner = new Scanner(myFile)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    StringBuilder remainingLine = new StringBuilder();

                    // Разбиваем строку на слова регулярным выражением
                    String[] words = line.split("(?<=\\P{Punct})(?=\\s)|(?<=\\s)(?=\\P{Punct})|(?<=\\s|^)\\s+|(?<=\\S)\\s+");

                    for (String word : words) {
                        String cleanedWord = word.replaceAll("^\\p{Punct}+", "").replaceAll("\\p{Punct}+$", "");

                        if (cleanedWord.length() == length && startsWithConsonant(cleanedWord)) {
                            foundWords.add(cleanedWord);
                        } else {
                            remainingLine.append(word);
                        }
                    }

                    remainingLines.add(remainingLine.toString().trim());
                }

                System.out.println("Найденные слова:");
                for (String word : foundWords) {
                    System.out.println(word);
                }

                try (PrintWriter writer = new PrintWriter(myFile)) {
                    for (String remainingLine : remainingLines) {
                        writer.println(remainingLine);
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
            }
        }
    }

    private static boolean startsWithConsonant(String word) {
        char firstChar = Character.toLowerCase(word.charAt(0));
        return "бвгджзйлмнопрстфхцчшщ".indexOf(firstChar) != -1;
    }
}
