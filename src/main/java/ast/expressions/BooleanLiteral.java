package ast.expressions;

import ast.Expression;

public class BooleanLiteral implements Expression {
    public boolean value;
    
    public BooleanLiteral(boolean value) {
        this.value = value;
    }
}