package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

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
    public void accept(IStatementVisitor visitor, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {
        visitor.visitLocalVarDecl(this, mv, locals, returnType);
    }
}