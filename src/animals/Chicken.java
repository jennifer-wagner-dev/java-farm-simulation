package animals;

public class Chicken extends Animal {
    // Eigenschaften Huhn
    private int eggs;

    public Chicken() {

    }

    public Chicken(String name, int hunger, int age, int eggs) {
        super(name, hunger, age);
        this.eggs = eggs;
    }

    public int getEggs() {
        return eggs;
    }

    public void setEggs(int eggs) {
        this.eggs = eggs;
    }
}