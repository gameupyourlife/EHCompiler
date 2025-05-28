package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class DoWhile implements Statement {
    public Expression condition;
    public Statement statement;
    
    public DoWhile() {}
    
    public DoWhile(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void accept(IStatementVisitor visitor, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {
        visitor.visitDoWhile(this, mv, locals, returnType);
    }
}