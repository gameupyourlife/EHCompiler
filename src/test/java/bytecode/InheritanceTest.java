package bytecode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.List;

import org.junit.jupiter.api.Test;

import ast.Field;
import ast.Program;

public class InheritanceTest {

    @Test
    void testSingleInheritance() throws Exception {
        ast.Class superClass = new ast.Class("Super");
        superClass.fields = List.of();
        superClass.methods = List.of();
        superClass.parentClass = null;

        ast.Class subClass = new ast.Class("Sub");
        subClass.fields = List.of();
        subClass.methods = List.of();
        subClass.parentClass = "Super";

        Program program = new Program(List.of(superClass, subClass));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] superBytes = byteMap.get("Super");
        byte[] subBytes   = byteMap.get("Sub");

        assertNotNull(superBytes, "Bytecode für Super darf nicht null sein");
        assertNotNull(subBytes,   "Bytecode für Sub darf nicht null sein");

        ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> dynSuper = loader.defineClass("Super", superBytes);
        Class<?> dynSub   = loader.defineClass("Sub",   subBytes);

        assertEquals(Object.class, dynSuper.getSuperclass());
        assertEquals(dynSuper, dynSub.getSuperclass());
    }

    @Test
    void testInheritanceWithMembers() throws Exception {
        ast.Class base = new ast.Class("Base");
        base.fields = List.of(
            new Field("x", ast.types.Type.INT, null)
        );
        base.methods = List.of(
            new ast.Method(ast.types.Type.VOID, "foo", List.of(), List.of(), false)
        );
        base.parentClass = null;

        ast.Class derived = new ast.Class("Derived");
        derived.fields = List.of(
            new Field("c",    ast.types.Type.CHAR,    null),
            new Field("flag", ast.types.Type.BOOLEAN, null)
        );
        derived.methods = List.of(
            new ast.Method(ast.types.Type.VOID, "bar", List.of(), List.of(), false)
        );
        derived.parentClass = "Base";

        Program program = new Program(List.of(base, derived));
        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] baseBytes    = byteMap.get("Base");
        byte[] derivedBytes = byteMap.get("Derived");

        assertNotNull(baseBytes,    "Bytecode für Base darf nicht null sein");
        assertNotNull(derivedBytes, "Bytecode für Derived darf nicht null sein");

        ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> dynBase    = loader.defineClass("Base",    baseBytes);
        Class<?> dynDerived = loader.defineClass("Derived", derivedBytes);

        assertEquals(dynBase, dynDerived.getSuperclass());

        java.lang.reflect.Field c     = dynDerived.getDeclaredField("c");
        java.lang.reflect.Field flag  = dynDerived.getDeclaredField("flag");
        assertEquals("c",    c.getName());
        assertEquals("flag", flag.getName());

        java.lang.reflect.Method foo = dynDerived.getMethod("foo");
        assertEquals("foo", foo.getName());
        java.lang.reflect.Method bar = dynDerived.getMethod("bar");
        assertEquals("bar", bar.getName());
    }

    @Test
    void testDeepInheritanceChain() throws Exception {
        // A → B → C
        ast.Class a = new ast.Class("A");
        a.fields = List.of();
        a.methods = List.of();
        a.parentClass = null;

        ast.Class b = new ast.Class("B");
        b.fields = List.of();
        b.methods = List.of();
        b.parentClass = "A";

        ast.Class c = new ast.Class("C");
        c.fields = List.of();
        c.methods = List.of();
        c.parentClass = "B";

        Program program = new Program(List.of(a, b, c));
        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] aBytes = byteMap.get("A");
        byte[] bBytes = byteMap.get("B");
        byte[] cBytes = byteMap.get("C");

        assertNotNull(aBytes);
        assertNotNull(bBytes);
        assertNotNull(cBytes);

        ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> dynA = loader.defineClass("A", aBytes);
        Class<?> dynB = loader.defineClass("B", bBytes);
        Class<?> dynC = loader.defineClass("C", cBytes);

        assertEquals(Object.class, dynA.getSuperclass());
        assertEquals(dynA,          dynB.getSuperclass());
        assertEquals(dynB,          dynC.getSuperclass());
    }
}
