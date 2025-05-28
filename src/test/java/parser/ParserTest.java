package parser;
import scannerparserlexer.ScannerParserLexer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ast.Program;

public class ParserTest {
    @Test
    void testParse() throws Exception {
        Program program = ScannerParserLexer.parse("class emptyClass {}");
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(1, program.classes.size(), "Program should contain one class");
        assertEquals("emptyClass", program.classes.get(0).name, "Class name should be 'emptyClass'");
        assertTrue(program.classes.get(0).fields.isEmpty(), "Class should have no fields");
        assertTrue(program.classes.get(0).methods.isEmpty(), "Class should have no methods");
    }
}