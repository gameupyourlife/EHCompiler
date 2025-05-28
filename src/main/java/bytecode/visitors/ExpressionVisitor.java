package bytecode.visitors;

import ast.expressions.*;
import bytecode.interfaces.IExpressionVisitor;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class ExpressionVisitor implements IExpressionVisitor {

    @Override
    public void visitAssign(Assign expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitBooleanConst(BooleanConst expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitBooleanLiteral(BooleanLiteral expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitCharConst(CharConst expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitEmptyExpression(EmptyExpression expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitIdentifier(Identifier expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitIntConst(IntConst expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitMethodCall(MethodCall expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitNull(Null expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitSuper(Super expr, MethodVisitor mv, Map<String, Integer> locals) {

    }

    @Override
    public void visitThis(This expr, MethodVisitor mv, Map<String, Integer> locals) {

    }
}
