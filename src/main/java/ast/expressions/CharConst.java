package ast.expressions;

import ast.Expression;

public class CharConst implements Expression {
    public char value;
    
    public CharConst(char value) {
        this.value = value;
    }
}