package org.example.SemTests;

import ast.*;
import ast.Class;
import ast.exprStatements.Unary;
import ast.statements.Return;
import org.example.semantic.semanticCheck;
import org.example.semantic.exceptions.semanticError;

import java.util.List;

public class SemaCheckTest {

    public static void main(String[] args) {
        // Expression: i++
        Unary returnExpr = new Unary(Operator.plusplus, new DummyIntExpression());

        // Return statement
        Return returnStmt = new Return(returnExpr);

        // Methode mit Rückgabetyp INT, die ein Return-Statement enthält
        Method method = new Method();
        method.name = "testMethod";
        method.type = Type.INT;
        method.parameters = List.of();
        method.statement = List.of(returnStmt);
        method.staticFlag = true;

        // Klasse mit Methodenliste
        Class testClass = new Class(
                "TestClass",             // Klassenname
                List.of(),               // Felder
                List.of(method),         // Methoden
                List.of(),               // Konstruktoren
                null                     // Parent-Klasse
        );

        // Programm mit einer Klasse
        Program program = new Program();
        program.classes = List.of(testClass);

        // Semantic check
        try {
            semanticCheck.generateTast(program);
            System.out.println("✅ Semantic check passed.");
        } catch (semanticError e) {
            System.out.println("❌ Semantic check failed:\n" + e.getMessage());
        }
    }

    // Dummy Expression für einen int-Wert
    static class DummyIntExpression implements Expression {
        @Override
        public String toString() {
            return "DummyInt";
        }
    }
}
