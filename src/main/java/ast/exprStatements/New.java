package ast.exprStatements;

import ast.Expression;
import ast.Statement;
import ast.expressions.Identifier;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class New implements Expression, Statement {
    public Identifier objectName;

    public New(Identifier objectName) {
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

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }

}
