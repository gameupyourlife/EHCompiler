package ast.exprStatements;

import ast.Expression;
import ast.Statement;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class New implements Expression, Statement {
    public String objectName;

    public New(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }

}
