package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int current = Storage.getFruits().getOrDefault(transaction.getFruit(), 0);
        int quantity = transaction.getQuantity();
        if ((quantity < 0) || ((long) current + (long) quantity > Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("Return quantity should be 0 - Integer.MAX_VALUE");
        }
        Storage.updateFruit(transaction.getFruit(), transaction.getQuantity());
    }
}
