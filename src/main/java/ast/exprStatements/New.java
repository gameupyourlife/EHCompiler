package ast.exprStatements;

import ast.Expression;
import ast.Statement;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class New implements Expression, Statement {
    public String objectName;

    public New(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitNew(this);
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitNew(this);
    }
}
