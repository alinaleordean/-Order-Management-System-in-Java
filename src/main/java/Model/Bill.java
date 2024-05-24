package Model;

/**
 * Reprezinta o factura asociata unei comenzi.
 *
 * @param id ID-ul facturii.
 * @param orderId ID-ul comenzii asociate facturii.
 * @param amount Suma totala a facturii.
 */
public record Bill(int id, int orderId, double amount) {
}
