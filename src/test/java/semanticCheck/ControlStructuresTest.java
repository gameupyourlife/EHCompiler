package semanticCheck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;
import org.junit.jupiter.api.Test;

import ast.Program;
import ast.Class;
import ast.Method;
import ast.statements.Block;
import ast.statements.While;
import ast.statements.Break;
import ast.statements.Continue;
import ast.statements.DoWhile;
import ast.statements.Return;
import ast.statements.LocalVarDecl;
import ast.statements.For;
import ast.statements.If;
import ast.expressions.Binary;
import ast.expressions.BooleanConst;
import ast.exprStatements.Unary;
import ast.expressions.Identifier;
import ast.expressions.IntConst;
import ast.types.Type;
import ast.Operator;
import ast.Parameter;

public class ControlStructuresTest {

        @Test
        void testIfElse_passes() {
                Class c = new Class("IfElse");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.BOOLEAN,
                                                "isZeroOrPositive",
                                                List.of(new Parameter(Type.INT, "number")),
                                                List.of(
                                                                new If(
                                                                                new Binary(Operator.GREATER_THAN,
                                                                                                new Identifier("number", null),
                                                                                                new IntConst(0)),
                                                                                new Return(new BooleanConst(true)),
                                                                                null),
                                                                new If(
                                                                                new Binary(Operator.EQUALS,
                                                                                                new Identifier("number", null),
                                                                                                new IntConst(0)),
                                                                                new Return(new BooleanConst(true)),
                                                                                null),
                                                                new Return(new BooleanConst(false))),
                                                false),
                                new Method(
                                                Type.BOOLEAN,
                                                "isEven",
                                                List.of(new Parameter(Type.INT, "number")),
                                                List.of(
                                                                new If(
                                                                                new Binary(
                                                                                                Operator.EQUALS,
                                                                                                new Binary(Operator.MODULUS,
                                                                                                                new Identifier("number", null),
                                                                                                                new IntConst(2)),
                                                                                                new IntConst(0)),
                                                                                new Return(new BooleanConst(true)),
                                                                                new Return(new BooleanConst(false)))),
                                                false),
                                new Method(
                                                Type.BOOLEAN,
                                                "isInRange",
                                                List.of(
                                                                new Parameter(Type.INT, "number"),
                                                                new Parameter(Type.INT, "min"),
                                                                new Parameter(Type.INT, "max")),
                                                List.of(
                                                                new If(
                                                                                new Binary(
                                                                                                Operator.AND,
                                                                                                new Binary(Operator.GREATER_THAN_OR_EQUAL,
                                                                                                                new Identifier("number", null),
                                                                                                                new Identifier("min", null)),
                                                                                                new Binary(Operator.LESS_THAN_OR_EQUAL,
                                                                                                                new Identifier("number", null),
                                                                                                                new Identifier("max", null))),
                                                                                new Return(new BooleanConst(true)),
                                                                                new Return(new BooleanConst(false)))),
                                                false));
                c.parentClass = null;

                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testNestedBlocks_passes() {
                Class c = new Class("NestedBlocks");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.INT,
                                                "nestedBlock",
                                                List.of(new Parameter(Type.INT, "x")),
                                                List.of(
                                                                new Block(List.of(
                                                                                new LocalVarDecl(
                                                                                                Type.INT,
                                                                                                "y",
                                                                                                new Binary(Operator.MULTIPLY,
                                                                                                                new Identifier("x", null),
                                                                                                                new IntConst(2))),
                                                                                new Block(List.of()))),
                                                                new Return(new Identifier("x", null))),
                                                false));
                c.parentClass = null;

                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        // =================================
        // While Loop and For Loop Tests
        // =================================
        @Test
        void testWhileLoop_passes() {
                Class c = new Class("WhileLoop");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.VOID,
                                                "whileLoop",
                                                List.of(),
                                                List.of(
                                                                new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                new While(
                                                                                new Binary(Operator.LESS_THAN,
                                                                                                new Identifier("i", null),
                                                                                                new IntConst(10)),
                                                                                new Block(List.of(new Unary(
                                                                                                Operator.INCREMENT,
                                                                                                new Identifier("i", null)))))),
                                                false));
                c.parentClass = null;
                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testWhileLoopWithBreak_passes() {
                Class c = new Class("WhileLoop");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.VOID,
                                                "whileLoopWithBreak",
                                                List.of(),
                                                List.of(
                                                                new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                new While(
                                                                                new Binary(Operator.LESS_THAN,
                                                                                                new Identifier("i", null),
                                                                                                new IntConst(10)),
                                                                                new Block(List.of(
                                                                                                new If(
                                                                                                                new Binary(Operator.EQUALS,
                                                                                                                                new Identifier("i", null),
                                                                                                                                new IntConst(5)),
                                                                                                                new Break(),
                                                                                                                null),
                                                                                                new Unary(Operator.INCREMENT,
                                                                                                                new Identifier("i", null)))))),
                                                false));
                c.parentClass = null;
                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testWhileLoopWithContinue_passes() {
                Class c = new Class("WhileLoop");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.VOID,
                                                "whileLoopWithContinue",
                                                List.of(),
                                                List.of(
                                                                new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                new While(
                                                                                new Binary(Operator.LESS_THAN,
                                                                                                new Identifier("i", null),
                                                                                                new IntConst(10)),
                                                                                new Block(List.of(
                                                                                                new Unary(Operator.INCREMENT,
                                                                                                                new Identifier("i", null)),
                                                                                                new If(
                                                                                                                new Binary(
                                                                                                                                Operator.EQUALS,
                                                                                                                                new Binary(Operator.MODULUS,
                                                                                                                                                new Identifier("i", null),
                                                                                                                                                new IntConst(2)),
                                                                                                                                new IntConst(0)),
                                                                                                                new Continue(),
                                                                                                                null))))),
                                                false));
                c.parentClass = null;
                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testNestedWhileLoop_passes() {
                Class c = new Class("WhileLoop");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.VOID,
                                                "nestedWhileLoop",
                                                List.of(),
                                                List.of(
                                                                new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                new While(
                                                                                new Binary(Operator.LESS_THAN,
                                                                                                new Identifier("i", null),
                                                                                                new IntConst(5)),
                                                                                new Block(List.of(
                                                                                                new LocalVarDecl(
                                                                                                                Type.INT,
                                                                                                                "j",
                                                                                                                new IntConst(0)),
                                                                                                new While(
                                                                                                                new Binary(Operator.LESS_THAN,
                                                                                                                                new Identifier("j", null),
                                                                                                                                new IntConst(3)),
                                                                                                                new Block(List.of(
                                                                                                                                new Unary(Operator.INCREMENT,
                                                                                                                                                new Identifier("j", null))))),
                                                                                                new Unary(Operator.INCREMENT,
                                                                                                                new Identifier("i", null)))))),
                                                false));
                c.parentClass = null;
                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testBasicForLoop_passes() {
                Class c = new Class("ForLoop");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.VOID,
                                                "basicForLoop",
                                                List.of(),
                                                List.of(
                                                                new For(
                                                                        new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                                new Binary(Operator.LESS_THAN,
                                                                                                new Identifier("i", null),
                                                                                                new IntConst(10)),
                                                                                new Unary(Operator.INCREMENT,
                                                                                                new Identifier("i", null)),
                                                                                new Block(List.of()))),
                                                false));
                c.parentClass = null;
                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testForLoopWithBreak_passes() {
                Class c = new Class("ForLoop");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.INT,
                                                "forLoopWithBreak",
                                                List.of(),
                                                List.of(
                                                                new LocalVarDecl(Type.INT, "result", new IntConst(0)),
                                                                new For(
                                                                                new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                                new Binary(Operator.LESS_THAN,
                                                                                                new Identifier("i", null),
                                                                                                new IntConst(10)),
                                                                                new Unary(Operator.INCREMENT,
                                                                                                new Identifier("i", null)),
                                                                                new Block(List.of(
                                                                                                new If(
                                                                                                                new Binary(Operator.EQUALS,
                                                                                                                                new Identifier("i", null),
                                                                                                                                new IntConst(5)),
                                                                                                                new Block(List.of()),
                                                                                                                null),
                                                                                                new Break()))),
                                                                new Return(new Identifier("result", null))),
                                                false));
                c.parentClass = null;
                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testForLoopWithContinue_passes() {
                Class c = new Class("ForLoop");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.INT,
                                                "forLoopWithContinue",
                                                List.of(),
                                                List.of(
                                                                new LocalVarDecl(Type.INT, "sumOfEvens",
                                                                                new IntConst(0)),
                                                                new For(
                                                                                new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                                new Binary(Operator.LESS_THAN,
                                                                                                new Identifier("i", null),
                                                                                                new IntConst(10)),
                                                                                new Unary(Operator.INCREMENT,
                                                                                                new Identifier("i", null)),
                                                                                new Block(List.of(
                                                                                                new If(
                                                                                                                new Binary(
                                                                                                                                Operator.EQUALS,
                                                                                                                                new Binary(Operator.MODULUS,
                                                                                                                                                new Identifier("i", null),
                                                                                                                                                new IntConst(2)),
                                                                                                                                new IntConst(0)),
                                                                                                                new Block(List.of()),
                                                                                                                null),
                                                                                                new Continue()))),
                                                                new Return(new Identifier("sumOfEvens", null))),
                                                false));
                c.parentClass = null;
                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testBreakOutsideLoop_fails() {
                Class c = new Class("BadControl");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.VOID,
                                                "breakOutside",
                                                List.of(),
                                                List.of(
                                                                new Break()

                                                ),
                                                false));
                c.parentClass = null;

                Program p = new Program(List.of(c));
                assertThrows(
                                semanticError.class,
                                () -> semanticCheck.generateTast(p),
                                "Break außerhalb einer Schleife muss einen semantischen Fehler auslösen");
        }

        @Test
        void testDoWhile_passes() {
                Class c = new Class("DoWhile");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.VOID,
                                                "doWhile",
                                                List.of(),
                                                List.of(
                                                                new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                new DoWhile(
                                                                                new Binary(Operator.LESS_THAN,
                                                                                                new Identifier("i", null),
                                                                                                new IntConst(5)),
                                                                                new Block(List.of(
                                                                                                new Unary(Operator.INCREMENT,
                                                                                                                new Identifier("i", null)))))),
                                                false));
                c.parentClass = null;

                Program p = new Program(List.of(c));
                assertDoesNotThrow(() -> semanticCheck.generateTast(p));
        }

        @Test
        void testDoWhileConditionNotBoolean_fails() {
                Class c = new Class("DoWhileBad");
                c.fields = List.of();
                c.methods = List.of(
                                new Method(
                                                Type.VOID,
                                                "doWhileBad",
                                                List.of(),
                                                List.of(
                                                                new LocalVarDecl(Type.INT, "i", new IntConst(0)),
                                                                new DoWhile(
                                                                                /*
                                                                                 * falsche Bedingung: int statt boolean
                                                                                 */
                                                                                new IntConst(5),
                                                                                new Block(List.of(
                                                                                                new Unary(Operator.INCREMENT,
                                                                                                                new Identifier("i", null)))))),
                                                false));
                c.parentClass = null;

                Program p = new Program(List.of(c));
                assertThrows(
                                semanticError.class,
                                () -> semanticCheck.generateTast(p),
                                "DoWhile-Bedingung muss boolean sein");
        }

}
