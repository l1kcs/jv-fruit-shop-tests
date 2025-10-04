package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private final Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationHandlers.clear();
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_returnsCorrectHandler_isOk() {
        operationHandlers.put(Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());
        assertInstanceOf(BalanceOperation.class, operationStrategy.get(Operation.BALANCE));
        assertInstanceOf(PurchaseOperation.class, operationStrategy.get(Operation.PURCHASE));
        assertInstanceOf(ReturnOperation.class, operationStrategy.get(Operation.RETURN));
        assertInstanceOf(SupplyOperation.class, operationStrategy.get(Operation.SUPPLY));
    }

    @Test
    void get_gettingOperationWithNoHandler_notOk() {
        operationHandlers.remove(Operation.BALANCE);
        assertThrows(RuntimeException.class, () -> operationStrategy.get(Operation.BALANCE));
    }

    @Test
    void get_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> operationStrategy.get(null));
    }

}
