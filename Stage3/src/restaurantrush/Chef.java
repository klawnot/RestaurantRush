package restaurantrush;

public class Chef {

    /**
     * Advances cooking by one preparation unit on an assigned order.
     * Transitions order to READY once remaining units reach 0.
     */
    public boolean cookOrder(Order order) {
        if (order == null || order.getStatus() != OrderStatus.ORDERED) {
            return false;
        }

        order.advancePreparation();
        return true;
    }
}