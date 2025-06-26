package ast.expressions;

import ast.Expression;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class Identifier implements Expression {
    public String name;     // ← speichern, was resolveType zurückliefert

    public Identifier(String name) {
        this.name = name;
    }

    // ← Getter für den Namen
    public String getName() {
        return name;
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        Type t = resolver.resolve(this);
        return t;
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        // Falls Statements mit Identifiers produziert werden sollen
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitIdentifier(this);
    }
}
