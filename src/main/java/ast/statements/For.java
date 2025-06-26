package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

import javax.swing.plaf.nimbus.State;

public class For implements Statement {
    public Statement init;
    public Expression condition;
    public Expression update;
    public Statement statement;
    
    public For() {}
    
    public For(Statement init, Expression condition, Expression update, Statement statement) {
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