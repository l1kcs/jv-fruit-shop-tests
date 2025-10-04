package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int current = Storage.getFruits().getOrDefault(transaction.getFruit(), 0);
        int quantity = transaction.getQuantity();
        if (current - quantity < 0 || quantity < 0) {
            throw new IllegalArgumentException("Purchase impossible.Wrong quantity of transaction");
        }
        Storage.updateFruit(transaction.getFruit(), -transaction.getQuantity());
    }
}
