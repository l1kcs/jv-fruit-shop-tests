package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    void setUp() throws Exception {
        var transactionsField = Storage.class.getDeclaredField("transactions");
        transactionsField.setAccessible(true);
        ((List<?>) transactionsField.get(null)).clear();

        var fruitsField = Storage.class.getDeclaredField("fruits");
        fruitsField.setAccessible(true);
        ((Map<?, ?>) fruitsField.get(null)).clear();
    }

    @Test
    void getRecords_emptyList_isOk() {
        assertTrue(Storage.getRecords().isEmpty(),
                "list of records should be empty at the beginning of testing");
    }

    @Test
    void getFruits_emptyMap_isOk() {
        assertTrue(Storage.getFruits().isEmpty(),
                "Map fruits should be empty at the beginning of testing");
    }

    @Test
    void addRecord_nullValue_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Storage.addRecord(null));
    }

    @Test
    void getRecords_gettingAllElements_isOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.BALANCE,
                "apple",
                100);
        Storage.addRecord(transaction);
        Storage.addRecord(FruitTransaction.create(Operation.SUPPLY,
                "banana",
                20));
        assertNotNull(Storage.getRecords().get(0));
        assertEquals(transaction, Storage.getRecords().get(0));
        assertEquals(2,Storage.getRecords().size());
    }

    @Test
    void addRecord_recordInList_isOk() {
        FruitTransaction transaction = FruitTransaction.create(Operation.BALANCE,
                "apple",
                100);
        Storage.addRecord(transaction);
        assertFalse(Storage.getRecords().isEmpty());
        assertEquals(transaction,Storage.getRecords().get(0));
    }

    @Test
    void updateFruits_addFruit_isOk() {
        Storage.updateFruit("apple",70);
        Storage.updateFruit("apple",70);
        assertEquals(140,Storage.getFruits().get("apple"));
    }

    @Test
    void updateFruits_subtractFruit_isOk() {
        Storage.updateFruit("apple",100);
        Storage.updateFruit("apple",-30);
        assertEquals(70,Storage.getFruits().get("apple"));
    }

    @Test
    void updateFruit_zeroQuantity_isOk() {
        Storage.updateFruit("apple", 0);
        assertEquals(0, Storage.getFruits().get("apple"));
    }

    @Test
    void updateFruit_largeValue_isOk() {
        Storage.updateFruit("apple", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, Storage.getFruits().get("apple"));
    }

    @Test
    void setFruit_setBalanceFruit_isOk() {
        Storage.setFruit("apple", 100);
        Storage.setFruit("apple",250);
        assertEquals(250, Storage.getFruits().get("apple"));
    }

    @Test
    void setFruit_setNegativeBalance_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Storage.setFruit("apple", -100));
    }

    @Test
    void setFruit_zeroQuantity_isOk() {
        Storage.setFruit("apple", 0);
        assertEquals(0, Storage.getFruits().get("apple"));
    }

    @Test
    void setFruit_largeValue_isOk() {
        Storage.setFruit("apple", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, Storage.getFruits().get("apple"));
    }
}
