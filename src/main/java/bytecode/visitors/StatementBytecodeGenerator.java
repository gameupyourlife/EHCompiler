package bytecode.visitors;

import ast.statements.*;
import bytecode.interfaces.IStatementBytecodeGenerator;

public class StatementBytecodeGenerator implements IStatementBytecodeGenerator {
    private final ExpressionBytecodeGenerator generator;

    public StatementBytecodeGenerator(ExpressionBytecodeGenerator generator) {
        this.generator = generator;
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
}
