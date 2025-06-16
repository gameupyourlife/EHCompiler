package ast.expressions;

import ast.Expression;
import ast.Operator;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class Binary implements Expression {
    Expression left;
    Expression right;
    Operator operator;

    public Binary(Expression left, Operator operator, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitBinary(this);
    }
}
