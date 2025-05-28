package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class CharConst implements Expression {
    public char value;
    
    public CharConst(char value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionVisitor visitor, MethodVisitor mv, Map<String, Integer> locals) {
        visitor.visitCharConst(this, mv, locals);
    }
}