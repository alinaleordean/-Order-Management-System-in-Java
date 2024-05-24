package Model;

/**
 * Reprezinta o comanda cu informatiile sale relevante.
 */
public class Order {
    private int id;
    private int client_id;
    private int product_id;
    private float quantity;

    /**
     * Constructorul implicit pentru clasa Order.
     */
    public Order() {
    }

    /**
     * Constructorul pentru clasa Order cu parametri.
     *
     * @param id ID-ul comenzii.
     * @param client_id ID-ul clientului care a facut comanda.
     * @param product_id ID-ul produsului comandat.
     * @param quantity Cantitatea comandata.
     */
    public Order(int id, int client_id, int product_id, float quantity) {
        this.id = id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    /**
     * Returneaza ID-ul comenzii.
     *
     * @return ID-ul comenzii.
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza ID-ul comenzii.
     *
     * @param id ID-ul nou al comenzii.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza ID-ul clientului care a facut comanda.
     *
     * @return ID-ul clientului.
     */
    public int getClient_id() {
        return client_id;
    }

    /**
     * Seteaza ID-ul clientului care a facut comanda.
     *
     * @param client_id ID-ul nou al clientului.
     */
    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    /**
     * Returneaza ID-ul produsului comandat.
     *
     * @return ID-ul produsului.
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * Seteaza ID-ul produsului comandat.
     *
     * @param product_id ID-ul nou al produsului.
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * Returneaza cantitatea comandata.
     *
     * @return Cantitatea comandata.
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * Seteaza cantitatea comandata.
     *
     * @param quantity Cantitatea noua.
     */
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * Seteaza produsul comandat.
     *
     * @param product Obiectul Product care contine informatiile produsului.
     */
    public void setProduct(Product product) {
        this.product_id = product.getProduct_id();
    }

    /**
     * Seteaza clientul care a facut comanda.
     *
     * @param client Obiectul Client care contine informatiile clientului.
     */
    public void setClient(Client client) {
        this.client_id = client.getClient_id();
    }

    /**
     * Returneaza o reprezentare sub forma de sir de caractere a obiectului Order.
     *
     * @return Sirul de caractere reprezentand comanda.
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                '}';
    }
}
