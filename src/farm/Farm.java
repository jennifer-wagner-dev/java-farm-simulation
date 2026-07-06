package farm;

import animals.Sheep;
import animals.Cow;
import animals.Chicken;

public class Farm {

    // Farm hat Tiere
    private Sheep sheep;
    private Cow cow;
    private Chicken chicken;

    // Leerer Constructor
    public Farm() {

    }

    public Sheep getSheep() {
        return sheep;
    }

    public void setSheep(Sheep sheep) {
        this.sheep = sheep;
    }

    public Cow getCow() {
        return cow;
    }

    public void setCow(Cow cow) {
        this.cow = cow;
    }

    public Chicken getChicken() {
        return chicken;
    }

    public void setChicken(Chicken chicken) {
        this.chicken = chicken;
    }

    // aktueller Hungerwert ausgeben
    public void printHunger() {
        System.out.println("Hunger Schaf: " + sheep.getHunger());
        System.out.println("Hunger Kuh: " + cow.getHunger());
        System.out.println("Hunger Huhn: " + chicken.getHunger());
    }

    // Hungerwert für ausgewähltes Tier zurückgeben
    public int getHungerForAnimal(String animal) {

        switch (animal) {
            case "schaf":
                System.out.println("Du hast das Schaf gewählt!");
                return sheep.getHunger();

            case "kuh":
                System.out.println("Du hast die Kuh gewählt!");
                return cow.getHunger();

            case "huhn":
                System.out.println("Du hast das Huhn gewählt!");
                return chicken.getHunger();

            default:
                System.out.println("Kein passendes Tier gefunden!");
                return -1;
        }
    }

    // Hungerwert des geütterten Tieres senken
    public void reduceHunger(String animal) {

        if (animal.equals("schaf")) {
            sheep.setHunger(sheep.getHunger() - 20);

            if (sheep.getHunger() < 0) {
                sheep.setHunger(0);
            }
        }

        if (animal.equals("kuh")) {
            cow.setHunger(cow.getHunger() - 20);

            if (cow.getHunger() < 0) {
                cow.setHunger(0);
            }
        }

        if (animal.equals("huhn")) {
            chicken.setHunger(chicken.getHunger() - 20);

            if (chicken.getHunger() < 0) {
                chicken.setHunger(0);
            }
        }
    }

    // Hungerwert der Tiere erhöhen
    public void hunger(String animal, boolean animalWasFed) {

        if (!animalWasFed || !animal.equals("schaf")) {
            sheep.setHunger(sheep.getHunger() + 10);
        }

        if (!animalWasFed || !animal.equals("kuh")) {
            cow.setHunger(cow.getHunger() + 10);
        }

        if (!animalWasFed || !animal.equals("huhn")) {
            chicken.setHunger(chicken.getHunger() + 10);
        }
    }

    // Tier verhungert?
    public boolean animalDied() {

        if (sheep.getHunger() > 100 || cow.getHunger() > 100 || chicken.getHunger() > 100) {
            return true;
        } else {
            return false;
        }
    }
}