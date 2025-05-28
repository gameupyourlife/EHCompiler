package ast.exprStatements;

import ast.Expression;
import ast.Statement;

public class Assign implements Expression, Statement {
    public Expression target;
    public Expression value;
    
    public Assign() {}
    
    public Assign(Expression target, Expression value) {
        this.target = target;
        this.value = value;
    }
}