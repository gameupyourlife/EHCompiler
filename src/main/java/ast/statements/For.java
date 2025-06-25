package ast.statements;

import ast.AbstractStatement;
import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class For extends AbstractStatement {
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

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitFor(this);
    }
}