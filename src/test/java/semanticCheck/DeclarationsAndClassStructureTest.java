package semanticCheck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;
import org.junit.jupiter.api.Test;

import ast.Program;
import ast.Class;
import ast.Method;
import ast.Parameter;
import ast.Field;
import ast.types.Type;

public class DeclarationsAndClassStructureTest {

    @Test
    void testEmptyClass() throws Exception {
        Class emptyClass = new Class("EmptyClass");
        emptyClass.fields      = List.of();
        emptyClass.methods     = List.of();
        emptyClass.parentClass = null;

        Program program = new Program(List.of(emptyClass));
        assertDoesNotThrow(() -> semanticCheck.generateTast(program));
    }

    @Test
    void testClassWithFields() throws Exception {
        Field f1 = new Field("flag",           Type.BOOLEAN, null);
        Field f2 = new Field("exampleInteger", Type.INT,     null);
        Field f3 = new Field("exampleChar",    Type.CHAR,    null);

        Class c = new Class("ClassWithFields");
        c.fields      = List.of(f1, f2, f3);
        c.methods     = List.of();
        c.parentClass = null;

        Program program = new Program(List.of(c));
        assertDoesNotThrow(() -> semanticCheck.generateTast(program));
    }

    @Test
    void testSingleMethod() throws Exception {
        Class c = new Class("ClassWithMethod");
        c.fields      = List.of();
        c.methods     = List.of(
            new Method(Type.VOID, "doSomething", List.of(), List.of(), false)
        );
        c.parentClass = null;

        Program program = new Program(List.of(c));
        assertDoesNotThrow(() -> semanticCheck.generateTast(program));
    }

    @Test
    void testSingleFieldMethod() throws Exception {
        Class c = new Class("ClassWithFieldAndMethod");
        c.fields      = List.of(new Field("exampleInteger", Type.INT, null));
        c.methods     = List.of(new Method(Type.VOID, "exampleMethod", List.of(), List.of(), false));
        c.parentClass = null;

        Program program = new Program(List.of(c));
        assertDoesNotThrow(() -> semanticCheck.generateTast(program));
    }

    @Test
    void testParamAndReturn() throws Exception {
        Class c = new Class("ClassWithParamAndReturn");
        c.fields  = List.of();
        c.methods = List.of(
            new Method(
                Type.INT,
                "calculate",
                List.of(new Parameter(Type.INT, "input")),
                List.of(), 
                false
            )
        );
        c.parentClass = null;

        Program program = new Program(List.of(c));
        assertDoesNotThrow(() -> semanticCheck.generateTast(program));
    }

    @Test
    void testDuplicateClassNamesFail() {
        Class c1 = new Class("DupClass");
        c1.fields      = List.of();
        c1.methods     = List.of();
        c1.parentClass = null;

        Class c2 = new Class("DupClass");
        c2.fields      = List.of();
        c2.methods     = List.of();
        c2.parentClass = null;

        Program program = new Program(List.of(c1, c2));
        assertThrows(
            semanticError.class,
            () -> semanticCheck.generateTast(program)
        );
    }

    @Test
    void testDuplicateFieldNamesFail() {
        Class c = new Class("ClassWithDupFields");
        c.fields      = List.of(
            new Field("f", Type.INT,     null),
            new Field("f", Type.BOOLEAN, null)
        );
        c.methods     = List.of();
        c.parentClass = null;

        Program program = new Program(List.of(c));
        assertThrows(
            semanticError.class,
            () -> semanticCheck.generateTast(program)
        );
    }

    @Test
    void testDuplicateMethodNamesFail() {
        Class c = new Class("ClassWithDupMethods");
        c.fields      = List.of();
        c.methods     = List.of(
            new Method(Type.VOID, "m", List.of(), List.of(), false),
            new Method(Type.INT,  "m", List.of(), List.of(), false)
        );
        c.parentClass = null;

        Program program = new Program(List.of(c));
        assertThrows(
            semanticError.class,
            () -> semanticCheck.generateTast(program)
        );
    }
}
