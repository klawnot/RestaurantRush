package restaurantrush;

public class Host {

    /**
     * Seats at most one customer from the waiting queue into an available table.
     * Returns the seated customer, or null if no one could be seated.
     */
    public Customer seatCustomer(RestaurantState state, int tableId) {
        if (state.getWaitingQueue().isEmpty()) {
            return null;
        }

        Table table = state.findTableById(tableId);
        if (table == null || table.isOccupied()) {
            return null;
        }

        Customer customer = state.getWaitingQueue().poll();
        table.seatCustomer(customer);
        return customer;
    }
}