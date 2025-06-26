package ast;

import bytecode.interfaces.IStatementBytecodeGenerator;

import java.util.List;

public interface MethodCallStatement extends Statement {
    void accept(IStatementBytecodeGenerator visitor, List<Class> classes);
}
