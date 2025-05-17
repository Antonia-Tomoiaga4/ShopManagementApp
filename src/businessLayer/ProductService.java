package businessLayer;

import dataAccessLayer.GenericDao;
import model.Product;
/**
 * gestioneaza operartiile legate de produse , facute intre gui si bd
 */
import java.util.List;

public class ProductService {
    /**obiect de tip GenericDao(clasa care face op CRUD pe un obiect product)
     */
    private final GenericDao<Product> genericDao = new GenericDao<>(Product.class);

    public List<Product> getAllProducts() {
        return genericDao.findAll();
    }

    public boolean addProduct(Product product) {
        return genericDao.insert(product);
    }

    public Product findById(int id) {
        return genericDao.findById(id);
    }

    public boolean updateProduct(Product product) {
        return genericDao.update(product);
    }

    public boolean deleteProduct(int id) {
        return genericDao.delete(id);
    }
}
