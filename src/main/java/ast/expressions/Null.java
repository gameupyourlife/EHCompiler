package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class Null implements Expression {
    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitNull(this);
    }
}