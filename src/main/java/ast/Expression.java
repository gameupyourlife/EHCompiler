package ast;

import bytecode.interfaces.IExpressionVisitor;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public interface Expression {
    void accept(IExpressionVisitor visitor, MethodVisitor mv, Map<String, Integer> locals);
}