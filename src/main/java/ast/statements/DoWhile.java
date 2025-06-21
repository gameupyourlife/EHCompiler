package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class DoWhile implements Statement {
    public Expression condition;
    public Block block;
    
    public DoWhile() {}
    
    public DoWhile(Expression condition, Block block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitDoWhile(this);
    }
}