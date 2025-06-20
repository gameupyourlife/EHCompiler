package semanticCheck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;
import org.junit.jupiter.api.Test;

import ast.Program;
import ast.Parameter;
import ast.statements.Return;
import ast.expressions.IntConst;
import ast.expressions.BooleanConst;
import ast.expressions.Identifier;
import ast.types.Type;

class InheritanceTest {

    @Test
    void testValidSingleInheritance() {
        ast.Class a = new ast.Class("A");
        a.fields = List.of();
        a.methods = List.of();
        a.parentClass = null;

        ast.Class b = new ast.Class("B");
        b.fields = List.of();
        b.methods = List.of();
        b.parentClass = "A";

        Program program = new Program(List.of(a, b));
        assertDoesNotThrow(() -> semanticCheck.generateTast(program));
    }

    @Test
    void testUnknownSuperclassFail() {
        ast.Class b = new ast.Class("B");
        b.fields = List.of();
        b.methods = List.of();
        b.parentClass = "Unknown";

        Program program = new Program(List.of(b));
        assertThrows(semanticError.class, () -> semanticCheck.generateTast(program));
    }

    @Test
    void testCircularInheritanceFail() {
        ast.Class a = new ast.Class("A");
        a.fields = List.of();
        a.methods = List.of();
        a.parentClass = "B";

        ast.Class b = new ast.Class("B");
        b.fields = List.of();
        b.methods = List.of();
        b.parentClass = "A";

        Program program = new Program(List.of(a, b));
        assertThrows(semanticError.class, () -> semanticCheck.generateTast(program));
    }

    @Test
    void testOverrideWithSameSignature() {
        ast.Method fA = new ast.Method(
                Type.INT,
                "f",
                List.of(),
                List.of(new Return(new IntConst(1))),
                false);
        ast.Class a = new ast.Class("A");
        a.fields = List.of();
        a.methods = List.of(fA);
        a.parentClass = null;

        ast.Method fB = new ast.Method(
                Type.INT,
                "f",
                List.of(),
                List.of(new Return(new IntConst(2))),
                false);
        ast.Class b = new ast.Class("B");
        b.fields = List.of();
        b.methods = List.of(fB);
        b.parentClass = "A";

        Program program = new Program(List.of(a, b));
        assertDoesNotThrow(() -> semanticCheck.generateTast(program));
    }

    @Test
    void testOverrideWithWrongReturnTypeFail() {
        ast.Method fA = new ast.Method(
                Type.INT,
                "f",
                List.of(),
                List.of(new Return(new IntConst(1))),
                false);
        ast.Class a = new ast.Class("A");
        a.fields = List.of();
        a.methods = List.of(fA);
        a.parentClass = null;

        ast.Method fB = new ast.Method(
                Type.BOOLEAN,
                "f",
                List.of(),
                List.of(new Return(new BooleanConst(true))),
                false);
        ast.Class b = new ast.Class("B");
        b.fields = List.of();
        b.methods = List.of(fB);
        b.parentClass = "A";

        Program program = new Program(List.of(a, b));
        assertThrows(semanticError.class, () -> semanticCheck.generateTast(program));
    }

    @Test
    void testOverrideWithParamMismatchFail() {
        ast.Method fA = new ast.Method(
                Type.INT,
                "f",
                List.of(new Parameter(Type.INT, "x")),
                List.of(new Return(new Identifier("x"))),
                false);
        ast.Class a = new ast.Class("A");
        a.fields = List.of();
        a.methods = List.of(fA);
        a.parentClass = null;

        ast.Method fB = new ast.Method(
                Type.INT,
                "f",
                List.of(),
                List.of(new Return(new IntConst(0))),
                false);
        ast.Class b = new ast.Class("B");
        b.fields = List.of();
        b.methods = List.of(fB);
        b.parentClass = "A";

        Program program = new Program(List.of(a, b));
        assertThrows(semanticError.class, () -> semanticCheck.generateTast(program));
    }
}
