package ast;

import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public interface Expression {
    void accept(IExpressionBytecodeGenerator visitor);
    Type resolveType(ITypeResolver resolver);
}