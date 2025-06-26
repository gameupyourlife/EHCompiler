package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

import java.util.List;

public class PrintlnStatement implements Statement {
    public final Expression expression;

    public PrintlnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitPrintLnStatement(this);

    }
}
