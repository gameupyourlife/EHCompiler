package semanticCheck;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;
import org.junit.jupiter.api.Test;
import ast.Program;
import ast.exprStatements.Unary;
import ast.Class;
import ast.Method;
import ast.statements.Return;
import ast.expressions.Binary;
import ast.expressions.BooleanConst;
import ast.expressions.IntConst;
import ast.expressions.BooleanLiteral;
import ast.expressions.Identifier;
import ast.Operator;
import ast.types.Type;

class ArithmeticBinarySemanticTest {

    // =================================
    // Arithmetic Operations Tests
    // =================================

    private Program makeProgram(Type returnType, Binary expr) {
        Method m = new Method(
                returnType,
                "test",
                List.of(),
                List.of(new Return(expr)),
                false);
        Class cls = new Class("TestClass");
        cls.fields = List.of();
        cls.methods = List.of(m);
        cls.parentClass = null;
        return new Program(List.of(cls));
    }

    @Test
    void testPlusWithInts() {
        Binary expr = new Binary(
                Operator.PLUS,
                new IntConst(1),
                new IntConst(2));
        assertDoesNotThrow(
                () -> semanticCheck.generateTast(makeProgram(Type.INT, expr)));
    }

    @Test
    void testMinusWithInts() {
        Binary expr = new Binary(
                Operator.MINUS,
                new IntConst(5),
                new IntConst(3));
        assertDoesNotThrow(
                () -> semanticCheck.generateTast(makeProgram(Type.INT, expr)));
    }

    @Test
    void testMultiplyWithInts() {
        Binary expr = new Binary(
                Operator.MULTIPLY,
                new IntConst(4),
                new IntConst(6));
        assertDoesNotThrow(
                () -> semanticCheck.generateTast(makeProgram(Type.INT, expr)));
    }

    @Test
    void testDivideWithInts() {
        Binary expr = new Binary(
                Operator.DIVIDE,
                new IntConst(10),
                new IntConst(2));
        assertDoesNotThrow(
                () -> semanticCheck.generateTast(makeProgram(Type.INT, expr)));
    }

    @Test
    void testModulusWithInts() {
        Binary expr = new Binary(
                Operator.MODULUS,
                new IntConst(9),
                new IntConst(4));
        assertDoesNotThrow(
                () -> semanticCheck.generateTast(makeProgram(Type.INT, expr)));
    }

    @Test
    void testPlusIntAndBooleanFail() {
        Binary bad = new Binary(
                Operator.PLUS,
                new IntConst(1),
                new BooleanLiteral(true));
        semanticError ex = assertThrows(
                semanticError.class,
                () -> semanticCheck.generateTast(makeProgram(Type.INT, bad)));
        String msg = ex.getMessage().toLowerCase();
        assertTrue(
                msg.contains("type") || msg.contains("int"));
    }

    // =================================
    // Test for Boolean Logic Operations
    // =================================

    private Program makeBinaryProgram(String name, Operator op) {
        Method m = new Method(
                Type.BOOLEAN,
                name,
                List.of(new ast.Parameter(Type.BOOLEAN, "a"), new ast.Parameter(Type.BOOLEAN, "b")),
                List.of(new Return(
                        new Binary(op, new Identifier("a"), new Identifier("b")))),
                false);
        Class cls = new Class("BooleanLogic");
        cls.fields = List.of();
        cls.methods = List.of(m);
        cls.parentClass = null;
        return new Program(List.of(cls));
    }

    private Program makeUnaryProgram(String name, ast.exprStatements.Unary expr) {
        Method m = new Method(
                Type.BOOLEAN,
                name,
                List.of(new ast.Parameter(Type.BOOLEAN, "a")),
                List.of(new Return(expr)),
                false);
        Class cls = new Class("BooleanLogic");
        cls.fields = List.of();
        cls.methods = List.of(m);
        cls.parentClass = null;
        return new Program(List.of(cls));
    }

    @Test
    void testAnd() {
        assertDoesNotThrow(() -> semanticCheck.generateTast(makeBinaryProgram("and", Operator.AND)));
    }

    @Test
    void testOr() {
        assertDoesNotThrow(() -> semanticCheck.generateTast(makeBinaryProgram("or", Operator.OR)));
    }

    @Test
    void testNot() {
        Unary expr = new Unary(Operator.NEGATE, new BooleanConst(true));
        assertDoesNotThrow(() -> semanticCheck.generateTast(makeUnaryProgram("not", expr)));
    }

    @Test
    void testNand() {
        Unary expr = new Unary(
                Operator.NEGATE,
                new Binary(Operator.AND, new Identifier("a"), new Identifier("b")));
        assertDoesNotThrow(() -> semanticCheck.generateTast(makeUnaryProgram("nand", expr)));
    }

    @Test
    void testNor() {
        Unary expr = new Unary(
                Operator.NEGATE,
                new Binary(Operator.OR, new Identifier("a"), new Identifier("b")));
        assertDoesNotThrow(() -> semanticCheck.generateTast(makeUnaryProgram("nor", expr)));
    }

    @Test
    void testTypeMismatchInAndFail() {
        ast.Method m = new ast.Method(
            Type.BOOLEAN,
            "and",
            List.of(
                new ast.Parameter(Type.BOOLEAN, "a"),
                new ast.Parameter(Type.BOOLEAN, "b")
            ),
            List.of(new Return(
                new Binary(
                    Operator.AND,
                    new BooleanConst(true),
                    new IntConst(1)
                )
            )),
            false
        );
        ast.Class cls = new ast.Class("BooleanLogic");
        cls.fields      = List.of();
        cls.methods     = List.of(m);
        cls.parentClass = null;
        Program badProgram = new Program(List.of(cls));

        semanticError ex = assertThrows(
            semanticError.class,
            () -> semanticCheck.generateTast(badProgram)
        );
        String msg = ex.getMessage().toLowerCase();
        assertTrue(msg.contains("type") || msg.contains("boolean"));
    }
}
