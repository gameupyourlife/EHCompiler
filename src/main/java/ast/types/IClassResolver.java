package ast.types;

import ast.Expression;

import java.util.List;

public interface IClassResolver {
    String resolveClassName(Expression expr);
    String makeMethodDescriptor(Type returnType, List<Type> paramTypes, String className);
    String getTypeDescriptor(Type type, String className);
}
