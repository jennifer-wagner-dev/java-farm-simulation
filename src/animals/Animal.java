package animals;

// Animal ist die Elternklasse für alle Tiere.
// Gemeinsame Eigenschaften und Methoden stehen hier.
public class Animal implements HungerManager {

    // Gemeinsame Eigenschaften aller Tiere
    private String name;
    private int hunger;
    private int age;

    // Speichert, ob das Tier krank ist
    private boolean sick;

    // Speichert, wie viele Runden das Tier schon krank ist
    private int sickRounds;

    // Speichert den Namen der Krankheit
    private String diseaseName;

    // Speichert die Persönlichkeit des Tieres
    private String personality;

    // Leerer Constructor
    public Animal() {

        // Tiere sind am Anfang gesund
        this.sick = false;

        // Tiere sind am Anfang 0 Runden krank
        this.sickRounds = 0;

        // Tiere haben am Anfang keine Krankheit
        this.diseaseName = "keine";

        // Tiere bekommen eine zufällige Persönlichkeit
        this.personality = getRandomPersonality();
    }

    // Constructor mit allen gemeinsamen Werten
    public Animal(String name, int hunger, int age) {
        this.name = name;
        this.hunger = hunger;
        this.age = age;

        // Neue Tiere sind am Anfang gesund
        this.sick = false;

        // Neue Tiere sind am Anfang 0 Runden krank
        this.sickRounds = 0;

        // Neue Tiere haben am Anfang keine Krankheit
        this.diseaseName = "keine";

        // Neue Tiere bekommen eine zufällige Persönlichkeit
        this.personality = getRandomPersonality();
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

    public boolean isSick() {
        return sick;
    }

    public void setSick(boolean sick) {
        this.sick = sick;
    }

    public int getSickRounds() {
        return sickRounds;
    }

    public void setSickRounds(int sickRounds) {
        this.sickRounds = sickRounds;
    }

    // Gibt den Namen der Krankheit zurück
    public String getDiseaseName() {
        return diseaseName;
    }

    // Setzt den Namen der Krankheit
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    // Gibt die Persönlichkeit des Tieres zurück
    public String getPersonality() {
        return personality;
    }

    // Setzt die Persönlichkeit des Tieres
    public void setPersonality(String personality) {
        this.personality = personality;
    }

    // Senkt den Hungerwert beim Füttern
    @Override
    public void feed() {
        hunger = hunger - 15;

        // Hunger soll nicht unter 0 fallen
        if (hunger < 0) {
            hunger = 0;
        }
    }

    // Erhöht den Hungerwert nach einer Runde
    @Override
    public void hunger() {

        // Kranke Tiere bekommen mehr Hunger
        if (sick) {
            hunger = hunger + 10;

        } else {
            // Gesunde Tiere bekommen normalen Hunger
            hunger = hunger + hungerValue;
        }
    }

    // Erhöht die Krankheitsdauer um 1 Runde
    public void increaseSickRounds() {

        // Nur kranke Tiere sammeln Krankheitsrunden
        if (sick) {
            sickRounds = sickRounds + 1;
        }
    }

    // Macht ein Tier krank
    public void makeSick() {
        sick = true;
        sickRounds = 0;

        // Beim Krankwerden bekommt das Tier eine zufällige Krankheit
        diseaseName = getRandomDiseaseName();
    }

    // Behandelt ein krankes Tier
    public void heal() {
        sick = false;
        sickRounds = 0;

        // Nach der Behandlung hat das Tier keine Krankheit mehr
        diseaseName = "keine";
    }

    // Gibt einen zufälligen lustigen Krankheitsnamen zurück
    public String getRandomDiseaseName() {

        // Zufallszahl von 1 bis 6 erzeugen
        int randomNumber = (int) (Math.random() * 6) + 1;

        if (randomNumber == 1) {
            return "Männerschnupfen";
        } else if (randomNumber == 2) {
            return "akute Dramaitis";
        } else if (randomNumber == 3) {
            return "Flauschfieber";
        } else if (randomNumber == 4) {
            return "Milch-Melancholie";
        } else if (randomNumber == 5) {
            return "Eier-Existenzkrise";
        } else {
            return "quakende Quarantäne";
        }
    }

    // Gibt eine zufällige Persönlichkeit zurück
    public String getRandomPersonality() {

        // Zufallszahl von 1 bis 5 erzeugen
        int randomNumber = (int) (Math.random() * 5) + 1;

        if (randomNumber == 1) {
            return "dramatisch";
        } else if (randomNumber == 2) {
            return "gierig";
        } else if (randomNumber == 3) {
            return "schüchtern";
        } else if (randomNumber == 4) {
            return "chaotisch";
        } else {
            return "verwöhnt";
        }
    }
}