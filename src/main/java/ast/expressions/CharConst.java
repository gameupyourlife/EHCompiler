package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class CharConst implements Expression {
    public char value;
    
    public CharConst(char value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitCharConst(this);
    }
}