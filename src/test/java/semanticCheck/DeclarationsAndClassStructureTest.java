package semanticCheck;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.example.semantic.semanticCheck;
import org.junit.jupiter.api.Test;
import ast.Program;
import ast.Class;
import ast.Method;
import ast.Parameter;
import ast.Field;

public class DeclarationsAndClassStructureTest {

    @Test
    void testEmptyClass() throws Exception {
        Class emptyClass = new Class("EmptyClass");
        emptyClass.fields      = List.of();
        emptyClass.methods     = List.of();
        emptyClass.parentClass = null;

        Program program = new Program(List.of(emptyClass));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram);
        assertEquals(1, semanticProgram.classes.size());
        Class cls = semanticProgram.classes.get(0);
        assertEquals("EmptyClass", cls.name);
        assertTrue(cls.fields.isEmpty());
        assertTrue(cls.methods.isEmpty());
    }

    @Test
    void testClassWithFields() throws Exception {
        Field booleanField = new Field("flag", ast.types.Type.BOOLEAN, null);
        Field integerField = new Field("exampleInteger", ast.types.Type.INT, null);
        Field charField    = new Field("exampleChar", ast.types.Type.CHAR, null);

        Class classWithFields = new Class("ClassWithFields");
        classWithFields.fields      = List.of(booleanField, integerField, charField);
        classWithFields.methods     = List.of();
        classWithFields.parentClass = null;

        Program program = new Program(List.of(classWithFields));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram);
        assertEquals(1, semanticProgram.classes.size());
        Class cls = semanticProgram.classes.get(0);
        assertEquals("ClassWithFields", cls.name);
        assertEquals(3, cls.fields.size());
        assertEquals("flag",           cls.fields.get(0).name);
        assertEquals(ast.types.Type.BOOLEAN, cls.fields.get(0).type);
        assertEquals("exampleInteger",cls.fields.get(1).name);
        assertEquals(ast.types.Type.INT,     cls.fields.get(1).type);
        assertEquals("exampleChar",    cls.fields.get(2).name);
        assertEquals(ast.types.Type.CHAR,    cls.fields.get(2).type);
        assertTrue(cls.methods.isEmpty());
    }

    @Test
    void testSingleMethod() throws Exception {
        Class classWithMethod = new Class("ClassWithMethod");
        classWithMethod.fields      = List.of();
        classWithMethod.methods     = List.of(
            new Method(ast.types.Type.VOID, "doSomething", List.of(), List.of(), false)
        );
        classWithMethod.parentClass = null;

        Program program = new Program(List.of(classWithMethod));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram);
        assertEquals(1, semanticProgram.classes.size());
        Class cls = semanticProgram.classes.get(0);
        assertEquals("ClassWithMethod", cls.name);
        assertTrue(cls.fields.isEmpty());
        assertEquals(1, cls.methods.size());
        assertEquals("doSomething", cls.methods.get(0).name);
        assertEquals(ast.types.Type.VOID, cls.methods.get(0).type);
    }

    @Test
    void testSingleFieldMethod() throws Exception {
        Class classWithFieldAndMethod = new Class("ClassWithFieldAndMethod");
        classWithFieldAndMethod.fields  = List.of(
            new Field("exampleInteger", ast.types.Type.INT, null)
        );
        classWithFieldAndMethod.methods = List.of(
            new Method(ast.types.Type.VOID, "exampleMethod", List.of(), List.of(), false)
        );
        classWithFieldAndMethod.parentClass = null;

        Program program = new Program(List.of(classWithFieldAndMethod));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram);
        assertEquals(1, semanticProgram.classes.size());
        Class cls = semanticProgram.classes.get(0);
        assertEquals("ClassWithFieldAndMethod", cls.name);
        assertEquals(1, cls.fields.size());
        assertEquals("exampleInteger", cls.fields.get(0).name);
        assertEquals(ast.types.Type.INT, cls.fields.get(0).type);
        assertEquals(1, cls.methods.size());
        assertEquals("exampleMethod", cls.methods.get(0).name);
        assertEquals(ast.types.Type.VOID, cls.methods.get(0).type);
    }

    @Test
    void testParamAndReturn() throws Exception {
        Class classWithParamAndReturn = new Class("ClassWithParamAndReturn");
        classWithParamAndReturn.fields  = List.of();
        classWithParamAndReturn.methods = List.of(
            new Method(
                ast.types.Type.INT,
                "calculate",
                List.of(new Parameter(ast.types.Type.INT, "input")),
                List.of(),
                false
            )
        );
        classWithParamAndReturn.parentClass = null;

        Program program = new Program(List.of(classWithParamAndReturn));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram);
        assertEquals(1, semanticProgram.classes.size());
        Class cls = semanticProgram.classes.get(0);
        assertEquals("ClassWithParamAndReturn", cls.name);
        assertTrue(cls.fields.isEmpty());
        assertEquals(1, cls.methods.size());
        assertEquals("calculate", cls.methods.get(0).name);
        assertEquals(ast.types.Type.INT, cls.methods.get(0).type);
        assertEquals(1, cls.methods.get(0).parameters.size());
        assertEquals("input", cls.methods.get(0).parameters.get(0).name);
        assertEquals(ast.types.Type.INT, cls.methods.get(0).parameters.get(0).type);
    }
}
