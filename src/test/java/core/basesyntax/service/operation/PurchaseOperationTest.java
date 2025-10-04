package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.clearRecords();
        purchaseOperation = new PurchaseOperation();
        Storage.setFruit("apple", 1000);
    }

    @Test
    void apply_purchaseCorrectQuantityForNewFruit_isOk() {
        FruitTransaction purchaseTransaction = FruitTransaction.create(Operation.PURCHASE,
                "apple",
                100);
        purchaseOperation.apply(purchaseTransaction);
        assertEquals(900, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_zeroQuantityTransaction_isOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.PURCHASE,
                "apple",
                0);
        purchaseOperation.apply(transaction);
        assertEquals(1000, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_zeroQuantityFruits_isOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.PURCHASE,
                "apple",
                1000);
        purchaseOperation.apply(transaction);
        assertEquals(0, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_negativeQuantityFruits_notOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.PURCHASE,
                "apple",
                Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> purchaseOperation.apply(transaction));
    }
}
