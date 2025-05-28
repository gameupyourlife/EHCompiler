package ast.exprStatements;

import ast.Expression;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class Assign {
    public Expression target;
    public Expression value;
    
    public Assign() {}
    
    public Assign(Expression target, Expression value) {
        this.target = target;
        this.value = value;
    }
}