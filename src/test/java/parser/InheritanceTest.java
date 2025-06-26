package parser;

import scannerparserlexer.ScannerParserLexer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ast.Program;

public class InheritanceTest {
    @Test
    void testNoExtendsClause() throws Exception {
        Program program = ScannerParserLexer.parse("class NoExtends {}");
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(1, program.classes.size(), "Program should contain one class");
        assertEquals("NoExtends", program.classes.get(0).name, "Class name should be 'NoExtends'");
    }

    @Test
    void testSingleInheritance() throws Exception {
        Program program = ScannerParserLexer.parse("class Sub extends Super {}");
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(1, program.classes.size(), "Program should contain one class");
        assertEquals("Sub", program.classes.get(0).name, "Class name should be 'Sub'");
        assertEquals("Super", program.classes.get(0).parentClass, "ParentClass should be 'Super'");
        assertTrue(program.classes.get(0).fields.isEmpty(), "Class should have no fields");
        assertTrue(program.classes.get(0).methods.isEmpty(), "Class should have no methods");
    }

    @Test
    void testMultipleClassesInheritance() throws Exception {
        Program program = ScannerParserLexer.parse(
            "class A {} class B extends A {}"
        );
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(2, program.classes.size(), "Program should contain two classes");

        // "A" ohne extends
        assertEquals("A", program.classes.get(0).name, "First class name should be 'A'");

        // "B" extends A
        assertEquals("B", program.classes.get(1).name, "Second class name should be 'B'");
        assertEquals("A", program.classes.get(1).parentClass, "Superclass of 'B' should be 'A'");
    }

    @Test
    void testInheritanceWithMembers() throws Exception {
        String source = 
            "class Base { int x; void foo() {} } " +
            "class Derived extends Base { char c; boolean flag; void bar() {} }";
        Program program = ScannerParserLexer.parse(source);

        assertNotNull(program, "Parsed program should not be null");
        assertEquals(2, program.classes.size(), "Program should contain two classes");

        // Base-Klasse
        assertEquals("Base", program.classes.get(0).name, "First class should be 'Base'");
        assertTrue(program.classes.get(0).fields.size() == 1, "Base should have one field");
        assertEquals("x", program.classes.get(0).fields.get(0).name, "Field name should be 'x'");
        assertEquals(1, program.classes.get(0).methods.size(), "Base should have one method");
        assertEquals("foo", program.classes.get(0).methods.get(0).name, "Method name should be 'foo'");

        // Derived-Klasse
        assertEquals("Derived", program.classes.get(1).name, "Second class should be 'Derived'");
        assertEquals("Base", program.classes.get(1).parentClass, "Derived should extend 'Base'");
        assertEquals(2, program.classes.get(1).fields.size(), "Derived should have two fields");
        assertEquals("c", program.classes.get(1).fields.get(0).name, "First field of Derived should be 'c'");
        assertEquals("flag", program.classes.get(1).fields.get(1).name, "Second field of Derived should be 'flag'");
        assertEquals(1, program.classes.get(1).methods.size(), "Derived should have one method");
        assertEquals("bar", program.classes.get(1).methods.get(0).name, "Method of Derived should be 'bar'");
    }

    @Test
    void testDeepInheritanceChain() throws Exception {
        Program program = ScannerParserLexer.parse(
            "class A {} class B extends A {} class C extends B {}"
        );
        assertNotNull(program, "Parsed program should not be null");
        assertEquals(3, program.classes.size(), "Program should contain three classes");

        assertEquals("A", program.classes.get(0).name, "First class should be 'A'");

        assertEquals("B", program.classes.get(1).name, "Second class should be 'B'");
        assertEquals("A", program.classes.get(1).parentClass, "Superclass of 'B' should be 'A'");

        assertEquals("C", program.classes.get(2).name, "Third class should be 'C'");
        assertEquals("B", program.classes.get(2).parentClass, "Superclass of 'C' should be 'B'");
    }
}

