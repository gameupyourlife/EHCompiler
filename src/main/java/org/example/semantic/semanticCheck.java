package org.example.semantic;

import ast.*;
import ast.Class;
import ast.exprStatements.Unary;
import ast.statements.LocalVarDecl;
import ast.statements.Return;
import ast.statements.If;
import ast.statements.While;
import ast.statements.For;
import ast.statements.DoWhile;
import ast.types.Type;
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
                if (!context.typeExists(field.type)) {
                    errors.add(new semanticError("Unknown type '" + field.type + "' for field '" + field.name + "'"));
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
        boolean valid = true;
        System.out.println("Checking field: " + field.name);
        if (!context.typeExists(field.type)) {
            errors.add(new semanticError("Unknown type '" + field.type + "' for field '" + field.name + "'"));
            valid = false;
        }
        return new typeCheckResult(valid, field);
    }

    public typeCheckResult typeCheck(Method method) {
        System.out.println("Checking method: " + method.name);
        boolean valid = true;

        // Rückgabetyp prüfen
        if (!context.typeExists(method.type)) {
            errors.add(new semanticError("Unknown return type '" + method.type + "' in method '" + method.name + "'"));
            valid = false;
        }

        context.enterScope();

        // Parameter prüfen und deklarieren
        Set<String> paramNames = new HashSet<>();
        if (method.parameters != null) {
            for (var param : method.parameters) {
                if (!paramNames.add(param.name)) {
                    errors.add(new semanticError("Duplicate parameter name '" + param.name + "' in method '" + method.name + "'"));
                    valid = false;
                }
                if (!context.typeExists(param.type)) {
                    errors.add(new semanticError("Unknown type '" + param.type + "' for parameter '" + param.name + "' in method '" + method.name + "'"));
                    valid = false;
                }
                if (!context.declareVariable(param.name, param.type)) {
                    errors.add(new semanticError("Variable '" + param.name + "' already declared in method '" + method.name + "'"));
                    valid = false;
                }
            }
        }

        // Statements prüfen
        if (method.statement != null) {
            for(Statement stmt : method.statement){
                if(stmt instanceof Return){
                    Return returnStmt = (Return) stmt; // Expliziter Cast
                    Expression returnExpr = returnStmt.expression;
                    Type exprType = evaluateExpressionType(returnExpr);
                    if(method.type != exprType){
                        errors.add(new semanticError("Return type for '" + method.name + "' doesn't match the method type: '" + method.type + ", " + exprType + "'"));
                    }
                }
            }
            for (Statement stmt : method.statement) {
                // Hier brauchst du noch eine typeCheck(Statement, Type) Methode
                typeCheckResult stmtRes = typeCheck(stmt, method.type);
                valid = valid && stmtRes.isValid();
            }
        }

        context.exitScope();

        return new typeCheckResult(valid, method);
    }

    public typeCheckResult typeCheck(Statement stmt, Type expectedReturnType) {
        boolean valid = true;

        // Beispiel: Prüfen, ob Statement ein ReturnStatement ist
        if (stmt instanceof Return) {
            Return retStmt = (Return) stmt;
            Type actualReturnType = evaluateExpressionType(retStmt.expression);
            if (actualReturnType != expectedReturnType) {
                errors.add(new semanticError("Return type mismatch: expected " + expectedReturnType + ", but got " + actualReturnType));
                valid = false;
            }
            return new typeCheckResult(valid, stmt);
        }

        // Behandlung von if–Anweisungen
        if (stmt instanceof If) {
            If ifStmt = (If) stmt;
            // 1) Bedingung auswerten muss boolean sein
            Type condType = evaluateExpressionType(ifStmt.condition);
            if (condType != Type.BOOLEAN) {
                errors.add(new semanticError(
                        "Bedingung in if muss BOOLEAN sein, aber gefunden: " + condType));
                valid = false;
            }
            // 2) Then–Zweig prüfen
            typeCheckResult thenRes = typeCheck(ifStmt.thenStatement, expectedReturnType);
            valid = valid && thenRes.isValid();
            // 3) Optional Else–Zweig prüfen
            if (ifStmt.elseStatement != null) {
                typeCheckResult elseRes = typeCheck(ifStmt.elseStatement, expectedReturnType);
                valid = valid && elseRes.isValid();
            }
            return new typeCheckResult(valid, stmt);
        }

        // While–Schleife
        if (stmt instanceof While) {
            While w = (While) stmt;
            // 1) Bedingung muss BOOLEAN sein
            Type condType = evaluateExpressionType(w.condition);
            if (condType != Type.BOOLEAN) {
                errors.add(new semanticError(
                        "Bedingung in while muss BOOLEAN sein, aber gefunden: " + condType));
                valid = false;
            }
            // 2) Body prüfen
            typeCheckResult bodyRes = typeCheck(w.statement, expectedReturnType);
            valid = valid && bodyRes.isValid();
            return new typeCheckResult(valid, stmt);
        }

        // For–Schleife
        if (stmt instanceof For) {
            For f = (For) stmt;
            // 1) Init (Expression) prüfen, falls vorhanden
            if (f.init != null) {
                Type initType = evaluateExpressionType(f.init);
                // hier evtl. weitergehende Checks, z.B. auf void o.Ä.
            }
            // 2) Bedingung muss BOOLEAN sein
            if (f.condition != null) {
                Type condType = evaluateExpressionType(f.condition);
                if (condType != Type.BOOLEAN) {
                    errors.add(new semanticError(
                            "Bedingung in for muss BOOLEAN sein, aber gefunden: " + condType));
                    valid = false;
                }
            }
            // 3) Update (Expression) prüfen, falls vorhanden
            if (f.update != null) {
                Type updType = evaluateExpressionType(f.update);
                // evtl. Check, dass updType nicht void ist
            }
            // 4) Body prüfen
            typeCheckResult bodyRes = typeCheck(f.statement, expectedReturnType);
            valid = valid && bodyRes.isValid();
            return new typeCheckResult(valid, stmt);
        }

        // Do-While-Schleife
        if (stmt instanceof DoWhile) {
            DoWhile d = (DoWhile) stmt;
            // 1) Body zuerst prüfen
            typeCheckResult bodyRes = typeCheck(d.statement, expectedReturnType);
            valid = valid && bodyRes.isValid();
            // 2) Bedingung muss BOOLEAN sein
            Type condType = evaluateExpressionType(d.condition);
            if (condType != Type.BOOLEAN) {
                errors.add(new semanticError(
                        "Bedingung in do-while muss BOOLEAN sein, aber gefunden: " + condType));
                valid = false;
            }
            return new typeCheckResult(valid, stmt);
        }

        // Beispiel: VariableDeclaration
        if (stmt instanceof LocalVarDecl) {
            LocalVarDecl varDecl = (LocalVarDecl) stmt;
            // Typ prüfen, Variable in Scope eintragen usw.
            // ...
            return new typeCheckResult(valid, stmt);
        }

        // Weitere Statement-Typen prüfen...

        return new typeCheckResult(valid, stmt);
    }

    public Type evaluateExpressionType(Expression expr) {
        if (expr == null) {
            errors.add(new semanticError("Null expression"));
            return null;
        }

        if (expr instanceof ast.expressions.Null) {
            // Null-Typ, ggf. als VOID oder spezieller NULL-Type behandeln
            return null;
        }

        if (expr instanceof ast.expressions.BooleanConst || expr instanceof ast.expressions.BooleanLiteral) {
            return Type.BOOLEAN;
        }

        if (expr instanceof ast.exprStatements.MethodCall) {
            ast.exprStatements.MethodCall call = (ast.exprStatements.MethodCall) expr;

            Type targetType = evaluateExpressionType(call.target);
            if (targetType == null) {
                errors.add(new semanticError("Cannot determine type of target in method call '" + call.methodName + "'"));
                return null;
            }

            Method method = context.findMethod(targetType, call.methodName);
            if (method == null) {
                errors.add(new semanticError("Method '" + call.methodName + "' not found in type '" + targetType + "'"));
                return null;
            }

            return method.type;
        }

        String className = expr.getClass().getSimpleName().toUpperCase();
        try {
            return Type.valueOf(className);
        } catch (IllegalArgumentException e) {
            if (expr instanceof ast.exprStatements.Unary) {
                Unary unary = (Unary) expr;
                Type operandType = evaluateExpressionType(unary.expression);
                if (operandType == Type.INT &&
                        (unary.operator == Operator.INCREMENT || unary.operator == Operator.DECREMENT)) {
                    return Type.INT;
                }
                errors.add(new semanticError("Unary operator '" + unary.operator + "' requires INT operand."));
                return null;
            }

            errors.add(new semanticError("Unsupported expression type: " + expr.getClass().getSimpleName()));
            return null;
        }
    }
}