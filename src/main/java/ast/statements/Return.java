package ast.statements;

import ast.Expression;
import ast.Statement;
import ast.Type;

public class Return implements Statement {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }

    public Expression expression;
}
