import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static final String DATA_FILE = "books.json";

    static void printBanner() {
        System.out.println("=== Library Tracker ===");
    }

    // Reads books.json and returns a list of {title, author}
    static List<String[]> loadBooks() {
        List<String[]> books = new ArrayList<>();
        File f = new File(DATA_FILE);
        if (!f.exists()) {
            return books;
        }
        try {
            String text = new String(Files.readAllBytes(f.toPath()), "UTF-8");
            Matcher m = Pattern.compile("\"title\":\\s*\"(.*?)\",\\s*\"author\":\\s*\"(.*?)\"").matcher(text);
            while (m.find()) {
                books.add(new String[]{m.group(1), m.group(2)});
            }
        } catch (IOException e) {
            System.out.println("Could not read " + DATA_FILE);
        }
        return books;
    }

    // Writes the list of books to books.json
    static void saveBooks(List<String[]> books) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < books.size(); i++) {
            sb.append("  {\"title\": \"").append(books.get(i)[0])
              .append("\", \"author\": \"").append(books.get(i)[1]).append("\"}");
            if (i < books.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]\n");
        try {
            Files.write(Paths.get(DATA_FILE), sb.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            System.out.println("Could not write " + DATA_FILE);
        }
    }

    // ---------- Student A: add and list ----------

    static void addBook(String title, String author) {
        List<String[]> books = loadBooks();
        books.add(new String[]{title, author});
        saveBooks(books);
        System.out.println("Book added.");
    }

    static void listBooks() {
        // TODO (Student A)
    }

    // ---------- Student B: search and remove ----------

    static void searchBooks(String keyword) {
        // TODO (Student B)
    }

    static void removeBook(String title) {
        // TODO (Student B)
    }

    public static void main(String[] args) {
        printBanner();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1) Add  2) List  3) Search  4) Remove  5) Quit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            if (choice.equals("1")) {
                System.out.print("Title: ");
                String title = sc.nextLine();
                System.out.print("Author: ");
                String author = sc.nextLine();
                addBook(title, author);
            } else if (choice.equals("2")) {
                listBooks();
            } else if (choice.equals("3")) {
                System.out.print("Keyword: ");
                searchBooks(sc.nextLine());
            } else if (choice.equals("4")) {
                System.out.print("Title: ");
                removeBook(sc.nextLine());
            } else if (choice.equals("5")) {
                break;
            }
        }
        sc.close();
    }
}
