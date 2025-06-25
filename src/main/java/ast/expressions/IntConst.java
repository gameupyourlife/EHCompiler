package ast.expressions;

import ast.AbstractExpression;
import ast.Expression;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class IntConst extends AbstractExpression {
    public int value;
    
    public IntConst(int value) {
        this.value = value;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {

    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitIntConst(this);
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }
}