package animals;

public class Cow {

    // Eigenschaften Kuh
    private String name;
    private int hunger;
    private int age;
    private int milk;

    public Cow() {

    }

    public Cow(String name, int hunger, int age, int wool) {
        this.name = name;
        this.hunger = hunger;
        this.age = age;
        this.milk = wool;
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

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }
}