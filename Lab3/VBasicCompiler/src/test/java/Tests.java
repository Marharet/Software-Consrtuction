import org.junit.jupiter.api.Test;
import org.src.Errors.ErrorHandling;
import org.src.SBasic.SBasicImplementation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    private String normalizeString(String str) {
        return str.replace("\r\n", "\n").trim();
    }

    @Test
    public void Test1() throws Exception {
        // Arrange
        Path filePath = Paths.get("src/test/resources/Input/test1.bas");
        SBasicImplementation interpreter = new SBasicImplementation(filePath.toString());
        String expected = normalizeString(Files.readString(Paths.get("src/test/resources/Output/test1result.txt")));

        // Act & Assert
        try {
            String actual = tapSystemOut(() -> interpreter.run());
            System.setOut(System.out);
            assertEquals(expected, normalizeString(actual));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void Test2() throws Exception {
        // Arrange
        Path filePath = Paths.get("src/test/resources/Input/test2.bas");
        SBasicImplementation interpreter = new SBasicImplementation(filePath.toString());
        String expected = normalizeString(Files.readString(Paths.get("src/test/resources/Output/test2result.txt")));

        // Act
        String actual = tapSystemOut(() -> interpreter.run());

        // Assert
        assertEquals(expected, normalizeString(actual));
    }

    @Test
    public void Test3() throws Exception {
        // Arrange
        Path filePath = Paths.get("src/test/resources/Input/test3.bas");
        SBasicImplementation interpreter = new SBasicImplementation(filePath.toString());
        String expected = normalizeString(Files.readString(Paths.get("src/test/resources/Output/test3result.txt")));

        // Act
        String actual = tapSystemOut(() -> interpreter.run());

        // Assert
        assertEquals(expected, normalizeString(actual));
    }

    @Test
    public void Test4() throws Exception {
        // Arrange
        Path filePath = Paths.get("src/test/resources/Input/test4.bas");
        SBasicImplementation interpreter = new SBasicImplementation(filePath.toString());
        String expected = normalizeString(Files.readString(Paths.get("src/test/resources/Output/test4result.txt")));

        // Act
        String actual = tapSystemOut(() -> interpreter.run());

        // Assert
        assertEquals(expected, normalizeString(actual));
    }

    @Test
    public void Test5() throws Exception {
        // Arrange
        Path filePath = Paths.get("src/test/resources/Input/test5.bas");
        SBasicImplementation interpreter = new SBasicImplementation(filePath.toString());
        String expected = normalizeString(Files.readString(Paths.get("src/test/resources/Output/test5result.txt")));

        // Act
        String actual = tapSystemOut(() -> interpreter.run());

        // Assert
        assertEquals(expected, normalizeString(actual));
    }
}
