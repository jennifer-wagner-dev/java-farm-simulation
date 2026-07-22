package style;

// Diese Klasse sammelt Farben, Rahmen und ASCII-Grafiken.
// Dadurch bleibt Main und Farm übersichtlicher.
public class GameStyle {

    // ANSI-Farben für die Konsole
    public static final String RESET = "\u001B[0m";

    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    // Hellere Farben
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Einheitliche Breite für Event-Boxen
    public static final int BOX_WIDTH = 60;

    // Macht einen Text farbig
    public static String color(String text, String color) {
        return color + text + RESET;
    }

    // Gibt eine Leerzeile aus
    public static void emptyLine() {
        System.out.println();
    }

    // Gibt eine Trennlinie aus
    public static void line() {
        System.out.println(BRIGHT_GREEN + "════════════════════════════════════════════════════════════" + RESET);
    }

    // Gibt eine kleinere Trennlinie aus
    public static void smallLine() {
        System.out.println(GREEN + "------------------------------------------------------------" + RESET);
    }

    // Gibt einen Titel in einer Box aus
    public static void titleBox(String title) {
        System.out.println();
        System.out.println(BRIGHT_CYAN + "╔════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(BRIGHT_CYAN + "║  " + BRIGHT_WHITE + title + RESET);
        System.out.println(BRIGHT_CYAN + "╚════════════════════════════════════════════════════════════╝" + RESET);
    }

    // Gibt eine Warnung aus
    public static void warning(String text) {
        System.out.println(BRIGHT_RED + "⚠ " + text + RESET);
    }

    // Gibt eine Erfolgsmeldung aus
    public static void success(String text) {
        System.out.println(BRIGHT_GREEN + "✓ " + text + RESET);
    }

    // Gibt eine Info aus
    public static void info(String text) {
        System.out.println(BRIGHT_CYAN + "➜ " + text + RESET);
    }

    // Startbildschirm als ASCII-Art
    public static void printFarmIntro() {
        System.out.println(BRIGHT_GREEN);
        System.out.println("      _.-^-._    .--.");
        System.out.println("   .-'   _   '-. |__|");
        System.out.println("  /     |_|     \\|  |");
        System.out.println(" /               \\  |");
        System.out.println("/|     _____     |\\ |");
        System.out.println(" |    |==|==|    |  |");
        System.out.println(" |    |--|--|    |  |");
        System.out.println(" |    |==|==|    |  |");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(RESET);

        System.out.println(BRIGHT_YELLOW + "        TIERDRAMA SIMULATOR" + RESET);
        System.out.println(GREEN + "   ein Managementspiel über Heu, Hoffnung und Kontrollverlust" + RESET);
        System.out.println();
    }

    // Kleine ASCII-Grafik für das Farmgericht
    public static void printCourtArt() {
        System.out.println(BRIGHT_PURPLE);
        System.out.println("        ___________________________");
        System.out.println("       /                           \\");
        System.out.println("      |        FARMGERICHT          |");
        System.out.println("      |_____________________________|");
        System.out.println("          \\  ^__^");
        System.out.println("           \\ (oo)\\_______");
        System.out.println("             (__)\\       )\\/\\");
        System.out.println("                 ||----w |");
        System.out.println("                 ||     ||");
        System.out.println(RESET);
    }

    // Kleine ASCII-Grafik für Ressourcen
    public static void printStorageArt() {
        System.out.println(BRIGHT_YELLOW);
        System.out.println("        __________________");
        System.out.println("       |  RESSOURCENLAGER |");
        System.out.println("       |__________________|");
        System.out.println("       |  []  []  []  []  |");
        System.out.println("       |  []  []  []  []  |");
        System.out.println("       |__________________|");
        System.out.println(RESET);
    }

    // Gibt farbigen Hunger-Status zurück
    public static String getHungerBar(int hunger) {
        String bar = "";

        int filledParts = hunger / 10;

        for (int i = 0; i < 10; i++) {
            if (i < filledParts) {
                bar = bar + "█";
            } else {
                bar = bar + "░";
            }
        }

        if (hunger >= 80) {
            return BRIGHT_RED + bar + " " + hunger + RESET;
        } else if (hunger >= 50) {
            return BRIGHT_YELLOW + bar + " " + hunger + RESET;
        } else {
            return BRIGHT_GREEN + bar + " " + hunger + RESET;
        }
    }

    // Versucht die Konsole zu leeren.
    // In IntelliJ klappt das manchmal nicht perfekt, aber im Terminal meist schon.
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Wartet, bis der User Enter drückt
    public static void pressEnterToContinue(java.util.Scanner scanner) {
        System.out.println();
        System.out.print(BRIGHT_CYAN + "Drücke Enter, um fortzufahren..." + RESET);
        scanner.nextLine();
    }

    // Kleiner Screen-Titel
    public static void screenTitle(String title) {
        System.out.println();
        System.out.println(BRIGHT_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        System.out.println(BRIGHT_YELLOW + "  " + title + RESET);
        System.out.println(BRIGHT_GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }

    // Farbiges Label
    public static String label(String text) {
        return BRIGHT_CYAN + text + RESET;
    }

    // Geld farbig
    public static String money(int money) {
        if (money <= 10) {
            return BRIGHT_RED + money + " Münzen" + RESET;
        } else if (money <= 30) {
            return BRIGHT_YELLOW + money + " Münzen" + RESET;
        } else {
            return BRIGHT_GREEN + money + " Münzen" + RESET;
        }
    }

    // Box für Aktionen / Ereignisse
    public static void logBox(String title) {
        String start = "╭─ " + title + " ";

        int remainingLength = BOX_WIDTH - start.length();

        if (remainingLength < 1) {
            remainingLength = 1;
        }

        System.out.println();
        System.out.println(BRIGHT_PURPLE + start + "─".repeat(remainingLength) + RESET);
    }

    // Ende einer Log-Box
    public static void endBox() {
        System.out.println(BRIGHT_PURPLE + "╰" + "─".repeat(BOX_WIDTH - 1) + RESET);
    }
}