package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class If implements Statement {
    public Expression condition;
    public Statement thenStatement;
    public Statement elseStatement;
    
    public If() {}
    
    public If(Expression condition, Statement thenStatement, Statement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitIf(this);
    }
}