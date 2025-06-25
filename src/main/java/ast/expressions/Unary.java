package ast.expressions;

import ast.AbstractExpression;
import ast.Operator;
import ast.Statement;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class Unary extends AbstractExpression implements Statement {
    public Operator operator;
    public AbstractExpression expression;

    public Unary(Operator operator, AbstractExpression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }
}
