package ast.types;

import ast.Expression;
import ast.expressions.Identifier;

import java.util.List;

public class ClassResolver implements IClassResolver {

    TypeResolver resolver;

    public ClassResolver(TypeResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public String resolveClassName(Expression expr) {
        Type type =  expr.resolveType(resolver);
        if (type != Type.CLASS) {
            throw new IllegalArgumentException(expr + " is not a class");
        }

        if (expr instanceof Identifier) {
            Identifier id = (Identifier) expr;
            if (id.name != null) {
                return id.className.replace('.', '/');
            }
        }

        throw new IllegalStateException("Cannot resolve class name from expression: " + expr);
    }

    @Override
    public String makeMethodDescriptor(Type returnType, List<Type> paramTypes, String className) {
        StringBuilder descriptor = new StringBuilder();
        descriptor.append('(');

        for (Type paramType : paramTypes) {
            descriptor.append(getTypeDescriptor(paramType, className));
        }

        descriptor.append(')');
        descriptor.append(getTypeDescriptor(returnType, className));
        return descriptor.toString();
    }

    @Override
    public String getTypeDescriptor(Type type, String className) {
        return switch (type) {
            case INT -> "I";
            case BOOLEAN -> "Z";
            case CHAR -> "C";
            case VOID -> "V";
            case CLASS -> "L" + className + ";";
            default -> throw new IllegalArgumentException("Unsupported type for descriptor: " + type);
        };
    }
}
