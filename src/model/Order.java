package model;

/**
 * clasa order reprezinta o comanda,formata dintr un client(id)
 * un produs(id) , cantiatea de produs si un id al comenzi
 */

public class Order {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    /**
     * constructor fara parametri pentru cand se creeaza obiectul prin reflectie
     * initializare cu setter ulterior
     */
    public Order() {}

    /**
     * cand se creeaza o comanda noua si id ul e generat automat(auto_increment primary key)
     * @param clientId
     * @param productId
     * @param quantity
     */
    public Order(int clientId, int productId, int quantity) {
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
