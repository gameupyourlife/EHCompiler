package ast.expressions;

import ast.Expression;
import ast.Operator;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class Binary implements Expression {
    public Operator operator;
    public Expression left;
    public Expression right;
    private Type type;

    public Binary(Operator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left     = left;
        this.right    = right;
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {

    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitBinary(this);
    }

}
