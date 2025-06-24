package ast;

import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public interface Expression {
    void accept(IExpressionBytecodeGenerator visitor);
    Type resolveType(ITypeResolver resolver);
}