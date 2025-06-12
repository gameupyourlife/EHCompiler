package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class Return implements Statement {
    public Expression expression;
    
    public Return() {}
    
    public Return(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitReturn(this);
    }
}