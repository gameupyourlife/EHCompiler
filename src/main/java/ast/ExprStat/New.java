package ast.ExprStat;

import ast.Expression;
import ast.Statement;
import ast.Type;

import java.util.List;

public class New implements Expression, Statement {
    private Type type;

    @Override
    public Type getType() { return type; }

    @Override
    public void setType(Type type) { this.type = type; }

    public List<Expression> constructorParams;
}
