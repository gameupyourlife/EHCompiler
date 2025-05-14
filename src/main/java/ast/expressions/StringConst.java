package ast.expressions;

import ast.Expression;

public class StringConst implements Expression {
    public String value;
    
    public StringConst(String value) {
        this.value = value;
    }
}