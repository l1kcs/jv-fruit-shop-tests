package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.clearRecords();
        Storage.clearFruits();
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceOperation());
        handlers.put(Operation.SUPPLY, new SupplyOperation());
        handlers.put(Operation.PURCHASE, new PurchaseOperation());
        handlers.put(Operation.RETURN, new ReturnOperation());
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(strategy);
    }

    @Test
    void process_multipleTransactions_updatesStorageCorrectly() {
        List<FruitTransaction> transactions = List.of(
                FruitTransaction.create(Operation.BALANCE, "apple", 50),
                FruitTransaction.create(Operation.SUPPLY, "apple", 20),
                FruitTransaction.create(Operation.PURCHASE, "apple", 30),
                FruitTransaction.create(Operation.RETURN, "apple", 5)
        );
        shopService.process(transactions);
        assertEquals(45, Storage.getFruits().get("apple"));
    }

    @Test
    void process_transactionsForDifferentFruits_updatesEachCorrectly() {
        List<FruitTransaction> transactions = List.of(
                FruitTransaction.create(Operation.BALANCE, "apple", 100),
                FruitTransaction.create(Operation.BALANCE, "banana", 50),
                FruitTransaction.create(Operation.PURCHASE, "apple", 40),
                FruitTransaction.create(Operation.SUPPLY, "banana", 25)
        );
        shopService.process(transactions);
        assertEquals(60, Storage.getFruits().get("apple"));
        assertEquals(75, Storage.getFruits().get("banana"));
    }

    @Test
    void process_emptyList_doesNothing() {
        shopService.process(List.of());
        assertEquals(0, Storage.getFruits().size());
    }
}
