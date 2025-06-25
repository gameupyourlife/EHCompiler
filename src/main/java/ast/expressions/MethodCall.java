package ast.expressions;

import ast.AbstractExpression;
import ast.Expression;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class MethodCall extends AbstractExpression {
    public AbstractExpression target;  // The object on which the method is called
    public String methodName;
    public List<Expression> arguments;
    
    public MethodCall() {
        this.arguments = new ArrayList<>();
    }
    
    public MethodCall(AbstractExpression target, String methodName, List<Expression> arguments) {
        this.target = target;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {

    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {

    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return null;
    }
}