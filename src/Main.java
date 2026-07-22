import animals.Animal;
import animals.Sheep;
import animals.Cow;
import animals.Chicken;
import animals.Duck;
import farm.Farm;
import style.GameStyle;

import java.util.Scanner;

public class Main {

    // Farm-Objekt, damit alle Methoden in Main darauf zugreifen können
    static Farm farm;

    public static void main(String[] args) {

        // Scanner für Benutzereingaben
        Scanner scanner = new Scanner(System.in);

        // Neue Farm erstellen
        farm = new Farm();

        // Namen der Farm abfragen
        System.out.print("Wie soll deine Farm heißen? ");
        String farmName = scanner.nextLine().trim();

        // Falls kein Farmname eingegeben wurde
        if (farmName.isBlank()) {
            farmName = "Chaos-Farm";
        }

        // Farmnamen setzen
        farm.setFarmName(farmName);

        // Begrüßung ausgeben
        GameStyle.printFarmIntro();

        GameStyle.titleBox("Willkommen im " + farm.getFarmName());
        System.out.println(GameStyle.color("Die Verwaltung wurde gestartet. Die Tiere wurden nicht gefragt.", GameStyle.BRIGHT_PURPLE));
        System.out.println(GameStyle.color("Hinweis: Enten mit Führungsambitionen sind meldepflichtig.", GameStyle.CYAN));

        // Starttiere zur Farm hinzufügen
        farm.addAnimal(new Sheep("Shaun", 80, 3, 20));
        farm.addAnimal(new Sheep("Wolly", 55, 2, 15));

        farm.addAnimal(new Cow("Berta", 60, 5, 30));
        farm.addAnimal(new Cow("Luna", 40, 4, 20));

        farm.addAnimal(new Chicken("Henrietta", 30, 2, 5));
        farm.addAnimal(new Chicken("Gisela", 70, 1, 8));

        // Neue Tierart
        farm.addAnimal(new Duck("Donald", 45, 2, 12));

        // Steuert, ob das Spiel weiterläuft
        boolean gameOver = false;

        // Zählt die gespielten Runden
        int roundCounter = 0;

        // Hauptschleife des Spiels
        while (!gameOver) {

            // Schönen Kopfbereich anzeigen
            printGameHeader(roundCounter);

            // Menü schön formatiert anzeigen
            printMenu();

            // Auswahl des Users einlesen
            String choice = scanner.nextLine().trim();

            // Menü-Auswahl prüfen
            switch (choice) {

                // Spiel beenden
                case "0":
                    System.out.println("Die Farmverwaltung wurde geschlossen. Die Tiere bleiben skeptisch.");
                    gameOver = true;
                    break;

                // Einzelnes Tier füttern
                case "1":
                    System.out.print("Welches Tier möchtest du füttern? Gib den Namen ein: ");
                    String animalName = scanner.nextLine().trim();

                    Animal selectedAnimal = farm.getAnimalByName(animalName);

                    if (selectedAnimal == null) {
                        break;
                    }

                    boolean animalWasFed = isHungry(selectedAnimal.getHunger(), scanner);

                    if (animalWasFed) {
                        animalWasFed = farm.feedAnimal(selectedAnimal);
                    }

                    // Hunger aller anderen Tiere erhöhen.
                    // Wenn das Füttern nicht geklappt hat, bekommt auch dieses Tier Hunger.
                    farm.hunger(selectedAnimal, animalWasFed);

                    roundCounter = finishRound(roundCounter, scanner);

                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind weg. Die Farmverwaltung wurde vorsorglich aufgelöst.");
                        gameOver = true;
                    }
                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Neues Tier erstellen
                case "2":
                    createAnimal(scanner);

                    roundCounter = finishRound(roundCounter, scanner);

                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind weg. Die Farmverwaltung wurde vorsorglich aufgelöst.");
                        gameOver = true;
                    }
                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Ressourcen einsammeln
                case "3":
                    farm.collectResources();

                    roundCounter = finishRound(roundCounter, scanner);

                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind weg. Die Farmverwaltung wurde vorsorglich aufgelöst.");
                        gameOver = true;
                    }
                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Ressourcenlager anzeigen
                case "4":
                    farm.printStorage();
                    break;

                // Tier behandeln lassen
                case "5":
                    System.out.print("Welches Tier soll behandelt werden? ");
                    String animalToTreat = scanner.nextLine().trim();

                    farm.treatAnimalByName(animalToTreat);

                    roundCounter = finishRound(roundCounter, scanner);

                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind weg. Die Farmverwaltung wurde vorsorglich aufgelöst.");
                        gameOver = true;
                    }
                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Ressourcen verkaufen
                case "6":
                    farm.sellResources();

                    roundCounter = finishRound(roundCounter, scanner);

                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind weg. Die Farmverwaltung wurde vorsorglich aufgelöst.");
                        gameOver = true;
                    }
                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Ganze Tierart füttern
                case "7":
                    System.out.print("Welche Tierart möchtest du füttern? ");
                    String animalType = scanner.nextLine().toLowerCase().trim();

                    farm.feedAnimalsByType(animalType);

                    roundCounter = finishRound(roundCounter, scanner);

                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind weg. Die Farmverwaltung wurde vorsorglich aufgelöst.");
                        gameOver = true;
                    }
                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Tiere ansehen
                case "8":
                    GameStyle.clearScreen();
                    printGameHeader(roundCounter);
                    farm.printAnimals();
                    GameStyle.pressEnterToContinue(scanner);
                    break;

                default:
                    System.out.println("Ungültige Auswahl.");
                    break;
            }
        }

        scanner.close();
    }

    // Gibt den oberen Bereich des Spiels aus
    public static void printGameHeader(int roundCounter) {

        System.out.println();
        System.out.println(GameStyle.BRIGHT_GREEN + "╔════════════════════════════════════════════════════╗" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "║ "
                + GameStyle.BRIGHT_YELLOW + farm.getFarmName()
                + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "╠════════════════════════════════════════════════════╣" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "║ "
                + GameStyle.BRIGHT_CYAN + "Runde " + roundCounter
                + GameStyle.BRIGHT_GREEN + "  │  "
                + GameStyle.BRIGHT_YELLOW + "Geld: " + farm.getMoney() + " Münzen"
                + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "╚════════════════════════════════════════════════════╝" + GameStyle.RESET);
    }

    // Gibt das Hauptmenü schön formatiert aus
    public static void printMenu() {

        System.out.println();
        System.out.println(GameStyle.BRIGHT_BLUE + "╭─ AKTION WÄHLEN ──────────────────────────────" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "1" + GameStyle.RESET + "  Einzelnes Tier füttern");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "2" + GameStyle.RESET + "  Neues Tier erstellen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "3" + GameStyle.RESET + "  Ressourcen einsammeln");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "4" + GameStyle.RESET + "  Ressourcenlager ansehen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "5" + GameStyle.RESET + "  Tierärztin rufen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "6" + GameStyle.RESET + "  Ressourcen verkaufen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "7" + GameStyle.RESET + "  Ganze Tierart füttern");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_CYAN + "8" + GameStyle.RESET + "  Tiere ansehen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_RED + "0" + GameStyle.RESET + "  Spiel beenden");
        System.out.println(GameStyle.BRIGHT_BLUE + "╰──────────────────────────────────────────────" + GameStyle.RESET);
        System.out.print(GameStyle.BRIGHT_YELLOW + "➜ Auswahl: " + GameStyle.RESET);
    }

    // Erstellt ein neues Tier und fügt es der Farm hinzu
    public static void createAnimal(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("              NEUES TIER ERSTELLEN");
        System.out.println("==================================================");

        // Tierart abfragen
        System.out.print("Welche Tierart möchtest du erstellen? ");
        String animalType = scanner.nextLine().toLowerCase().trim();

        // Prüfen, ob eine Tierart eingegeben wurde
        if (animalType.isBlank()) {
            System.out.println("Ohne Tierart kann leider kein mysteriöses Farmwesen erstellt werden.");
            return;
        }

        // Namen abfragen
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        // Prüfen, ob ein Name eingegeben wurde
        if (name.isBlank()) {
            System.out.println("Ein Tier ohne Namen? Sehr verdächtig. Tier wurde nicht erstellt.");
            return;
        }

        // Hunger und Alter mit Standardwerten einlesen
        int hunger = readIntWithDefault(scanner, "Hunger", 50);
        int age = readIntWithDefault(scanner, "Alter", 1);

        // Neues Schaf erstellen
        if (animalType.equals("schaf")) {

            // Wolle mit Standardwert 0 einlesen
            int wool = readIntWithDefault(scanner, "Wolle", 0);

            farm.addAnimal(new Sheep(name, hunger, age, wool));
            GameStyle.success(name + " wurde als Schaf erstellt.");

            // Neue Kuh erstellen
        } else if (animalType.equals("kuh")) {

            // Milch mit Standardwert 0 einlesen
            int milk = readIntWithDefault(scanner, "Milch", 0);

            farm.addAnimal(new Cow(name, hunger, age, milk));
            GameStyle.success(name + " wurde als Kuh erstellt.");

            // Neues Huhn erstellen
        } else if (animalType.equals("huhn")) {

            // Eier mit Standardwert 0 einlesen
            int eggs = readIntWithDefault(scanner, "Eier", 0);

            farm.addAnimal(new Chicken(name, hunger, age, eggs));
            GameStyle.success(name + " wurde als Huhn erstellt.");

            // Neue Ente erstellen
        } else if (animalType.equals("ente")) {

            // Federn mit Standardwert 0 einlesen
            int feathers = readIntWithDefault(scanner, "Federn", 0);

            farm.addAnimal(new Duck(name, hunger, age, feathers));
            GameStyle.success(name + " wurde als Ente erstellt.");

            // Wenn keine passende Tierart eingegeben wurde
        } else {
            System.out.println("Diese Tierart gibt es nicht.");
        }
    }

    // Liest eine Zahl ein.
    // Wenn der User nichts eingibt, wird ein Standardwert verwendet.
    public static int readIntWithDefault(Scanner scanner, String text, int defaultValue) {

        System.out.print(text + " [" + defaultValue + "]: ");

        String input = scanner.nextLine().trim();

        if (input.isBlank()) {
            return defaultValue;
        }

        return Integer.parseInt(input);
    }

    // Entscheidet anhand des Hungerwertes, ob gefüttert werden soll
    public static boolean isHungry(int animalHunger, Scanner scanner) {

        if (animalHunger >= 75) {
            System.out.println("Das Tier wird gefüttert.");
            return true;

        } else if (animalHunger >= 50) {
            System.out.print("Das Tier ist mäßig hungrig. Möchtest du es füttern? ");
            String answer = scanner.nextLine().toLowerCase().trim();

            if (answer.equals("ja")) {
                System.out.println("Das Tier wird gefüttert.");
                return true;
            } else {
                System.out.println("Das Tier wird nicht gefüttert.");
                return false;
            }

        } else {
            System.out.println("Das Tier ist nicht hungrig genug.");
            return false;
        }
    }

    // Erhöht den Rundenzähler und prüft, ob alle Tiere älter werden müssen
    public static int increaseRound(int roundCounter) {

        roundCounter = roundCounter + 1;

        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("Runde " + roundCounter + " wurde überlebt.");

        if (roundCounter % 3 == 0) {
            farm.increaseAge();
            System.out.println("Alle Tiere sind älter geworden. Niemand wurde gefragt.");
        }

        System.out.println("--------------------------------------------------");

        return roundCounter;
    }

    // Schließt eine Runde ab
    public static int finishRound(int roundCounter, Scanner scanner) {

        roundCounter = increaseRound(roundCounter);

        // Krankheitsdauer pro Runde erhöhen
        farm.increaseSickRounds();

        // Zufällige Ereignisse prüfen
        farm.randomDisease();
        farm.spreadDisease();
        farm.randomFarmEvent(scanner);
        farm.randomAnimalComment();

        // Tote Tiere entfernen
        farm.removeDeadAnimals();

        return roundCounter;
    }
}