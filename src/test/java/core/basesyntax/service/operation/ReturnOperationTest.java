package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        Storage.clearRecords();
        returnOperation = new ReturnOperation();
        Storage.setFruit("apple", 20);
    }

    @Test
    void apply_returnCorrectQuantityForNewFruit_isOk() {
        FruitTransaction returnTransaction = FruitTransaction.create(Operation.RETURN,
                "apple",
                100);
        returnOperation.apply(returnTransaction);
        assertEquals(120, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_zeroQuantity_isOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.RETURN,
                "apple",
                0);
        returnOperation.apply(transaction);
        assertEquals(20, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_overflowQuantity_notOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.RETURN,
                "apple",
                Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> returnOperation.apply(transaction));
    }
}
