package bytecode.visitors;

import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import ast.exprStatements.New;
import ast.exprStatements.Unary;
import ast.statements.*;
import bytecode.interfaces.IStatementBytecodeGenerator;
import org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class StatementBytecodeGenerator implements IStatementBytecodeGenerator {

    private final ExpressionBytecodeGenerator generator;
    private final MethodVisitor mv;
    private final Map<String, Integer> locals;

    public StatementBytecodeGenerator(ExpressionBytecodeGenerator generator,  MethodVisitor mv, Map<String, Integer> locals) {
        this.generator = generator;
        this.mv = mv;
        this.locals = locals;
    }


    @Override
    public void visitBlock(Block stmt) {

    }

    @Override
    public void visitBreak(Break stmt) {

    }

    @Override
    public void visitContinue(Continue stmt) {

    }

    @Override
    public void visitDoWhile(DoWhile stmt) {

    }

    @Override
    public void visitEmptyStatement(EmptyStatement stmt) {

    }

    @Override
    public void visitExpressionStatement(ExpressionStatement stmt) {

    }

    @Override
    public void visitFor(For stmt) {

    }

    @Override
    public void visitIf(If stmt) {

    }

    @Override
    public void visitLocalVarDecl(LocalVarDecl stmt) {

    }

    @Override
    public void visitReturn(Return stmt) {

    }

    @Override
    public void visitSwitchStatement(SwitchStatement stmt) {

    }

    @Override
    public void visitWhile(While stmt) {

    }

    @Override
    public void visitAssign(Assign stmt) {

    }

    @Override
    public void visitMethodCall(MethodCall stmt) {

    }

    @Override
    public void visitNew(New stmt) {

    }

    @Override
    public void visitUnary(Unary stmt) {

    }
}
