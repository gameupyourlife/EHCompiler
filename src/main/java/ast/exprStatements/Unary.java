package ast.exprStatements;

import ast.Expression;
import ast.Operator;
import ast.Statement;

public class Unary implements Expression, Statement {
    public Operator operator;
    public Expression expression;

    public Unary(Operator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }
}
