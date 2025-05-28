package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class This implements Expression {

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitThis(this);
    }
}