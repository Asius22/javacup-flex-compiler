package tree.table;

/**
 * deve essere estesa per avere valori differenti in base a
 * se stiamo leggendo la entry di una dichiarazione variabile,
 * dichiarazione funzione
 * poi vediamo se farne altri
 */

public abstract class Entry {
    private String name;
    private String kindOfEntry;

    public Entry(String name, String kindOfEntry) {
        this.name = name;
        this.kindOfEntry = kindOfEntry;
    }

    public String getName() {
        return name;
    }

    public String getKindOfEntry() {
        return kindOfEntry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKindOfEntry(String kindOfEntry) {
        this.kindOfEntry = kindOfEntry;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "name='" + name + '\'' +
                ", kindOfEntry='" + kindOfEntry + '\'' +
                '}';
    }
}
