package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class PrintlnStatement implements Statement {
    public Expression expression;

    public PrintlnStatement() {
    }

    public PrintlnStatement(Expression expression) {
                this.expression = expression;
    }
    
    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitPrintLnStatement(this);

    }
}
