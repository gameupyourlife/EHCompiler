package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class Identifier implements Expression {
    public String name;
    
    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public void accept(IExpressionVisitor visitor, MethodVisitor mv, Map<String, Integer> locals) {
        visitor.visitIdentifier(this, mv, locals);
    }
}