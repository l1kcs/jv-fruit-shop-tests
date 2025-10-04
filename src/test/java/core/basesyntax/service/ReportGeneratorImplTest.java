package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {

    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clearFruits();
    }

    @Test
    void getReport_emptyStorage_isOk() {
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity", report);
    }

    @Test
    void getReport_singleFruit_isOk() {
        Storage.setFruit("apple", 10);
        String report = reportGenerator.getReport();
        String expected = "fruit,quantity" + System.lineSeparator() + "apple,10";
        assertEquals(expected, report);
    }

    @Test
    void getReport_multipleFruits_isOk() {
        Storage.setFruit("apple", 10);
        Storage.setFruit("banana", 20);
        String report = reportGenerator.getReport();

        // порядок у HashMap непередбачуваний, перевіряємо на наявність рядків
        assertTrue(report.startsWith("fruit,quantity"));
        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,20"));

        // перевіряємо кількість рядків
        long linesCount = report.lines().count();
        assertEquals(3, linesCount); // header + 2 фрукти
    }

    @Test
    void getReport_zeroAndLargeQuantities_isOk() {
        Storage.setFruit("apple", 0);
        Storage.setFruit("banana", Integer.MAX_VALUE);
        String report = reportGenerator.getReport();

        assertTrue(report.contains("apple,0"));
        assertTrue(report.contains("banana," + Integer.MAX_VALUE));
    }

}
