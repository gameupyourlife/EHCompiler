package ast.types;

import ast.exprStatements.*;
import ast.expressions.*;

import javax.naming.OperationNotSupportedException;

public class TypeResolver implements ITypeResolver {

    @Override
    public Type resolve(BooleanConst expr) {
        return Type.BOOLEAN;
    }

    @Override
    public Type resolve(BooleanLiteral expr) {
        return Type.BOOLEAN;
    }

    @Override
    public Type resolve(CharConst expr) {
        return Type.CHAR;
    }

    @Override
    public Type resolve(EmptyExpression expr) {
        return Type.VOID;
    }

    @Override
    public Type resolve(Identifier expr) {
        return Type.UNKNOWN;
    }

    @Override
    public Type resolve(IntConst expr) {
        return Type.INT;
    }

    @Override
    public Type resolve(Null expr) {
        return Type.NULL;
    }

    @Override
    public Type resolve(Super expr) {
        return Type.CLASS;
    }

    @Override
    public Type resolve(This expr) {
        return Type.CLASS;
    }

    @Override
    public Type resolve(Unary expr) {
        Type inner = expr.expression.resolveType(this);
        switch (expr.operator) {
            case NEGATE:
                if (inner == Type.BOOLEAN)
                    return Type.BOOLEAN;
                break;
            case UMINUS:
                if (inner == Type.INT)
                    return Type.INT;
                break;
            case INCREMENT:
            case DECREMENT:
                if (inner == Type.INT)
                    return Type.INT;
                break;
            default:
                break;
        }
        return Type.UNKNOWN;
    }

    @Override
    public Type resolve(New expr) {
        return Type.CLASS;
    }

    @Override
    public Type resolve(Binary expr) {
        Type left = expr.left.resolveType(this);
        Type right = expr.right.resolveType(this);
        if (left != right) {
            throw new IllegalStateException("Type mismatch: " + left + " vs " + right);
        }

        return switch (expr.operator) {
            case PLUS, MINUS, MULTIPLY, DIVIDE, MODULO -> {
                if (left != Type.INT) {
                    throw new IllegalStateException("Arithmetic ops require int, got: " + left);
                }
                yield Type.INT;
            }
            case EQUALS, NOT_EQUALS, LESS_THAN, LESS_OR_EQUALS, GREATER_OR_EQUALS, GREATER_THAN -> {
                if (left != Type.BOOLEAN) {
                    throw new IllegalStateException("Arithmetic ops require boolean, got: " + left);
                }
                yield Type.BOOLEAN;
            }
            default -> throw new UnsupportedOperationException("No " + expr.operator + " on " + left);
        };
    }
}
