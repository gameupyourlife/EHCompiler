package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class Super implements Expression {
    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitSuper(this);
    }
}