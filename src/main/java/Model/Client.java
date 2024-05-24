package Model;

/**
 * Reprezinta un client cu informatiile sale relevante.
 */
public class Client {
    private int client_id;
    private String name;
    private String email;
    private String address;

    /**
     * Constructorul implicit pentru clasa Client.
     */
    public Client() {
    }

    /**
     * Constructorul pentru clasa Client cu parametri.
     *
     * @param client_id ID-ul clientului.
     * @param name Numele clientului.
     * @param email Email-ul clientului.
     * @param address Adresa clientului.
     */
    public Client(int client_id, String name, String email, String address) {
        this.client_id = client_id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     * Returneaza ID-ul clientului.
     *
     * @return ID-ul clientului.
     */
    public int getClient_id() {
        return client_id;
    }

    /**
     * Seteaza ID-ul clientului.
     *
     * @param client_id ID-ul nou al clientului.
     */
    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    /**
     * Returneaza numele clientului.
     *
     * @return Numele clientului.
     */
    public String getName() {
        return name;
    }

    /**
     * Seteaza numele clientului.
     *
     * @param name Numele nou al clientului.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returneaza email-ul clientului.
     *
     * @return Email-ul clientului.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Seteaza email-ul clientului.
     *
     * @param email Email-ul nou al clientului.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returneaza adresa clientului.
     *
     * @return Adresa clientului.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Seteaza adresa clientului.
     *
     * @param address Adresa noua a clientului.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Seteaza ID-ul clientului (pentru consistenta).
     *
     * @param id ID-ul nou al clientului.
     */
    public void setId(int id) {
        this.client_id = id;
    }

    /**
     * Returneaza o reprezentare sub forma de sir de caractere a obiectului Client.
     *
     * @return Sirul de caractere reprezentand clientul.
     */
    @Override
    public String toString() {
        return "Client{" +
                "client_id=" + client_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
