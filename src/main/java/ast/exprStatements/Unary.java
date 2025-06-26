package ast.exprStatements;

import ast.Expression;
import ast.Operator;
import ast.Statement;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;
import ast.types.ITypeResolver;
import ast.types.Type;

public class Unary implements Expression, Statement {
    public Operator operator;
    public Expression expression;

    public Unary(Operator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitUnary(this);
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitUnary(this);
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }
}
