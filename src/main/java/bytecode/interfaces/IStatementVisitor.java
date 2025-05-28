package bytecode.interfaces;

import ast.Type;
import ast.statements.*;
import org.objectweb.asm.MethodVisitor;
import java.util.Map;

public interface IStatementVisitor {
    void visitBlock(Block stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitBreak(Break stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitContinue(Continue stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitDoWhile(DoWhile stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitEmptyStatement(EmptyStatement stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitExpressionStatement(ExpressionStatement stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitFor(For stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitIf(If stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitLocalVarDecl(LocalVarDecl stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitReturn(Return stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitSwitchStatement(SwitchStatement stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
    void visitWhile(While stmt, MethodVisitor mv, Map<String, Integer> locals, Type returnType);
}