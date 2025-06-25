package ast.statements;

import ast.AbstractExpression;
import ast.AbstractStatement;
import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class ExpressionStatement extends AbstractStatement {
    public AbstractExpression expression;
    
    public ExpressionStatement(AbstractExpression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitExpressionStatement(this);
    }
}