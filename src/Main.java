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

        if (farmName.isBlank()) {
            farmName = "ShitShow Stables";
        }

        // Farmnamen setzen
        farm.setFarmName(farmName);

        // Begrüßung ausgeben
        GameStyle.printFarmIntro();

        GameStyle.titleBox("Willkommen im " + farm.getFarmName());
        System.out.println(GameStyle.color("Die Verwaltung wurde gestartet. Die Tiere wurden nicht gefragt.", GameStyle.BRIGHT_PURPLE));
        System.out.println(GameStyle.color("Hinweis: Enten mit Führungsambitionen sind meldepflichtig.", GameStyle.CYAN));

        // Starttiere zur Farm hinzufügen
        farm.addAnimal(new Sheep("Shaun", 80, 3, 3));
        farm.addAnimal(new Sheep("Wolly", 55, 2, 2));

        farm.addAnimal(new Cow("Berta", 60, 5, 4));
        farm.addAnimal(new Cow("Luna", 40, 4, 3));

        farm.addAnimal(new Chicken("Henrietta", 30, 2, 2));
        farm.addAnimal(new Chicken("Gisela", 70, 1, 3));

        farm.addAnimal(new Duck("Donald", 45, 2, 2));


        // Steuert, ob das Spiel weiterläuft
        boolean gameOver = false;

        // Zählt die gespielten Runden
        int roundCounter = 0;

        // Hauptschleife des Spiels
        while (!gameOver) {

            printGameHeader(roundCounter);
            printMenu();

            String choice = readMenuChoice(scanner);

            // Menü-Auswahl prüfen
            switch (choice) {

                // Spiel beenden
                case "0":
                    System.out.println("Die Farmverwaltung wurde geschlossen. Die Tiere bleiben skeptisch.");
                    gameOver = true;
                    break;

                // Einzelnes Tier füttern
                case "1":
                    System.out.print("Für welches Tier soll eine Versorgungsmaßnahme eingeleitet werden? ");
                    String animalName = scanner.nextLine().trim();

                    Animal selectedAnimal = farm.getAnimalByName(animalName);

                    if (selectedAnimal == null) {
                        GameStyle.pressEnterToContinue(scanner);
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

                    gameOver = checkGameOver();

                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Neues Tier erstellen
                case "2":
                    createAnimal(scanner);

                    farm.hungerAllAnimals();

                    gameOver = checkGameOver();

                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Ressourcen einsammeln
                case "3":
                    farm.collectResources();

                    farm.hungerAllAnimals();

                    roundCounter = finishRound(roundCounter, scanner);

                    gameOver = checkGameOver();

                    GameStyle.pressEnterToContinue(scanner);
                    break;


                //Ressourcenlager anzeigen
                case "4":
                    GameStyle.clearScreen();
                    printGameHeader(roundCounter);

                    farm.printStorage();

                    farm.hungerAllAnimals();

                    roundCounter = finishRound(roundCounter, scanner);

                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Tier behandeln lassen
                case "5":
                    System.out.print("Für welches Tier soll die Tierärztin gerufen werden? ");
                    String animalToTreat = scanner.nextLine().trim();

                    farm.treatAnimalByName(animalToTreat);

                    farm.hungerAllAnimals();

                    roundCounter = finishRound(roundCounter, scanner);

                    gameOver = checkGameOver();

                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Ressourcen verkaufen
                case "6":
                    farm.sellResources();

                    farm.hungerAllAnimals();

                    roundCounter = finishRound(roundCounter, scanner);

                    gameOver = checkGameOver();

                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Ganze Tierart füttern
                case "7":
                    System.out.print("Für welche Tierart soll Gruppenversorgung beantragt werden? ");
                    String animalType = scanner.nextLine().toLowerCase().trim();

                    farm.feedAnimalsByType(animalType);

                    farm.hungerExceptType(animalType);

                    roundCounter = finishRound(roundCounter, scanner);
                    gameOver = checkGameOver();

                    GameStyle.pressEnterToContinue(scanner);
                    break;

                // Tiere ansehen
                    case "8":
                    farm.printAnimals();

                        gameOver = checkGameOver();

                    GameStyle.pressEnterToContinue(scanner);
                    break;

                default:
                    GameStyle.warning("Dieses Aktenzeichen existiert nicht.");
                    System.out.println("Die Farmverwaltung prüft den Vorgang.");
                    System.out.println("Ergebnis: Nein.");
                    GameStyle.pressEnterToContinue(scanner);
                    break;
            }
        }

        scanner.close();
    }

    public static boolean checkGameOver() {

        if (farm.hasNoAnimals()) {
            GameStyle.warning("Das Tierregister ist leer.");
            System.out.println("Alle Tiere sind weg. Die Farmverwaltung wurde vorsorglich aufgelöst.");
            System.out.println("Der Abschlussbericht besteht nur aus Fragezeichen.");
            return true;
        }

        return false;
    }

    public static void printGameHeader(int roundCounter) {

        System.out.println();
        System.out.println(GameStyle.BRIGHT_GREEN + "╔════════════════════════════════════════════════════╗" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "║ "
                + GameStyle.BRIGHT_YELLOW + "TIERDRAMA SIMULATOR"
                + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "╠════════════════════════════════════════════════════╣" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "║ "
                + GameStyle.BRIGHT_CYAN + farm.getFarmName()
                + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "║ "
                + GameStyle.BRIGHT_PURPLE + "Runde: "
                + GameStyle.BRIGHT_WHITE + roundCounter
                + GameStyle.BRIGHT_GREEN + "  │  "
                + GameStyle.BRIGHT_PURPLE + "Budget: "
                + GameStyle.BRIGHT_WHITE + farm.getMoney() + " Münzen"
                + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "╚════════════════════════════════════════════════════╝" + GameStyle.RESET);
    }

    public static void printMenu() {

        System.out.println();
        System.out.println(GameStyle.BRIGHT_BLUE + "╭─ AMTLICHES AKTIONSMENÜ ─────────────────────" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "1" + GameStyle.RESET + "  Versorgungsmaßnahme für ein Tier einleiten");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "2" + GameStyle.RESET + "  Neue Tierakte anlegen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "3" + GameStyle.RESET + "  Ressourcensammlung durchführen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "4" + GameStyle.RESET + "  Ressourcenlager kontrollieren");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "5" + GameStyle.RESET + "  Tierärztin mit Formular 12-T rufen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "6" + GameStyle.RESET + "  Verkaufsverfahren einleiten");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_GREEN + "7" + GameStyle.RESET + "  Gruppenversorgungsmaßnahme beantragen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_CYAN + "8" + GameStyle.RESET + "  Tierregister einsehen");
        System.out.println(GameStyle.BRIGHT_BLUE + "│ " + GameStyle.BRIGHT_RED + "0" + GameStyle.RESET + "  Farmverwaltung schließen");
        System.out.println(GameStyle.BRIGHT_BLUE + "╰──────────────────────────────────────────────" + GameStyle.RESET);
        System.out.print(GameStyle.BRIGHT_YELLOW + "➜ Aktenzeichen deiner Entscheidung: " + GameStyle.RESET);
    }

    public static void createAnimal(Scanner scanner) {

        GameStyle.logBox("TIERAKTE ANLEGEN");

        System.out.println("Die Farmverwaltung eröffnet eine neue Tierakte.");
        System.out.println("Bitte mache ehrliche Angaben.");
        System.out.println("Das Tier wird später sowieso auffällig.");
        System.out.println();

        System.out.print(GameStyle.label("Tierart ") + "(schaf / kuh / huhn / ente): ");
        String animalType = scanner.nextLine().toLowerCase().trim();

        if (animalType.isBlank()) {
            GameStyle.warning("Ohne Tierart kann keine Akte eröffnet werden.");
            System.out.println("Die Verwaltung stempelt traurig ins Leere.");
            GameStyle.endBox();
            return;
        }

        if (!animalType.equals("schaf")
                && !animalType.equals("kuh")
                && !animalType.equals("huhn")
                && !animalType.equals("ente")) {

            GameStyle.warning("Diese Tierart ist im System nicht vorgesehen.");
            System.out.println("Die Farmverwaltung akzeptiert aktuell nur:");
            System.out.println("schaf, kuh, huhn oder ente.");
            System.out.println("Alles andere wäre vermutlich ein DLC.");
            GameStyle.endBox();
            return;
        }

        System.out.print(GameStyle.label("Name: "));
        String name = scanner.nextLine().trim();

        if (name.isBlank()) {
            GameStyle.warning("Ein Tier ohne Namen kann nicht verwaltet werden.");
            System.out.println("Die Akte wurde aus Gründen der bürokratischen Würde geschlossen.");
            GameStyle.endBox();
            return;
        }

        if (farm.animalNameExists(name)) {
            GameStyle.warning("Dieser Name ist bereits im Tierregister vergeben.");
            System.out.println("Die Farmverwaltung verweigert doppelte Aktenführung.");
            System.out.println("Bitte eröffne eine neue Akte mit eindeutigem Namen.");
            GameStyle.endBox();
            return;
        }

        int hunger = readIntWithDefault(scanner, "Start-Hunger", 30);
        int age = readIntWithDefault(scanner, "Alter", 1);

        System.out.println();

        switch (animalType) {

            case "schaf":
                int wool = readIntWithDefault(scanner, "Start-Wolle", 0);

                Sheep sheep = new Sheep(name, hunger, age, wool);
                farm.addAnimal(sheep);

                printAnimalCreatedMessage(name, "Schaf", "Wolle", wool);
                break;

            case "kuh":
                int milk = readIntWithDefault(scanner, "Start-Milch", 0);

                Cow cow = new Cow(name, hunger, age, milk);
                farm.addAnimal(cow);

                printAnimalCreatedMessage(name, "Kuh", "Milch", milk);
                break;

            case "huhn":
                int eggs = readIntWithDefault(scanner, "Start-Eier", 0);

                Chicken chicken = new Chicken(name, hunger, age, eggs);
                farm.addAnimal(chicken);

                printAnimalCreatedMessage(name, "Huhn", "Eier", eggs);
                break;

            case "ente":
                int feathers = readIntWithDefault(scanner, "Start-Federn", 0);

                Duck duck = new Duck(name, hunger, age, feathers);
                farm.addAnimal(duck);

                printAnimalCreatedMessage(name, "Ente", "Federn", feathers);
                break;
        }

        GameStyle.endBox();
    }

    public static void printAnimalCreatedMessage(String name, String animalType, String resourceName, int resourceAmount) {

        System.out.println();
        System.out.println(GameStyle.BRIGHT_GREEN + "✓ Tierakte wurde erfolgreich angelegt." + GameStyle.RESET);

        System.out.println(GameStyle.label("Name: ")
                + name);

        System.out.println(GameStyle.label("Tierart: ")
                + animalType);

        System.out.println(GameStyle.label("Erfasste Ressource: ")
                + resourceAmount
                + " "
                + resourceName);

        System.out.println();

        System.out.println(name + " wurde ins Tierregister aufgenommen.");
        System.out.println("Die Farmverwaltung übernimmt keine Verantwortung für spätere Persönlichkeitsentwicklung.");
    }

    public static int readIntWithDefault(Scanner scanner, String text, int defaultValue) {

        System.out.print(GameStyle.label(text + " [" + defaultValue + "]: "));

        String input = scanner.nextLine().trim();

        if (input.isBlank()) {
            System.out.println("Keine Angabe. Die Verwaltung nimmt einfach " + defaultValue + ".");
            return defaultValue;
        }

        try {
            return Integer.parseInt(input);

        } catch (NumberFormatException exception) {
            System.out.println("Diese Eingabe war keine Zahl.");
            System.out.println("Die Farmverwaltung ignoriert das und nimmt " + defaultValue + ".");
            return defaultValue;
        }
    }

    public static boolean isHungry(int animalHunger, Scanner scanner) {

        if (animalHunger >= 75) {
            GameStyle.info("Die Versorgungslage ist kritisch. Maßnahme wird eingeleitet.");
            return true;

        } else if (animalHunger >= 50) {
            System.out.print(GameStyle.label("Die Lage ist mittelmäßig dramatisch. Trotzdem füttern? "));
            String answer = scanner.nextLine().toLowerCase().trim();

            if (answer.equals("ja")) {
                GameStyle.info("Versorgungsmaßnahme wurde genehmigt.");
                return true;
            } else {
                GameStyle.info("Versorgungsmaßnahme wurde vertagt. Das Tier merkt sich das.");
                return false;
            }

        } else {
            GameStyle.info("Das Tier ist laut Aktenlage nicht hungrig genug.");
            System.out.println("Die Farmverwaltung spart Futter und nennt es Strategie.");
            return false;
        }
    }

    // Erhöht den Rundenzähler und prüft, ob alle Tiere älter werden müssen
    public static int increaseRound(int roundCounter) {

        roundCounter = roundCounter + 1;

        GameStyle.logBox("RUNDENENDE");

        System.out.println(GameStyle.label("Runde: ")
                + roundCounter
                + " wurde überlebt.");

        if (roundCounter % 3 == 0) {
            farm.increaseAge();

            System.out.println(GameStyle.label("Alterung: ")
                    + "Alle Tiere sind älter geworden. Niemand wurde gefragt.");
        }

        GameStyle.endBox();

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




    public static String readMenuChoice(Scanner scanner) {

        String choice = scanner.nextLine().trim();

        // Leere Eingaben ignorieren
        while (choice.isBlank()) {
            GameStyle.info("Kein Aktenzeichen eingegeben. Die Verwaltung wartet weiter.");
            System.out.print(GameStyle.BRIGHT_YELLOW + "➜ Aktenzeichen deiner Entscheidung: " + GameStyle.RESET);
            choice = scanner.nextLine().trim();
        }

        return choice;
    }
}