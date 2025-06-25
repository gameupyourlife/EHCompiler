package ast.statements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IStatementBytecodeGenerator;

import java.util.List;

public class PrintlnStatement implements Statement {
    public final List<Expression> expressions;

    public PrintlnStatement(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitPrintStatementln(this);

    }
}
