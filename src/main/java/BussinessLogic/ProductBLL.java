package BussinessLogic;

import DataAccess.ProductDAO;
import Model.Product;

import java.util.List;

/**
 * ProductBLL (Business Logic Layer) este responsabil pentru gestionarea logicii de afaceri
 * referitoare la operatiunile asupra produselor. Interactioneaza cu ProductDAO (Data Access Object)
 * pentru a realiza operatiuni CRUD asupra datelor despre produse.
 */
public class ProductBLL {
    private ProductDAO productDAO = new ProductDAO();

    /**
     * Adauga un nou produs in baza de date.
     *
     * @param product obiectul Product care urmeaza sa fie adaugat.
     */
    public void addProduct(Product product) {
        productDAO.insert(product);
    }

    /**
     * Actualizeaza un produs existent in baza de date.
     *
     * @param product obiectul Product care contine informatiile actualizate.
     */
    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    /**
     * Sterge un produs din baza de date pe baza ID-ului furnizat.
     *
     * @param productId ID-ul produsului care urmeaza sa fie sters.
     */
    public void deleteProduct(int productId) {
        productDAO.delete(productId);
    }

    /**
     * Returneaza toate produsele din baza de date.
     *
     * @return o lista cu toate produsele.
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Gaseste un produs in baza de date pe baza ID-ului furnizat.
     *
     * @param id ID-ul produsului care urmeaza sa fie gasit.
     * @return obiectul Product cu ID-ul specificat, sau null daca nu este gasit.
     */
    public Product findProductById(int id) {
        return productDAO.findById(id);
    }
}
