package ast;

import ast.types.Type;
import ast.types.ITypeResolver;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

/**
 * Basis-Klasse für alle Expressions.
 * Hier wird der Typ-Annotation eingeführt.
 */
public abstract class AbstractExpression implements Expression {
    /** Wird vom Semantic Checker gefüllt */
    private Type type;

    /** Rückgabe des zuvor berechneten Typs */
    public Type getType() {
        return type;
    }

    /** Speichern des vom Semantic Checker berechneten Typs */
    public void setType(Type type) {
        this.type = type;
    }

    public abstract void accept(IStatementBytecodeGenerator visitor);

    /** Muss von den konkreten Knoten implementiert werden */
    @Override
    public abstract void accept(IExpressionBytecodeGenerator visitor);

    /** Muss von den konkreten Knoten implementiert werden */
    @Override
    public abstract Type resolveType(ITypeResolver resolver);
}