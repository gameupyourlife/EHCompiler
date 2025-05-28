package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class While implements Statement {
    public Expression condition;
    public Statement statement;
    
    public While() {}
    
    public While(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitWhile(this);
    }

}