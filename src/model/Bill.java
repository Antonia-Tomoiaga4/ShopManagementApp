package model;

/**
 *  clasa bill reprezinta o factura  imutabila, generată la finalizarea unei comenzi.
 *
 * @param clientName  Numele clientului
 * @param productName Numele produsului
 * @param quantity    Cantitatea comandată
 * @param totalPrice  Prețul total
 */
public record Bill(String clientName, String productName, int quantity, double totalPrice) {}
