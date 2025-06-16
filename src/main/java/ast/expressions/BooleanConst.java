package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class BooleanConst implements Expression {
    public boolean value;
    
    public BooleanConst(boolean value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitBooleanConst(this);
    }
}