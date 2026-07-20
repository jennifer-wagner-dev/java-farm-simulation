package animals;

// Chicken erbt von Animal und kann Ressourcen produzieren
public class Chicken extends Animal implements ResourceProducer {

    // Eigene Eigenschaft des Huhns
    private int eggs;

    // Leerer Constructor
    public Chicken() {

    }

    // Constructor mit allen Werten
    public Chicken(String name, int hunger, int age, int eggs) {
        // name, hunger und age kommen aus Animal
        super(name, hunger, age);

        // eggs gehört nur zum Huhn
        this.eggs = eggs;
    }

    public int getEggs() {
        return eggs;
    }

    public void setEggs(int eggs) {
        this.eggs = eggs;
    }

    // Beim Füttern wird zuerst normal gefüttert.
    // Danach legt das Huhn 1 Ei dazu.
    @Override
    public void feed() {
        super.feed();
        eggs = eggs + 1;
    }

    // Sammelt die Eier ein und setzt sie danach auf 0
    @Override
    public int collectResource() {
        int collectedEggs = eggs;
        eggs = 0;
        return collectedEggs;
    }

    // Gibt zurück, welche Ressource das Huhn produziert
    @Override
    public String getResourceName() {
        return "Eier";
    }
}