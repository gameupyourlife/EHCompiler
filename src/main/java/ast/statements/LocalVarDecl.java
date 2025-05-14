package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;

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
}