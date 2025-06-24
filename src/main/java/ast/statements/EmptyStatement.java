package ast.statements;

import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class EmptyStatement implements Statement {

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitEmptyStatement(this);
    }
}