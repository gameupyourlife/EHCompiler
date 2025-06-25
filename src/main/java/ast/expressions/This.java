package ast.expressions;

import ast.AbstractExpression;
import ast.Expression;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class This extends AbstractExpression {

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {

    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitThis(this);
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }
}