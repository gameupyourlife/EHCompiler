package bytecode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ast.Program;
import ast.exprStatements.Unary;
import ast.Class;
import ast.Method;
import ast.Parameter;
import ast.statements.Return;
import ast.expressions.Binary;
import ast.expressions.Identifier;
import ast.types.Type;
import ast.Operator;

public class PrimitiveOperationsTest {

    @Test
    void testAdd() throws Exception {
        Class cls = new Class("ArithmeticOps");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.INT,
                "add",
                List.of(new Parameter(Type.INT, "a"), new Parameter(Type.INT, "b")),
                List.of(new Return(new Binary(Operator.PLUS, new Identifier("a"), new Identifier("b")))),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ArithmeticOps");
        assertNotNull(classBytes, "Bytecode for ArithmeticOps must not be null");

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("ArithmeticOps", classBytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("add", int.class, int.class);
        assertEquals("add",       m.getName());
        assertEquals(int.class,   m.getReturnType());
        assertEquals(2, m.getParameterCount(), "Method 'add' should have 2 parameters");
        assertEquals(int.class, m.getParameterTypes()[0], "First parameter should be of type int");
        assertEquals(int.class, m.getParameterTypes()[1], "Second parameter should be of type int");
    }

    @Test
    void testSubtract() throws Exception {
        Class cls = new Class("ArithmeticOps");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.INT,
                "subtract",
                List.of(new Parameter(Type.INT, "a"), new Parameter(Type.INT, "b")),
                List.of(new Return(new Binary(Operator.MINUS, new Identifier("a"), new Identifier("b")))),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ArithmeticOps");
        assertNotNull(classBytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("ArithmeticOps", classBytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("subtract", int.class, int.class);
        assertEquals("subtract",  m.getName());
        assertEquals(int.class,   m.getReturnType());
        assertEquals(2, m.getParameterCount(), "Method 'subtract' should have 2 parameters");
        assertEquals(int.class, m.getParameterTypes()[0], "First parameter should be of type int");
        assertEquals(int.class, m.getParameterTypes()[1], "Second parameter should be of type int");
    }

    @Test
    void testMultiply() throws Exception {
        Class cls = new Class("ArithmeticOps");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.INT,
                "multiply",
                List.of(new Parameter(Type.INT, "a"), new Parameter(Type.INT, "b")),
                List.of(new Return(new Binary(Operator.MULTIPLY, new Identifier("a"), new Identifier("b")))),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ArithmeticOps");
        assertNotNull(classBytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("ArithmeticOps", classBytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("multiply", int.class, int.class);
        assertEquals("multiply",  m.getName());
        assertEquals(int.class,   m.getReturnType());
        assertEquals(int.class, m.getParameterTypes()[0], "First parameter should be of type int");
        assertEquals(int.class, m.getParameterTypes()[1], "Second parameter should be of type int");
    }

    @Test
    void testDivide() throws Exception {
        Class cls = new Class("ArithmeticOps");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.INT,
                "divide",
                List.of(new Parameter(Type.INT, "a"), new Parameter(Type.INT, "b")),
                List.of(new Return(new Binary(Operator.DIVIDE, new Identifier("a"), new Identifier("b")))),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ArithmeticOps");
        assertNotNull(classBytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("ArithmeticOps", classBytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("divide", int.class, int.class);
        assertEquals("divide",    m.getName());
        assertEquals(int.class,   m.getReturnType());
        assertEquals(int.class, m.getParameterTypes()[0], "First parameter should be of type int");
        assertEquals(int.class, m.getParameterTypes()[1], "Second parameter should be of type int");
    }

    @Test
    void testModulus() throws Exception {
        Class cls = new Class("ArithmeticOps");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.INT,
                "modulus",
                List.of(new Parameter(Type.INT, "a"), new Parameter(Type.INT, "b")),
                List.of(new Return(new Binary(Operator.MODULUS, new Identifier("a"), new Identifier("b")))),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] classBytes = byteMap.get("ArithmeticOps");
        assertNotNull(classBytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("ArithmeticOps", classBytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("modulus", int.class, int.class);
        assertEquals("modulus",   m.getName());
        assertEquals(int.class,   m.getReturnType());
        assertEquals(int.class, m.getParameterTypes()[0], "First parameter should be of type int");
        assertEquals(int.class, m.getParameterTypes()[1], "Second parameter should be of type int");
    }

    // ==============================
    // Test Booleean Operations
    // ==============================

    @Test
    void testAnd() throws Exception {
        Class cls = new Class("BooleanLogic");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.BOOLEAN,
                "and",
                List.of(
                    new Parameter(Type.BOOLEAN, "a"),
                    new Parameter(Type.BOOLEAN, "b")
                ),
                List.of(new Return(
                    new Binary(
                        Operator.AND,
                        new Identifier("a"),
                        new Identifier("b")
                    )
                )),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] bytes = byteMap.get("BooleanLogic");
        assertNotNull(bytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("BooleanLogic", bytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("and", boolean.class, boolean.class);
        assertEquals("and", m.getName());
        assertEquals(boolean.class, m.getReturnType());
    }

    @Test
    void testOr() throws Exception {
        Class cls = new Class("BooleanLogic");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.BOOLEAN,
                "or",
                List.of(
                    new Parameter(Type.BOOLEAN, "a"),
                    new Parameter(Type.BOOLEAN, "b")
                ),
                List.of(new Return(
                    new Binary(
                        Operator.OR,
                        new Identifier("a"),
                        new Identifier("b")
                    )
                )),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] bytes = byteMap.get("BooleanLogic");
        assertNotNull(bytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("BooleanLogic", bytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("or", boolean.class, boolean.class);
        assertEquals("or", m.getName());
        assertEquals(boolean.class, m.getReturnType());
    }

    @Test
    void testNot() throws Exception {
        Class cls = new Class("BooleanLogic");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.BOOLEAN,
                "not",
                List.of(new Parameter(Type.BOOLEAN, "a")),
                List.of(new Return(
                    new Unary(
                        Operator.NEGATE,
                        new Identifier("a")
                    )
                )),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] bytes = byteMap.get("BooleanLogic");
        assertNotNull(bytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("BooleanLogic", bytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("not", boolean.class);
        assertEquals("not", m.getName());
        assertEquals(boolean.class, m.getReturnType());
    }

    @Test
    void testNand() throws Exception {
        Class cls = new Class("BooleanLogic");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.BOOLEAN,
                "nand",
                List.of(
                    new Parameter(Type.BOOLEAN, "a"),
                    new Parameter(Type.BOOLEAN, "b")
                ),
                List.of(new Return(
                    new Unary(
                        Operator.NEGATE,
                        new Binary(
                            Operator.AND,
                            new Identifier("a"),
                            new Identifier("b")
                        )
                    )
                )),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] bytes = byteMap.get("BooleanLogic");
        assertNotNull(bytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("BooleanLogic", bytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("nand", boolean.class, boolean.class);
        assertEquals("nand", m.getName());
        assertEquals(boolean.class, m.getReturnType());
    }

    @Test
    void testNor() throws Exception {
        Class cls = new Class("BooleanLogic");
        cls.fields      = List.of();
        cls.methods     = List.of(
            new Method(
                Type.BOOLEAN,
                "nor",
                List.of(
                    new Parameter(Type.BOOLEAN, "a"),
                    new Parameter(Type.BOOLEAN, "b")
                ),
                List.of(new Return(
                    new Unary(
                        Operator.NEGATE,
                        new Binary(
                            Operator.OR,
                            new Identifier("a"),
                            new Identifier("b")
                        )
                    )
                )),
                false
            )
        );
        cls.parentClass = null;
        Program program = new Program(List.of(cls));

        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String, byte[]> byteMap = gen.generateByteCode(program);
        byte[] bytes = byteMap.get("BooleanLogic");
        assertNotNull(bytes);

        java.lang.Class<?> dyn = new ByteArrayClassLoader().defineClass("BooleanLogic", bytes);
        java.lang.reflect.Method m = dyn.getDeclaredMethod("nor", boolean.class, boolean.class);
        assertEquals("nor", m.getName());
        assertEquals(boolean.class, m.getReturnType());
    }
}
