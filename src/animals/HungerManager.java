package animals;

// Dieses Interface beschreibt, dass ein Tier Hunger verändern kann.
public interface HungerManager {

    // Konstanter Wert, um den der Hunger normalerweise steigt
    int hungerValue = 10;

    // Methode zum Füttern
    void feed();

    // Methode, damit ein Tier hungriger wird
    void hunger();
}