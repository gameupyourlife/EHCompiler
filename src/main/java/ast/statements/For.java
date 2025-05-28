package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementVisitor;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class For implements Statement {
    public Expression init;
    public Expression condition;
    public Expression update;
    public Statement statement;
    
    public For() {}
    
    public For(Expression init, Expression condition, Expression update, Statement statement) {
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.statement = statement;
    }

    @Override
    public void accept(IStatementVisitor visitor, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {
        visitor.visitFor(this, mv, locals, returnType);
    }
}