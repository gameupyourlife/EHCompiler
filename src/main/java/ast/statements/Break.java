package ast.statements;

import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.Label;

public class Break implements Statement {

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitBreak(this);
    }
}