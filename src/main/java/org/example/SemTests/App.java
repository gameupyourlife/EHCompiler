package org.example.SemTests;

import ast.Class;
import ast.Program;
import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;

import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Zwei Klassen mit dem gleichen Namen → sollte Fehler erzeugen
        Class classA = new Class("A", Collections.emptyList(), Collections.emptyList(), null, null);
        Class classB = new Class("A", Collections.emptyList(), Collections.emptyList(), null, null);

        // Beide Klassen dem Programm hinzufügen
        Program program = new Program(List.of(classA, classB));

        try {
            semanticCheck.generateTast(program);
            System.out.println("Semantic check successful");
        } catch (semanticError e) {
            System.err.println("Fehler bei der Semantikprüfung:");
            System.err.println(e.getMessage());
        }
    }
}