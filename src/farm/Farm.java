package farm;

import animals.Animal;
import animals.Sheep;
import animals.Cow;
import animals.Chicken;
import animals.Duck;
import animals.ResourceProducer;
import style.GameStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Farm {

    // Name der Farm
    private String farmName;

    // Liste, in der beliebig viele Tiere gespeichert werden können
    private List<Animal> animals;

    // Lager für eingesammelte Ressourcen
    private int storedWool;
    private int storedMilk;
    private int storedEggs;
    private int storedFeathers;

    // Geld der Farm
    private int money;

    // Leerer Constructor
    public Farm() {

        // Standardname, falls später keiner gesetzt wird
        farmName = "ShitShow Stables";

        // Neue leere Tierliste erstellen
        animals = new ArrayList<>();

        // Ressourcenlager am Anfang auf 0 setzen
        storedWool = 0;
        storedMilk = 0;
        storedEggs = 0;
        storedFeathers = 0;

        // Startgeld der Farm
        money = 50;
    }

    // Gibt den Namen der Farm zurück
    public String getFarmName() {
        return farmName;
    }

    // Setzt den Namen der Farm
    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    // Gibt die Tierliste zurück
    public List<Animal> getAnimals() {
        return animals;
    }

    // Setzt die Tierliste
    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    // Gibt das aktuelle Geld der Farm zurück
    public int getMoney() {
        return money;
    }

    // Fügt ein neues Tier zur Farm hinzu
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    // Gibt alle Tiere optisch schöner aus
    public void printAnimals() {

        if (animals.isEmpty()) {
            GameStyle.warning("Es gibt keine Tiere mehr auf der Farm.");
            return;
        }

        GameStyle.titleBox("TIERE AUF DER FARM");

        for (Animal animal : animals) {

            String animalSymbol = "❓";
            String resourceText = "";

            if (animal instanceof Sheep) {
                Sheep sheep = (Sheep) animal;
                animalSymbol = "🐑";
                resourceText = "Wolle: " + sheep.getWool();

            } else if (animal instanceof Cow) {
                Cow cow = (Cow) animal;
                animalSymbol = "🐄";
                resourceText = "Milch: " + cow.getMilk();

            } else if (animal instanceof Chicken) {
                Chicken chicken = (Chicken) animal;
                animalSymbol = "🐔";
                resourceText = "Eier: " + chicken.getEggs();

            } else if (animal instanceof Duck) {
                Duck duck = (Duck) animal;
                animalSymbol = "🦆";
                resourceText = "Federn: " + duck.getFeathers();
            }

            String statusText;

            if (animal.isSick()) {
                statusText = GameStyle.BRIGHT_RED
                        + "krank | "
                        + animal.getDiseaseName()
                        + " | seit "
                        + animal.getSickRounds()
                        + " Runden"
                        + GameStyle.RESET;
            } else {
                statusText = GameStyle.BRIGHT_GREEN + "gesund" + GameStyle.RESET;
            }

            System.out.println(GameStyle.GREEN + "╭─ "
                    + GameStyle.BRIGHT_YELLOW + animalSymbol + "  " + animal.getName()
                    + GameStyle.GREEN + " ─────────────────────────────────────"
                    + GameStyle.RESET);

            System.out.println(GameStyle.GREEN + "│ " + GameStyle.RESET
                    + "Alter:          " + GameStyle.BRIGHT_CYAN + animal.getAge() + GameStyle.RESET);

            System.out.println(GameStyle.GREEN + "│ " + GameStyle.RESET
                    + "Hunger:         " + GameStyle.getHungerBar(animal.getHunger()));

            System.out.println(GameStyle.GREEN + "│ " + GameStyle.RESET
                    + "Persönlichkeit: "
                    + getPersonalityIcon(animal)
                    + " "
                    + GameStyle.BRIGHT_PURPLE
                    + animal.getPersonality()
                    + GameStyle.RESET);

            System.out.println(GameStyle.GREEN + "│ " + GameStyle.RESET
                    + "Status:         " + statusText);

            System.out.println(GameStyle.GREEN + "│ " + GameStyle.RESET
                    + "Ressource:      "
                    + GameStyle.BRIGHT_YELLOW
                    + resourceText
                    + GameStyle.RESET);

            System.out.println(GameStyle.GREEN + "╰──────────────────────────────────────────────" + GameStyle.RESET);
            System.out.println();
        }
    }

    // Sucht ein Tier über seinen Namen
    public Animal getAnimalByName(String name) {

        // Leerzeichen am Anfang und Ende entfernen
        String searchedName = name.trim();

        // Alle Tiere durchsuchen
        for (Animal animal : animals) {

            // Groß- und Kleinschreibung ist egal
            if (animal.getName().equalsIgnoreCase(searchedName)) {
                return animal;
            }
        }

        // Wenn kein Tier gefunden wurde
        GameStyle.warning("Dieses Tier existiert laut Farmverwaltung nicht. Verdächtig.");
        return null;
    }

    // Füttert ein einzelnes Tier, wenn genug Geld für Futter vorhanden ist
    public boolean feedAnimal(Animal animal) {

        // Futterkosten hängen von der Persönlichkeit ab
        int feedCost = getFeedCost(animal);

        GameStyle.logBox("FÜTTERUNG");

        System.out.println(GameStyle.label("Tier: ") + animal.getName());
        System.out.println(GameStyle.label("Persönlichkeit: ")
                + getPersonalityIcon(animal)
                + " "
                + animal.getPersonality());

        // Prüfen, ob genug Geld vorhanden ist
        if (money < feedCost) {
            GameStyle.warning("Du hast nicht genug Geld für Futter.");
            System.out.println(GameStyle.label("Benötigt: ") + feedCost + " Münzen");
            System.out.println(GameStyle.label("Vorhanden: ") + GameStyle.money(money));
            System.out.println("Die Tiere schauen dich enttäuscht an.");
            GameStyle.endBox();

            // false bedeutet: Das Tier konnte nicht gefüttert werden
            return false;
        }

        // Hunger vor dem Füttern speichern
        int hungerBefore = animal.getHunger();

        // Geld für das Futter abziehen
        money = money - feedCost;

        // Das Tier wird gefüttert
        animal.feed();

        // Persönlichkeitseffekt nach dem Füttern anwenden
        String personalityEffect = applyPersonalityAfterFeeding(animal);

        // Hunger nach dem Füttern speichern
        int hungerAfter = animal.getHunger();

        System.out.println(GameStyle.label("Aktion: ") + getRandomFeedMessage(animal));
        System.out.println(GameStyle.label("Hunger vorher: ") + GameStyle.getHungerBar(hungerBefore));
        System.out.println(GameStyle.label("Hunger nachher: ") + GameStyle.getHungerBar(hungerAfter));
        System.out.println(GameStyle.label("Kosten: ") + feedCost + " Münzen");
        System.out.println(GameStyle.label("Geld: ") + GameStyle.money(money));

        GameStyle.endBox();

        // true bedeutet: Das Tier wurde erfolgreich gefüttert
        return true;
    }

    // Gibt einen lustigen Fütterungs-Spruch zurück,
    // abhängig von der Persönlichkeit des Tieres
    public String getRandomFeedMessage(Animal animal) {

        // Persönlichkeit des Tieres holen
        String personality = animal.getPersonality();

        if (personality.equals("dramatisch")) {
            return animal.getName() + " seufzt dramatisch und frisst, als wäre es eine Filmszene.";

        } else if (personality.equals("gierig")) {
            return animal.getName() + " inhaliert das Futter, bevor jemand blinzeln kann.";

        } else if (personality.equals("schüchtern")) {
            return animal.getName() + " nimmt das Futter vorsichtig und tut so, als wäre nichts passiert.";

        } else if (personality.equals("chaotisch")) {
            return animal.getName() + " verteilt erst die Hälfte des Futters und frisst dann den Rest.";

        } else if (personality.equals("verwöhnt")) {
            return animal.getName() + " akzeptiert das Futter nur unter Protest.";

        } else {
            return animal.getName() + " wurde gefüttert.";
        }
    }

    // Gibt passend zur Persönlichkeit ein Icon zurück
    public String getPersonalityIcon(Animal animal) {

        // Persönlichkeit des Tieres holen
        String personality = animal.getPersonality();

        if (personality.equals("dramatisch")) {
            return "🎭";
        } else if (personality.equals("gierig")) {
            return "🍽️";
        } else if (personality.equals("schüchtern")) {
            return "🫣";
        } else if (personality.equals("chaotisch")) {
            return "🌪️";
        } else if (personality.equals("verwöhnt")) {
            return "👑";
        } else {
            return "✨";
        }
    }

    // Gibt zurück, wie viel das Füttern kostet.
    // Verwöhnte Tiere kosten mehr, weil sie natürlich Spezialfutter wollen.
    public int getFeedCost(Animal animal) {

        int feedCost = 5;

        if (animal.getPersonality().equals("verwöhnt")) {
            feedCost = 8;
        }

        return feedCost;
    }

    // Wendet nach dem Füttern einen kleinen Effekt an,
// abhängig von der Persönlichkeit des Tieres.
// Die Methode gibt den Effekt als Text zurück.
// Wendet nach dem Füttern einen kleinen Effekt an,
// abhängig von der Persönlichkeit des Tieres.
// Die Methode gibt den Effekt als Text zurück.
    public String applyPersonalityAfterFeeding(Animal animal) {

        // Persönlichkeit des Tieres holen
        String personality = animal.getPersonality();

        if (personality.equals("gierig")) {

            // Gierige Tiere profitieren stärker vom Futter
            animal.setHunger(animal.getHunger() - 5);

            // Hunger soll nicht unter 0 fallen
            if (animal.getHunger() < 0) {
                animal.setHunger(0);
            }

            return animal.getName() + " hat noch Nachschlag gefunden. Hunger zusätzlich -5.";

        } else if (personality.equals("chaotisch")) {

            // Chaotische Tiere haben einen zufälligen Effekt
            int randomNumber = (int) (Math.random() * 2) + 1;

            if (randomNumber == 1) {

                // Glücklicher chaotischer Effekt
                animal.setHunger(animal.getHunger() - 10);

                if (animal.getHunger() < 0) {
                    animal.setHunger(0);
                }

                return animal.getName() + " hat das Futter überraschend effizient genutzt. Hunger zusätzlich -10.";

            } else {

                // Ungünstiger chaotischer Effekt
                animal.setHunger(animal.getHunger() + 5);

                return animal.getName() + " hat die Hälfte des Futters verschüttet. Hunger wieder +5.";
            }

        } else if (personality.equals("dramatisch")) {

            // Dramatische Tiere ändern keine Werte, machen aber Drama
            return animal.getName() + " tut so, als hätte diese Mahlzeit das eigene Leben verändert.";

        } else if (personality.equals("schüchtern")) {

            // Schüchterne Tiere ändern keine Werte
            return animal.getName() + " frisst heimlich, aber dankbar.";

        } else if (personality.equals("verwöhnt")) {

            // Verwöhnte Tiere ändern hier keine Werte,
            // kosten aber durch getFeedCost() mehr beim Füttern
            return animal.getName() + " akzeptiert das Futter, möchte aber nächstes Mal Bio-Heu.";

        } else {
            return animal.getName() + " frisst ganz normal. Verdächtig normal.";
        }
    }

    // Gibt einen lustigen Krankheits-Spruch zurück,
    // abhängig von der Persönlichkeit des Tieres
    public String getDiseaseMessage(Animal animal) {

        // Persönlichkeit des Tieres holen
        String personality = animal.getPersonality();

        if (personality.equals("dramatisch")) {
            return animal.getName() + " liegt theatralisch herum. Diagnose: " + animal.getDiseaseName() + ".";

        } else if (personality.equals("gierig")) {
            return animal.getName() + " ist krank, fragt aber trotzdem nach Snacks. Diagnose: " + animal.getDiseaseName() + ".";

        } else if (personality.equals("schüchtern")) {
            return animal.getName() + " ist leise krank geworden. Diagnose: " + animal.getDiseaseName() + ".";

        } else if (personality.equals("chaotisch")) {
            return animal.getName() + " ist irgendwie krank geworden. Niemand weiß genau wie. Diagnose: " + animal.getDiseaseName() + ".";

        } else if (personality.equals("verwöhnt")) {
            return animal.getName() + " verlangt eine Wärmflasche und Premium-Heu. Diagnose: " + animal.getDiseaseName() + ".";

        } else {
            return animal.getName() + " ist krank geworden. Diagnose: " + animal.getDiseaseName() + ".";
        }
    }

    // Gibt einen lustigen Spruch nach der Behandlung zurück,
    // abhängig von der Persönlichkeit des Tieres
    public String getTreatmentMessage(Animal animal) {

        // Persönlichkeit des Tieres holen
        String personality = animal.getPersonality();

        if (personality.equals("dramatisch")) {
            return animal.getName() + " verlässt die Behandlung wie nach einem Oscar-würdigen Krankenhausdrama.";

        } else if (personality.equals("gierig")) {
            return animal.getName() + " ist geheilt und fragt sofort, ob es im Wartezimmer Snacks gibt.";

        } else if (personality.equals("schüchtern")) {
            return animal.getName() + " wurde behandelt und bedankt sich sehr leise.";

        } else if (personality.equals("chaotisch")) {
            return animal.getName() + " wurde behandelt. Die Tierärztin braucht jetzt erstmal Urlaub.";

        } else if (personality.equals("verwöhnt")) {
            return animal.getName() + " wurde behandelt und bewertet die Tierärztin mit 3 von 5 Sternen.";

        } else {
            return animal.getName() + " wurde behandelt und ist wieder gesund.";
        }
    }

    // Füttert alle Tiere einer bestimmten Tierart
    public void feedAnimalsByType(String animalType) {

        int totalCost = 0;
        int animalCount = 0;

        GameStyle.logBox("GRUPPENFÜTTERUNG");

        System.out.println(GameStyle.label("Tierart: ") + animalType);

        // Zuerst zählen wir, wie viele Tiere zu der eingegebenen Tierart passen
        for (Animal animal : animals) {

            if (animalType.equals("schaf") && animal instanceof Sheep) {
                animalCount = animalCount + 1;
                totalCost = totalCost + getFeedCost(animal);

            } else if (animalType.equals("kuh") && animal instanceof Cow) {
                animalCount = animalCount + 1;
                totalCost = totalCost + getFeedCost(animal);

            } else if (animalType.equals("huhn") && animal instanceof Chicken) {
                animalCount = animalCount + 1;
                totalCost = totalCost + getFeedCost(animal);

            } else if (animalType.equals("ente") && animal instanceof Duck) {
                animalCount = animalCount + 1;
                totalCost = totalCost + getFeedCost(animal);
            }
        }

        // Wenn keine passenden Tiere gefunden wurden
        if (animalCount == 0) {
            GameStyle.warning("Es wurden keine Tiere dieser Art gefunden.");
            GameStyle.endBox();
            return;
        }

        // Prüfen, ob genug Geld vorhanden ist
        if (money < totalCost) {
            GameStyle.warning("Du hast nicht genug Geld, um alle Tiere dieser Art zu füttern.");
            System.out.println(GameStyle.label("Benötigt: ") + totalCost + " Münzen");
            System.out.println(GameStyle.label("Vorhanden: ") + GameStyle.money(money));
            System.out.println("Die Farm geht offiziell in den Sparmodus.");
            GameStyle.endBox();
            return;
        }

        // Geld abziehen
        money = money - totalCost;

        System.out.println(GameStyle.label("Gefundene Tiere: ") + animalCount);
        System.out.println(GameStyle.label("Gesamtkosten: ") + totalCost + " Münzen");
        System.out.println();

        // Jetzt werden alle passenden Tiere gefüttert
        for (Animal animal : animals) {

            boolean matchesType =
                    (animalType.equals("schaf") && animal instanceof Sheep)
                            || (animalType.equals("kuh") && animal instanceof Cow)
                            || (animalType.equals("huhn") && animal instanceof Chicken)
                            || (animalType.equals("ente") && animal instanceof Duck);

            if (matchesType) {

                int hungerBefore = animal.getHunger();

                animal.feed();

                String personalityEffect = applyPersonalityAfterFeeding(animal);

                int hungerAfter = animal.getHunger();

                System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                        + animal.getName()
                        + " "
                        + getPersonalityIcon(animal)
                        + " "
                        + GameStyle.BRIGHT_PURPLE
                        + animal.getPersonality()
                        + GameStyle.RESET);

                System.out.println("  " + GameStyle.label("Effekt: ") + personalityEffect);
                System.out.println("  " + GameStyle.label("Hunger: ")
                        + GameStyle.getHungerBar(hungerBefore)
                        + " → "
                        + GameStyle.getHungerBar(hungerAfter));
                System.out.println();
            }
        }

        System.out.println(GameStyle.label("Ergebnis: ") + "Alle Tiere der Art '" + animalType + "' wurden gefüttert.");
        System.out.println(GameStyle.label("Geld: ") + GameStyle.money(money));

        GameStyle.endBox();
    }

    // Erhöht den Hunger aller Tiere.
    // Wenn ein Tier gefüttert wurde, wird genau dieses Tier nicht erhöht.
    public void hunger(Animal fedAnimal, boolean animalWasFed) {

        // Alle Tiere durchgehen
        for (Animal animal : animals) {

            // Prüfen, ob dieses Tier gerade gefüttert wurde
            boolean isFedAnimal = animalWasFed && animal == fedAnimal;

            // Nur Tiere, die nicht erfolgreich gefüttert wurden,
            // bekommen zusätzlichen Hunger
            if (!isFedAnimal) {
                animal.hunger();
            }
        }
    }

    // Erhöht das Alter aller Tiere
    public void increaseAge() {

        // Alle Tiere durchgehen
        for (Animal animal : animals) {

            // Alter um 1 erhöhen
            animal.setAge(animal.getAge() + 1);
        }
    }

    // Erhöht bei allen kranken Tieren die Krankheitsdauer
    public void increaseSickRounds() {

        // Alle Tiere durchgehen
        for (Animal animal : animals) {

            // Animal prüft selbst, ob es krank ist
            animal.increaseSickRounds();
        }
    }

    // Sammelt die Ressourcen von allen Tieren ein,
// die das Interface ResourceProducer verwenden
    public void collectResources() {

        GameStyle.logBox("RESSOURCEN EINSAMMELN");

        int totalCollected = 0;

        // Die Schleife geht jedes Tier in der Farm durch
        for (Animal animal : animals) {

            // Prüfen, ob das Tier Ressourcen produzieren kann
            if (animal instanceof ResourceProducer) {

                // Das Tier wird als ResourceProducer behandelt,
                // damit collectResource() und getResourceName() benutzt werden können
                ResourceProducer producer = (ResourceProducer) animal;

                // Ressource einsammeln
                // Im Tier selbst wird der Ressourcenwert dabei auf 0 gesetzt
                int collectedAmount = producer.collectResource();

                // Name der Ressource holen
                String resourceName = producer.getResourceName();

                // Eingesammelte Ressource ins Lager legen
                if (resourceName.equals("Wolle")) {
                    storedWool = storedWool + collectedAmount;
                } else if (resourceName.equals("Milch")) {
                    storedMilk = storedMilk + collectedAmount;
                } else if (resourceName.equals("Eier")) {
                    storedEggs = storedEggs + collectedAmount;
                } else if (resourceName.equals("Federn")) {
                    storedFeathers = storedFeathers + collectedAmount;
                }

                totalCollected = totalCollected + collectedAmount;

                // Ausgabe pro Tier
                System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                        + animal.getName()
                        + " gibt "
                        + GameStyle.BRIGHT_YELLOW
                        + collectedAmount
                        + " "
                        + resourceName
                        + GameStyle.RESET
                        + ".");
            }
        }

        System.out.println();

        if (totalCollected == 0) {
            System.out.println(GameStyle.label("Ergebnis: ") + "Es gab nichts einzusammeln.");
            System.out.println("Die Tiere nennen das eine künstlerische Pause.");
        } else {
            System.out.println(GameStyle.label("Ergebnis: ")
                    + "Insgesamt wurden "
                    + GameStyle.BRIGHT_YELLOW
                    + totalCollected
                    + GameStyle.RESET
                    + " Ressourcen eingesammelt.");
        }

        System.out.println(GameStyle.label("Lager: ")
                + "Wolle "
                + storedWool
                + " | Milch "
                + storedMilk
                + " | Eier "
                + storedEggs
                + " | Federn "
                + storedFeathers);

        GameStyle.endBox();
    }

    // Gibt den aktuellen Inhalt des Ressourcenlagers und das Geld schön formatiert aus
    public void printStorage() {

        GameStyle.printStorageArt();

        System.out.println(GameStyle.BRIGHT_YELLOW + "╭─ RESSOURCENLAGER VON " + farmName.toUpperCase() + " ─────────────" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_YELLOW + "│ " + GameStyle.RESET + "🐑 Wolle:   " + storedWool);
        System.out.println(GameStyle.BRIGHT_YELLOW + "│ " + GameStyle.RESET + "🐄 Milch:   " + storedMilk);
        System.out.println(GameStyle.BRIGHT_YELLOW + "│ " + GameStyle.RESET + "🐔 Eier:    " + storedEggs);
        System.out.println(GameStyle.BRIGHT_YELLOW + "│ " + GameStyle.RESET + "🦆 Federn:  " + storedFeathers);
        System.out.println(GameStyle.BRIGHT_YELLOW + "├──────────────────────────────────────────────" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_YELLOW + "│ " + GameStyle.RESET + "💰 Geld:    " + money + " Münzen");
        System.out.println(GameStyle.BRIGHT_YELLOW + "╰──────────────────────────────────────────────" + GameStyle.RESET);
    }

    // Verkauft alle Ressourcen aus dem Lager
    public void sellResources() {

        GameStyle.logBox("RESSOURCEN VERKAUFEN");

        // Preise der einzelnen Ressourcen
        int woolPrice = 3;
        int milkPrice = 2;
        int eggPrice = 1;
        int featherPrice = 2;

        // Einzelwerte berechnen
        int woolValue = storedWool * woolPrice;
        int milkValue = storedMilk * milkPrice;
        int eggValue = storedEggs * eggPrice;
        int featherValue = storedFeathers * featherPrice;

        // Gesamtwert berechnen
        int earnedMoney = woolValue + milkValue + eggValue + featherValue;

        // Wenn keine Ressourcen vorhanden sind
        if (earnedMoney == 0) {
            GameStyle.warning("Du hast keine Ressourcen zum Verkaufen.");
            System.out.println("Der Marktstand ist leer. Sehr minimalistisch.");
            System.out.println("Ein Kunde fragt trotzdem nach Rabatt.");
            GameStyle.endBox();
            return;
        }

        System.out.println(GameStyle.label("Verkaufspreise:"));

        System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                + "Wolle:   "
                + GameStyle.BRIGHT_YELLOW
                + storedWool
                + GameStyle.RESET
                + " x "
                + woolPrice
                + " = "
                + GameStyle.BRIGHT_GREEN
                + woolValue
                + " Münzen"
                + GameStyle.RESET);

        System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                + "Milch:   "
                + GameStyle.BRIGHT_YELLOW
                + storedMilk
                + GameStyle.RESET
                + " x "
                + milkPrice
                + " = "
                + GameStyle.BRIGHT_GREEN
                + milkValue
                + " Münzen"
                + GameStyle.RESET);

        System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                + "Eier:    "
                + GameStyle.BRIGHT_YELLOW
                + storedEggs
                + GameStyle.RESET
                + " x "
                + eggPrice
                + " = "
                + GameStyle.BRIGHT_GREEN
                + eggValue
                + " Münzen"
                + GameStyle.RESET);

        System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                + "Federn:  "
                + GameStyle.BRIGHT_YELLOW
                + storedFeathers
                + GameStyle.RESET
                + " x "
                + featherPrice
                + " = "
                + GameStyle.BRIGHT_GREEN
                + featherValue
                + " Münzen"
                + GameStyle.RESET);

        System.out.println();

        // Geld zur Farm hinzufügen
        money = money + earnedMoney;

        // Ressourcenlager leeren
        storedWool = 0;
        storedMilk = 0;
        storedEggs = 0;
        storedFeathers = 0;

        System.out.println(GameStyle.label("Einnahmen: ")
                + GameStyle.BRIGHT_GREEN
                + earnedMoney
                + " Münzen"
                + GameStyle.RESET);

        System.out.println(GameStyle.label("Aktuelles Geld: ")
                + GameStyle.money(money));

        System.out.println();

        System.out.println("Der Marktstand war ein voller Erfolg.");
        System.out.println("Die Ente nennt es passives Einkommen.");

        GameStyle.endBox();
    }

    // Behandelt ein krankes Tier anhand seines Namens
    public void treatAnimalByName(String name) {

        GameStyle.logBox("TIERÄRZTIN");

        // Die Behandlung durch die Tierärztin kostet 30 Münzen
        int treatmentCost = 30;

        // Tier über den Namen suchen
        Animal animal = getAnimalByName(name);

        // Wenn kein Tier gefunden wurde, wird die Methode beendet
        if (animal == null) {
            GameStyle.warning("Die Tierärztin schaut auf die Liste und findet dieses Tier nicht.");
            System.out.println("Diagnose: Verwaltungsfehler mit leichtem Kontrollverlust.");
            GameStyle.endBox();
            return;
        }

        System.out.println(GameStyle.label("Patient: ")
                + animal.getName()
                + " "
                + getPersonalityIcon(animal)
                + " "
                + GameStyle.BRIGHT_PURPLE
                + animal.getPersonality()
                + GameStyle.RESET);

        System.out.println(GameStyle.label("Aktuelles Geld: ")
                + GameStyle.money(money));

        System.out.println();

        // Prüfen, ob das Tier überhaupt krank ist
        if (!animal.isSick()) {
            System.out.println(GameStyle.BRIGHT_GREEN + "✓ " + animal.getName() + " ist nicht krank." + GameStyle.RESET);
            System.out.println("Die Tierärztin setzt professionell das Stethoskop an.");
            System.out.println("Dann nickt sie ernst und sagt:");
            System.out.println("\"Dieses Tier ist medizinisch unauffällig, aber emotional auffällig.\"");
            System.out.println();
            System.out.println(GameStyle.label("Kosten: ") + "0 Münzen");
            GameStyle.endBox();
            return;
        }

        // Diagnose anzeigen
        System.out.println(GameStyle.label("Diagnose: ")
                + GameStyle.BRIGHT_RED
                + animal.getDiseaseName()
                + GameStyle.RESET);

        System.out.println(GameStyle.label("Krank seit: ")
                + animal.getSickRounds()
                + " Runden");

        System.out.println(GameStyle.label("Behandlungskosten: ")
                + treatmentCost
                + " Münzen");

        System.out.println();

        // Prüfen, ob genug Geld für die Behandlung vorhanden ist
        if (money < treatmentCost) {
            GameStyle.warning("Du hast nicht genug Geld für die Behandlung.");
            System.out.println("Die Tierärztin schaut dich an wie ein offener Mahnbescheid.");
            System.out.println();
            System.out.println(GameStyle.label("Benötigt: ") + treatmentCost + " Münzen");
            System.out.println(GameStyle.label("Vorhanden: ") + GameStyle.money(money));
            System.out.println();
            System.out.println("Das Tier bleibt krank.");
            GameStyle.endBox();
            return;
        }

        // Geld für die Behandlung abziehen
        money = money - treatmentCost;

        // Tier heilen
        animal.heal();

        System.out.println(GameStyle.BRIGHT_GREEN + "✓ Behandlung erfolgreich." + GameStyle.RESET);
        System.out.println(getTreatmentMessage(animal));

        System.out.println();

        System.out.println(GameStyle.label("Kosten: ")
                + treatmentCost
                + " Münzen");

        System.out.println(GameStyle.label("Aktuelles Geld: ")
                + GameStyle.money(money));

        GameStyle.endBox();
    }

    // Mit dieser Methode kann zufällig ein Tier krank werden
    public void randomDisease() {

        // Wenn es keine Tiere gibt, kann auch kein Tier krank werden
        if (animals.isEmpty()) {
            return;
        }

        // Zufallszahl von 1 bis 10 erzeugen
        int randomNumber = (int) (Math.random() * 10) + 1;

        // Nur bei 1 oder 2 passiert etwas
        // Das entspricht ungefähr 20 Prozent Chance
        if (randomNumber <= 2) {

            // Zufällige Position in der Tierliste auswählen
            int randomIndex = (int) (Math.random() * animals.size());

            // Tier an dieser Position aus der Liste holen
            Animal randomAnimal = animals.get(randomIndex);

            // Nur gesunde Tiere sollen neu krank werden
            if (!randomAnimal.isSick()) {

                // Schüchterne Tiere können sich manchmal verstecken
                if (randomAnimal.getPersonality().equals("schüchtern")) {

                    int hideChance = (int) (Math.random() * 2) + 1;

                    if (hideChance == 1) {
                        System.out.println(randomAnimal.getName() + " hat sich rechtzeitig hinter einem Heuballen versteckt und ist nicht krank geworden.");
                        return;
                    }
                }

                // Tier krank machen
                randomAnimal.makeSick();

                // Ausgabe für den User
                System.out.println(getDiseaseMessage(randomAnimal));
            }
        }
    }

    // Prüft, ob kranke Tiere andere Tiere anstecken
    public void spreadDisease() {

        // Wenn weniger als 2 Tiere auf der Farm sind,
        // kann kein anderes Tier angesteckt werden
        if (animals.size() < 2) {
            return;
        }

        // Die Schleife geht alle Tiere durch
        for (Animal sickAnimal : animals) {

            // Nur kranke Tiere, die schon mindestens 3 Runden krank sind,
            // können andere Tiere anstecken
            if (sickAnimal.isSick() && sickAnimal.getSickRounds() >= 3) {

                // Zufällige Position in der Tierliste auswählen
                int randomIndex = (int) (Math.random() * animals.size());

                // Tier an dieser zufälligen Position holen
                Animal otherAnimal = animals.get(randomIndex);

                // Das Tier darf sich nicht selbst anstecken
                // und es soll nur ein gesundes Tier angesteckt werden
                if (otherAnimal != sickAnimal && !otherAnimal.isSick()) {

                    // Schüchterne Tiere können sich manchmal vor Ansteckung drücken
                    if (otherAnimal.getPersonality().equals("schüchtern")) {

                        int hideChance = (int) (Math.random() * 2) + 1;

                        if (hideChance == 1) {
                            System.out.println(otherAnimal.getName() + " hat sozialen Kontakt vermieden und wurde nicht angesteckt.");
                            return;
                        }
                    }

                    // Tier krank machen
                    otherAnimal.makeSick();

                    // Ausgabe für den User
                    System.out.println(sickAnimal.getName() + " hat " + otherAnimal.getName() + " angesteckt.");
                    System.out.println(getDiseaseMessage(otherAnimal));
                }
            }
        }
    }

    // Löst manchmal ein zufälliges Farm-Event aus
    public void randomFarmEvent(Scanner scanner) {

        // Wenn es keine Tiere gibt, kann kein Tier-Event passieren
        if (animals.isEmpty()) {
            return;
        }

        // Zufallszahl von 1 bis 10
        int eventChance = (int) (Math.random() * 10) + 1;

        // Nur bei 1, 2 oder 3 passiert ein Event
        // Das entspricht ungefähr 30 Prozent Chance pro Runde
        if (eventChance > 3) {
            return;
        }

        // Zufälliges Event von 1 bis 8 auswählen
        int eventNumber = (int) (Math.random() * 8) + 1;

        // Zufälliges Tier aus der Liste auswählen
        int randomIndex = (int) (Math.random() * animals.size());
        Animal randomAnimal = animals.get(randomIndex);

        // Event 1: Das Tier bekommt weniger Hunger
        if (eventNumber == 1) {

            randomAnimal.setHunger(randomAnimal.getHunger() - 10);

            // Hunger soll nicht unter 0 fallen
            if (randomAnimal.getHunger() < 0) {
                randomAnimal.setHunger(0);
            }

            System.out.println(randomAnimal.getName() + " hat heimlich Snacks gefunden. Hunger -10.");

            // Event 2: Das Tier bekommt mehr Hunger
        } else if (eventNumber == 2) {

            randomAnimal.setHunger(randomAnimal.getHunger() + 10);

            System.out.println(randomAnimal.getName() + " hatte einen dramatischen Tag. Hunger +10.");

            // Event 3: Alle Tiere werden ein bisschen älter
        } else if (eventNumber == 3) {

            increaseAge();

            System.out.println("Die Zeit ist heute irgendwie schneller vergangen. Alle Tiere sind älter geworden.");

            // Event 4: Die Farm bekommt etwas Geld geschenkt
        } else if (eventNumber == 4) {

            money = money + 15;

            System.out.println("Ein Tourist hat deine Farm süß gefunden und 15 Münzen gespendet.");

            // Event 5: Die Farm verliert etwas Geld
        } else if (eventNumber == 5) {

            money = money - 10;

            // Geld soll nicht unter 0 fallen
            if (money < 0) {
                money = 0;
            }

            System.out.println("Die Tierärztin hat eine Rechnung aus 2023 gefunden. Geld -10.");

            // Event 6: Ressourcen-Bonus je nach Tierart
        } else if (eventNumber == 6) {

            // Wenn das zufällige Tier ein Schaf ist
            if (randomAnimal instanceof Sheep) {
                Sheep sheep = (Sheep) randomAnimal;
                sheep.setWool(sheep.getWool() + 4);

                System.out.println(sheep.getName() + " hatte einen Flausch-Boost. Wolle +4.");

                // Wenn das zufällige Tier eine Kuh ist
            } else if (randomAnimal instanceof Cow) {
                Cow cow = (Cow) randomAnimal;
                cow.setMilk(cow.getMilk() + 3);

                System.out.println(cow.getName() + " war heute extra motiviert. Milch +3.");

                // Wenn das zufällige Tier ein Huhn ist
            } else if (randomAnimal instanceof Chicken) {
                Chicken chicken = (Chicken) randomAnimal;
                chicken.setEggs(chicken.getEggs() + 3);

                System.out.println(chicken.getName() + " legt aus Trotz Extra-Eier. Eier +3.");

                // Wenn das zufällige Tier eine Ente ist
            } else if (randomAnimal instanceof Duck) {
                Duck duck = (Duck) randomAnimal;
                duck.setFeathers(duck.getFeathers() + 2);

                System.out.println(duck.getName() + " hat dramatisch im Regen posiert. Federn +2.");
            }

            // Event 7: Die Tiere gründen eine Gewerkschaft
        } else if (eventNumber == 7) {

            System.out.println("Donald hat eine Gewerkschaft gegründet.");
            System.out.println("Alle Tiere fordern bessere Snacks. Hunger +5 bei allen.");

            // Alle Tiere werden ein bisschen hungriger,
            // weil sie jetzt bessere Arbeitsbedingungen und Snacks fordern
            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() + 5);
            }

            // Event 8: Großes Spezialevent
        } else if (eventNumber == 8) {

            farmCourtEvent(scanner);
        }
    }

    // Großes Spezialevent: Die Tiere halten ein Farmgericht ab
    public void farmCourtEvent(Scanner scanner) {

        // Wenn es keine Tiere gibt, kann kein Gericht stattfinden
        if (animals.isEmpty()) {
            return;
        }

        // Zufälliges Tier als Ankläger auswählen
        int randomIndex = (int) (Math.random() * animals.size());
        Animal accuser = animals.get(randomIndex);

        GameStyle.printCourtArt();

        System.out.println(GameStyle.BRIGHT_PURPLE + "╔════════════════════════════════════════════════════════════╗" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_PURPLE + "║                    SPEZIALEVENT                          ║" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_PURPLE + "╚════════════════════════════════════════════════════════════╝" + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_RED + "⚖️  DAS FARMGERICHT TAGT." + GameStyle.RESET);
        System.out.println(accuser.getName() + " erhebt schwere Vorwürfe gegen die Farmleitung.");
        System.out.println();
        System.out.println("Anklagepunkte:");
        System.out.println("- emotional fragwürdige Fütterungszeiten");
        System.out.println("- zu wenig Premium-Heu");
        System.out.println("- mindestens ein Tier fühlt sich nicht ausreichend gesehen");
        System.out.println();
        System.out.println("Wie möchtest du dich verteidigen?");
        System.out.println("1 - Die Tiere mit Snacks bestechen");
        System.out.println("2 - Eine dramatische Verteidigungsrede halten");
        System.out.println("3 - Einen Sündenbock benennen");
        System.out.print("Auswahl: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {

            // Bestechung kostet Geld
            int bribeCost = 20;

            if (money < bribeCost) {
                System.out.println("Du möchtest die Tiere bestechen, hast aber nicht genug Geld.");
                System.out.println("Das Gericht wertet das als peinlich. Alle Tiere bekommen Hunger +10.");

                for (Animal animal : animals) {
                    animal.setHunger(animal.getHunger() + 10);
                }

                return;
            }

            money = money - bribeCost;

            System.out.println("Du verteilst Snacks im Gerichtssaal.");
            System.out.println("Die Moral ist fragwürdig, aber effektiv.");
            System.out.println("Geld -" + bribeCost);
            System.out.println("Alle Tiere Hunger -10.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() - 10);

                if (animal.getHunger() < 0) {
                    animal.setHunger(0);
                }
            }

        } else if (choice.equals("2")) {

            // Dramatische Rede mit Zufallsausgang
            int speechChance = (int) (Math.random() * 2) + 1;

            System.out.println("Du trittst nach vorne und hältst eine Rede,");
            System.out.println("die irgendwo zwischen Shakespeare und Schadensbegrenzung liegt.");

            if (speechChance == 1) {
                System.out.println("Die Tiere sind bewegt.");
                System.out.println(accuser.getName() + " wischt sich eine Träne aus dem Fell.");
                System.out.println("Ein Sponsor sieht das Drama und spendet 25 Münzen.");

                money = money + 25;

            } else {
                System.out.println("Die Rede kommt nicht gut an.");
                System.out.println("Ein Huhn ruft: 'Objection!' obwohl niemand weiß, woher es das Wort kennt.");
                System.out.println("Alle Tiere Hunger +10.");

                for (Animal animal : animals) {
                    animal.setHunger(animal.getHunger() + 10);
                }
            }

        } else if (choice.equals("3")) {

            // Sündenbock-Variante
            if (animals.size() < 2) {
                System.out.println("Du möchtest einen Sündenbock benennen, aber es gibt nur ein Tier.");
                System.out.println("Das ist unangenehm offensichtlich.");
                accuser.setPersonality("dramatisch");
                return;
            }

            Animal scapegoat = accuser;

            // Solange zufällig neu wählen, bis es nicht der Ankläger selbst ist
            while (scapegoat == accuser) {
                int scapegoatIndex = (int) (Math.random() * animals.size());
                scapegoat = animals.get(scapegoatIndex);
            }

            System.out.println("Du benennst " + scapegoat.getName() + " als Sündenbock.");
            System.out.println("Niemand versteht den Zusammenhang, aber alle nicken ernst.");
            System.out.println(scapegoat.getName() + " wird ab jetzt dramatisch.");
            System.out.println("Alle anderen Tiere Hunger -5.");

            scapegoat.setPersonality("dramatisch");

            for (Animal animal : animals) {
                if (animal != scapegoat) {
                    animal.setHunger(animal.getHunger() - 5);

                    if (animal.getHunger() < 0) {
                        animal.setHunger(0);
                    }
                }
            }

        } else {
            System.out.println("Du schweigst.");
            System.out.println("Das Gericht interpretiert das als Schuldeingeständnis.");
            System.out.println("Alle Tiere Hunger +15.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() + 15);
            }
        }

        System.out.println("Das Farmgericht wird vertagt.");
        System.out.println("Aktuelles Geld: " + money + " Münzen");
    }

    // Gibt manchmal einen lustigen Kommentar zu einem zufälligen Tier aus
    public void randomAnimalComment() {

        // Wenn es keine Tiere gibt, kann auch kein Kommentar ausgegeben werden
        if (animals.isEmpty()) {
            return;
        }

        // Zufallszahl von 1 bis 10 erzeugen
        int commentChance = (int) (Math.random() * 10) + 1;

        // Nur bei 1, 2 oder 3 kommt ein Kommentar
        // Das entspricht ungefähr 30 Prozent Chance pro Runde
        if (commentChance > 3) {
            return;
        }

        // Zufälliges Tier aus der Liste auswählen
        int randomIndex = (int) (Math.random() * animals.size());
        Animal randomAnimal = animals.get(randomIndex);

        // Zufälligen Kommentar auswählen
        int commentNumber = (int) (Math.random() * 8) + 1;

        if (commentNumber == 1) {
            System.out.println(randomAnimal.getName() + " schaut dich an wie ein unbezahlter Praktikant.");
        } else if (commentNumber == 2) {
            System.out.println(randomAnimal.getName() + " hat Hauptcharakter-Energie und keinerlei Qualifikation.");
        } else if (commentNumber == 3) {
            System.out.println(randomAnimal.getName() + " fordert ein Meeting. Niemand weiß worüber.");
        } else if (commentNumber == 4) {
            System.out.println(randomAnimal.getName() + " starrt dramatisch in die Ferne. Es regnet nicht mal.");
        } else if (commentNumber == 5) {
            System.out.println(randomAnimal.getName() + " war kurz produktiv. Die Gerüchte konnten nicht bestätigt werden.");
        } else if (commentNumber == 6) {
            System.out.println(randomAnimal.getName() + " plant etwas mit Excel. Das kann nicht gut sein.");
        } else if (commentNumber == 7) {
            System.out.println(randomAnimal.getName() + " macht Geräusche, die nach Beschwerde-Mail klingen.");
        } else {
            System.out.println(randomAnimal.getName() + " existiert heute im Energiesparmodus.");
        }
    }

    // Entfernt Tiere, die verhungert sind oder zu lange krank waren
    public void removeDeadAnimals() {

        // Wir gehen rückwärts durch die Liste.
        // Das ist sicherer, wenn wir Elemente aus einer Liste entfernen.
        for (int i = animals.size() - 1; i >= 0; i--) {

            // Tier an der aktuellen Position holen
            Animal animal = animals.get(i);

            // Prüfen, ob das Tier verhungert ist
            if (animal.getHunger() > 100) {

                System.out.println(animal.getName() + " ist verhungert und wurde von der Farm entfernt.");
                System.out.println("Die Farm hält eine sehr kurze, aber emotionale Gedenkminute.");

                // Tier aus der Liste entfernen
                animals.remove(i);

                // Prüfen, ob das Tier zu lange krank war
            } else if (animal.isSick() && animal.getSickRounds() >= 5) {

                System.out.println(animal.getName() + " war zu lange krank und wurde von der Farm entfernt.");
                System.out.println("Die Tierärztin sagt: 'Das hätte man früher googeln sollen.'");

                // Tier aus der Liste entfernen
                animals.remove(i);
            }
        }
    }

    // Prüft, ob die Farm keine Tiere mehr hat
    public boolean hasNoAnimals() {

        // Wenn die Liste leer ist, gibt es keine Tiere mehr
        return animals.isEmpty();
    }
}