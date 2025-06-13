package semanticCheck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.example.semantic.semanticCheck;
import org.junit.jupiter.api.Test;

import ast.Field;
import ast.Program;

public class DeclarationsAndClassStructureTest {
    @Test
    void testEmptyClass() throws Exception {
        ast.Class emptyClass = new ast.Class("EmptyClass");
        emptyClass.fields = List.of();
        emptyClass.methods = List.of();
        emptyClass.parentClass = null;

        Program program = new Program(List.of(emptyClass));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram, "Semantic program should not be null");
        assertEquals(1, semanticProgram.classes.size(), "There should be one class in the program");
        assertEquals("EmptyClass", semanticProgram.classes.get(0).name, "Class name should be 'EmptyClass'");
        assertTrue(semanticProgram.classes.get(0).fields.isEmpty(), "Class should have no fields");
        assertTrue(semanticProgram.classes.get(0).methods.isEmpty(), "Class should have no methods");
    }

    @Test
    void testClassWithFields() throws Exception {
        Field booleanField = new Field(
                "flag",
                ast.Type.BOOLEAN,
                null);
        Field integerField = new Field(
                "exampleInteger",
                ast.Type.INT,
                null);
        Field charField = new Field(
                "exampleChar",
                ast.Type.CHAR,
                null);
        List<Field> fields = new ArrayList<Field>();
        fields.add(booleanField);
        fields.add(integerField);
        fields.add(charField);
        ast.Class classWithFields = new ast.Class("ClassWithFields");
        classWithFields.fields = fields;
        classWithFields.methods = List.of();
        classWithFields.parentClass = null;

        Program program = new Program(List.of(classWithFields));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram, "Semantic program should not be null");
        assertEquals(1, semanticProgram.classes.size(), "There should be one class in the program");
        assertEquals("ClassWithFields", semanticProgram.classes.get(0).name, "Class name should be 'ClassWithFields'");
        assertEquals(3, semanticProgram.classes.get(0).fields.size(), "Class should have two fields");
        assertEquals("flag", semanticProgram.classes.get(0).fields.get(0).name, "First field name should be 'flag'");
        assertEquals(ast.Type.BOOLEAN, semanticProgram.classes.get(0).fields.get(0).type,
                "First field type should be BOOLEAN");
        assertEquals("exampleInteger", semanticProgram.classes.get(0).fields.get(1).name,
                "Second field name should be 'exampleInteger'");
        assertEquals(ast.Type.INT, semanticProgram.classes.get(0).fields.get(1).type,
                "Second field type should be INT");
        assertEquals("exampleChar", semanticProgram.classes.get(0).fields.get(2).name,
                "Third field name should be 'exampleChar'");
        assertEquals(ast.Type.CHAR, semanticProgram.classes.get(0).fields.get(2).type,
                "Third field type should be CHAR");
    }

    @Test
    void testSingleMethod() throws Exception {
        ast.Class classWithMethod = new ast.Class("ClassWithMethod");
        classWithMethod.fields = List.of();
        classWithMethod.methods = List.of(
                new ast.Method(
                        ast.Type.VOID,
                        "doSomething",
                        List.of(),
                        List.of(),
                        false));
        classWithMethod.parentClass = null;

        Program program = new Program(List.of(classWithMethod));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram, "Semantic program should not be null");
        assertEquals(1, semanticProgram.classes.size(), "There should be one class in the program");
        assertEquals("ClassWithMethod", semanticProgram.classes.get(0).name, "Class name should be 'ClassWithMethod'");
        assertTrue(semanticProgram.classes.get(0).fields.isEmpty(), "Class should have no fields");
        assertEquals(1, semanticProgram.classes.get(0).methods.size(), "Class should have one method");
        assertEquals("exampleMethod", semanticProgram.classes.get(0).methods.get(0).name,
                "Method name should be 'exampleMethod'");
        assertEquals(ast.Type.VOID, semanticProgram.classes.get(0).methods.get(0).type,
                "Method return type should be 'void'");
    }

    @Test
    void testSingleFieldMethod() throws Exception {
        ast.Class classWithFieldAndMethod = new ast.Class("ClassWithFieldAndMethod");
        classWithFieldAndMethod.fields = List.of(
                new Field("exampleInteger", ast.Type.INT, null));
        classWithFieldAndMethod.methods = List.of(
                new ast.Method(
                        ast.Type.VOID,
                        "exampleMethod",
                        List.of(),
                        List.of(),
                        false));
        classWithFieldAndMethod.parentClass = null;

        Program program = new Program(List.of(classWithFieldAndMethod));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram, "Semantic program should not be null");
        assertEquals(1, semanticProgram.classes.size(), "There should be one class in the program");
        assertEquals("ClassWithFieldAndMethod", semanticProgram.classes.get(0).name,
                "Class name should be 'ClassWithFieldAndMethod'");
        assertEquals(1, semanticProgram.classes.get(0).fields.size(), "Class should have one field");
        assertEquals("exampleInteger", semanticProgram.classes.get(0).fields.get(0).name,
                "Field name should be 'exampleInteger'");
        assertEquals(ast.Type.INT, semanticProgram.classes.get(0).fields.get(0).type,
                "Field type should be INT");
        assertEquals(1, semanticProgram.classes.get(0).methods.size(), "Class should have one method");
        assertEquals("exampleMethod", semanticProgram.classes.get(0).methods.get(0).name,
                "Method name should be 'exampleMethod'");
        assertEquals(ast.Type.VOID, semanticProgram.classes.get(0).methods.get(0).type,
                "Method return type should be 'void'");
    }

    @Test
    void testParamAndReturn() throws Exception {
        ast.Class classWithParamAndReturn = new ast.Class("ClassWithParamAndReturn");
        classWithParamAndReturn.fields = List.of();
        classWithParamAndReturn.methods = List.of(
                new ast.Method(
                        ast.Type.INT,
                        "calculate",
                        List.of(new ast.Parameter(ast.Type.INT, "input")),
                        List.of(),
                        false));
        classWithParamAndReturn.parentClass = null;

        Program program = new Program(List.of(classWithParamAndReturn));
        Program semanticProgram = semanticCheck.generateTast(program);

        assertNotNull(semanticProgram, "Semantic program should not be null");
        assertEquals(1, semanticProgram.classes.size(), "There should be one class in the program");
        assertEquals("ClassWithParamAndReturn", semanticProgram.classes.get(0).name,
                "Class name should be 'ClassWithParamAndReturn'");
        assertTrue(semanticProgram.classes.get(0).fields.isEmpty(), "Class should have no fields");
        assertEquals(1, semanticProgram.classes.get(0).methods.size(), "Class should have one method");
        assertEquals("exampleMethod", semanticProgram.classes.get(0).methods.get(0).name,
                "Method name should be 'exampleMethod'");
        assertEquals(ast.Type.INT, semanticProgram.classes.get(0).methods.get(0).type,
                "Method return type should be 'int'");
        assertEquals(1, semanticProgram.classes.get(0).methods.get(0).parameters.size(),
                "Method should have one parameter");
        assertEquals("param", semanticProgram.classes.get(0).methods.get(0).parameters.get(0).name,
                "Parameter name should be 'param'");
        assertEquals(ast.Type.INT, semanticProgram.classes.get(0).methods.get(0).parameters.get(0).type,
                "Parameter type should be 'int'");
    }
}