package ast.ExprStat;

import ast.Expression;
import ast.Statement;
import ast.Type;

import java.util.List;

public class MethodCall implements Expression, Statement {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }

    public String name;

    public Expression receiver;

    public List<Expression> expressions;
}
