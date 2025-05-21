package ast.statements;

import ast.Expression;
import ast.Statement;

public class While implements Statement {
    public Expression condition;
    public Statement statement;
    
    public While() {}
    
    public While(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }
}