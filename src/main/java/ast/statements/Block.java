package ast.statements;

import ast.Statement;
import ast.Type;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Block implements Statement {
    public List<Statement> statements;
    
    public Block() {
        this.statements = new ArrayList<>();
    }
    
    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitBlock(this);
    }
}