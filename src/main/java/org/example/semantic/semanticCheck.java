package org.example.semantic;

import ast.Method;
import ast.Program;
import ast.Class;
import ast.Field;
import org.example.context.context;
import org.example.semantic.exceptions.semanticError;
import org.example.visitor.semanticVisitor;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class semanticCheck implements semanticVisitor {

    private context context;
    private final List<Exception> errors = new ArrayList<>();

    public static Program generateTast(Program program) throws semanticError {
        semanticCheck checker = new semanticCheck();
        typeCheckResult result = checker.typeCheck(program);
        if (!result.isValid()) {
            StringBuilder sb = new StringBuilder("Semantic check failed:\n");
            for (Exception e : checker.errors) {
                sb.append("- ").append(e.getMessage()).append("\n");
            }
            throw new semanticError(sb.toString());
        }
        return program;
    }

    public typeCheckResult typeCheck(Program toCheck) {
        context = new context(toCheck);
        //toCheck.setContext(context); // falls vorhanden

        boolean valid = true;
        Set<String> seenClassNames = new HashSet<>();

        for (Class cls : toCheck.classes) {
            String name = cls.name;
            if (!seenClassNames.add(name)) {
                errors.add(new semanticError("Duplicate class name: '" + name + "'"));
                valid = false;
            }

            typeCheckResult classRes = typeCheck(cls);
            valid = valid && classRes.isValid();
        }

        return new typeCheckResult(valid, toCheck);
    }

    public typeCheckResult typeCheck(Class toCheck) {
        boolean valid = true;
        String className = toCheck.name;
        System.out.println("Checking class: " + className);

        // Doppelte Felder prüfen
        Set<String> fieldNames = new HashSet<>();
        if (toCheck.fields != null) {
            for (Field field : toCheck.fields) {
                if (!fieldNames.add(field.name)) {
                    errors.add(new semanticError("Duplicate field name '" + field.name + "' in class '" + className + "'"));
                    valid = false;
                }
            }
        }

        // Doppelte Methoden prüfen
        Set<String> methodNames = new HashSet<>();
        if (toCheck.methods != null) {
            for (Method method : toCheck.methods) {
                if (!methodNames.add(method.name)) {
                    errors.add(new semanticError("Duplicate method name '" + method.name + "' in class '" + className + "'"));
                    valid = false;
                }

                typeCheckResult methodResult = typeCheck(method);
                valid = valid && methodResult.isValid();
            }
        }

        return new typeCheckResult(valid, toCheck);
    }

    public typeCheckResult typeCheck(Field field) {
        System.out.println("Checking field: " + field.name);
        return new typeCheckResult(true, field);
    }

    public typeCheckResult typeCheck(Method method) {
        System.out.println("Checking method: " + method.name);
        return new typeCheckResult(true, method);
    }
}