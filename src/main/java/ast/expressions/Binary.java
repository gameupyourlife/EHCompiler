package ast.expressions;

import ast.Expression;
import ast.Operator;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class Binary implements Expression {
    public Expression left;
    public Expression right;
    public Operator operator;

    public Binary(Expression left, Operator operator, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitBinary(this);
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        resolver.resolve(this);
    }
}
