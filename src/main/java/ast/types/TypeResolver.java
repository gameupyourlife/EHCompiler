package ast.types;

import ast.exprStatements.*;
import ast.expressions.*;

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
            case UMINUS, INCREMENT, DECREMENT:
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
        Type leftType = expr.left.resolveType(this);
        Type rightType = expr.right.resolveType(this);

        switch (expr.operator) {
            case PLUS, MULTIPLY, MINUS, DIVIDE, MODULUS:
                if (leftType == Type.INT && rightType == Type.INT)
                    return Type.INT;
                break;
            case NEGATE:
                if (leftType == Type.BOOLEAN && rightType == Type.BOOLEAN)
                    return Type.BOOLEAN;
                break;
            default:
                break;
        }
        return Type.UNKNOWN;
    }

    @Override
    public Type resolve(MethodCall expr) {
        return null;
    }
}
