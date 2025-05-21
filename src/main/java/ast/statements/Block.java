package ast.statements;

import ast.Statement;
import ast.Type;

import java.util.List;

public class Block implements Statement {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }

    public List<Statement> statements;
}
