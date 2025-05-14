package ast.statements;

import ast.Statement;
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
}