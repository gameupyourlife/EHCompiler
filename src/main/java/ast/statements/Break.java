package ast.statements;

import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class Break implements Statement {

    @Override
    public void accept(IStatementVisitor visitor, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {
        visitor.visitBreak(this, mv, locals, returnType);
    }
}