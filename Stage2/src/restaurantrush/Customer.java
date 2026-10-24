package restaurantrush;

public class Customer {
    private final String id;
    private final int arrivalTurn;
    private int satisfaction;
    private CustomerState state;
    private Table table;
    private Order order;

    public Customer(String id, int arrivalTurn) {
        this.id = id;
        this.arrivalTurn = arrivalTurn;
        this.satisfaction = 100; // Base starting satisfaction
        this.state = CustomerState.WAITING;
        this.table = null;
        this.order = null;
    }

    public String getId() {
        return id;
    }

    public int getArrivalTurn() {
        return arrivalTurn;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = Math.max(0, Math.min(100, satisfaction));
    }

    public CustomerState getState() {
        return state;
    }

    public void setState(CustomerState state) {
        this.state = state;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Regular customers lose 8 satisfaction points per turn while unserved.
     * Can be overridden by subclasses (e.g. VIP loses 5, Critic loses 12).
     */
    public int getSatisfactionLossPerTurn() {
        return 8; //
    }

    /**
     * Calculates the bill for the ordered item.
     * Regular customers pay full price. Subclasses can apply discounts.
     */
    public int calculateBill() {
        if (order == null || order.getMenuItem() == null) {
            return 0;
        }
        return order.getMenuItem().getPriceCents(); //[cite: 1]
    }

    /**
     * Customers choose their own food.
     * Base implementation selects randomly from the available menu.
     */
    public MenuItem chooseFood(Menu menu) {
        return menu.getRandomItem(); //[cite: 1]
    }

    /**
     * Applies end-of-turn satisfaction loss if waiting or seated without food.
     * If satisfaction hits 0, the customer leaves immediately.
     */
    public void applyTurnDecay() {
        if (state == CustomerState.WAITING || state == CustomerState.SEATED) { //[cite: 1]
            this.satisfaction = Math.max(0, this.satisfaction - getSatisfactionLossPerTurn()); //[cite: 1]
            if (this.satisfaction == 0) {
                this.state = CustomerState.ABANDONED; //[cite: 1]
            }
        }
    }

    public String getTypeLabel() {
        return "Standard";
    }
    
    @Override
    public String toString() {
        return String.format("Customer %s [Sat: %d, State: %s]", id, satisfaction, state);
    }
}