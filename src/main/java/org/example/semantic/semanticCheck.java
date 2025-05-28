package org.example.semantic;

import org.example.context.context;
import org.example.semantic.exceptions.semanticError;
import org.example.syntaxtree.structure.classDecl;
import org.example.syntaxtree.structure.methodDecl;
import org.example.syntaxtree.structure.program;
import org.example.syntaxtree.structure.varDecl;
import org.example.visitor.semanticVisitor;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class semanticCheck implements semanticVisitor {

    private context context;
    private final List<Exception> errors = new ArrayList<>();

    public static program generateTast(program program) throws semanticError {
        semanticCheck checker = new semanticCheck();
        typeCheckResult result = program.accept(checker);
        if (!result.isValid()) {
            StringBuilder sb = new StringBuilder("Semantic check failed:\n");
            for (Exception e : checker.errors) {
                sb.append("- ").append(e.getMessage()).append("\n");
            }
            throw new semanticError(sb.toString());
        }
        return program;
    }

    @Override
    public typeCheckResult typeCheck(program toCheck) {
        context = new context(toCheck);
        toCheck.setContext(context);

        boolean valid = true;
        Set<String> seenClassNames = new HashSet<>();

        for (classDecl cls : toCheck.getClasses()) {
            String name = cls.getIdentifier();
            if (seenClassNames.contains(name)) {
                errors.add(new semanticError("Duplicate class name: '" + name + "'"));
                valid = false;
            } else {
                seenClassNames.add(name);
            }

            typeCheckResult classRes = cls.accept(this);
            valid = valid && classRes.isValid();
        }

        return new typeCheckResult(valid, null);
    }

    /*@Override
    public typeCheckResult typeCheck(classDecl toCheck) {
        System.out.println("Checking class: " + toCheck.getIdentifier());
        return new typeCheckResult(true, null);
    }*/

    @Override
    public typeCheckResult typeCheck(classDecl toCheck) {
        boolean valid = true;

        String className = toCheck.getIdentifier();
        System.out.println("Checking class: " + className);

        // Feldnamen prüfen
        Set<String> fieldNames = new HashSet<>();
        for (varDecl field : toCheck.getFields()) {
            String name = field.getIdentifier();
            if (!fieldNames.add(name)) {
                errors.add(new semanticError("Duplicate field name '" + name + "' in class '" + className + "'"));
                valid = false;
            }
        }

        // Methodennamen prüfen
        Set<String> methodNames = new HashSet<>();
        for (methodDecl method : toCheck.getMethods()) {
            String methodName = method.getIdentifier();
            if (!methodNames.add(methodName)) {
                errors.add(new semanticError("Duplicate method name '" + methodName + "' in class '" + className + "'"));
                valid = false;
            }

            // Optional: Methodeninhalte prüfen
            typeCheckResult methodResult = method.accept(this);
            valid = valid && methodResult.isValid();
        }

        return new typeCheckResult(valid, null);
    }

    @Override
    public typeCheckResult typeCheck(varDecl varDecl) {
        // Hier könntest du z. B. prüfen, ob der Typ existiert oder erlaubt ist
        System.out.println("Checking field: " + varDecl.getIdentifier());
        return new typeCheckResult(true, null); // Noch kein Typ-Check vorhanden
    }

    @Override
    public typeCheckResult typeCheck(methodDecl methodDecl) {
        // Du könntest später Parameter und Rückgabetyp prüfen
        System.out.println("Checking method: " + methodDecl.getIdentifier());
        return new typeCheckResult(true, null); // Noch kein Fehler
    }
}