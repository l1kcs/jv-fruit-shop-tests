package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private List<String> linesFromFileReader;
    private DataConverter dataConverterTest;

    @BeforeEach
    void setUp() {
        Storage.clearRecords();
        dataConverterTest = new DataConverterImpl();
        linesFromFileReader = new ArrayList<>();
    }

    @Test
    void convertToTransaction_nullInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverterTest.convertToTransaction(null));
    }

    @Test
    void convertToTransaction_emptyLineInHeaderList_notOk() {
        linesFromFileReader.add("");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverterTest.convertToTransaction(linesFromFileReader));
    }

    @Test
    void convertToTransaction_emptyLineInDataList_notOk() {
        linesFromFileReader.add("header");
        linesFromFileReader.add("");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverterTest.convertToTransaction(linesFromFileReader));
    }

    @Test
    void convertToTransaction_addingTransaction_isOk() {
        linesFromFileReader.add("header");
        linesFromFileReader.add("b,apple,100");
        int sizeBefore = Storage.getRecords().size();
        dataConverterTest.convertToTransaction(linesFromFileReader);
        assertEquals(sizeBefore + 1, Storage.getRecords().size());
    }

    @Test
    void convertToTransaction_invalidLineFormat_notOk() {
        linesFromFileReader.add("header");
        linesFromFileReader.add("b,apple");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverterTest.convertToTransaction(linesFromFileReader));
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        linesFromFileReader.add("header");
        linesFromFileReader.add("x,apple,10");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverterTest.convertToTransaction(linesFromFileReader));
    }

    @Test
    void convertToTransaction_multipleLines_isOk() {
        linesFromFileReader.add("header");
        linesFromFileReader.add("b,apple,100");
        linesFromFileReader.add("s,banana,50");
        int sizeBefore = Storage.getRecords().size();
        dataConverterTest.convertToTransaction(linesFromFileReader);
        assertEquals(sizeBefore + 2, Storage.getRecords().size());
    }

    @Test
    void convertToTransaction_lineWithSpaces_isOk() {
        linesFromFileReader.add("header");
        linesFromFileReader.add(" b , apple , 100 "); // з пробілами

        int sizeBefore = Storage.getRecords().size();
        dataConverterTest.convertToTransaction(linesFromFileReader);
        assertEquals(sizeBefore + 1, Storage.getRecords().size());
    }

}
