package ast.statements;

import ast.Expression;
import ast.Type;

public class Continue implements Expression {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }
}
