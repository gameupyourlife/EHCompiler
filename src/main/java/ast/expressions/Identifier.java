package ast.expressions;

import ast.Expression;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class Identifier implements Expression {
    public String name;
    public String className;

    public Identifier(String name, String className) {
        this.name = name;
        this.className = className;
    }

    // ← Getter für den Namen
    public String getName() {
        return name;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitIdentifier(this);
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        return resolver.resolve(this);
    }
}