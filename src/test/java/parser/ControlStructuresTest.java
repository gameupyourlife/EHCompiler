package parser;

import scannerparserlexer.ScannerParserLexer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ast.Program;
import ast.Class;
import ast.statements.If;
import ast.statements.Return;
import ast.types.Type;

class IfElseParsingTest {

    @Test
    void testParseIsZeroOrPositive() throws Exception {
        String src = "class IfElse { boolean isZeroOrPositive(int number) { " +
                     "if (number > 0) { return true; } " +
                     "if (number == 0) { return true; } " +
                     "return false; } }";
        Program program = ScannerParserLexer.parse(src);
        assertNotNull(program);
        assertEquals(1, program.classes.size());

        Class cls = program.classes.get(0);
        assertEquals("IfElse", cls.name);
        assertEquals(1, cls.methods.size());

        var method = cls.methods.get(0);
        assertEquals("isZeroOrPositive", method.name);
        assertEquals(Type.BOOLEAN, method.type);
        assertEquals(1, method.parameters.size());
        assertEquals("number", method.parameters.get(0).name);
        assertEquals(Type.INT, method.parameters.get(0).type);

        assertEquals(3, method.statement.size());

        assertTrue(method.statement.get(0) instanceof If);
        If if1 = (If) method.statement.get(0);
        assertNull(if1.elseStatement);
        assertTrue(if1.thenStatement instanceof Return);

        assertTrue(method.statement.get(1) instanceof If);
        If if2 = (If) method.statement.get(1);
        assertNull(if2.elseStatement);
        assertTrue(if2.thenStatement instanceof Return);

        assertTrue(method.statement.get(2) instanceof Return);
    }

    @Test
    void testParseIsEven() throws Exception {
        String src = "class IfElse { boolean isEven(int number) { " +
                     "if (number % 2 == 0) { return true; } else { return false; } } }";
        Program program = ScannerParserLexer.parse(src);
        Class cls = program.classes.get(0);
        var method = cls.methods.get(0);

        // Ein If mit Else
        assertEquals(1, method.statement.size());
        assertTrue(method.statement.get(0) instanceof If);
        If ifStmt = (If) method.statement.get(0);
        assertNotNull(ifStmt.elseStatement);
        assertTrue(ifStmt.thenStatement instanceof Return);
        assertTrue(ifStmt.elseStatement instanceof Return);
    }

    @Test
    void testParseIsInRange() throws Exception {
        String src = "class IfElse { boolean isInRange(int number,int min,int max) { " +
                     "if (number >= min && number <= max) { return true; } else { return false; } } }";
        Program program = ScannerParserLexer.parse(src);
        Class cls = program.classes.get(0);
        var method = cls.methods.get(0);

        // Ein If mit Else
        assertEquals(1, method.statement.size());
        assertTrue(method.statement.get(0) instanceof If);
        If ifStmt = (If) method.statement.get(0);
        assertNotNull(ifStmt.elseStatement);
        assertTrue(ifStmt.thenStatement instanceof Return);
        assertTrue(ifStmt.elseStatement instanceof Return);
    }
}
