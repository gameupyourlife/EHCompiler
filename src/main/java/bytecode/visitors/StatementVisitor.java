package bytecode.visitors;

import ast.Type;
import ast.statements.*;
import bytecode.interfaces.IStatementVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;

public class StatementVisitor implements IStatementVisitor {
    private final ExpressionVisitor exprVisitor;

    public StatementVisitor(ExpressionVisitor exprVisitor) {
        this.exprVisitor = exprVisitor;
    }

    @Override
    public void visitBlock(Block stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitBreak(Break stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitContinue(Continue stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitDoWhile(While stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitEmptyStatement(EmptyStatement stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitExpressionStatement(ExpressionStatement stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitFor(For stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitIf(If stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitLocalVarDecl(LocalVarDecl stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitReturn(Return stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitSwitchStatement(SwitchStatement stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {

    }

    @Override
    public void visitWhile(While stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType) {
        Label startLabel = new Label();
        Label endLabel = new Label();

        mv.visitLabel(startLabel);

        stmt.condition.accept(exprVisitor, mv, locals);

        mv.visitJumpInsn(Opcodes.IFEQ, endLabel);

        stmt.statement.accept(this, mv, locals, returnType);

        mv.visitJumpInsn(Opcodes.GOTO, startLabel);

        mv.visitLabel(endLabel);
    }
}
