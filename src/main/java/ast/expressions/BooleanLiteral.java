package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class BooleanLiteral implements Expression {
    public boolean value;
    
    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitBooleanLiteral(this);
    }
}