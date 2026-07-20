package animals;

// Cow erbt von Animal und kann Ressourcen produzieren
public class Cow extends Animal implements ResourceProducer {

    // Eigene Eigenschaft der Kuh
    private int milk;

    // Leerer Constructor
    public Cow() {

    }

    // Constructor mit allen Werten
    public Cow(String name, int hunger, int age, int milk) {
        // name, hunger und age kommen aus Animal
        super(name, hunger, age);

        // milk gehört nur zur Kuh
        this.milk = milk;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    // Spezielle feed-Methode für die Kuh
    // Die Kuh bekommt nur 10 Hunger abgezogen
    // und produziert danach 1 Milch dazu.
    @Override
    public void feed() {
        setHunger(getHunger() - 10);

        // Hunger soll nicht unter 0 fallen
        if (getHunger() < 0) {
            setHunger(0);
        }

        milk = milk + 1;
    }

    // Sammelt die Milch ein und setzt sie danach auf 0
    @Override
    public int collectResource() {
        int collectedMilk = milk;
        milk = 0;
        return collectedMilk;
    }

    // Gibt zurück, welche Ressource die Kuh produziert
    @Override
    public String getResourceName() {
        return "Milch";
    }
}