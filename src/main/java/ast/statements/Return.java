package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class Return implements Statement {
    public Expression expression;
    
    public Return() {}
    
    public Return(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(IStatementVisitor visitor, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {
        visitor.visitReturn(this, mv, locals, returnType);
    }
}