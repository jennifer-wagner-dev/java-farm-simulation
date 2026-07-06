package animals;

public class Chicken {
    // Eigenschaften Huhn
    private String name;
    private int hunger;
    private int age;
    private int eggs;

    public Chicken() {

    }

    public Chicken(String name, int hunger, int age, int wool) {
        this.name = name;
        this.hunger = hunger;
        this.age = age;
        this.eggs = wool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getEggs() {
        return eggs;
    }

    public void setEggs(int eggs) {
        this.eggs = eggs;
    }
}