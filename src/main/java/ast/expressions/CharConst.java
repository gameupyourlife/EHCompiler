package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class CharConst implements Expression {
    public char value;
    
    public CharConst(char value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitCharConst(this);
    }
}