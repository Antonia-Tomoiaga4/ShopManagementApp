package model;
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    /**
     * constructor fara parametri pentru cand se creeaza obiectul prin reflectie
     * initializare cu setter ulterior
     */
    public Product() {}

    /**
     * cand se creeaza un produs si id ul e generat automat(auto_increment primary key)
     * @param name
     * @param price
     * @param stock
     */
    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public String toString() {
        return name;
    }
}
