package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class Identifier implements Expression {
    public String name;
    
    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitIdentifier(this);
    }
}