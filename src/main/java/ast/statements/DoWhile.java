package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class DoWhile implements Statement {
    public Expression condition;
    public Statement statement;
    
    public DoWhile() {}
    
    public DoWhile(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitDoWhile(this);
    }
}