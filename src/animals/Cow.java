package animals;

public class Cow extends Animal {

    // Eigenschaften Kuh
    private int milk;

    public Cow() {

    }

    public Cow(String name, int hunger, int age, int milk) {
        super(name, hunger, age);
        this.milk = milk;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    @Override
    public void feed() {
        setHunger(getHunger() - 10);

        if (getHunger() < 0) {
            setHunger(0);
        }

        milk = milk + 1;
    }
}