package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class EmptyExpression implements Expression {
    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitEmptyExpression(this);
    }
}