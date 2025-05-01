import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SmartChatBot {

    // Method to get current time
    static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return now.format(formatter);
    }

    // Method to get current day
    static String getDayOfWeek() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");
        return now.format(formatter);
    }

    // Method to get current date
    static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return now.format(formatter);
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Map<String, String> knowledge = new HashMap<>();

        // Load saved knowledge
        File file = new File("knowledge.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                knowledge.put(parts[0].toLowerCase(), parts[1]);
            }
        }
        reader.close();

        System.out.println("🤖 SmartChatBot is ready! Type 'exit' to quit.");

        while (true) {
            System.out.print("You: ");
            String input = sc.nextLine().toLowerCase().trim();

            if (input.equals("exit")) {
                System.out.println("Bot: Goodbye!");
                break;
            }

            // Special dynamic responses
            if (input.equals("what is the time")) {
                System.out.println("Bot: The current time is " + getCurrentTime() + ".");
            } else if (input.equals("what day is it")) {
                System.out.println("Bot: Today is " + getDayOfWeek() + ".");
            } else if (input.equals("what date is today") || input.equals("what is the date today")) {
                System.out.println("Bot: Today's date is " + getCurrentDate() + ".");
            }

            // Respond from knowledge
            else if (knowledge.containsKey(input)) {
                System.out.println("Bot: " + knowledge.get(input));
            }

            // Learn new answer
            else {
                System.out.println("Bot: I don't know that yet. Can you teach me?");
                System.out.print("You (answer): ");
                String newAnswer = sc.nextLine();
                knowledge.put(input, newAnswer);

                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(input + "=" + newAnswer);
                writer.newLine();
                writer.close();

                System.out.println("Bot: Thanks! I've learned something new.");
            }
        }

        sc.close();
    }
}

      