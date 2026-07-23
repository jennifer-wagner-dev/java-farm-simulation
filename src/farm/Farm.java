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
    private final List<Animal> animals;

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

    // Gibt das aktuelle Geld der Farm zurück
    public int getMoney() {
        return money;
    }

    // Fügt ein neues Tier zur Farm hinzu
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    // Gibt alle Tiere auf der Farm aus
    public void printAnimals() {

        if (animals.isEmpty()) {
            GameStyle.logBox("TIERREGISTER");
            GameStyle.warning("Das Tierregister ist leer.");
            System.out.println("Die Farmverwaltung schaut betreten auf die leere Akte.");
            System.out.println("Niemand muht. Niemand quakt. Es ist unangenehm ruhig.");
            GameStyle.endBox();
            return;
        }

        GameStyle.logBox("TIERREGISTER");

        System.out.println("Die Farmverwaltung öffnet das Tierregister.");
        System.out.println("Alle Tiere wurden amtlich erfasst. Widerstand war zwecklos.");
        System.out.println();

        for (Animal animal : animals) {

            String animalSymbol = "❓";
            String animalType = "Unbekanntes Wesen";
            String resourceText = "";

            if (animal instanceof Sheep sheep) {
                animalSymbol = "🐑";
                animalType = "Schaf";
                resourceText = "Wolle: " + sheep.getWool();

            } else if (animal instanceof Cow cow) {
                animalSymbol = "🐄";
                animalType = "Kuh";
                resourceText = "Milch: " + cow.getMilk();

            } else if (animal instanceof Chicken chicken) {
                animalSymbol = "🐔";
                animalType = "Huhn";
                resourceText = "Eier: " + chicken.getEggs();

            } else if (animal instanceof Duck duck) {
                animalSymbol = "🦆";
                animalType = "Ente";
                resourceText = "Federn: " + duck.getFeathers();
            }

            System.out.println(GameStyle.BRIGHT_PURPLE
                    + "├─ AKTE: "
                    + animalSymbol
                    + " "
                    + animal.getName()
                    + GameStyle.RESET);

            System.out.println(GameStyle.label("│ Tierart: ")
                    + animalType);

            System.out.println(GameStyle.label("│ Alter: ")
                    + animal.getAge()
                    + " Jahre");

            System.out.println(GameStyle.label("│ Hunger: ")
                    + GameStyle.getHungerBar(animal.getHunger()));

            System.out.println(GameStyle.label("│ Persönlichkeit: ")
                    + getPersonalityIcon(animal)
                    + " "
                    + GameStyle.BRIGHT_PURPLE
                    + animal.getPersonality()
                    + GameStyle.RESET);

            if (animal.isSick()) {
                System.out.println(GameStyle.label("│ Gesundheitsstatus: ")
                        + GameStyle.BRIGHT_RED
                        + "krank"
                        + GameStyle.RESET);

                System.out.println(GameStyle.label("│ Diagnose: ")
                        + GameStyle.BRIGHT_RED
                        + animal.getDiseaseName()
                        + GameStyle.RESET);

                System.out.println(GameStyle.label("│ Krank seit: ")
                        + animal.getSickRounds()
                        + " Runden");

            } else {
                System.out.println(GameStyle.label("│ Gesundheitsstatus: ")
                        + GameStyle.BRIGHT_GREEN
                        + "gesund"
                        + GameStyle.RESET);
            }

            System.out.println(GameStyle.label("│ Ressource: ")
                    + GameStyle.BRIGHT_YELLOW
                    + resourceText
                    + GameStyle.RESET);

            System.out.println(GameStyle.label("│ Aktennotiz: ")
                    + getAnimalFileNote(animal));

            System.out.println();
        }

        System.out.println("Das Tierregister wurde geschlossen.");
        System.out.println("Mehr Ordnung wäre möglich, aber unrealistisch.");

        GameStyle.endBox();
    }

    // Gibt eine kleine Aktennotiz passend zur Persönlichkeit zurück
    public String getAnimalFileNote(Animal animal) {

        String personality = animal.getPersonality();

        switch (personality) {
            case "dramatisch":
                return "🎭";

            case "gierig":
                return "💸";

            case "schüchtern":
                return "🫣";

            case "chaotisch":
                return "🌀";

            case "verwöhnt":
                return "👑";

            default:
                return "❓";
        }
    }

    // Sucht ein Tier anhand seines Namens
    public Animal getAnimalByName(String name) {

        // Alle Tiere in der Liste durchgehen
        for (Animal animal : animals) {

            // Namen vergleichen
            // equalsIgnoreCase bedeutet: Groß- und Kleinschreibung ist egal
            if (animal.getName().equalsIgnoreCase(name.trim())) {
                return animal;
            }
        }

        // Wenn kein Tier gefunden wurde
        GameStyle.warning("Dieses Tier existiert laut Farmverwaltung nicht.");
        System.out.println("Die Akte wurde nicht gefunden.");
        System.out.println("Das ist entweder ein Eingabefehler oder ein sehr gutes Versteck.");

        return null;
    }

    // Prüft, ob ein Tiername bereits existiert
    public boolean animalNameExists(String name) {

        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }

        return false;
    }

    // Prüft, ob ein Tier zu einer bestimmten Tierart gehört
    public boolean matchesAnimalType(Animal animal, String animalType) {

        switch (animalType) {
            case "schaf":
                return animal instanceof Sheep;

            case "kuh":
                return animal instanceof Cow;

            case "huhn":
                return animal instanceof Chicken;

            case "ente":
                return animal instanceof Duck;

            default:
                return false;
        }
    }

    // Füttert ein einzelnes Tier
    public boolean feedAnimal(Animal animal) {

        GameStyle.logBox("VERSORGUNGSMASSNAHME");

        int feedCost = getFeedCost(animal);

        System.out.println(GameStyle.label("Tier: ")
                + animal.getName()
                + " "
                + getPersonalityIcon(animal)
                + " "
                + GameStyle.BRIGHT_PURPLE
                + animal.getPersonality()
                + GameStyle.RESET);

        System.out.println(GameStyle.label("Futterkosten: ")
                + feedCost
                + " Münzen");

        System.out.println(GameStyle.label("Budgetstatus: ")
                + GameStyle.money(money));

        System.out.println();

        if (money < feedCost) {
            GameStyle.warning("Die Versorgung wurde vom Budget abgelehnt.");
            System.out.println("Das Tier schaut dich an wie eine unbezahlte Rechnung.");
            System.out.println("Die Farmverwaltung empfiehlt: weniger Armut, mehr Münzen.");
            GameStyle.endBox();
            return false;
        }

        int hungerBefore = animal.getHunger();

        money = money - feedCost;
        animal.feed();

        String personalityEffect = applyPersonalityAfterFeeding(animal);

        System.out.println(GameStyle.BRIGHT_GREEN + "✓ Versorgung durchgeführt." + GameStyle.RESET);
        System.out.println(GameStyle.label("Hunger vorher: ")
                + GameStyle.getHungerBar(hungerBefore));
        System.out.println(GameStyle.label("Hunger nachher:  ")
                + GameStyle.getHungerBar(animal.getHunger()));
        System.out.println(GameStyle.label("Budgetstatus: ")
                + GameStyle.money(money));

        if (!personalityEffect.isBlank()) {
            System.out.println();
            System.out.println(personalityEffect);
        }

        GameStyle.endBox();

        return true;
    }

    // Gibt ein Icon passend zur Persönlichkeit zurück
    public String getPersonalityIcon(Animal animal) {

        String personality = animal.getPersonality();

        switch (personality) {
            case "dramatisch":
                return "🎭";

            case "gierig":
                return "💸";

            case "schüchtern":
                return "🫣";

            case "chaotisch":
                return "🌀";

            case "verwöhnt":
                return "👑";

            default:
                return "❓";
        }
    }

    // Gibt zurück, wie viel das Füttern kostet
    public int getFeedCost(Animal animal) {

        int feedCost = 6;

        if (animal instanceof Cow) {
            feedCost = 8;

        } else if (animal instanceof Sheep) {
            feedCost = 7;

        } else if (animal instanceof Chicken) {
            feedCost = 4;

        } else if (animal instanceof Duck) {
            feedCost = 5;
        }

        if (animal.getPersonality().equals("verwöhnt")) {
            feedCost = feedCost + 3;
        }

        return feedCost;
    }

    // Erhöht den Hunger aller Tiere
    public void hungerAllAnimals() {

        for (Animal animal : animals) {
            animal.hunger();
        }
    }

    // Erhöht den Hunger aller Tiere außer der gefütterten Tierart
    public void hungerExceptType(String animalType) {

        for (Animal animal : animals) {

            boolean isFedType =
                    (animalType.equals("schaf") && animal instanceof Sheep)
                            || (animalType.equals("kuh") && animal instanceof Cow)
                            || (animalType.equals("huhn") && animal instanceof Chicken)
                            || (animalType.equals("ente") && animal instanceof Duck);

            if (!isFedType) {
                animal.hunger();
            }
        }
    }

    // Persönlichkeit beeinflusst manchmal das Verhalten nach dem Füttern
    public String applyPersonalityAfterFeeding(Animal animal) {

        String personality = animal.getPersonality();

        if (personality.equals("gierig")) {
            int randomNumber = (int) (Math.random() * 2) + 1;

            if (randomNumber == 1) {
                animal.setHunger(animal.getHunger() + 5);

                return animal.getName()
                        + " ist gierig und meldet direkt Nachschlag an.\n"
                        + "Die Farmverwaltung nimmt das unter Protest zur Kenntnis.";
            }

        } else if (personality.equals("verwöhnt")) {
            int randomNumber = (int) (Math.random() * 2) + 1;

            if (randomNumber == 1) {
                animal.setHunger(animal.getHunger() + 5);

                return animal.getName()
                        + " findet das Futter mittelmäßig.\n"
                        + "Eine Bewertung mit zwei Sternen wurde angedroht.";
            }

        } else if (personality.equals("dramatisch")) {
            int randomNumber = (int) (Math.random() * 2) + 1;

            if (randomNumber == 1) {
                return animal.getName()
                        + " frisst, als wäre es die letzte Szene einer Netflix-Serie.";
            }

        } else if (personality.equals("chaotisch")) {
            int randomNumber = (int) (Math.random() * 2) + 1;

            if (randomNumber == 1) {
                animal.setHunger(animal.getHunger() - 5);

                if (animal.getHunger() < 0) {
                    animal.setHunger(0);
                }

                return animal.getName()
                        + " hat aus Versehen effizient gegessen.\n"
                        + "Niemand weiß, wie das passieren konnte.";
            }

        } else if (personality.equals("schüchtern")) {
            int randomNumber = (int) (Math.random() * 2) + 1;

            if (randomNumber == 1) {
                return animal.getName()
                        + " frisst leise in der Ecke und möchte nicht darüber sprechen.";
            }
        }

        return "";
    }

    // Gibt einen passenden Text zur Krankheit zurück
    public String getDiseaseMessage(Animal animal) {

        String diseaseName = animal.getDiseaseName();

        if (diseaseName.equals("Männerschnupfen")) {
            return animal.getName()
                    + " leidet sichtbar und möchte dafür bitte eine Bühne.\n"
                    + "Die Symptome sind laut, aber medizinisch schwer beleidigt.";

        } else if (diseaseName.equals("akute Dramaitis")) {
            return animal.getName()
                    + " hat akute Dramaitis.\n"
                    + "Jede Bewegung wird als emotionaler Wendepunkt inszeniert.";

        } else if (diseaseName.equals("Flauschfieber")) {
            return animal.getName()
                    + " hat Flauschfieber.\n"
                    + "Die Felllage ist weich, aber organisatorisch bedenklich.";

        } else if (diseaseName.equals("Milch-Melancholie")) {
            return animal.getName()
                    + " wurde von Milch-Melancholie erfasst.\n"
                    + "Die Stimmung ist pasteurisiert, aber instabil.";

        } else if (diseaseName.equals("Eier-Existenzkrise")) {
            return animal.getName()
                    + " befindet sich in einer Eier-Existenzkrise.\n"
                    + "Die Frage \"Was war zuerst?\" wurde offiziell untersagt.";

        } else if (diseaseName.equals("quakende Quarantäne")) {
            return animal.getName()
                    + " wurde in quakende Quarantäne eingestuft.\n"
                    + "Das Geräuschlevel ist meldepflichtig.";

        } else {
            return animal.getName()
                    + " hat eine unbekannte Erkrankung.\n"
                    + "Die Farmverwaltung nennt es vorsichtig \"irgendwas mit Tier\".";
        }
    }

    // Gibt einen kurzen Behandlungstext zurück
    public String getTreatmentMessage(Animal animal) {

        String diseaseName = animal.getDiseaseName();

        switch (diseaseName) {
            case "Männerschnupfen":
                return animal.getName() + " bekommt Tee, Decke und maximale Aufmerksamkeit.";

            case "akute Dramaitis":
                return animal.getName() + " bekommt eine Notfallration Realität.";

            case "Flauschfieber":
                return animal.getName() + " wird durchgekämmt. Die Flauschlage ist stabil.";

            case "Milch-Melancholie":
                return animal.getName() + " bekommt Zuspruch und Kalzium.";

            case "Eier-Existenzkrise":
                return animal.getName() + " bekommt eine kurze Identitätsberatung.";

            case "quakende Quarantäne":
                return animal.getName() + " wird aus der Quarantäne entlassen. Leider hörbar.";

            default:
                return animal.getName() + " bekommt eine Standardbehandlung nach Farmprotokoll 404.";
        }
    }

    // Füttert alle Tiere einer bestimmten Tierart
    public void feedAnimalsByType(String animalType) {

        GameStyle.logBox("GRUPPENVERSORGUNG");

        int totalCost = 0;
        int fedAnimals = 0;

        // Erst berechnen, wie teuer die Gruppenfütterung wäre
        for (Animal animal : animals) {

            boolean matchesType = matchesAnimalType(animal, animalType);

            if (matchesType) {
                totalCost = totalCost + getFeedCost(animal);
                fedAnimals = fedAnimals + 1;
            }
        }

        if (fedAnimals == 0) {
            GameStyle.warning("Für diese Tierart liegt keine Akte vor.");
            System.out.println("Die Farmverwaltung konnte niemanden zustellen.");
            GameStyle.endBox();
            return;
        }

        System.out.println(GameStyle.label("Tierart: ")
                + animalType);

        System.out.println(GameStyle.label("Betroffene Tiere: ")
                + fedAnimals);

        System.out.println(GameStyle.label("Gesamtkosten: ")
                + totalCost
                + " Münzen");

        System.out.println(GameStyle.label("Budgetstatus: ")
                + GameStyle.money(money));

        System.out.println();

        if (money < totalCost) {
            GameStyle.warning("Die Gruppenversorgung wurde vom Budgetausschuss blockiert.");
            System.out.println("Die Tiere gründen spontan eine sehr passive Gewerkschaft.");
            System.out.println("Forderung: Futter. Irgendwann. Vielleicht.");
            GameStyle.endBox();
            return;
        }

        // Geld einmalig abziehen
        money = money - totalCost;

        System.out.println(GameStyle.BRIGHT_GREEN + "✓ Gruppenversorgung genehmigt." + GameStyle.RESET);
        System.out.println();

        // Danach alle passenden Tiere füttern
        for (Animal animal : animals) {

            boolean matchesType = matchesAnimalType(animal, animalType);

            if (matchesType) {
                int hungerBefore = animal.getHunger();

                animal.feed();

                String personalityEffect = applyPersonalityAfterFeeding(animal);

                System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                        + animal.getName()
                        + ": "
                        + GameStyle.getHungerBar(hungerBefore)
                        + " → "
                        + GameStyle.getHungerBar(animal.getHunger()));

                if (!personalityEffect.isBlank()) {
                    System.out.println("  " + personalityEffect.replace("\n", "\n  "));
                }
            }
        }

        System.out.println();
        System.out.println(GameStyle.label("Budgetstatus nach Maßnahme: ")
                + GameStyle.money(money));

        System.out.println("Die Maßnahme wurde dokumentiert. Niemand liest die Dokumentation.");

        GameStyle.endBox();
    }

    // Erhöht den Hunger aller Tiere.
    // Wenn ein Tier gefüttert wurde, wird genau dieses Tier nicht erhöht.
    public void hunger(Animal fedAnimal, boolean animalWasFed) {

        for (Animal animal : animals) {

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

        for (Animal animal : animals) {

            animal.setAge(animal.getAge() + 1);
        }
    }

    // Erhöht bei allen kranken Tieren die Krankheitsdauer
    public void increaseSickRounds() {

        for (Animal animal : animals) {

            // Animal prüft selbst, ob es krank ist
            animal.increaseSickRounds();
        }
    }

    // Sammelt die Ressourcen von allen Tieren ein die Interface ResourceProducer verwenden
    public void collectResources() {

        GameStyle.logBox("RESSOURCENPRÜFUNG");

        int totalCollected = 0;

        System.out.println("Tierische Sachleistungen werden eingesammelt.");
        System.out.println();


        for (Animal animal : animals) {
            if (animal instanceof ResourceProducer producer) {
                int collectedAmount = producer.collectResource();
                String resourceName = producer.getResourceName();
                switch (resourceName) {
                    case "Wolle":
                        storedWool = storedWool + collectedAmount;
                        break;

                    case "Milch":
                        storedMilk = storedMilk + collectedAmount;
                        break;

                    case "Eier":
                        storedEggs = storedEggs + collectedAmount;
                        break;

                    case "Federn":
                        storedFeathers = storedFeathers + collectedAmount;
                        break;

                    default:
                        GameStyle.warning("Unbekannte Ressource: " + resourceName);
                        break;
                }
                totalCollected = totalCollected + collectedAmount;


                System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                        + animal.getName()
                        + ": "
                        + GameStyle.BRIGHT_YELLOW
                        + collectedAmount
                        + " "
                        + resourceName
                        + GameStyle.RESET);
            }
        }

        System.out.println();

        if (totalCollected == 0) {
            System.out.println(GameStyle.label("Ergebnis: ")
                    + "Nichts eingesammelt. Das Lager bleibt traurig.");
        } else {
            System.out.println(GameStyle.label("Ergebnis: ")
                    + totalCollected
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

    // Gibt das Ressourcenlager aus
    public void printStorage() {

        GameStyle.logBox("RESSOURCENLAGER");

        GameStyle.printStorageArt();

        System.out.println("Die Farmverwaltung öffnet das Lager.");
        System.out.println("Es riecht nach Heu, Hoffnung und ungeklärter Buchhaltung.");
        System.out.println();
        System.out.println(GameStyle.label("Amtlich erfasster Bestand:"));
        System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                + "Wolle:   "
                + GameStyle.BRIGHT_YELLOW
                + storedWool
                + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                + "Milch:   "
                + GameStyle.BRIGHT_YELLOW
                + storedMilk
                + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                + "Eier:    "
                + GameStyle.BRIGHT_YELLOW
                + storedEggs
                + GameStyle.RESET);
        System.out.println(GameStyle.BRIGHT_GREEN + "• " + GameStyle.RESET
                + "Federn:  "
                + GameStyle.BRIGHT_YELLOW
                + storedFeathers
                + GameStyle.RESET);
        System.out.println();

        int totalResources = storedWool + storedMilk + storedEggs + storedFeathers;

        if (totalResources == 0) {
            System.out.println(GameStyle.label("Lagebericht: ")
                    + "Das Lager ist leer.");

            System.out.println("Die Buchhaltung nennt das übersichtlich.");
            System.out.println("Die Tiere nennen es enttäuschend.");

        } else if (totalResources < 20) {
            System.out.println(GameStyle.label("Lagebericht: ")
                    + "Der Bestand ist vorhanden, aber nicht beeindruckend.");

            System.out.println("Die Farmverwaltung spricht trotzdem von Wachstum.");

        } else if (totalResources < 50) {
            System.out.println(GameStyle.label("Lagebericht: ")
                    + "Der Bestand wirkt überraschend stabil.");

            System.out.println("Niemand weiß, wie es so weit kommen konnte.");

        } else {
            System.out.println(GameStyle.label("Lagebericht: ")
                    + "Das Lager ist gut gefüllt.");

            System.out.println("Die Ente spricht bereits von Expansion.");
            System.out.println("Das ist beunruhigend.");
        }

        GameStyle.endBox();
    }

    // Verkauft alle Ressourcen aus dem Lager
    public void sellResources() {

        GameStyle.logBox("MARKTSTAND");

        int woolPrice = 2;
        int milkPrice = 2;
        int eggPrice = 1;
        int featherPrice = 1;

        int woolValue = storedWool * woolPrice;
        int milkValue = storedMilk * milkPrice;
        int eggValue = storedEggs * eggPrice;
        int featherValue = storedFeathers * featherPrice;

        int earnedMoney = woolValue + milkValue + eggValue + featherValue;

        if (earnedMoney == 0) {
            GameStyle.warning("Keine Ressourcen zum Verkaufen.");
            System.out.println("Der Marktstand steht trotzdem. Aus Prinzip.");
            GameStyle.endBox();
            return;
        }

        System.out.println(GameStyle.label("Verkauf:"));
        System.out.println("• Wolle:   " + storedWool + " x " + woolPrice + " = " + woolValue + " Münzen");
        System.out.println("• Milch:   " + storedMilk + " x " + milkPrice + " = " + milkValue + " Münzen");
        System.out.println("• Eier:    " + storedEggs + " x " + eggPrice + " = " + eggValue + " Münzen");
        System.out.println("• Federn:  " + storedFeathers + " x " + featherPrice + " = " + featherValue + " Münzen");

        money = money + earnedMoney;

        storedWool = 0;
        storedMilk = 0;
        storedEggs = 0;
        storedFeathers = 0;

        System.out.println();
        System.out.println(GameStyle.label("Einnahmen: ")
                + GameStyle.BRIGHT_GREEN
                + earnedMoney
                + " Münzen"
                + GameStyle.RESET);
        System.out.println(GameStyle.label("Budget: ")
                + GameStyle.money(money));
        System.out.println("Die Ente nennt es Skalierung.");

        GameStyle.endBox();
    }

    // Behandelt ein krankes Tier anhand seines Namens
    public void treatAnimalByName(String name) {

        GameStyle.logBox("TIERÄRZTIN");

        int treatmentCost = 30;

        Animal animal = getAnimalByName(name);

        if (animal == null) {
            GameStyle.warning("Keine Patientenakte gefunden.");
            System.out.println("Die Tierärztin wirkt administrativ verletzt.");
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

        System.out.println(GameStyle.label("Budget: ")
                + GameStyle.money(money));

        System.out.println();

        if (!animal.isSick()) {
            System.out.println(GameStyle.BRIGHT_GREEN + "✓ Keine Behandlung nötig." + GameStyle.RESET);
            System.out.println(animal.getName() + " ist gesund. Die Stimmung bleibt trotzdem verdächtig.");
            System.out.println(GameStyle.label("Kosten: ") + "0 Münzen");

            GameStyle.endBox();
            return;
        }

        System.out.println(GameStyle.label("Diagnose: ")
                + GameStyle.BRIGHT_RED
                + animal.getDiseaseName()
                + GameStyle.RESET);

        System.out.println(GameStyle.label("Krank seit: ")
                + animal.getSickRounds()
                + " Runden");

        System.out.println(GameStyle.label("Kosten: ")
                + treatmentCost
                + " Münzen");

        System.out.println();

        if (money < treatmentCost) {
            GameStyle.warning("Nicht genug Geld für die Behandlung.");
            System.out.println("Das Budget sagt nein. Sehr laut.");
            System.out.println(GameStyle.label("Vorhanden: ")
                    + GameStyle.money(money));

            GameStyle.endBox();
            return;
        }

        money = money - treatmentCost;

        String treatmentMessage = getTreatmentMessage(animal);

        animal.heal();

        System.out.println(GameStyle.BRIGHT_GREEN + "✓ Behandlung erfolgreich." + GameStyle.RESET);
        System.out.println(treatmentMessage);
        System.out.println();
        System.out.println(GameStyle.label("Budget danach: ")
                + GameStyle.money(money));

        GameStyle.endBox();
    }

    // Löst zufällig eine Krankheit bei einem Tier aus
    public void randomDisease() {

        // Wenn es keine Tiere gibt, kann auch niemand krank werden
        if (animals.isEmpty()) {
            return;
        }

        int randomNumber = (int) (Math.random() * 10) + 1;

        if (randomNumber > 2) {
            return;
        }

        int randomIndex = (int) (Math.random() * animals.size());
        Animal randomAnimal = animals.get(randomIndex);

        // Wenn das Tier schon krank ist, passiert nichts Neues
        if (randomAnimal.isSick()) {
            return;
        }

        // Schüchterne Tiere haben manchmal Glück weil sie sich rechtzeitig verstecken
        if (randomAnimal.getPersonality().equals("schüchtern")) {
            int hideChance = (int) (Math.random() * 2) + 1;

            if (hideChance == 1) {
                GameStyle.logBox("GESUNDHEITSAMT MELDET SICH");

                System.out.println(GameStyle.label("Tier: ")
                        + randomAnimal.getName()
                        + " "
                        + getPersonalityIcon(randomAnimal)
                        + " "
                        + GameStyle.BRIGHT_PURPLE
                        + randomAnimal.getPersonality()
                        + GameStyle.RESET);

                System.out.println();

                System.out.println(randomAnimal.getName()
                        + " hat sich rechtzeitig hinter einem Heuballen versteckt.");

                System.out.println("Die Krankheit konnte nicht zugestellt werden.");
                System.out.println("Das Gesundheitsamt ist irritiert, aber machtlos.");

                GameStyle.endBox();
                return;
            }
        }

        // Tier krank machen
        randomAnimal.makeSick();

        GameStyle.logBox("GESUNDHEITSAMT MELDET SICH");

        System.out.println(GameStyle.label("Tier: ")
                + randomAnimal.getName()
                + " "
                + getPersonalityIcon(randomAnimal)
                + " "
                + GameStyle.BRIGHT_PURPLE
                + randomAnimal.getPersonality()
                + GameStyle.RESET);

        System.out.println(GameStyle.label("Diagnose: ")
                + GameStyle.BRIGHT_RED
                + randomAnimal.getDiseaseName()
                + GameStyle.RESET);

        System.out.println();

        System.out.println(getDiseaseMessage(randomAnimal));

        System.out.println();
        System.out.println("Die Farmverwaltung empfiehlt eine Tierärztin.");
        System.out.println("Oder Panik. Panik ist günstiger, aber weniger wirksam.");

        GameStyle.endBox();
    }

    // Krankheiten können sich auf andere Tiere übertragen
    public void spreadDisease() {

        if (animals.size() < 2) {
            return;
        }

        for (Animal sickAnimal : animals) {

            if (sickAnimal.isSick()) {

                int randomNumber = (int) (Math.random() * 10) + 1;

                if (randomNumber <= 2) {

                    int randomIndex = (int) (Math.random() * animals.size());
                    Animal otherAnimal = animals.get(randomIndex);

                    if (otherAnimal != sickAnimal && !otherAnimal.isSick()) {
                        otherAnimal.makeSick();

                        GameStyle.logBox("ANSTECKUNGSVORGANG");
                        System.out.println(GameStyle.label("Ursprung: ")
                                + sickAnimal.getName()
                                + " mit "
                                + GameStyle.BRIGHT_RED
                                + sickAnimal.getDiseaseName()
                                + GameStyle.RESET);
                        System.out.println(GameStyle.label("Neuer Fall: ")
                                + otherAnimal.getName()
                                + " "
                                + getPersonalityIcon(otherAnimal)
                                + " "
                                + GameStyle.BRIGHT_PURPLE
                                + otherAnimal.getPersonality()
                                + GameStyle.RESET);
                        System.out.println(GameStyle.label("Diagnose: ")
                                + GameStyle.BRIGHT_RED
                                + otherAnimal.getDiseaseName()
                                + GameStyle.RESET);

                        System.out.println();

                        System.out.println("Die Lage wurde als \"überschaubar chaotisch\" eingestuft.");
                        System.out.println("Eine Krisensitzung wurde angesetzt und sofort wieder vergessen.");

                        GameStyle.endBox();
                    }
                }
            }
        }
    }

    // Zufälliges Farm-Ereignis am Ende einer Runde
    public void randomFarmEvent(Scanner scanner) {

        // Wenn es keine Tiere gibt, passiert nichts mehr
        if (animals.isEmpty()) {
            return;
        }

        int randomNumber = (int) (Math.random() * 100) + 1;

        if (randomNumber > 35) {
            return;
        }

        int eventNumber = (int) (Math.random() * 5) + 1;

        if (eventNumber == 1) {
            hayInspectionEvent();

        } else if (eventNumber == 2) {
            suspiciousDuckEvent();

        } else if (eventNumber == 3) {
            budgetMeetingEvent();

        } else if (eventNumber == 4) {
            lostFormEvent();

        } else {
            farmCourtEvent(scanner);
        }
    }

    // Ereignis: Heu-Kontrolle
    public void hayInspectionEvent() {

        GameStyle.logBox("HEU-KONTROLLE");

        System.out.println("Das Amt für Tierdrama und Heuordnung führt eine Stichprobe durch.");
        System.out.println("Niemand weiß, wer die Kontrolle beauftragt hat.");
        System.out.println("Alle wirken trotzdem schuldig.");

        System.out.println();

        int randomNumber = (int) (Math.random() * 2) + 1;

        if (randomNumber == 1) {
            int bonus = 15;
            money = money + bonus;

            System.out.println(GameStyle.BRIGHT_GREEN + "✓ Die Heulage wurde als akzeptabel eingestuft." + GameStyle.RESET);
            System.out.println("Du erhältst " + bonus + " Münzen Qualitätsbonus.");
            System.out.println("Das Formular wurde korrekt gelocht. Das beeindruckt alle.");
        } else {
            System.out.println(GameStyle.BRIGHT_RED + "⚠ Die Heulage ist organisatorisch bedenklich." + GameStyle.RESET);
            System.out.println("Die Tiere sind enttäuscht, aber nicht überrascht.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() + 5);
            }

            System.out.println("Alle Tiere bekommen +5 Hunger.");
        }

        GameStyle.endBox();
    }

    // Ereignis: Verdächtige Ente
    public void suspiciousDuckEvent() {

        GameStyle.logBox("ENTENLAGEBERICHT");

        Duck duck = null;

        // Prüfen, ob es überhaupt eine Ente gibt
        for (Animal animal : animals) {
            if (animal instanceof Duck) {
                duck = (Duck) animal;
                break;
            }
        }

        // Falls keine Ente existiert, passiert ein allgemeines Ereignis
        if (duck == null) {
            System.out.println("Es wurde eine verdächtige Ente gemeldet.");
            System.out.println("Problem: Auf deiner Farm gibt es keine Ente.");
            System.out.println("Die Farmverwaltung bewertet das als besonders verdächtig.");

            int bonus = 5;
            money = money + bonus;

            System.out.println();
            System.out.println("Du bekommst " + bonus + " Münzen für kreative Problemlösung.");
            GameStyle.endBox();
            return;
        }

        System.out.println(GameStyle.label("Betroffene Ente: ")
                + duck.getName()
                + " "
                + getPersonalityIcon(duck)
                + " "
                + GameStyle.BRIGHT_PURPLE
                + duck.getPersonality()
                + GameStyle.RESET);

        System.out.println();

        int randomNumber = (int) (Math.random() * 2) + 1;

        if (randomNumber == 1) {
            int bonus = 20;
            money = money + bonus;

            System.out.println(duck.getName() + " hat heimlich einen Businessplan geschrieben.");
            System.out.println("Niemand versteht ihn, aber Investor:innen lieben Pfeile nach oben.");
            System.out.println();
            System.out.println(GameStyle.BRIGHT_GREEN + "✓ Du erhältst " + bonus + " Münzen." + GameStyle.RESET);
            System.out.println("Die Ente nennt es Skalierung.");
        } else {
            System.out.println(duck.getName() + " hat ein Meeting ohne Agenda einberufen.");
            System.out.println("Alle verlassen es hungriger und mit weniger Lebenswillen.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() + 10);
            }

            System.out.println();
            System.out.println(GameStyle.BRIGHT_RED + "⚠ Alle Tiere bekommen +10 Hunger." + GameStyle.RESET);
        }

        GameStyle.endBox();
    }

    // Ereignis: Budgetmeeting
    public void budgetMeetingEvent() {

        GameStyle.logBox("BUDGETMEETING");

        System.out.println("Die Farmverwaltung setzt ein Budgetmeeting an.");
        System.out.println("Es gibt keine Agenda, aber sehr starke Meinungen.");

        System.out.println();

        int randomNumber = (int) (Math.random() * 2) + 1;

        if (randomNumber == 1) {
            int saving = 10;
            money = money + saving;

            System.out.println(GameStyle.BRIGHT_GREEN + "✓ Durch konsequentes Weglassen wurde Geld gespart." + GameStyle.RESET);
            System.out.println("Niemand weiß, was weggelassen wurde.");
            System.out.println("Du erhältst " + saving + " Münzen.");
        } else {
            int cost = 10;

            System.out.println(GameStyle.BRIGHT_RED + "⚠ Das Meeting verursacht Folgekosten." + GameStyle.RESET);
            System.out.println("Es wurden Snacks bestellt. Für Tiere. Aus Versehen.");

            if (money >= cost) {
                money = money - cost;
                System.out.println("Du verlierst " + cost + " Münzen.");
            } else {
                System.out.println("Du hast nicht genug Geld.");
                System.out.println("Das Budgetmeeting wird als emotionaler Schaden verbucht.");

                for (Animal animal : animals) {
                    animal.setHunger(animal.getHunger() + 5);
                }

                System.out.println("Alle Tiere bekommen +5 Hunger.");
            }
        }

        GameStyle.endBox();
    }

    // Ereignis: Formular verschwunden
    public void lostFormEvent() {

        GameStyle.logBox("FORMULAR 7B FEHLT");

        System.out.println("Ein wichtiges Formular ist verschwunden.");
        System.out.println("Niemand weiß, wofür es war.");
        System.out.println("Aber alle sind sicher, dass es wichtig war.");

        System.out.println();

        int randomNumber = (int) (Math.random() * 2) + 1;

        if (randomNumber == 1) {
            System.out.println(GameStyle.BRIGHT_GREEN + "✓ Das Formular taucht im Futterlager wieder auf." + GameStyle.RESET);
            System.out.println("Es riecht nach Heu und passiver Aggression.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() - 5);

                if (animal.getHunger() < 0) {
                    animal.setHunger(0);
                }
            }

            System.out.println("Alle Tiere bekommen -5 Hunger.");
        } else {
            System.out.println(GameStyle.BRIGHT_RED + "⚠ Das Formular bleibt verschwunden." + GameStyle.RESET);
            System.out.println("Die Farmverwaltung reagiert mit einer neuen Excel-Liste.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() + 5);
            }

            System.out.println("Alle Tiere bekommen +5 Hunger.");
        }

        GameStyle.endBox();
    }

    // Besonderes Ereignis: Das Farmgericht tagt
    public void farmCourtEvent(Scanner scanner) {

        GameStyle.logBox("FARMGERICHT");

        if (animals.isEmpty()) {
            System.out.println("Das Farmgericht tagt. Niemand erscheint.");
            System.out.println("Die Aktenlage ist leer, aber unangenehm.");
            GameStyle.endBox();
            return;
        }

        int randomIndex = (int) (Math.random() * animals.size());
        Animal accuser = animals.get(randomIndex);

        GameStyle.printCourtArt();

        System.out.println(GameStyle.label("Kläger: ")
                + accuser.getName()
                + " "
                + getPersonalityIcon(accuser)
                + " "
                + GameStyle.BRIGHT_PURPLE
                + accuser.getPersonality()
                + GameStyle.RESET);

        System.out.println(GameStyle.label("Vorwurf: ")
                + "Heuversagen mit emotionaler Folgewirkung");

        System.out.println();

        System.out.println(GameStyle.label("Optionen:"));
        System.out.println("1 - 20 Münzen Vergleich zahlen");
        System.out.println("2 - Verteidigungsrede halten");
        System.out.println("3 - Sündenbock benennen");
        System.out.println("4 - Schweigen");

        System.out.println();
        System.out.print(GameStyle.BRIGHT_YELLOW + "➜ Entscheidung: " + GameStyle.RESET);
        String decision = scanner.nextLine().trim();

        System.out.println();

        switch (decision) {

            case "1":
                handleCourtBribe(accuser);
                break;

            case "2":
                handleCourtSpeech(accuser);
                break;

            case "3":
                handleCourtScapegoat(accuser);
                break;

            case "4":
                handleCourtSilence(accuser);
                break;

            default:
                GameStyle.warning("Ungültige Entscheidung.");
                System.out.println("Das Gericht wertet das als Schweigen.");
                handleCourtSilence(accuser);
                break;
        }

        System.out.println();
        System.out.println(GameStyle.label("Budget: ")
                + GameStyle.money(money));

        GameStyle.endBox();
    }

    // Option 1: Außergerichtliche Einigung
    // Option 1: Vergleich zahlen
    public void handleCourtBribe(Animal accuser) {

        int settlementCost = 20;

        if (money >= settlementCost) {
            money = money - settlementCost;

            System.out.println(GameStyle.BRIGHT_GREEN + "✓ Vergleich angenommen." + GameStyle.RESET);
            System.out.println(accuser.getName() + " nennt es Gerechtigkeit.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() - 10);

                if (animal.getHunger() < 0) {
                    animal.setHunger(0);
                }
            }

            System.out.println("Alle Tiere bekommen -10 Hunger.");

        } else {
            GameStyle.warning("Nicht genug Geld für den Vergleich.");
            System.out.println("Das Gericht seufzt in Paragrafen.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() + 10);
            }

            System.out.println("Alle Tiere bekommen +10 Hunger.");
        }
    }

    // Option 2: Dramatische Verteidigungsrede
    public void handleCourtSpeech(Animal accuser) {

        int randomNumber = (int) (Math.random() * 2) + 1;

        System.out.println("Du hältst eine Rede über Heu, Verantwortung und Führung.");

        if (randomNumber == 1) {
            int reward = 25;
            money = money + reward;

            System.out.println(GameStyle.BRIGHT_GREEN + "✓ Die Rede wirkt überraschend seriös." + GameStyle.RESET);
            System.out.println(accuser.getName() + " zieht die Beschwerde zurück.");
            System.out.println("Du erhältst " + reward + " Münzen.");

        } else {
            GameStyle.warning("Die Rede eskaliert.");
            System.out.println(accuser.getName() + " fühlt sich nicht gehört.");

            for (Animal animal : animals) {
                animal.setHunger(animal.getHunger() + 10);
            }

            System.out.println("Alle Tiere bekommen +10 Hunger.");
        }
    }

    // Option 3: Sündenbock benennen
    public void handleCourtScapegoat(Animal accuser) {

        if (animals.size() < 2) {
            System.out.println("Es gibt kein anderes Tier.");
            System.out.println(accuser.getName() + " wird dadurch dramatischer.");

            accuser.setPersonality("dramatisch");
            return;
        }

        Animal scapegoat = accuser;

        while (scapegoat == accuser) {
            int randomIndex = (int) (Math.random() * animals.size());
            scapegoat = animals.get(randomIndex);
        }

        System.out.println(scapegoat.getName() + " wird als Managementproblem benannt.");
        System.out.println("Die Personalentwicklung nennt es Wachstum.");

        scapegoat.setPersonality("dramatisch");

        for (Animal animal : animals) {
            if (animal != scapegoat) {
                animal.setHunger(animal.getHunger() - 5);

                if (animal.getHunger() < 0) {
                    animal.setHunger(0);
                }
            }
        }

        System.out.println("Alle anderen Tiere bekommen -5 Hunger.");
    }

    // Option 4: Schweigen
    public void handleCourtSilence(Animal accuser) {

        System.out.println("Du schweigst.");
        System.out.println(accuser.getName() + " wertet das als Schuldeingeständnis.");

        for (Animal animal : animals) {
            animal.setHunger(animal.getHunger() + 15);
        }

        System.out.println("Alle Tiere bekommen +15 Hunger.");
    }

    // Gibt zufällig einen kurzen Kommentar von einem Tier aus
    public void randomAnimalComment() {

        if (animals.isEmpty()) {
            return;
        }

        int commentChance = (int) (Math.random() * 100) + 1;

        if (commentChance > 30) {
            return;
        }

        int randomIndex = (int) (Math.random() * animals.size());
        Animal randomAnimal = animals.get(randomIndex);

        int commentNumber = (int) (Math.random() * 8) + 1;

        GameStyle.logBox("TIERISCHE MELDUNG");

        switch (commentNumber) {
            case 1:
                System.out.println(randomAnimal.getName() + " wirkt unbezahlt.");
                break;

            case 2:
                System.out.println(randomAnimal.getName() + " fordert ein Meeting. Grund unklar.");
                break;

            case 3:
                System.out.println(randomAnimal.getName() + " hat Hauptcharakter-Energie.");
                break;

            case 4:
                System.out.println(randomAnimal.getName() + " ignoriert die Farmordnung.");
                break;

            case 5:
                System.out.println(randomAnimal.getName() + " existiert heute im Energiesparmodus.");
                break;

            case 6:
                System.out.println(randomAnimal.getName() + " plant etwas mit Excel.");
                break;

            case 7:
                System.out.println(randomAnimal.getName() + " schaut nach Beschwerde-Mail aus.");
                break;

            default:
                System.out.println(randomAnimal.getName() + " beantragt emotionalen Sonderurlaub.");
                break;
        }

        GameStyle.endBox();
    }

    // Entfernt Tiere, deren Hunger zu hoch ist oder die zu lange krank waren
    public void removeDeadAnimals() {

        // Wir zählen rückwärts, weil beim Entfernen sonst die Liste durcheinander kommen kann
        for (int i = animals.size() - 1; i >= 0; i--) {

            Animal animal = animals.get(i);

            boolean diesFromHunger = animal.getHunger() >= 100;
            boolean diesFromDisease = animal.isSick() && animal.getSickRounds() >= 5;

            if (diesFromHunger || diesFromDisease) {

                GameStyle.logBox("TIERREGISTER-AUSTRAGUNG");

                System.out.println(GameStyle.label("Betroffenes Tier: ")
                        + animal.getName()
                        + " "
                        + getPersonalityIcon(animal)
                        + " "
                        + GameStyle.BRIGHT_PURPLE
                        + animal.getPersonality()
                        + GameStyle.RESET);

                System.out.println(GameStyle.label("Hungerstatus: ")
                        + GameStyle.getHungerBar(animal.getHunger()));

                if (animal.isSick()) {
                    System.out.println(GameStyle.label("Diagnose: ")
                            + GameStyle.BRIGHT_RED
                            + animal.getDiseaseName()
                            + GameStyle.RESET);

                    System.out.println(GameStyle.label("Krank seit: ")
                            + animal.getSickRounds()
                            + " Runden");
                }

                System.out.println();

                System.out.println(animal.getName()
                        + " hat die Farm verlassen.");

                if (diesFromHunger) {
                    System.out.println("Offizieller Grund: Versorgungslage dauerhaft katastrophal.");
                }

                if (diesFromDisease) {
                    System.out.println("Offizieller Grund: Krankheitsfall wurde zu lange ignoriert.");
                    System.out.println("Das Gesundheitsamt nennt das \"organisatorisch bemerkenswert\".");
                }

                System.out.println("Die Farmverwaltung spricht von einem bedauerlichen Einzelfall.");
                System.out.println("Es ist nicht der erste Einzelfall.");

                animals.remove(i);

                GameStyle.endBox();
            }
        }
    }

    // Prüft, ob die Farm keine Tiere mehr hat
    public boolean hasNoAnimals() {

        // Wenn die Liste leer ist, gibt es keine Tiere mehr
        return animals.isEmpty();
    }
}