package ast.ExprStat;

import ast.Expression;
import ast.Statement;
import ast.Type;

public class Assign implements Expression, Statement {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }

    public Expression statementExpression;

    public Expression expression;
}
