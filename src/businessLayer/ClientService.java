package businessLayer;

import dataAccessLayer.GenericDao;
import model.Client;

import java.util.List;

/**
 * gestioneaza op legate de client, dintre bd si gui
 */
public class ClientService {
    /**obiect de tip GenericDao(clasa care face op CRUD pe un obiect client)
     */
    private final GenericDao<Client> genericDao = new GenericDao<>(Client.class);

    public List<Client> getAllClients() {
        return genericDao.findAll();
    }

    public boolean addClient(Client client) {
        return genericDao.insert(client);
    }

    public Client findById(int id) {
        return genericDao.findById(id);
    }

    public boolean updateClient(Client client) {
        return genericDao.update(client);
    }

    public boolean deleteClient(int id) {
        return genericDao.delete(id);
    }
}
