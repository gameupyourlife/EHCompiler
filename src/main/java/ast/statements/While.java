package ast.statements;
import ast.Statement;
import ast.Type;
import ast.Expression;

public class While implements Statement {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }

    public Expression condition;

    public Block block;
}
