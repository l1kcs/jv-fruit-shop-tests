package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        Storage.clearRecords();
        supplyOperation = new SupplyOperation();
        Storage.setFruit("apple", 20);
    }

    @Test
    void apply_addsCorrectQuantityForNewFruit_isOk() {
        FruitTransaction supplyTransaction = FruitTransaction.create(Operation.SUPPLY,
                "apple",
                100);
        supplyOperation.apply(supplyTransaction);
        assertEquals(120, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_zeroQuantity_isOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.SUPPLY,
                "apple",
                0);
        supplyOperation.apply(transaction);
        assertEquals(20, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_overflowQuantity_notOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.SUPPLY,
                "apple",
                Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> supplyOperation.apply(transaction));
    }
}
