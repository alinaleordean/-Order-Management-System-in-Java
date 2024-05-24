package Model;

/**
 * Reprezinta un produs cu informatiile sale relevante.
 */
public class Product {
    private int product_id;
    private String name;
    private double price;
    private int stock;

    /**
     * Constructorul implicit pentru clasa Product.
     */
    public Product() {
    }

    /**
     * Constructorul pentru clasa Product cu parametri.
     *
     * @param product_id ID-ul produsului.
     * @param name Numele produsului.
     * @param price Pretul produsului.
     * @param stock Stocul disponibil pentru produs.
     */
    public Product(int product_id, String name, double price, int stock) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Returneaza ID-ul produsului.
     *
     * @return ID-ul produsului.
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * Seteaza ID-ul produsului.
     *
     * @param product_id ID-ul nou al produsului.
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * Returneaza numele produsului.
     *
     * @return Numele produsului.
     */
    public String getName() {
        return name;
    }

    /**
     * Seteaza numele produsului.
     *
     * @param name Numele nou al produsului.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returneaza pretul produsului.
     *
     * @return Pretul produsului.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Seteaza pretul produsului.
     *
     * @param price Pretul nou al produsului.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returneaza stocul disponibil al produsului.
     *
     * @return Stocul disponibil.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Seteaza stocul disponibil al produsului.
     *
     * @param stock Noul stoc al produsului.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returneaza o reprezentare sub forma de sir de caractere a obiectului Product.
     *
     * @return Sirul de caractere reprezentand produsul.
     */
    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
