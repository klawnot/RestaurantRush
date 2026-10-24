package restaurantrush;

public class CriticCustomer extends Customer {

    public CriticCustomer(String id, int arrivalTurn) {
        super(id, arrivalTurn);
    }

    /**
     * Critics are far less patient: loses 12 satisfaction points per turn.
     */
    @Override
    public int getSatisfactionLossPerTurn() {
        return 12;
    }

    /**
     * Critics execute custom food selection logic.
     * Selects based on specific criteria (e.g., first available menu item or targeted item)
     * falling back to the standard random choice if unavailable.
     */
    @Override
    public MenuItem chooseFood(Menu menu) {
        if (menu != null && !menu.getItems().isEmpty()) {
            // Evaluates custom preference (e.g., first item on the menu)
            return menu.getItems().get(0);
        }
        return super.chooseFood(menu);
    }

    @Override
    public String getTypeLabel() {
        return "Critic";
    }
}
