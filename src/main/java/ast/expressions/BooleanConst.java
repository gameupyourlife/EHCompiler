package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionVisitor;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class BooleanConst implements Expression {
    public boolean value;
    
    public BooleanConst(boolean value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionVisitor visitor, MethodVisitor mv, Map<String, Integer> locals) {
        visitor.visitBooleanConst(this, mv, locals);
    }
}