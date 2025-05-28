package ast;

import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public interface Statement {
    void accept(IStatementBytecodeGenerator visitor);
}
