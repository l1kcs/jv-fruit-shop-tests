package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionServiceImplTest {
    private FruitTransactionService service;

    @BeforeEach
    void setUp() {
        service = new FruitTransactionServiceImpl();
        Storage.clearRecords();
    }

    @Test
    void createTransaction_validInput_transactionAddedToStorage() {
        service.createTransaction(Operation.SUPPLY, "apple", 100);
        assertEquals(1, Storage.getRecords().size());
        FruitTransaction transaction = Storage.getRecords().get(0);
        assertEquals(Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void createTransaction_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                service.createTransaction(Operation.SUPPLY, null, 10));
    }

    @Test
    void createTransaction_nullOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                service.createTransaction(null, "apple", 10));
    }

    @Test
    void createTransaction_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                service.createTransaction(Operation.SUPPLY, "apple", -10));
    }
}

