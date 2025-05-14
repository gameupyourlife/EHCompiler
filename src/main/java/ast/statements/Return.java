package ast.statements;

import ast.Expression;
import ast.Statement;

public class Return implements Statement {
    public Expression expression;
    
    public Return() {}
    
    public Return(Expression expression) {
        this.expression = expression;
    }
}