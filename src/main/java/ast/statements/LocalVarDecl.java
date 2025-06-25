package ast.statements;

import ast.AbstractExpression;
import ast.AbstractStatement;
import ast.Expression;
import ast.Statement;
import ast.types.Type;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class LocalVarDecl extends AbstractStatement {
    public Type type;
    public String name;
    public AbstractExpression init;
    
    public LocalVarDecl() {}
    
    public LocalVarDecl(Type type, String name, AbstractExpression init) {
        this.type = type;
        this.name = name;
        this.init = init;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitLocalVarDecl(this);
    }
}