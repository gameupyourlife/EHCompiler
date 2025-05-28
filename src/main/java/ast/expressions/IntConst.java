package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class IntConst implements Expression {
    public int value;
    
    public IntConst(int value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitIntConst(this);
    }
}