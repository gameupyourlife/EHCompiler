package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class While implements Statement {
    public Expression condition;
    public Block block;
    
    public While() {}
    
    public While(Expression condition, Block block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitWhile(this);
    }
}