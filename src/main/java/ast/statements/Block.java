package ast.statements;

import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;
import java.util.ArrayList;
import java.util.List;

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