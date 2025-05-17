package model;

/**
 * clasa client,reprezinta clientii din baza de date au
 * doar nume si id ,
 */
public class Client {
    private int id;
    private String name;

    /**
     * constructor fara parametri pentru cand se creeaza obiectul prin reflectie
     * initializare cu setter ulterior
     */
    public Client() {
    }

    /**
     * cand se creeaza un client si id ul e generat automat(auto_increment primary key)
     * @param name
     */
    public Client(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
