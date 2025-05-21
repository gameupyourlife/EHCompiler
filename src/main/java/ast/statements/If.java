package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;

public class If implements Statement {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }

    public Expression ifCondition;

    public Expression elseCondition;

    public Block ifStatement;

    public Block elseStatement;
}
