import animals.Sheep;
import animals.Cow;
import animals.Chicken;
import farm.Farm;

import java.util.Scanner;

public class Main {

    // Farm als Objekt
    static Farm farm;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // neue Farm
        farm = new Farm();

        // Tiere erstellen
        Sheep sheep = new Sheep("Shaun", 80, 3, 20);
        Cow cow = new Cow("Berta", 60, 5, 30);
        Chicken chicken = new Chicken("Henrietta", 30, 2, 5);

        // in Farm setzen
        farm.setSheep(sheep);
        farm.setCow(cow);
        farm.setChicken(chicken);

        boolean animalDied = false;

        while (!animalDied) {

            // Hungerwert anzeigen
            farm.printHunger();

            System.out.print("Welches Tier möchtest du auswählen? ");
            String animal = scanner.nextLine().toLowerCase();

            // Hungerwert holen
            int animalHunger = farm.getHungerForAnimal(animal);

            // Schleife neu, wenn kein passendes Tier
            if (animalHunger < 0) {
                continue;
            }

            // Prüfen ob Füttern
            boolean animalWasFed = isHungry(animalHunger, scanner);

            // Hungerwert senken, wenn gefüttert
            if (animalWasFed) {
                farm.reduceHunger(animal);
            }

            // Hungerwert erhöhen bei nicht gefütterten Tieren
            farm.hunger(animal, animalWasFed);

            // Ist ein Tier verhungert?
            if (farm.animalDied()) {
                System.out.println("Eines der Tiere ist verhungert.");
                animalDied = true;
            }

            System.out.println();
        }

        scanner.close();
    }

    // mit Hungerwert entscheiden ob gefüttert wird
    public static boolean isHungry(int animalHunger, Scanner scanner) {

        if (animalHunger >= 75) {
            System.out.println("Das Tier wird gefüttert.");
            return true;
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
        } else {
            System.out.println("Das Tier ist nicht hungrig genug.");
            return false;
        }
    }
}