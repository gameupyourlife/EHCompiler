package ast.expressions;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodCall implements Expression {
    public Expression target;  // The object on which the method is called
    public String methodName;
    public List<Expression> arguments;
    
    public MethodCall() {
        this.arguments = new ArrayList<>();
    }
    
    public MethodCall(Expression target, String methodName, List<Expression> arguments) {
        this.target = target;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor, MethodVisitor mv, Map<String, Integer> locals) {
        visitor.visitMethodCall(this, mv, locals);
    }
}