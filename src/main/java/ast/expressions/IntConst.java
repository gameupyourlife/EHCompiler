package ast.expressions;

import ast.Expression;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;

public class IntConst implements Expression {
    public int value;
    
    public IntConst(int value) {
        this.value = value;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitIntConst(this);
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }
}