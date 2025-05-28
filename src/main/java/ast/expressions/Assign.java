package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionVisitor;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class Assign implements Expression {
    public Expression target;
    public Expression value;
    
    public Assign() {}
    
    public Assign(Expression target, Expression value) {
        this.target = target;
        this.value = value;
    }

    @Override
    public void accept(IExpressionVisitor visitor, MethodVisitor mv, Map<String, Integer> locals) {
        visitor.visitAssign(this, mv, locals);
    }
}