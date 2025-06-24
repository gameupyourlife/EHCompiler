package org.example.SemTests;

import ast.*;
import ast.Class;
import ast.Method;
import ast.types.Type;
import ast.expressions.BooleanLiteral;
import ast.expressions.IntConst;
import ast.statements.While;
import ast.statements.For;
import ast.statements.DoWhile;
import ast.statements.EmptyStatement;
import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;

import java.util.List;

public class LoopTest {

    public static void main(String[] args) {
        testWhileLoop();
        testForLoop();
        testDoWhileLoop();
        testDoWhileLoopNegative();
        testForLoopNegative();
        testWhileLoopNegative();
    }

    static void testWhileLoop() {
        // Programm mit einer Klasse und einem While-Loop
        Program program = new Program();

        Method method = new Method();
        method.name = "whileTest";
        method.type = Type.VOID;
        method.parameters = List.of();

        // while(true) {}
        While w = new While(new BooleanLiteral(true), new EmptyStatement());
        method.statement = List.of(w);
        method.staticFlag = true;

        Class cls = new Class(
                "WhileClass",
                List.of(),
                List.of(method),
                List.of(),
                null
        );
        program.classes = List.of(cls);

        try {
            semanticCheck.generateTast(program);
            System.out.println("✅ While loop test passed.");
        } catch (semanticError e) {
            System.out.println("❌ While loop test failed: " + e.getMessage());
        }
    }

    static void testForLoop() {
        // Programm mit einer Klasse und einem For-Loop
        Program program = new Program();

        Method method = new Method();
        method.name = "forTest";
        method.type = Type.VOID;
        method.parameters = List.of();

        // for (; true; ) {}
        For f = new For(null, new BooleanLiteral(true), null, new EmptyStatement());
        method.statement = List.of(f);
        method.staticFlag = true;

        Class cls = new Class(
                "ForClass",
                List.of(),
                List.of(method),
                List.of(),
                null
        );
        program.classes = List.of(cls);

        try {
            semanticCheck.generateTast(program);
            System.out.println("✅ For loop test passed.");
        } catch (semanticError e) {
            System.out.println("❌ For loop test failed: " + e.getMessage());
        }
    }

    static void testDoWhileLoop() {
        // Programm mit einer Klasse und einem Do-While-Loop
        Program program = new Program();

        Method method = new Method();
        method.name = "doWhileTest";
        method.type = Type.VOID;
        method.parameters = List.of();

        // do {} while(true);
        DoWhile d = new DoWhile(new BooleanLiteral(true), new EmptyStatement());
        method.statement = List.of(d);
        method.staticFlag = true;

        Class cls = new Class(
                "DoWhileClass",
                List.of(),
                List.of(method),
                List.of(),
                null
        );
        program.classes = List.of(cls);

        try {
            semanticCheck.generateTast(program);
            System.out.println("✅ Do-While loop test passed.");
        } catch (semanticError e) {
            System.out.println("❌ Do-While loop test failed: " + e.getMessage());
        }
    }

    // Negative tests
    static void testWhileLoopNegative() {
        Program program = new Program();

        Method method = new Method();
        method.name = "whileTestNegative";
        method.type = Type.VOID;
        method.parameters = List.of();

        // while(1) {} -> error
        While w = new While(new IntConst(1), new EmptyStatement());
        method.statement = List.of(w);
        method.staticFlag = true;

        Class cls = new Class(
                "WhileNegClass",
                List.of(),
                List.of(method),
                List.of(),
                null
        );
        program.classes = List.of(cls);

        try {
            semanticCheck.generateTast(program);
            System.out.println("❌ While loop negative test failed: no error thrown");
        } catch (semanticError e) {
            System.out.println("✅ While loop negative test passed: " + e.getMessage());
        }
    }

    static void testForLoopNegative() {
        Program program = new Program();

        Method method = new Method();
        method.name = "forTestNegative";
        method.type = Type.VOID;
        method.parameters = List.of();

        // for (; 0; ) {} -> error
        For f = new For(null, new IntConst(0), null, new EmptyStatement());
        method.statement = List.of(f);
        method.staticFlag = true;

        Class cls = new Class(
                "ForNegClass",
                List.of(),
                List.of(method),
                List.of(),
                null
        );
        program.classes = List.of(cls);

        try {
            semanticCheck.generateTast(program);
            System.out.println("❌ For loop negative test failed: no error thrown");
        } catch (semanticError e) {
            System.out.println("✅ For loop negative test passed: " + e.getMessage());
        }
    }

    static void testDoWhileLoopNegative() {
        Program program = new Program();

        Method method = new Method();
        method.name = "doWhileTestNegative";
        method.type = Type.VOID;
        method.parameters = List.of();

        // do {} while(2); -> error
        DoWhile d = new DoWhile(new IntConst(2), new EmptyStatement());
        method.statement = List.of(d);
        method.staticFlag = true;

        Class cls = new Class(
                "DoWhileNegClass",
                List.of(),
                List.of(method),
                List.of(),
                null
        );
        program.classes = List.of(cls);

        try {
            semanticCheck.generateTast(program);
            System.out.println("❌ Do-While loop negative test failed: no error thrown");
        } catch (semanticError e) {
            System.out.println("✅ Do-While loop negative test passed: " + e.getMessage());
        }
    }
}
