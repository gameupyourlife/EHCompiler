package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class Null implements Expression {
    @Override
    public void accept(IExpressionVisitor visitor, MethodVisitor mv, Map<String, Integer> locals) {
        visitor.visitNull(this, mv, locals);
    }
}