package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class IntConst implements Expression {
    public int value;
    
    public IntConst(int value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionVisitor visitor, MethodVisitor mv, Map<String, Integer> locals) {
        visitor.visitIntConst(this, mv, locals);
    }
}