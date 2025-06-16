package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.types.Type;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class LocalVarDecl implements Statement {
    public Type type;
    public String name;
    public Expression init;
    
    public LocalVarDecl() {}
    
    public LocalVarDecl(Type type, String name, Expression init) {
        this.type = type;
        this.name = name;
        this.init = init;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitLocalVarDecl(this);
    }
}