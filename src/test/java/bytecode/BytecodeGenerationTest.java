package bytecode;

import ast.Program;
import ast.Field;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BytecodeGenerationTest {

    public static class ByteArrayClassLoader extends ClassLoader {
        public java.lang.Class<?> defineClass(String name, byte[] b) {
            return super.defineClass(name, b, 0, b.length);
        }
    }

@Test
    void testGenerateEmptyClass() throws Exception {
        ast.Class emptyClass = new ast.Class("EmptyClass");
        emptyClass.fields      = List.of();
        emptyClass.methods     = List.of();
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
    void testGenerateClassWithFields() throws Exception {
        Field booleanField = new Field(
            "flag",               
            ast.Type.BOOLEAN,         
            null                  
        );
        Field integerField = new Field(
            "exampleInteger",               
            ast.Type.INT,         
            null                  
        );
        List<Field> fields = new ArrayList<Field>();
        fields.add(booleanField);
        fields.add(integerField);
        ast.Class classWithBool = new ast.Class("ClassWithFields");
        classWithBool.fields      = fields;
        classWithBool.methods     = List.of();
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
}
