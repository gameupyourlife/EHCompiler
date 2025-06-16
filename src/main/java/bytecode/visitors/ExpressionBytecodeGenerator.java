package bytecode.visitors;

import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import ast.exprStatements.New;
import ast.exprStatements.Unary;
import ast.expressions.*;
import bytecode.interfaces.IExpressionBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;

public class ExpressionBytecodeGenerator implements IExpressionBytecodeGenerator {

    private final MethodVisitor mv;
    private final Map<String, Integer> locals;

    public ExpressionBytecodeGenerator(MethodVisitor mv, Map<String, Integer> locals) {
        this.mv = mv;
        this.locals = locals;
    }

    @Override
    public void visitBooleanConst(BooleanConst expr) {
        if (expr.value) {
            mv.visitInsn(Opcodes.ICONST_1);
        } else {
            mv.visitInsn(Opcodes.ICONST_0);
        }
    }

    @Override
    public void visitBooleanLiteral(BooleanLiteral expr) {
        if (expr.value) {
            mv.visitInsn(Opcodes.ICONST_1);
        } else {
            mv.visitInsn(Opcodes.ICONST_0);
        }
    }

    @Override
    public void visitCharConst(CharConst expr) {
        char value = expr.value;
        if (value <= 127) {
            mv.visitIntInsn(Opcodes.BIPUSH, (int) value);
        } else if (value <= 32767) {
            mv.visitIntInsn(Opcodes.SIPUSH, (int) value);
        } else {
            mv.visitLdcInsn(value);
        }
    }

    @Override
    public void visitEmptyExpression(EmptyExpression expr) {
        // no bytecode necessary
    }

    @Override
    public void visitIdentifier(Identifier expr) {
        // Je nach Typ muss anderer bytecode erstellt werden
        // -> SemantikCheck muss den Typ liefern
    }

    @Override
    public void visitIntConst(IntConst expr) {
        int value = expr.value;
        if (value >= -1 && value <= 5) {
            mv.visitInsn(Opcodes.ICONST_0 + value);
        } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.BIPUSH, value);
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.SIPUSH, value);
        } else {
            mv.visitLdcInsn(value);
        }
    }

    @Override
    public void visitNull(Null expr) {
        mv.visitInsn(Opcodes.ACONST_NULL);
    }

    @Override
    public void visitSuper(Super expr) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        // loads current instance like "this"
    }

    @Override
    public void visitThis(This expr) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
    }

    @Override
    public void visitBinary(Binary expr) {
        // Typ nötig
    }

    @Override
    public void visitUnary(Unary expr) {
        // Typ nötig
    }

    @Override
    public void visitAssign(Assign expr) {
        expr.accept(this);
        mv.visitInsn(Opcodes.DUP);
    }

    @Override
    public void visitMethodCall(MethodCall expr) {

    }

    @Override
    public void visitNew(New expr) {

    }
}
