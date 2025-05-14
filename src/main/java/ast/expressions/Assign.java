package ast.expressions;

import ast.Expression;

public class Assign implements Expression {
    public Expression target;
    public Expression value;
    
    public Assign() {}
    
    public Assign(Expression target, Expression value) {
        this.target = target;
        this.value = value;
    }
}