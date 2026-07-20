package animals;

// Dieses Interface beschreibt Tiere,
// die Ressourcen produzieren können.
// Zum Beispiel: Wolle, Milch, Eier oder Federn
public interface ResourceProducer {

    // Sammelt die Ressource ein
    // und gibt zurück, wie viel eingesammelt wurde.
    int collectResource();

    // Gibt den Namen der Ressource zurück.
    String getResourceName();
}