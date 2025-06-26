package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class ExpressionStatement implements Statement {
    public Expression expression;
    
    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }


    @Override
    public void accept(IStatementBytecodeGenerator visitor) {

    }
}