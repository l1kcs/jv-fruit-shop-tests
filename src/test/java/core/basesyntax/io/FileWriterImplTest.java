package core.basesyntax.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private FileWriter fileWriterTest;
    private File outputFileTest;

    @BeforeEach
    void setUp() {
        fileWriterTest = new FileWriterImpl();
        outputFileTest = new File("src/test/resources/testOutput.csv");
        if (outputFileTest.exists()) {
            outputFileTest.delete();
        }
    }

    @Test
    void write_validData_isOk() throws IOException {
        String content = "a,apple,10\nb,banana,20";
        fileWriterTest.write(content, outputFileTest);
        assertTrue(outputFileTest.exists());
        List<String> lines = Files.readAllLines(outputFileTest.toPath());
        assertEquals(2, lines.size());
        assertEquals("a,apple,10", lines.get(0));
        assertEquals("b,banana,20", lines.get(1));
    }

    @Test
    void write_emptyString_fileIsEmpty() throws IOException {
        fileWriterTest.write("", outputFileTest);
        List<String> lines = Files.readAllLines(outputFileTest.toPath());
        assertTrue(lines.isEmpty());
    }

    @AfterEach
    void tearDown() {
        outputFileTest.delete();
    }
}
