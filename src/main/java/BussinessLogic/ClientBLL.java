package BussinessLogic;

import DataAccess.ClientDAO;
import Model.Client;
import java.util.List;

/**
 * ClientBLL (Business Logic Layer) este responsabil pentru gestionarea logicii de afaceri
 * referitoare la operatiunile asupra clientilor. Interactioneaza cu ClientDAO (Data Access Object)
 * pentru a realiza operatiuni CRUD asupra datelor despre clienti.
 */
public class ClientBLL {
    private ClientDAO clientDAO = new ClientDAO();

    /**
     * Returneaza toti clientii din baza de date.
     *
     * @return o lista cu toti clientii.
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Adauga un nou client in baza de date.
     *
     * @param client obiectul Client care urmeaza sa fie adaugat.
     */
    public void addClient(Client client) {
        clientDAO.insert(client);
    }

    /**
     * Actualizeaza un client existent in baza de date.
     *
     * @param client obiectul Client care contine informatiile actualizate.
     */
    public void updateClient(Client client) {
        clientDAO.update(client);
    }

    /**
     * Sterge un client din baza de date pe baza ID-ului furnizat.
     *
     * @param id ID-ul clientului care urmeaza sa fie sters.
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }
}
