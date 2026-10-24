package restaurantrush;

public class Waiter {

    /**
     * Seats the next waiting customer from the queue at a specified free table.
     */
    public boolean seatCustomer(RestaurantState state, int tableId) {
        if (state.getWaitingQueue().isEmpty()) {
            return false;
        }

        Table table = state.findTableById(tableId);
        if (table == null || table.isOccupied()) {
            return false;
        }

        Customer customer = state.getWaitingQueue().poll();
        table.seatCustomer(customer);
        return true;
    }

    /**
     * Takes an order from a seated customer who does not have an active order yet.
     */
    public boolean takeOrder(Table table, Menu menu, RestaurantState state) {
        if (table == null || !table.isOccupied()) {
            return false;
        }

        Customer customer = table.getCurrentCustomer();
        if (customer.getOrder() != null) {
            return false; // Order already taken
        }

        MenuItem chosenItem = customer.chooseFood(menu);
        int finalPriceCents = chosenItem.getPriceCents();

        // 1. Apply VIP discount first (10% off)
        if (customer instanceof VIPCustomer) {
            finalPriceCents = (int) Math.round(finalPriceCents * 0.90);
        }

        // 2. Apply Happy Hour discount next (20% off) if active
        if (state.isHappyHourActive()) {
            finalPriceCents = (int) Math.round(finalPriceCents * 0.80);
        }

        String orderId = "ORD-" + customer.getId();
        Order order = new Order(orderId, customer.getId(), chosenItem, finalPriceCents);

        customer.setOrder(order);
        state.addOrder(order);
        return true;
    }

    /**
     * Serves an order that was already READY at the start of the action phase.
     */
    public boolean serveOrder(Order order, RestaurantState state) {
        if (order == null || order.getStatus() != OrderStatus.READY) {
            return false; // Can only serve dishes that are fully READY
        }

        order.setStatus(OrderStatus.SERVED);

        // Locate customer and transition their state to eating
        for (Table table : state.getTables()) {
            if (table.isOccupied() && table.getCurrentCustomer().getId().equals(order.getCustomerId())) {
                table.getCurrentCustomer().setState(CustomerState.SERVED_EATING);
                break;
            }
        }
        return true;
    }
}