import java.util.*;

/**
 * Simple CLI Ticket Management System (in-memory)
 * Run: javac TicketSystem.java && java TicketSystem
 */
public class TicketSystem {

    // ---- Model ----
    enum Status {
        OPEN, IN_PROGRESS, RESOLVED, CLOSED
    }

    static class Ticket {
        int id;
        String title;
        String description;
        String createdBy;
        String assignedTo; // optional
        Status status;
        long createdAtMillis;

        Ticket(int id, String title, String description, String createdBy) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.createdBy = createdBy;
            this.assignedTo = "UNASSIGNED";
            this.status = Status.OPEN;
            this.createdAtMillis = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "Ticket{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", status=" + status +
                    ", createdBy='" + createdBy + '\'' +
                    ", assignedTo='" + assignedTo + '\'' +
                    '}';
        }
    }

    // ---- Storage ----
    private static final Map<Integer, Ticket> tickets = new LinkedHashMap<>();
    private static int nextId = 1001;

    // ---- Main ----
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = readInt(sc, "Enter choice: ");

            switch (choice) {
                case 1 -> createTicket(sc);
                case 2 -> listTickets();
                case 3 -> updateStatus(sc);
                case 4 -> assignTicket(sc);
                case 5 -> searchById(sc);
                case 6 -> {
                    System.out.println("Bye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // ---- Features ----
    private static void createTicket(Scanner sc) {
        System.out.println("\n--- Create Ticket ---");
        String title = readLine(sc, "Title: ");
        String desc = readLine(sc, "Description: ");
        String createdBy = readLine(sc, "Created By: ");

        int id = nextId++;
        Ticket t = new Ticket(id, title, desc, createdBy);
        tickets.put(id, t);

        System.out.println("✅ Ticket created: " + t);
    }

    private static void listTickets() {
        System.out.println("\n--- All Tickets ---");
        if (tickets.isEmpty()) {
            System.out.println("No tickets yet.");
            return;
        }
        for (Ticket t : tickets.values()) {
            System.out.println(t);
        }
    }

    private static void updateStatus(Scanner sc) {
        System.out.println("\n--- Update Status ---");
        int id = readInt(sc, "Ticket ID: ");
        Ticket t = tickets.get(id);

        if (t == null) {
            System.out.println("❌ Ticket not found.");
            return;
        }

        System.out.println("Current status: " + t.status);
        System.out.println("Choose new status:");
        System.out.println("1) OPEN  2) IN_PROGRESS  3) RESOLVED  4) CLOSED");
        int s = readInt(sc, "Enter: ");

        Status newStatus = switch (s) {
            case 1 -> Status.OPEN;
            case 2 -> Status.IN_PROGRESS;
            case 3 -> Status.RESOLVED;
            case 4 -> Status.CLOSED;
            default -> null;
        };

        if (newStatus == null) {
            System.out.println("❌ Invalid status choice.");
            return;
        }

        t.status = newStatus;
        System.out.println("✅ Updated: " + t);
    }

    private static void assignTicket(Scanner sc) {
        System.out.println("\n--- Assign Ticket ---");
        int id = readInt(sc, "Ticket ID: ");
        Ticket t = tickets.get(id);

        if (t == null) {
            System.out.println("❌ Ticket not found.");
            return;
        }

        String person = readLine(sc, "Assign to (name): ");
        t.assignedTo = person.isBlank() ? "UNASSIGNED" : person.trim();
        System.out.println("✅ Assigned: " + t);
    }

    private static void searchById(Scanner sc) {
        System.out.println("\n--- Search Ticket ---");
        int id = readInt(sc, "Ticket ID: ");
        Ticket t = tickets.get(id);

        if (t == null) {
            System.out.println("❌ Ticket not found.");
        } else {
            System.out.println("✅ Found: " + t);
            System.out.println("Title: " + t.title);
            System.out.println("Description: " + t.description);
            System.out.println("Created By: " + t.createdBy);
            System.out.println("Assigned To: " + t.assignedTo);
            System.out.println("Status: " + t.status);
        }
    }

    // ---- Helpers (Input safe) ----
    private static void printMenu() {
        System.out.println("\n==============================");
        System.out.println("     Ticket Management CLI");
        System.out.println("==============================");
        System.out.println("1) Create Ticket");
        System.out.println("2) List Tickets");
        System.out.println("3) Update Ticket Status");
        System.out.println("4) Assign Ticket");
        System.out.println("5) Search Ticket By ID");
        System.out.println("6) Exit");
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number.");
            }
        }
    }

    private static String readLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
