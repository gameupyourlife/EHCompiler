package ast;

import bytecode.interfaces.IStatementBytecodeGenerator;

import java.util.List;

public interface Statement {
    void accept(IStatementBytecodeGenerator visitor);
}
