package ast.statements;

import ast.Expression;
import ast.Statement;

public class For implements Statement {
    public Expression init;
    public Expression condition;
    public Expression update;
    public Statement statement;
    
    public For() {}
    
    public For(Expression init, Expression condition, Expression update, Statement statement) {
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.statement = statement;
    }
}
