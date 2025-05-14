package ast.expressions;

import ast.Expression;

public class Identifier implements Expression {
    public String name;
    
    public Identifier(String name) {
        this.name = name;
    }
}