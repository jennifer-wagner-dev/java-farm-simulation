package animals;

// Sheep erbt von Animal und kann Ressourcen produzieren
public class Sheep extends Animal implements ResourceProducer {

    // Eigene Eigenschaft des Schafs
    private int wool;

    // Leerer Constructor
    public Sheep() {

    }

    // Constructor mit allen Werten
    public Sheep(String name, int hunger, int age, int wool) {
        // name, hunger und age kommen aus Animal
        super(name, hunger, age);

        // wool gehört nur zum Schaf
        this.wool = wool;
    }

    public int getWool() {
        return wool;
    }

    public void setWool(int wool) {
        this.wool = wool;
    }

    // Beim Füttern wird zuerst die normale feed-Methode aus Animal ausgeführt.
    // Danach bekommt das Schaf 1 Wolle dazu.
    @Override
    public void feed() {
        super.feed();
        wool = wool + 1;
    }

    // Sammelt die Wolle ein und setzt sie danach auf 0
    @Override
    public int collectResource() {
        int collectedWool = wool;
        wool = 0;
        return collectedWool;
    }

    // Gibt zurück, welche Ressource das Schaf produziert
    @Override
    public String getResourceName() {
        return "Wolle";
    }
}