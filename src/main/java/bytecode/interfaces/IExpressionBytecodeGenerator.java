package bytecode.interfaces;

import ast.expressions.*;

public interface IExpressionBytecodeGenerator {
    void visitBooleanConst(BooleanConst expr);
    void visitBooleanLiteral(BooleanLiteral expr);
    void visitCharConst(CharConst expr);
    void visitEmptyExpression(EmptyExpression expr);
    void visitIdentifier(Identifier expr);
    void visitIntConst(IntConst expr);
    void visitNull(Null expr);
    void visitSuper(Super expr);
    void visitThis(This expr);
    void visitBinary(Binary expr);
}
