package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int quantity = transaction.getQuantity();
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Operation cannot "
                    + "be proceeded due to quantity out of bounds");
        }
        Storage.setFruit(transaction.getFruit(), transaction.getQuantity());
    }
}
