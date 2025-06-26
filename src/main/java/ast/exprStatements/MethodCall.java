package ast.exprStatements;

import ast.Class;
import ast.Expression;
import ast.MethodCallStatement;
import ast.Statement;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;
import java.util.ArrayList;
import java.util.List;

public class MethodCall implements Expression, MethodCallStatement {
    public Expression target;  // The object on which the method is called
    public String methodName;
    public List<Expression> arguments;
    
    public MethodCall() {
        this.arguments = new ArrayList<>();
    }
    
    public MethodCall(Expression target, String methodName, List<Expression> arguments) {
        this.target = target;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitMethodCall(this);
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return null;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {

    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor, List<Class> classes) {
        visitor.visitMethodCall(this, classes);
    }
}