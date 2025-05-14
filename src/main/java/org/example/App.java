package org.example;

import java.util.List;

import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;
import org.example.syntaxtree.structure.classDecl;
import org.example.syntaxtree.structure.program;

public class App {
    public static void main(String[] args) {
        classDecl classA = new classDecl("A");
        classDecl classB = new classDecl("A"); // gleiche Namen!

        program program = new program(List.of(classA, classB));

        try {
            semanticCheck.generateTast(program);
            System.out.println("Semantic check successful");
        } catch (semanticError e) {
            System.err.println(e.getMessage());
        }
    }
}