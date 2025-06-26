package ast.types;

import ast.exprStatements.MethodCall;
import ast.exprStatements.New;
import ast.exprStatements.Unary;
import ast.expressions.*;
import org.example.context.Context;

public class TypeResolver implements ITypeResolver {

    public static Context ctx;

    public TypeResolver(Context ctx) {
        this.ctx = ctx;
    }

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

        Type inner;
        if (expr.expression instanceof Identifier) {
            inner =  ((Identifier) expr.expression).getType();
        }else {
            inner = expr.expression.resolveType(this);
        }

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

        Type leftType;
        if (expr.left instanceof Identifier) {
            leftType =  ((Identifier) expr.left).getType();
        }else {
            leftType = expr.left.resolveType(this);
        }

        Type rightType;
        if (expr.right instanceof Identifier) {
            rightType =  ((Identifier) expr.right).getType();
        }else {
            rightType = expr.right.resolveType(this);
        }

        switch (expr.operator) {
            case PLUS, MULTIPLY, MINUS, DIVIDE, MODULUS:
                if (leftType == Type.INT && rightType == Type.INT)
                    return Type.INT;
                else throw new UnsupportedOperationException("Operator " + expr.operator + " not supported");
            case NEGATE, AND, OR:
                if (leftType == Type.BOOLEAN && rightType == Type.BOOLEAN)
                    return Type.BOOLEAN;
                else throw new UnsupportedOperationException("Operator " + expr.operator + " not supported");
            case LESS_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL:
                if (leftType == Type.INT && rightType == Type.INT)
                    return Type.BOOLEAN;
            default:
                return Type.UNKNOWN;
        }
    }

    @Override
    public Type resolve(MethodCall expr) {
        return null;
    }
}
