package animals;

// Duck ist unsere neue Tierart.
// Sie erbt von Animal und kann Ressourcen produzieren.
public class Duck extends Animal implements ResourceProducer {

    // Eigene Eigenschaft der Ente
    private int feathers;

    // Leerer Constructor
    public Duck() {

    }

    // Constructor mit allen Werten
    public Duck(String name, int hunger, int age, int feathers) {
        // name, hunger und age kommen aus Animal
        super(name, hunger, age);

        // feathers gehört nur zur Ente
        this.feathers = feathers;
    }

    public int getFeathers() {
        return feathers;
    }

    public void setFeathers(int feathers) {
        this.feathers = feathers;
    }

    // Beim Füttern wird zuerst normal gefüttert.
    // Danach bekommt die Ente 1 Feder dazu.
    @Override
    public void feed() {
        super.feed();
        feathers = feathers + 1;
    }

    // Sammelt die Federn ein und setzt sie danach auf 0
    @Override
    public int collectResource() {
        int collectedFeathers = feathers;
        feathers = 0;
        return collectedFeathers;
    }

    // Gibt zurück, welche Ressource die Ente produziert
    @Override
    public String getResourceName() {
        return "Federn";
    }
}