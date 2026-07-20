import animals.Animal;
import animals.Sheep;
import animals.Cow;
import animals.Chicken;
import animals.Duck;
import farm.Farm;

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
        String farmName = scanner.nextLine();

        // Farmnamen setzen
        farm.setFarmName(farmName);

        // Begrüßung ausgeben
        System.out.println();
        System.out.println("==================================================");
        System.out.println("        Willkommen auf " + farm.getFarmName());
        System.out.println("==================================================");
        System.out.println("Möge das Chaos beginnen.");
        System.out.println();

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

            // Alle Tiere schön formatiert anzeigen
            farm.printAnimals();

            // Menü schön formatiert anzeigen
            printMenu();

            // Auswahl des Users einlesen
            String choice = scanner.nextLine();

            // Menü-Auswahl prüfen
            switch (choice) {

                // Spiel beenden
                case "0":
                    System.out.println("Spiel wurde beendet.");
                    gameOver = true;
                    break;

                // Einzelnes Tier füttern
                case "1":
                    System.out.print("Welches Tier möchtest du füttern? Gib den Namen ein: ");
                    String animalName = scanner.nextLine();

                    // Tier anhand des Namens suchen
                    Animal selectedAnimal = farm.getAnimalByName(animalName);

                    // Wenn kein Tier gefunden wurde, wird diese Runde abgebrochen
                    if (selectedAnimal == null) {
                        break;
                    }

                    // Prüfen, ob das Tier gefüttert werden soll
                    boolean animalWasFed = isHungry(selectedAnimal.getHunger(), scanner);

                    // Wenn das Tier gefüttert werden soll,
                    // prüft die Farm zuerst, ob genug Geld für Futter da ist
                    if (animalWasFed) {
                        animalWasFed = farm.feedAnimal(selectedAnimal);
                    }

                    // Hunger aller anderen Tiere erhöhen
                    // Wenn das Füttern nicht geklappt hat,
                    // bekommt auch dieses Tier Hunger dazu
                    farm.hunger(selectedAnimal, animalWasFed);

                    // Runde abschließen
                    roundCounter = finishRound(roundCounter);

                    // Prüfen, ob noch Tiere leben
                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind gestorben. Das Spiel ist vorbei.");
                        gameOver = true;
                    }

                    break;

                // Neues Tier erstellen
                case "2":
                    createAnimal(scanner);

                    // Runde abschließen, weil eine Aktion ausgeführt wurde
                    roundCounter = finishRound(roundCounter);

                    // Prüfen, ob noch Tiere leben
                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind gestorben. Das Spiel ist vorbei.");
                        gameOver = true;
                    }

                    break;

                // Ressourcen einsammeln
                case "3":
                    farm.collectResources();

                    // Runde abschließen, weil eine Aktion ausgeführt wurde
                    roundCounter = finishRound(roundCounter);

                    // Prüfen, ob noch Tiere leben
                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind gestorben. Das Spiel ist vorbei.");
                        gameOver = true;
                    }

                    break;

                // Ressourcenlager anzeigen
                case "4":

                    // Lager anzeigen zählt nicht als Runde,
                    // weil sich dadurch im Spiel nichts verändert
                    farm.printStorage();

                    break;

                // Tier behandeln lassen
                case "5":
                    System.out.print("Welches Tier soll behandelt werden? ");
                    String animalToTreat = scanner.nextLine();

                    // Tier behandeln lassen
                    farm.treatAnimalByName(animalToTreat);

                    // Runde abschließen, weil eine Aktion ausgeführt wurde
                    roundCounter = finishRound(roundCounter);

                    // Prüfen, ob noch Tiere leben
                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind gestorben. Das Spiel ist vorbei.");
                        gameOver = true;
                    }

                    break;

                // Ressourcen verkaufen
                case "6":

                    // Ressourcen aus dem Lager verkaufen
                    farm.sellResources();

                    // Runde abschließen, weil eine Aktion ausgeführt wurde
                    roundCounter = finishRound(roundCounter);

                    // Prüfen, ob noch Tiere leben
                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind gestorben. Das Spiel ist vorbei.");
                        gameOver = true;
                    }

                    break;

// Ganze Tierart füttern
                case "7":
                    System.out.print("Welche Tierart möchtest du füttern? ");
                    String animalType = scanner.nextLine().toLowerCase();

                    // Alle Tiere dieser Art füttern
                    farm.feedAnimalsByType(animalType);

                    // Runde abschließen, weil eine Aktion ausgeführt wurde
                    roundCounter = finishRound(roundCounter);

                    // Prüfen, ob noch Tiere leben
                    if (farm.hasNoAnimals()) {
                        System.out.println("Alle Tiere sind gestorben. Das Spiel ist vorbei.");
                        gameOver = true;
                    }

                    break;

                // Ungültige Eingabe
                default:
                    System.out.println("Ungültige Auswahl.");
                    break;
            }
        }

        // Scanner schließen
        scanner.close();
    }

    // Gibt den oberen Bereich des Spiels schön formatiert aus
    public static void printGameHeader(int roundCounter) {

        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-58s ║%n", farm.getFarmName());
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        System.out.println("Runde: " + roundCounter + " | Geld: " + farm.getMoney() + " Münzen");
    }

    // Gibt das Hauptmenü schön formatiert aus
    public static void printMenu() {

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("│                           MENÜ                             │");
        System.out.println("├────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1 - Einzelnes Tier füttern                                 │");
        System.out.println("│ 2 - Neues Tier erstellen                                   │");
        System.out.println("│ 3 - Ressourcen einsammeln                                  │");
        System.out.println("│ 4 - Ressourcenlager anzeigen                               │");
        System.out.println("│ 5 - Tier behandeln lassen                                  │");
        System.out.println("│ 6 - Ressourcen verkaufen                                   │");
        System.out.println("│ 7 - Ganze Tierart füttern                                  │");
        System.out.println("│ 0 - Spiel beenden                                          │");
        System.out.println("└────────────────────────────────────────────────────────────┘");
        System.out.print("Auswahl: ");
    }

    // Erstellt ein neues Tier und fügt es der Farm hinzu
    public static void createAnimal(Scanner scanner) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("              NEUES TIER ERSTELLEN");
        System.out.println("==================================================");

        System.out.print("Welche Tierart möchtest du erstellen? ");
        String animalType = scanner.nextLine().toLowerCase();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Hunger: ");
        int hunger = scanner.nextInt();

        System.out.print("Alter: ");
        int age = scanner.nextInt();

        // Neues Schaf erstellen
        if (animalType.equals("schaf")) {

            System.out.print("Wolle: ");
            int wool = scanner.nextInt();

            // nextLine leert den Eingabepuffer nach nextInt
            scanner.nextLine();

            farm.addAnimal(new Sheep(name, hunger, age, wool));
            System.out.println(name + " wurde als Schaf erstellt.");

            // Neue Kuh erstellen
        } else if (animalType.equals("kuh")) {

            System.out.print("Milch: ");
            int milk = scanner.nextInt();

            // nextLine leert den Eingabepuffer nach nextInt
            scanner.nextLine();

            farm.addAnimal(new Cow(name, hunger, age, milk));
            System.out.println(name + " wurde als Kuh erstellt.");

            // Neues Huhn erstellen
        } else if (animalType.equals("huhn")) {

            System.out.print("Eier: ");
            int eggs = scanner.nextInt();

            // nextLine leert den Eingabepuffer nach nextInt
            scanner.nextLine();

            farm.addAnimal(new Chicken(name, hunger, age, eggs));
            System.out.println(name + " wurde als Huhn erstellt.");

            // Neue Ente erstellen
        } else if (animalType.equals("ente")) {

            System.out.print("Federn: ");
            int feathers = scanner.nextInt();

            // nextLine leert den Eingabepuffer nach nextInt
            scanner.nextLine();

            farm.addAnimal(new Duck(name, hunger, age, feathers));
            System.out.println(name + " wurde als Ente erstellt.");

            // Wenn keine passende Tierart eingegeben wurde
        } else {

            // nextLine leert den Eingabepuffer nach nextInt
            scanner.nextLine();

            System.out.println("Diese Tierart gibt es nicht.");
        }
    }

    // Entscheidet anhand des Hungerwertes, ob gefüttert werden soll
    public static boolean isHungry(int animalHunger, Scanner scanner) {

        // Ab Hunger 75 wird automatisch gefüttert
        if (animalHunger >= 75) {
            System.out.println("Das Tier wird gefüttert.");
            return true;

            // Ab Hunger 50 darf der User entscheiden
        } else if (animalHunger >= 50) {

            System.out.print("Das Tier ist mäßig hungrig. Möchtest du es füttern? ");
            String answer = scanner.nextLine().toLowerCase();

            if (answer.equals("ja")) {
                System.out.println("Das Tier wird gefüttert.");
                return true;
            } else {
                System.out.println("Das Tier wird nicht gefüttert.");
                return false;
            }

            // Unter Hunger 50 wird nicht gefüttert
        } else {
            System.out.println("Das Tier ist nicht hungrig genug.");
            return false;
        }
    }

    // Erhöht den Rundenzähler und prüft,
    // ob alle Tiere älter werden müssen
    public static int increaseRound(int roundCounter) {

        // Eine neue Runde wird gezählt
        roundCounter = roundCounter + 1;

        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("Runde " + roundCounter + " wurde abgeschlossen.");

        // Jede dritte Runde werden alle Tiere älter
        if (roundCounter % 3 == 0) {
            farm.increaseAge();
            System.out.println("Alle Tiere sind älter geworden.");
        }

        System.out.println("--------------------------------------------------");

        // Neuer Rundenzähler wird zurückgegeben
        return roundCounter;
    }

    // Schließt eine Runde ab
// Hier passiert alles, was nach einer Aktion passieren soll
    public static int finishRound(int roundCounter) {

        // Rundenzähler erhöhen
        roundCounter = increaseRound(roundCounter);

        // Krankheitsdauer bei kranken Tieren erhöhen
        farm.increaseSickRounds();

        // Zufällig prüfen, ob ein Tier krank wird
        farm.randomDisease();

        // Prüfen, ob kranke Tiere andere Tiere anstecken
        farm.spreadDisease();

        // Zufälliges lustiges Farm-Event auslösen
        farm.randomFarmEvent();

        // Zufälligen Tier-Kommentar ausgeben
        farm.randomAnimalComment();

        // Tote Tiere von der Farm entfernen
        farm.removeDeadAnimals();

        // Neuen Rundenzähler zurückgeben
        return roundCounter;
    }
}