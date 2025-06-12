package parser;

import scannerparserlexer.ScannerParserLexer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ast.Program;

public class DeclarationsAndClassStructureTest {
    @Test
    void testEmptyClass() throws Exception {
        Program program = ScannerParserLexer.parse("class emptyClass {}");
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(1, program.classes.size(), "Program should contain one class");
        assertEquals("emptyClass", program.classes.get(0).name, "Class name should be 'emptyClass'");
        assertTrue(program.classes.get(0).fields.isEmpty(), "Class should have no fields");
        assertTrue(program.classes.get(0).methods.isEmpty(), "Class should have no methods");
    }

    @Test
    void testClassWithFields() throws Exception {
        Program program = ScannerParserLexer.parse("class ClassWithFields { int exampleInteger; boolean flag; }");
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(1, program.classes.size(), "Program should contain one class");
        assertEquals("ClassWithFields", program.classes.get(0).name, "Class name should be 'ClassWithFields'");
        assertEquals(2, program.classes.get(0).fields.size(), "Class should have two fields");
        assertEquals("exampleInteger", program.classes.get(0).fields.get(0).name,
                "First field name should be 'exampleInteger'");
        assertEquals("flag", program.classes.get(0).fields.get(1).name, "Second field name should be 'flag'");
        assertTrue(program.classes.get(0).methods.isEmpty(), "Class should have no methods");
    }

    @Test
    void testSingleMethod() throws Exception {
        Program program = ScannerParserLexer.parse("class ClassWithMethod { void exampleMethod() {} }");
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(1, program.classes.size(), "Program should contain one class");
        assertEquals("ClassWithMethod", program.classes.get(0).name, "Class name should be 'ClassWithMethod'");
        assertTrue(program.classes.get(0).fields.isEmpty(), "Class should have no fields");
        assertEquals(1, program.classes.get(0).methods.size(), "Class should have one method");
        assertEquals("exampleMethod", program.classes.get(0).methods.get(0).name,
                "Method name should be 'exampleMethod'");
        assertEquals(ast.Type.VOID, program.classes.get(0).methods.get(0).type, "Method return type should be 'void'");
    }

    @Test
    void testSingleFieldMethod() throws Exception {
        Program program = ScannerParserLexer
                .parse("class ClassWithFieldAndMethod { int exampleInteger; void exampleMethod() {} }");
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(1, program.classes.size(), "Program should contain one class");
        assertEquals("ClassWithFieldAndMethod", program.classes.get(0).name,
                "Class name should be 'ClassWithFieldAndMethod'");
        assertEquals(1, program.classes.get(0).fields.size(), "Class should have one field");
        assertEquals("exampleInteger", program.classes.get(0).fields.get(0).name,
                "Field name should be 'exampleInteger'");
        assertEquals(1, program.classes.get(0).methods.size(), "Class should have one method");
        assertEquals("exampleMethod", program.classes.get(0).methods.get(0).name,
                "Method name should be 'exampleMethod'");
        assertEquals(ast.Type.VOID, program.classes.get(0).methods.get(0).type, "Method return type should be 'void'");
    }

    @Test
    void testParamAndReturn() throws Exception {
        Program program = ScannerParserLexer
                .parse("class ClassWithParamAndReturn { int exampleMethod(int param) { return param; } }");
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(1, program.classes.size(), "Program should contain one class");
        assertEquals("ClassWithParamAndReturn", program.classes.get(0).name,
                "Class name should be 'ClassWithParamAndReturn'");
        assertTrue(program.classes.get(0).fields.isEmpty(), "Class should have no fields");
        assertEquals(1, program.classes.get(0).methods.size(), "Class should have one method");
        assertEquals("exampleMethod", program.classes.get(0).methods.get(0).name,
                "Method name should be 'exampleMethod'");
        assertEquals(ast.Type.INT, program.classes.get(0).methods.get(0).type, "Method return type should be 'int'");
        assertEquals(1, program.classes.get(0).methods.get(0).parameters.size(), "Method should have one parameter");
        assertEquals("param", program.classes.get(0).methods.get(0).parameters.get(0).name,
                "Parameter name should be 'param'");
        assertEquals(ast.Type.INT, program.classes.get(0).methods.get(0).parameters.get(0).type,
                "Parameter type should be 'int'");
    }
}