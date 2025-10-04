package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    private FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public static FruitTransaction create(Operation operation, String fruit, int quantity) {
        if (operation == null || fruit == null || quantity < 0) {
            throw new IllegalArgumentException("Illegal argument in verify().Check your params");
        }
        return new FruitTransaction(operation, fruit, quantity);
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return operation + "," + fruit + "," + quantity;
    }
}
