package org.example.semantic;

import ast.*;
import ast.Class;
import ast.exprStatements.Unary;
import ast.statements.ExpressionStatement;
import ast.statements.LocalVarDecl;
import ast.statements.Return;
import ast.statements.If;
import ast.statements.While;
import ast.statements.For;
import ast.statements.DoWhile;
import ast.expressions.Identifier;
import ast.expressions.Binary;
import ast.expressions.BooleanConst;
import ast.expressions.IntConst;
import ast.Operator;
import ast.types.Type;
import ast.statements.Block;
import ast.statements.Break;
import ast.statements.Continue;
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
    private int loopDepth = 0;

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
        if (stmt instanceof LocalVarDecl) {
            LocalVarDecl decl = (LocalVarDecl) stmt;
            // Typ existiert?
            if (!context.typeExists(decl.type)) {
                errors.add(new semanticError(
                        "Unbekannter Typ '" + decl.type + "' für Variable '" + decl.name + "'"));
            }
            // Variable deklarieren
            if (!context.declareVariable(decl.name, decl.type)) {
                errors.add(new semanticError(
                        "Variable '" + decl.name + "' bereits deklariert"));
            }
            // Initialisierer prüfen
            if (decl.init != null) {
                Type initT = evaluateExpressionType(decl.init);
                if (initT != decl.type) {
                    errors.add(new semanticError(
                            "Typ-Mismatch: erwartete " + decl.type + ", aber erhalten: " + initT));
                }
            }
            return new typeCheckResult(errors.isEmpty(), stmt);
        }
        if (stmt instanceof ExpressionStatement) {
            ExpressionStatement es = (ExpressionStatement) stmt;
            // z.B. "sumOfEvens += i;" oder "i = 0;"
            evaluateExpressionType(es.expression);
            return new typeCheckResult(true, stmt);
        }

        // Neuer Scope für Blöcke
        if (stmt instanceof ast.statements.Block) {
            ast.statements.Block b = (ast.statements.Block) stmt;
            context.enterScope();
            boolean blockValid = true;
            for (Statement inner : b.statements) {
                typeCheckResult r = typeCheck(inner, expectedReturnType);
                blockValid &= r.isValid();
            }
            context.exitScope();
            return new typeCheckResult(blockValid, stmt);
        }

        // break/continue nur innerhalb einer Schleife
        if (stmt instanceof Break) {
            if (loopDepth == 0) {
                errors.add(new semanticError("Break außerhalb einer Schleife"));
                return new typeCheckResult(false, stmt);
                }
            return new typeCheckResult(valid, stmt);
        }
        if (stmt instanceof Continue) {
            if (loopDepth == 0) {
                errors.add(new semanticError("Continue außerhalb einer Schleife"));
                return new typeCheckResult(false, stmt);
                }
            return new typeCheckResult(valid, stmt);
        }

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

        // if–Anweisungen
        if (stmt instanceof If) {
            If ifStmt = (If) stmt;
            // 1) Bedingung auswerten muss boolean sein
            Type condType = evaluateExpressionType(ifStmt.condition);
            if (condType != Type.BOOLEAN) {
                errors.add(new semanticError(
                        "Bedingung in if muss BOOLEAN sein, aber gefunden: " + condType));
                valid = false;
            }
            // Then–Zweig prüfen
            typeCheckResult thenRes = typeCheck(ifStmt.thenStatement, expectedReturnType);
            valid = valid && thenRes.isValid();
            // Optional Else–Zweig prüfen
            if (ifStmt.elseStatement != null) {
                typeCheckResult elseRes = typeCheck(ifStmt.elseStatement, expectedReturnType);
                valid = valid && elseRes.isValid();
            }
            return new typeCheckResult(valid, stmt);
        }

        // While–Schleife
        if (stmt instanceof While) {
            While w = (While) stmt;
            Type condType = evaluateExpressionType(w.condition);
            if (condType != Type.BOOLEAN) {
                errors.add(new semanticError(
                        "Bedingung in while muss BOOLEAN sein, aber gefunden: " + condType));
                valid = false;
            }
            loopDepth++;
            // Body prüfen
            typeCheckResult bodyRes = typeCheck(w.statement, expectedReturnType);
            valid = valid && bodyRes.isValid();
            loopDepth--;
            return new typeCheckResult(valid, stmt);
        }

        // For–Schleife
        if (stmt instanceof For) {
            For f = (For) stmt;

            // 1) Neuer Scope für Init, Condition, Update und Body
            context.enterScope();

            // 2) Init-Teil: Deklaration oder Zuweisung
            if (f.init instanceof LocalVarDecl) {
                // "int i = 0;"
                typeCheck((LocalVarDecl) f.init, expectedReturnType);
            } else if (f.init instanceof ExpressionStatement) {
                // "i = 0;"
                ExpressionStatement initStmt = (ExpressionStatement) f.init;
                evaluateExpressionType(initStmt.expression);
            }

            // 3) Condition prüfen
            if (f.condition != null) {
                Type condT = evaluateExpressionType(f.condition);
                if (condT != Type.BOOLEAN) {
                    errors.add(new semanticError(
                            "Bedingung in for muss BOOLEAN sein, aber gefunden: " + condT));
                }
            }

            // 4) Update prüfen (z.B. i++)
            if (f.update != null) {
                // Bei euch ist das direkt eine Expression, also:
                evaluateExpressionType(f.update);
            }

            // 5) Body prüfen, mit loopDepth für break/continue
            loopDepth++;
            typeCheck(f.statement, expectedReturnType);
            loopDepth--;

            // 6) Scope wieder verlassen
            context.exitScope();

            return new typeCheckResult(true, stmt);
        }

        // Do-While-Schleife
        if (stmt instanceof DoWhile) {
            DoWhile d = (DoWhile) stmt;
            loopDepth++;
            // Body zuerst prüfen
            typeCheckResult bodyRes = typeCheck(d.statement, expectedReturnType);
            valid = valid && bodyRes.isValid();
            // Bedingung muss BOOLEAN sein
            Type condType = evaluateExpressionType(d.condition);
            if (condType != Type.BOOLEAN) {
                errors.add(new semanticError(
                        "Bedingung in do-while muss BOOLEAN sein, aber gefunden: " + condType));
                valid = false;
            }
            loopDepth--;
            return new typeCheckResult(valid, stmt);
        }
        return new typeCheckResult(valid, stmt);
    }

    public Type evaluateExpressionType(Expression expr) {
        if (expr instanceof ast.expressions.Identifier) {
            String name = ((ast.expressions.Identifier) expr).name;
            Type t = context.lookupVariable(name);
            if (t == null) {
                errors.add(new semanticError("Variable '" + name + "' nicht deklariert"));
                return null;
            }
            return t;
        }
        // Null-Ausdruck
        if (expr == null) {
            errors.add(new semanticError("Null expression"));
            return null;
        }

        // Literale
        if (expr instanceof ast.expressions.Null) {
            return null;
        }
        if (expr instanceof BooleanConst) {
            return Type.BOOLEAN;
        }
        if (expr instanceof IntConst) {
            return Type.INT;
        }

        // Identifier (Variable oder Parameter)
        if (expr instanceof Identifier) {
            Identifier id = (Identifier) expr;
            Type varType = context.lookupVariable(id.name);
            if (varType == null) {
                // ggf. auch Felder prüfen: varType = context.lookupField(id.name);
                errors.add(new semanticError("Variable '" + id.name + "' nicht deklariert"));
                return null;
            }
            return varType;
        }

        // Binäroperationen
        if (expr instanceof Binary) {
            Binary bin = (Binary) expr;
            Type left  = evaluateExpressionType(bin.left);
            Type right = evaluateExpressionType(bin.right);
            Operator op = bin.operator;

            switch (op) {
                case PLUS, MINUS, MULTIPLY, DIVIDE, MODULUS -> {
                    if (left != Type.INT || right != Type.INT) {
                        errors.add(new semanticError(
                                "Operator '" + op.getSymbol() +
                                        "' nur auf INT anwendbar, aber gefunden: " +
                                        left + " und " + right));
                        return null;
                    }
                    return Type.INT;
                }
                case AND, OR -> {
                    if (left != Type.BOOLEAN || right != Type.BOOLEAN) {
                        errors.add(new semanticError(
                                "Operator '" + op.getSymbol() +
                                        "' nur auf BOOLEAN anwendbar, aber gefunden: " +
                                        left + " und " + right));
                        return null;
                    }
                    return Type.BOOLEAN;
                }
                case EQUALS, NOT_EQUALS -> {
                    if (left == right &&
                            (left == Type.INT || left == Type.BOOLEAN)) {
                        return Type.BOOLEAN;
                    }
                    errors.add(new semanticError(
                            "Operator '" + op.getSymbol() +
                                    "' nur auf gleich typisierten INT oder BOOLEAN anwendbar, aber gefunden: " +
                                    left + " und " + right));
                    return null;
                }
                case LESS_THAN, LESS_THAN_OR_EQUAL,
                     GREATER_THAN, GREATER_THAN_OR_EQUAL -> {
                    if (left != Type.INT || right != Type.INT) {
                        errors.add(new semanticError(
                                "Operator '" + op.getSymbol() +
                                        "' nur auf INT anwendbar, aber gefunden: " +
                                        left + " und " + right));
                        return null;
                    }
                    return Type.BOOLEAN;
                }
                default -> {
                    errors.add(new semanticError("Unbekannter Operator: " + op.getSymbol()));
                    return null;
                }
            }
        }

        // Method-Calls (dein bestehender Code)
        if (expr instanceof ast.exprStatements.MethodCall) {
            ast.exprStatements.MethodCall call = (ast.exprStatements.MethodCall) expr;
            Type targetType = evaluateExpressionType(call.target);
            if (targetType == null) {
                errors.add(new semanticError(
                        "Cannot determine type of target in method call '" +
                                call.methodName + "'"));
                return null;
            }
            Method m = context.findMethod(targetType, call.methodName);
            if (m == null) {
                errors.add(new semanticError(
                        "Method '" + call.methodName +
                                "' not found in type '" + targetType + "'"));
                return null;
            }
            return m.type;
        }

        // Unäre Operatoren
        if (expr instanceof Unary) {
            Unary unary = (Unary) expr;
            Type operand = evaluateExpressionType(unary.expression);

            // Logisches NOT (Symbol z.B. "!")
            if (unary.operator == Operator.NEGATE) {
                if (operand != Type.BOOLEAN) {
                    errors.add(new semanticError(
                            "Unary operator 'NEGATE' erfordert BOOLEAN operand, aber gefunden: " + operand));
                    return null;
                }
                return Type.BOOLEAN;
            }
            // Arithmetisches Minus
            if (unary.operator == Operator.UMINUS) {
                if (operand != Type.INT) {
                    errors.add(new semanticError(
                            "Unary operator 'UMINUS' erfordert INT operand, aber gefunden: " + operand));
                    return null;
                }
                return Type.INT;
            }
            // Prä-/Post-Inkrement, -Dekrement
            if (unary.operator == Operator.INCREMENT || unary.operator == Operator.DECREMENT) {
                if (operand != Type.INT) {
                    errors.add(new semanticError(
                            "Unary operator '" + unary.operator + "' nur auf INT anwendbar, aber gefunden: " + operand));
                    return null;
                }
                return Type.INT;
            }

            errors.add(new semanticError("Unsupported unary operator: " + unary.operator));
            return null;
        }

        // Fallback über Enum-Namen
        String className = expr.getClass().getSimpleName().toUpperCase();
        try {
            return Type.valueOf(className);
        } catch (IllegalArgumentException e) {
            errors.add(new semanticError(
                    "Unsupported expression type: " + expr.getClass().getSimpleName()));
            return null;
        }
    }
}