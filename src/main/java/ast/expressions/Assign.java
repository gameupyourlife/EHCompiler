package ast.expressions;

import ast.AbstractExpression;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class Assign extends AbstractExpression{
    public AbstractExpression target;
    public AbstractExpression value;
    
    public Assign() {}
    
    public Assign(AbstractExpression target, AbstractExpression value) {
        this.target = target;
        this.value = value;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {

    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {

    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return null;
    }
}