package ast.expressions;

import ast.Expression;

public class IdentifierExpression implements Expression {
    public String name;
    
    public IdentifierExpression(String name) {
        this.name = name;
    }
}