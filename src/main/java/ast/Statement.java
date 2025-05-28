package ast;

import bytecode.interfaces.IStatementVisitor;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public interface Statement {
    void accept(IStatementVisitor visitor, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
}
