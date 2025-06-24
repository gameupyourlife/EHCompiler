package org.example.SemTests;

import ast.Class;
import ast.Field;
import ast.Method;
import ast.Program;
import ast.types.Type;

import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;

import java.util.List;

public class TestSemantic {
    public static void main(String[] args) {
        try {
            // Zwei Felder mit gleichem Namen -> Fehler
            Field field1 = new Field("x", Type.INT, null);
            Field field2 = new Field("x", Type.INT, null);

            // Zwei Methoden mit gleichem Namen -> Fehler
            Method method1 = new Method();
            method1.name = "foo";

            Method method2 = new Method();
            method2.name = "foo";

            // Klasse mit doppelten Feldern und Methoden
            Class testClass = new Class(
                    "MyClass",
                    List.of(field1, field2),
                    List.of(method1, method2),
                    null,
                    null
            );

            // Programm mit nur einer Klasse
            Program testProgram = new Program(List.of(testClass));

            // Semantikprüfung starten
            semanticCheck.generateTast(testProgram);

            System.out.println("Semantikprüfung erfolgreich!");
        } catch (semanticError e) {
            System.out.println("Fehler bei der Semantikprüfung:");
            System.out.println(e.getMessage());
        }
    }
}