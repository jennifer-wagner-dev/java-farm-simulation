package farm;

import java.util.Scanner;

public class _Farm_alt {

    static int hungerSheep = 80;
    static int hungerCow = 30;
    static int hungerChicken = 30;

    static boolean isSheep = false;
    static boolean isCow = false;
    static boolean isChicken = false;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean animalDied = false;

        while (!animalDied) {

            isSheep = false;
            isCow = false;
            isChicken = false;

            printHunger();

            System.out.print("Welches Tier möchtest du auswählen? ");
            String animal = scanner.nextLine().toLowerCase();

            int animalHunger = getHungerForAnimal(animal);

            if (animalHunger < 0) {
                continue;
            }

            boolean animalWasFed = isHungry(animalHunger, scanner);

            if (animalWasFed) {

                if (isSheep) {
                    hungerSheep = hungerSheep - 20;

                    if (hungerSheep < 0) {
                        hungerSheep = 0;
                    }
                }

                if (isCow) {
                    hungerCow = hungerCow - 20;

                    if (hungerCow < 0) {
                        hungerCow = 0;
                    }
                }

                if (isChicken) {
                    hungerChicken = hungerChicken - 20;

                    if (hungerChicken < 0) {
                        hungerChicken = 0;
                    }
                }
            }

            hunger(animalWasFed);

            if (hungerSheep > 100 || hungerCow > 100 || hungerChicken > 100) {
                System.out.println("Eines der Tiere ist verhungert.");
                animalDied = true;
            }

            System.out.println();
        }

        scanner.close();
    }

    public static void printHunger() {
        System.out.println("Hunger Schaf: " + hungerSheep);
        System.out.println("Hunger Kuh: " + hungerCow);
        System.out.println("Hunger Huhn: " + hungerChicken);
    }

    public static int getHungerForAnimal(String animal) {

        switch (animal) {
            case "schaf":
                System.out.println("Du hast das Schaf gewählt!");
                isSheep = true;
                return hungerSheep;

            case "kuh":
                System.out.println("Du hast die Kuh gewählt!");
                isCow = true;
                return hungerCow;

            case "huhn":
                System.out.println("Du hast das Huhn gewählt!");
                isChicken = true;
                return hungerChicken;

            default:
                System.out.println("Kein passendes Tier gefunden!");
                return -1;
        }
    }

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

    public static void hunger(boolean animalWasFed) {

        if (!animalWasFed) {
            hungerSheep = hungerSheep + 10;
            hungerCow = hungerCow + 10;
            hungerChicken = hungerChicken + 10;
        } else {
            if (!isSheep) {
                hungerSheep = hungerSheep + 10;
            }

            if (!isCow) {
                hungerCow = hungerCow + 10;
            }

            if (!isChicken) {
                hungerChicken = hungerChicken + 10;
            }
        }
    }
}