package bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> map = gen.generateByteCode(program);
        byte[] bytes = map.get("EmptyClass");
        assertNotNull(bytes, "Bytecode darf nicht null sein");

        java.lang.Class<?> dyn = new ByteArrayClassLoader()
                .defineClass("EmptyClass", bytes);

        assertEquals("EmptyClass", dyn.getSimpleName());
        assertEquals(Object.class, dyn.getSuperclass());
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
        List<Field> fields = new ArrayList<Field>();
        fields.add(booleanField);
        fields.add(integerField);
        ast.Class classWithBool = new ast.Class("ClassWithFields");
        classWithBool.fields = fields;
        classWithBool.methods = List.of();
        classWithBool.parentClass = null;

        Program program = new Program(List.of(classWithBool));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ClassWithFields");
        assertNotNull(classBytes, "Bytecode darf nicht null sein");

        Class<?> dyn = new ByteArrayClassLoader()
                .defineClass("ClassWithFields", classBytes);

        java.lang.reflect.Field f = dyn.getDeclaredField("flag");
        assertEquals("flag", f.getName());
        assertEquals(boolean.class, f.getType());

        java.lang.reflect.Field i = dyn.getDeclaredField("exampleInteger");
        assertEquals("exampleInteger", i.getName());
        assertEquals(int.class, i.getType());
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

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ClassWithMethod");
        assertNotNull(classBytes, "Bytecode darf nicht null sein");

        Class<?> dyn = new ByteArrayClassLoader()
                .defineClass("ClassWithMethod", classBytes);

        java.lang.reflect.Method m = dyn.getDeclaredMethod("doSomething");
        assertEquals("doSomething", m.getName());
        assertEquals(void.class, m.getReturnType());
    }

    @Test
    void testSingleFieldMethod() throws Exception {
        ast.Class classWithFieldMethod = new ast.Class("ClassWithFieldMethod");
        classWithFieldMethod.fields = List.of(
                new Field("field", ast.Type.INT, null));
        classWithFieldMethod.methods = List.of(
                new ast.Method(
                        ast.Type.INT,
                        "getField",
                        List.of(),
                        List.of(),
                        false));
        classWithFieldMethod.parentClass = null;

        Program program = new Program(List.of(classWithFieldMethod));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ClassWithFieldMethod");
        assertNotNull(classBytes, "Bytecode darf nicht null sein");

        Class<?> dyn = new ByteArrayClassLoader()
                .defineClass("ClassWithFieldMethod", classBytes);

        java.lang.reflect.Method m = dyn.getDeclaredMethod("getField");
        assertEquals("getField", m.getName());
        assertEquals(int.class, m.getReturnType());
    }

    @Test
    void testParamAndReturnType() throws Exception {
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

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ClassWithParamAndReturn");
        assertNotNull(classBytes, "Bytecode darf nicht null sein");

        Class<?> dyn = new ByteArrayClassLoader()
                .defineClass("ClassWithParamAndReturn", classBytes);

        java.lang.reflect.Method m = dyn.getDeclaredMethod("calculate", int.class);
        assertEquals("calculate", m.getName());
        assertEquals(int.class, m.getReturnType());
    }
}
