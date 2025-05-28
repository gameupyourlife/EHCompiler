package ast.statements;

import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class Continue implements Statement {

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitContinue(this);
    }
}