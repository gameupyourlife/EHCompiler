package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class For implements Statement {
    public Expression init;
    public Expression condition;
    public Expression update;
    public Block block;
    
    public For() {}
    
    public For(Expression init, Expression condition, Expression update, Block block) {
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.block = block;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitFor(this);
    }
}