package ast.statements;

import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class Continue implements Statement {

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitContinue(this);
    }
}