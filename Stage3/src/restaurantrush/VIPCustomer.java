package restaurantrush;

public class VIPCustomer extends Customer {

    public VIPCustomer(String id, int arrivalTurn) {
        super(id, arrivalTurn);
    }

    /**
     * VIPs are more patient: loses 5 satisfaction points per turn.
     */
    @Override
    public int getSatisfactionLossPerTurn() {
        return 5;
    }

    /**
     * In Stage 3, discounts are locked into the Order when taken.
     * Defers to Customer.calculateBill() which reads the locked final price.
     */
    @Override
    public int calculateBill() {
        return super.calculateBill();
    }

    @Override
    public String getTypeLabel() {
        return "VIP";
    }
}