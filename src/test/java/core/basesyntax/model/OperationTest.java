package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OperationTest {
    @Test
    void fromCode_validOperators_isOk() {
        assertEquals(Operation.BALANCE, Operation.fromCode("b"));
        assertEquals(Operation.SUPPLY, Operation.fromCode("s"));
        assertEquals(Operation.PURCHASE, Operation.fromCode("p"));
        assertEquals(Operation.RETURN, Operation.fromCode("r"));
    }

    @Test
    void fromCode_emptyOperatorCheck_notOk() {
        assertThrows(IllegalArgumentException.class,() -> Operation.fromCode(""));
    }

    @Test
    void fromCode_unknownOperatorCheck_notOk() {
        assertThrows(IllegalArgumentException.class,() -> Operation.fromCode("k"));
    }

    @Test
    void fromCode_incorrectOperatorInit_notOk() {
        assertThrows(IllegalArgumentException.class,() -> Operation.fromCode("BALANCE"));
        assertThrows(IllegalArgumentException.class,() -> Operation.fromCode("bb"));
        assertThrows(IllegalArgumentException.class,() -> Operation.fromCode("B"));
    }
}
