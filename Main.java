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
        System.out.println("=== Library Tracker - Version A ===");
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
    if (title.trim().isEmpty() || author.trim().isEmpty()) {
        System.out.println("Title and author cannot be empty.");
        return;
    }
    List<String[]> books = loadBooks();
    books.add(new String[]{title.trim(), author.trim()});
    saveBooks(books);
    System.out.println("Book added.");
}

    static void listBooks() {
        List<String[]> books = loadBooks();
        if (books.isEmpty()) {
            System.out.println("The library is empty.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i)[0] + " by " + books.get(i)[1]);
        }
    }

    // ---------- Student B: search and remove ----------

    
static void searchBooks(String keyword) {
    String query = keyword.trim().toLowerCase();

    if (query.isEmpty()) {
        System.out.println("Please enter a search keyword.");
        return;
    }

    List<String[]> books = loadBooks();
    boolean found = false;

    for (String[] book : books) {
        if (book[0].toLowerCase().contains(query)
                || book[1].toLowerCase().contains(query)) {
            System.out.println(book[0] + " by " + book[1]);
            found = true;
        }
    }

    if (!found) {
       System.out.println("there is not a book with this name");
    }
}


    
static void removeBook(String title) {
    String target = title.trim();

    if (target.isEmpty()) {
        System.out.println("Please enter a book title.");
        return;
    }

    List<String[]> books = loadBooks();

    for (int i = 0; i < books.size(); i++) {
        if (books.get(i)[0].equalsIgnoreCase(target)) {
            String removedTitle = books.get(i)[0];
            books.remove(i);
            saveBooks(books);
            System.out.println("Removed: " + removedTitle);
            return;
        }
    }

System.out.println("Book not found.");
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
