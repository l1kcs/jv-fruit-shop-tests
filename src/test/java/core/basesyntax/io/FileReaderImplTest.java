package core.basesyntax.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReader fileReaderTest;
    private File fileTest;
    private List<String> inputReportTest;

    @BeforeEach
    void setUp() {
        fileTest = new File(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource("reportToReadTest.csv"))
                        .getFile());
        fileReaderTest = new FileReaderImpl();
    }

    @Test
    void read_readingCsvFile_isOk() {
        inputReportTest = fileReaderTest.read(fileTest);
        assertNotNull(inputReportTest.get(0));
        assertEquals(9, inputReportTest.size());
        assertEquals("b,banana,20", inputReportTest.get(1));
    }

    @Test
    void read_readingEmptyFile_isOk() {
        File emptyFile = new File(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource("empty.csv"))
                        .getFile());
        inputReportTest = fileReaderTest.read(emptyFile);
        assertNotNull(inputReportTest);
        assertTrue(inputReportTest.isEmpty());
    }

    @Test
    void read_nonExistingFile_notOk() {
        File nonExisting = new File("src/test/resources/nonexistent.csv");
        assertThrows(RuntimeException.class, () -> fileReaderTest.read(nonExisting));
    }
}
