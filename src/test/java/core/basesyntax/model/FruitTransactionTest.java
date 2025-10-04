package core.basesyntax.model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    void create_checkTemplate_isOk() {
        FruitTransaction fruitTransactionTest = FruitTransaction.create(Operation.BALANCE,
                "apple",
                100);
        assertEquals("BALANCE,apple,100",fruitTransactionTest.toString());
    }

    @Test
    void create_nullValueCheck_isOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.create(null,"apple", 100));
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.create(Operation.BALANCE,null, 100));
    }

}
