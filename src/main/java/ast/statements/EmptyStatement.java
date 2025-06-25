package ast.statements;

import ast.AbstractStatement;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class EmptyStatement extends AbstractStatement {

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitEmptyStatement(this);
    }
}