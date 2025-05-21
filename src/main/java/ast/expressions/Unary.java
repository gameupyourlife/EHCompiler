package ast.expressions;

import ast.Expression;
import ast.Operator;
import ast.Type;

public class Unary implements Expression {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }

    public Operator operator;

    public Expression expression;
}
