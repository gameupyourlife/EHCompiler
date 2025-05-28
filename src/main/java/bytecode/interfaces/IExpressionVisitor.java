package bytecode.interfaces;

import ast.expressions.*;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public interface IExpressionVisitor {
    void visitAssign(Assign expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitBooleanConst(BooleanConst expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitBooleanLiteral(BooleanLiteral expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitCharConst(CharConst expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitEmptyExpression(EmptyExpression expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitIdentifier(Identifier expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitIntConst(IntConst expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitMethodCall(MethodCall expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitNull(Null expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitSuper(Super expr, MethodVisitor mv, Map<String, Integer> locals);
    void visitThis(This expr, MethodVisitor mv, Map<String, Integer> locals);
}
