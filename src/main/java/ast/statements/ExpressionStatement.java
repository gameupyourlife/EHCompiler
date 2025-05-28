package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class ExpressionStatement implements Statement {
    public Expression expression;
    
    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(IStatementVisitor visitor, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {
        visitor.visitExpressionStatement(this, mv, locals, returnType);
    }
}