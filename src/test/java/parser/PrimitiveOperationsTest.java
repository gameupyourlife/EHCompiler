package parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import scannerparserlexer.ScannerParserLexer;

import ast.Program;
import ast.exprStatements.Unary;
import ast.Class;
import ast.Method;
import ast.statements.Return;
import ast.expressions.Binary;
import ast.expressions.Identifier;
import ast.Operator;

class PrimitiveOperationsTest {

    private Return getSingleReturn(String src) throws Exception {
        Program program = ScannerParserLexer.parse(src);
        Class cls = program.classes.get(0);
        Method m = cls.methods.get(0);
        assertEquals(1, m.statement.size());
        assertTrue(m.statement.get(0) instanceof Return);
        return (Return) m.statement.get(0);
    }

    @Test
    void testAdd() throws Exception {
        String src =
            "class ArithmeticOps { " +
            "  int add(int a, int b) { return a + b; }" +
            "}";
        Return ret = getSingleReturn(src);
        assertTrue(ret.expression instanceof Binary);
        Binary bin = (Binary) ret.expression;
        assertEquals(Operator.PLUS, bin.operator);
        assertTrue(bin.left  instanceof Identifier);
        assertEquals("a", ((Identifier)bin.left).name);
        assertTrue(bin.right instanceof Identifier);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testSubtract() throws Exception {
        String src =
            "class ArithmeticOps { " +
            "  int subtract(int a, int b) { return a - b; }" +
            "}";
        Return ret = getSingleReturn(src);
        assertTrue(ret.expression instanceof Binary);
        Binary bin = (Binary) ret.expression;
        assertEquals(Operator.MINUS, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testMultiply() throws Exception {
        String src =
            "class ArithmeticOps { " +
            "  int multiply(int a, int b) { return a * b; }" +
            "}";
        Return ret = getSingleReturn(src);
        Binary bin = (Binary) ret.expression;
        assertEquals(Operator.MULTIPLY, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testDivide() throws Exception {
        String src =
            "class ArithmeticOps { " +
            "  int divide(int a, int b) { return a / b; }" +
            "}";
        Return ret = getSingleReturn(src);
        Binary bin = (Binary) ret.expression;
        assertEquals(Operator.DIVIDE, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testModulus() throws Exception {
        String src =
            "class ArithmeticOps { " +
            "  int modulus(int a, int b) { return a % b; }" +
            "}";
        Return ret = getSingleReturn(src);
        Binary bin = (Binary) ret.expression;
        assertEquals(Operator.MODULUS, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    // =======================
    // Test Boolean Operations
    // =======================

    @Test
    void testParseAndAst() throws Exception {
        String src =
            "class BooleanLogic { " +
            "  boolean and(boolean a, boolean b) { return a && b; }" +
            "}";
        Return ret = getSingleReturn(src);
        assertTrue(ret.expression instanceof Binary);
        Binary bin = (Binary) ret.expression;
        assertEquals(Operator.AND, bin.operator);
        assertTrue(bin.left  instanceof Identifier);
        assertEquals("a", ((Identifier)bin.left).name);
        assertTrue(bin.right instanceof Identifier);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testParseOrAst() throws Exception {
        String src =
            "class BooleanLogic { " +
            "  boolean or(boolean a, boolean b) { return a || b; }" +
            "}";
        Return ret = getSingleReturn(src);
        Binary bin = (Binary) ret.expression;
        assertEquals(Operator.OR, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testParseXorAst() throws Exception {
        String src =
            "class BooleanLogic { " +
            "  boolean xor(boolean a, boolean b) { return a ^ b; }" +
            "}";
        Return ret = getSingleReturn(src);
        Binary bin = (Binary) ret.expression;
        assertEquals(Operator.XOR, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testParseNotAst() throws Exception {
        String src =
            "class BooleanLogic { " +
            "  boolean not(boolean a) { return !a; }" +
            "}";
        Return ret = getSingleReturn(src);
        assertTrue(ret.expression instanceof Unary);
        Unary un = (Unary) ret.expression;
        assertEquals(Operator.NEGATE, un.operator);
        assertTrue(un.expression instanceof Identifier);
        assertEquals("a", ((Identifier)un.expression).name);
    }

    @Test
    void testParseNandAst() throws Exception {
        String src =
            "class BooleanLogic { " +
            "  boolean nand(boolean a, boolean b) { return !(a && b); }" +
            "}";
        Return ret = getSingleReturn(src);
        Unary un = (Unary) ret.expression;
        assertEquals(Operator.NEGATE, un.operator);
        assertTrue(un.expression instanceof Binary);
        Binary bin = (Binary) un.expression;
        assertEquals(Operator.AND, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testParseNorAst() throws Exception {
        String src =
            "class BooleanLogic { " +
            "  boolean nor(boolean a, boolean b) { return !(a || b); }" +
            "}";
        Return ret = getSingleReturn(src);
        Unary un = (Unary) ret.expression;
        assertEquals(Operator.NEGATE, un.operator);
        Binary bin = (Binary) un.expression;
        assertEquals(Operator.OR, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }

    @Test
    void testParseXnorAst() throws Exception {
        String src =
            "class BooleanLogic { " +
            "  boolean xnor(boolean a, boolean b) { return !(a ^ b); }" +
            "}";
        Return ret = getSingleReturn(src);
        Unary un = (Unary) ret.expression;
        assertEquals(Operator.NEGATE, un.operator);
        Binary bin = (Binary) un.expression;
        assertEquals(Operator.XOR, bin.operator);
        assertEquals("a", ((Identifier)bin.left).name);
        assertEquals("b", ((Identifier)bin.right).name);
    }
}
