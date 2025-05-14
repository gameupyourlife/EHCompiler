package ast.statements;

import ast.Expression;
import ast.Statement;

public class ExpressionStatement implements Statement {
    public Expression expression;
    
    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }
}