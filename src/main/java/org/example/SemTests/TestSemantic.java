package org.example.SemTests;

import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;
import org.example.syntaxtree.structure.*;

import java.util.List;

public class TestSemantic {
    public static void main(String[] args) {
        try {
            varDecl field1 = new varDecl("x");
            varDecl field2 = new varDecl("x");

            methodDecl method1 = new methodDecl("foo");
            methodDecl method2 = new methodDecl("foo");

            classDecl testClass = new classDecl(
                    "MyClass",
                    List.of(field1, field2),
                    List.of(method1, method2)
            );

            program testProgram = new program(List.of(testClass));

            semanticCheck.generateTast(testProgram);
            System.out.println("✅ Semantikprüfung erfolgreich!");
        } catch (semanticError e) {
            System.out.println("❌ Fehler bei der Semantikprüfung:");
            System.out.println(e.getMessage());
        }
    }
}
