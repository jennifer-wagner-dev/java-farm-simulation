package animals;

public class Sheep extends Animal {

    // Eigenschaften Schaf
    private int wool;

    public Sheep() {

    }

    public Sheep(String name, int hunger, int age, int wool) {
        super(name, hunger, age);
        this.wool = wool;
    }

    public int getWool() {
        return wool;
    }

    public void setWool(int wool) {
        this.wool = wool;
    }

    @Override
    public void feed() {
        super.feed();
        wool = wool + 1;
    }

}