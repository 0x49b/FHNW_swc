import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeCoverageTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    public final void testPathExample() {
        CodeCoverage clzzUnderTst = new CodeCoverage();
        String value = clzzUnderTst.pathExample(true);
        System.out.println("this is the value: " + value);
        assertEquals("true", value, "should be true");
    }

}
