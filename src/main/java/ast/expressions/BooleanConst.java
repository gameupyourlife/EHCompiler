package ast.expressions;

import ast.Expression;

public class BooleanConst implements Expression {
    public boolean value;
    
    public BooleanConst(boolean value) {
        this.value = value;
    }
}