package bytecode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ast.Program;
import ast.Class;
import ast.Method;
import ast.Parameter;
import ast.statements.Block;
import ast.statements.If;
import ast.statements.While;
import ast.statements.Break;
import ast.statements.Continue;
import ast.statements.LocalVarDecl;
import ast.statements.Return;
import ast.expressions.Binary;
import ast.exprStatements.Unary;
import ast.expressions.Identifier;
import ast.expressions.IntConst;
import ast.expressions.BooleanConst;
import ast.types.Type;
import ast.Operator;

public class ControlStructuresTest {

    @Test
    void testIfElse() throws Exception {
        Class cls = new Class("IfElse");
        cls.fields  = List.of();
        cls.methods = List.of(
            new Method(
                Type.BOOLEAN,
                "isZeroOrPositive",
                List.of(new Parameter(Type.INT,"number")),
                List.of(
                    new If(
                        new Binary(Operator.GREATER_THAN,
                                   new Identifier("number"),
                                   new IntConst(0)),
                        new Return(new BooleanConst(true)),
                        null
                    ),
                    new If(
                        new Binary(Operator.EQUALS,
                                   new Identifier("number"),
                                   new IntConst(0)),
                        new Return(new BooleanConst(true)),
                        null
                    ),
                    new Return(new BooleanConst(false))
                ),
                false
            ),
            new Method(
                Type.BOOLEAN,
                "isEven",
                List.of(new Parameter(Type.INT,"number")),
                List.of(
                    new If(
                        new Binary(Operator.EQUALS,
                                   new Binary(Operator.MODULUS,
                                              new Identifier("number"),
                                              new IntConst(2)),
                                   new IntConst(0)),
                        new Return(new BooleanConst(true)),
                        new Return(new BooleanConst(false))
                    )
                ),
                false
            ),
            new Method(
                Type.BOOLEAN,
                "isInRange",
                List.of(
                    new Parameter(Type.INT,"number"),
                    new Parameter(Type.INT,"min"),
                    new Parameter(Type.INT,"max")
                ),
                List.of(
                    new If(
                        new Binary(Operator.AND,
                                   new Binary(Operator.GREATER_THAN_OR_EQUAL,
                                              new Identifier("number"),
                                              new Identifier("min")),
                                   new Binary(Operator.LESS_THAN_OR_EQUAL,
                                              new Identifier("number"),
                                              new Identifier("max"))
                        ),
                        new Return(new BooleanConst(true)),
                        new Return(new BooleanConst(false))
                    )
                ),
                false
            )
        );
        cls.parentClass = null;

        Program program = new Program(List.of(cls));
        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String,byte[]> byteMap = gen.generateByteCode(program);
        byte[] bytes = byteMap.get("IfElse");
        assertNotNull(bytes);

        var loader = new ByteArrayClassLoader();
        java.lang.Class<?> dyn = loader.defineClass("IfElse", bytes);

        assertEquals(boolean.class,
            dyn.getDeclaredMethod("isZeroOrPositive", int.class)
               .getReturnType()
        );
        assertEquals(boolean.class,
            dyn.getDeclaredMethod("isEven", int.class)
               .getReturnType()
        );
        assertEquals(boolean.class,
            dyn.getDeclaredMethod("isInRange", int.class, int.class, int.class)
               .getReturnType()
        );
    }

    @Test
    void testNestedBlocks() throws Exception {
        Class cls = new Class("NestedBlocks");
        cls.fields  = List.of();
        cls.methods = List.of(
            new Method(
                Type.INT,
                "nestedBlock",
                List.of(new Parameter(Type.INT,"x")),
                List.of(
                    new Block(List.of(
                        new LocalVarDecl(Type.INT, "y",
                            new Binary(Operator.MULTIPLY,
                                       new Identifier("x"),
                                       new IntConst(2))
                        ),
                        new Block(List.of(
                        ))
                    )),
                    new Return(new Identifier("x"))
                ),
                false
            )
        );
        cls.parentClass = null;

        Program program = new Program(List.of(cls));
        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String,byte[]> byteMap = gen.generateByteCode(program);
        byte[] bytes = byteMap.get("NestedBlocks");
        assertNotNull(bytes);

        var loader = new ByteArrayClassLoader();
        java.lang.Class<?> dyn = loader.defineClass("NestedBlocks", bytes);

        assertEquals(int.class,
            dyn.getDeclaredMethod("nestedBlock", int.class)
               .getReturnType()
        );
    }

    @Test
    void testWhileLoops() throws Exception {
        Class cls = new Class("WhileLoop");
        cls.fields  = List.of();
        cls.methods = List.of(
            new Method(
                Type.VOID,
                "whileLoop",
                List.of(),
                List.of(
                    new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                    new While(
                        new Binary(Operator.LESS_THAN,
                                   new Identifier("i"),
                                   new IntConst(10)),
                        new Block(List.of(
                            new Unary(Operator.INCREMENT,
                                      new Identifier("i"))
                        ))
                    )
                ),
                false
            ),
            new Method(
                Type.VOID,
                "whileLoopWithBreak",
                List.of(),
                List.of(
                    new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                    new While(
                        new Binary(Operator.LESS_THAN,
                                   new Identifier("i"),
                                   new IntConst(10)),
                        new Block(List.of(
                            new If(
                                new Binary(Operator.EQUALS,
                                           new Identifier("i"),
                                           new IntConst(5)),
                                new Break(),
                                null
                            ),
                            new Unary(Operator.INCREMENT,
                                      new Identifier("i"))
                        ))
                    )
                ),
                false
            ),
            new Method(
                Type.VOID,
                "whileLoopWithContinue",
                List.of(),
                List.of(
                    new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                    new While(
                        new Binary(Operator.LESS_THAN,
                                   new Identifier("i"),
                                   new IntConst(10)),
                        new Block(List.of(
                            new Unary(Operator.INCREMENT,
                                      new Identifier("i")),
                            new If(
                                new Binary(Operator.EQUALS,
                                           new Binary(Operator.MODULUS,
                                                      new Identifier("i"),
                                                      new IntConst(2)),
                                           new IntConst(0)),
                                new Continue(),
                                null
                            )
                        ))
                    )
                ),
                false
            )
        );
        cls.parentClass = null;

        Program program = new Program(List.of(cls));
        ByteCodeGenerator gen = new ByteCodeGenerator();
        Map<String,byte[]> byteMap = gen.generateByteCode(program);
        byte[] bytes = byteMap.get("WhileLoop");
        assertNotNull(bytes);

        var loader = new ByteArrayClassLoader();
        java.lang.Class<?> dyn = loader.defineClass("WhileLoop", bytes);

        assertEquals(void.class,
            dyn.getDeclaredMethod("whileLoop")
               .getReturnType()
        );
        assertEquals(void.class,
            dyn.getDeclaredMethod("whileLoopWithBreak")
               .getReturnType()
        );
        assertEquals(void.class,
            dyn.getDeclaredMethod("whileLoopWithContinue")
               .getReturnType()
        );
    }
}
