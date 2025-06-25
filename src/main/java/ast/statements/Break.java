package ast.statements;

import ast.AbstractStatement;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class Break extends AbstractStatement {

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitBreak(this);
    }
}