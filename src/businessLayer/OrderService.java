package businessLayer;
import dataAccessLayer.GenericDao;
import model.Product;
import model.Order;

import java.util.List;

/**
 * gestioneaza operatiile legate de comenzi , facute intre gui si bd
 */
public class OrderService {
    /**obiect de tip GenericDao(clasa care face op CRUD pe un obiect Order)
     */
    private final GenericDao<Order> orderDao = new GenericDao<>(Order.class);
    /**
     * obiect productservice care verifica si actualizeaza stocul cand se face o comanda
     */
    private final ProductService productService = new ProductService();

    /**
     * plaseaza o comanda daca este stoc
     * @param order
     * @return
     */
    public boolean placeOrder(Order order) {
        Product p = productService.findById(order.getProductId());
        if (p == null || p.getStock() < order.getQuantity()) {
            return false;
        }

        boolean ok = orderDao.insert(order);
        if (ok) {
            p.setStock(p.getStock() - order.getQuantity());
            productService.updateProduct(p);
        }
        return ok;
    }

    /**
     * @return toate comenziile din bd
     */
    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    /**\
     * @param id
     * @return sterge o comanda dupa id
     */
    public boolean deleteOrder(int id) {
        return orderDao.delete(id);
    }
}
