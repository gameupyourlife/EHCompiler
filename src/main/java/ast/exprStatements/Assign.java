package ast.exprStatements;

import ast.Expression;
import ast.Statement;
import ast.types.ITypeResolver;
import ast.types.Type;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import bytecode.interfaces.IStatementBytecodeGenerator;

import javax.swing.plaf.nimbus.State;

public class Assign implements Expression, Statement {
    public Expression target;
    public Expression value;
    
    public Assign() {}
    
    public Assign(Expression target, Expression value) {
        this.target = target;
        this.value = value;
    }

    @Override
    public void accept(IExpressionBytecodeGenerator visitor) {
        visitor.visitAssign(this);
    }

    @Override
    public void accept(IStatementBytecodeGenerator visitor) {
        visitor.visitAssign(this);
    }

    @Override
    public Type resolveType(ITypeResolver resolver) {
        // Implementierung von Statements fehlt noch
        return null;
    }
}