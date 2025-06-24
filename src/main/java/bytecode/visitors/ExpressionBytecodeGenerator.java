package bytecode.visitors;

import ast.expressions.*;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class ExpressionBytecodeGenerator implements IExpressionBytecodeGenerator {

    MethodVisitor mv;
    Map<String, Integer> locals;

    @Override
    public void visitBooleanConst(BooleanConst expr) {

    }

    @Override
    public void visitBooleanLiteral(BooleanLiteral expr) {

    }

    @Override
    public void visitCharConst(CharConst expr) {

    }

    @Override
    public void visitEmptyExpression(EmptyExpression expr) {

    }

    @Override
    public void visitIdentifier(Identifier expr) {

    }

    @Override
    public void visitIntConst(IntConst expr) {

    }

    @Override
    public void visitNull(Null expr) {

    }

    @Override
    public void visitSuper(Super expr) {

    }

    @Override
    public void visitThis(This expr) {

    }

    @Override
    public void visitBinary(Binary expr) {
    
    }
}
