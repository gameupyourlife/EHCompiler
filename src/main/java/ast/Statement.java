package ast;

import bytecode.interfaces.IStatementBytecodeGenerator;

public interface Statement {
    void accept(IStatementBytecodeGenerator visitor);
}
