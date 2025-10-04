package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        Storage.clearRecords();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void apply_setsCorrectQuantityForNewFruit_isOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.BALANCE,
                "apple",
                50);
        balanceOperation.apply(transaction);
        assertEquals(50, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_overwritesExistingQuantity_isOk() {
        Storage.setFruit("apple", 20);
        FruitTransaction transaction = FruitTransaction.create(Operation.BALANCE,
                "apple",
                70);
        balanceOperation.apply(transaction);
        assertEquals(70, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_zeroQuantity_isOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.BALANCE,
                "apple",
                0);
        balanceOperation.apply(transaction);
        assertEquals(0, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_largeQuantity_setsCorrectly() {
        FruitTransaction transaction = FruitTransaction.create(Operation.BALANCE,
                "apple",
                Integer.MAX_VALUE);
        balanceOperation.apply(transaction);
        assertEquals(Integer.MAX_VALUE, Storage.getFruits().get("apple"));
    }
}
