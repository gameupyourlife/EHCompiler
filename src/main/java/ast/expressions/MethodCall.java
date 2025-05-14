package ast.expressions;

import ast.Expression;
import java.util.ArrayList;
import java.util.List;

public class MethodCall implements Expression {
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
}