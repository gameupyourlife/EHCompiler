package semanticCheck;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;
import org.junit.jupiter.api.Test;
import ast.Program;
import ast.Class;
import ast.Method;
import ast.statements.Return;
import ast.expressions.Binary;
import ast.expressions.IntConst;
import ast.expressions.BooleanLiteral;
import ast.Operator;
import ast.types.Type;

class ArithmeticBinarySemanticTest {

    private Program makeProgram(Type returnType, Binary expr) {
        Method m = new Method(
            returnType,
            "test",
            List.of(),
            List.of(new Return(expr)),
            false
        );
        Class cls = new Class("TestClass");
        cls.fields      = List.of();
        cls.methods     = List.of(m);
        cls.parentClass = null;
        return new Program(List.of(cls));
    }

    @Test
    void testPlusWithInts() {
        Binary expr = new Binary(
            Operator.PLUS,
            new IntConst(1),
            new IntConst(2)
        );
        assertDoesNotThrow(
            () -> semanticCheck.generateTast(makeProgram(Type.INT, expr))
        );
    }

    @Test
    void testMinusWithInts() {
        Binary expr = new Binary(
            Operator.MINUS,
            new IntConst(5),
            new IntConst(3)
        );
        assertDoesNotThrow(
            () -> semanticCheck.generateTast(makeProgram(Type.INT, expr))
        );
    }

    @Test
    void testMultiplyWithInts() {
        Binary expr = new Binary(
            Operator.MULTIPLY,
            new IntConst(4),
            new IntConst(6)
        );
        assertDoesNotThrow(
            () -> semanticCheck.generateTast(makeProgram(Type.INT, expr))
        );
    }

    @Test
    void testDivideWithInts() {
        Binary expr = new Binary(
            Operator.DIVIDE,
            new IntConst(10),
            new IntConst(2)
        );
        assertDoesNotThrow(
            () -> semanticCheck.generateTast(makeProgram(Type.INT, expr))
        );
    }

    @Test
    void testModulusWithInts() {
        Binary expr = new Binary(
            Operator.MODULUS,
            new IntConst(9),
            new IntConst(4)
        );
        assertDoesNotThrow(
            () -> semanticCheck.generateTast(makeProgram(Type.INT, expr))
        );
    }

    @Test
    void testPlusIntAndBoolean() {
        Binary bad = new Binary(
            Operator.PLUS,
            new IntConst(1),
            new BooleanLiteral(true)
        );
        semanticError ex = assertThrows(
            semanticError.class,
            () -> semanticCheck.generateTast(makeProgram(Type.INT, bad))
        );
        String msg = ex.getMessage().toLowerCase();
        assertTrue(
            msg.contains("type") || msg.contains("int")
        );
    }
}
