package bytecode.interfaces;

import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import ast.exprStatements.New;
import ast.exprStatements.Unary;
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
    void visitUnary(Unary expr);
    void visitAssign(Assign expr);
    void visitMethodCall(MethodCall expr);
    void visitNew(New expr);
}
