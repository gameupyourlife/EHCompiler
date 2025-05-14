package ast.expressions;

import ast.Expression;

public class IntConst implements Expression {
    public int value;
    
    public IntConst(int value) {
        this.value = value;
    }
}