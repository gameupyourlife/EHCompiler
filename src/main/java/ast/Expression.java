package ast;

import bytecode.interfaces.IExpressionBytecodeGenerator;

public interface Expression {
    void accept(IExpressionBytecodeGenerator visitor);
}